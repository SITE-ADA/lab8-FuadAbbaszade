package az.edu.ada.wm2.courseservice.service;

import az.edu.ada.wm2.courseservice.client.StudentFeignClient;
import az.edu.ada.wm2.courseservice.exception.CourseNotFoundException;
import az.edu.ada.wm2.courseservice.exception.EnrollmentAlreadyExistsException;
import az.edu.ada.wm2.courseservice.exception.EnrollmentNotFoundException;
import az.edu.ada.wm2.courseservice.exception.InvalidCoursePrerequisiteException;
import az.edu.ada.wm2.courseservice.exception.PrerequisiteNotSatisfiedException;
import az.edu.ada.wm2.courseservice.exception.RemoteStudentNotFoundException;
import az.edu.ada.wm2.courseservice.exception.StudentServiceCommunicationException;
import az.edu.ada.wm2.courseservice.model.dto.CourseRequestDto;
import az.edu.ada.wm2.courseservice.model.dto.CourseResponseDto;
import az.edu.ada.wm2.courseservice.model.dto.CourseStudentsResponseDto;
import az.edu.ada.wm2.courseservice.model.dto.EnrolledStudentDto;
import az.edu.ada.wm2.courseservice.model.dto.EnrollmentStatusUpdateRequestDto;
import az.edu.ada.wm2.courseservice.model.dto.EnrollmentResponseDto;
import az.edu.ada.wm2.courseservice.model.dto.StudentDto;
import az.edu.ada.wm2.courseservice.model.entity.Course;
import az.edu.ada.wm2.courseservice.model.entity.Enrollment;
import az.edu.ada.wm2.courseservice.model.entity.EnrollmentStatus;
import az.edu.ada.wm2.courseservice.repository.CourseRepository;
import az.edu.ada.wm2.courseservice.repository.EnrollmentRepository;
import feign.FeignException;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourseService {

    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final StudentFeignClient studentFeignClient;
    private final RestTemplate restTemplate;

    @Value("${student.service.base-url}")
    private String studentServiceBaseUrl;

    public CourseResponseDto createCourse(CourseRequestDto requestDto) {
        validatePrerequisiteReference(requestDto.getPrerequisiteCourseId(), null);

        Course course = Course.builder()
                .title(requestDto.getTitle())
                .code(requestDto.getCode())
                .credits(requestDto.getCredits())
                .prerequisiteCourseId(requestDto.getPrerequisiteCourseId())
                .build();

        Course savedCourse = courseRepository.save(course);
        return toCourseResponseDto(savedCourse);
    }

    public List<CourseResponseDto> getAllCourses() {
        return courseRepository.findAll()
                .stream()
                .map(this::toCourseResponseDto)
                .toList();
    }

    public CourseResponseDto getCourseById(Long id) {
        Course course = findCourseOrThrow(id);
        return toCourseResponseDto(course);
    }

    public CourseResponseDto updateCourse(Long id, CourseRequestDto requestDto) {
        Course existingCourse = findCourseOrThrow(id);
        validatePrerequisiteReference(requestDto.getPrerequisiteCourseId(), id);

        existingCourse.setTitle(requestDto.getTitle());
        existingCourse.setCode(requestDto.getCode());
        existingCourse.setCredits(requestDto.getCredits());
        existingCourse.setPrerequisiteCourseId(requestDto.getPrerequisiteCourseId());

        Course updatedCourse = courseRepository.save(existingCourse);
        return toCourseResponseDto(updatedCourse);
    }

    public void deleteCourse(Long id) {
        Course course = findCourseOrThrow(id);
        courseRepository.delete(course);
    }

    public EnrollmentResponseDto enrollStudent(Long courseId, Long studentId) {
        log.debug("Enrolling student {} into course {}", studentId, courseId);
        Course course = findCourseOrThrow(courseId);

        if (enrollmentRepository.existsByCourseIdAndStudentId(courseId, studentId)) {
            throw new EnrollmentAlreadyExistsException(courseId, studentId);
        }

        validateStudentWithFeign(studentId);
        validatePrerequisiteEligibility(course, studentId);

        Enrollment enrollment = Enrollment.builder()
                .courseId(courseId)
                .studentId(studentId)
                .enrollmentDate(LocalDate.now())
                .status(EnrollmentStatus.ACTIVE)
                .build();
        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);

        return toEnrollmentResponseDto(savedEnrollment);
    }

    public EnrollmentResponseDto updateEnrollmentStatus(Long courseId, Long studentId, EnrollmentStatusUpdateRequestDto requestDto) {
        Enrollment enrollment = enrollmentRepository.findByCourseIdAndStudentId(courseId, studentId)
                .orElseThrow(() -> new EnrollmentNotFoundException(courseId, studentId));

        enrollment.setStatus(requestDto.getStatus());
        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);

        return new EnrollmentResponseDto(
                savedEnrollment.getId(),
                savedEnrollment.getCourseId(),
                savedEnrollment.getStudentId(),
                savedEnrollment.getEnrollmentDate(),
                savedEnrollment.getStatus().name(),
                "Enrollment status updated successfully."
        );
    }

    public CourseStudentsResponseDto getCourseStudents(Long courseId) {
        log.debug("Fetching students for course {}", courseId);
        Course course = findCourseOrThrow(courseId);

        List<EnrolledStudentDto> students = enrollmentRepository.findByCourseId(courseId)
                .stream()
                .map(this::toEnrolledStudentDto)
                .toList();

        return new CourseStudentsResponseDto(course.getId(), course.getTitle(), students);
    }

    private void validateStudentWithFeign(Long studentId) {
        try {
            log.debug("Validating student {} via Feign", studentId);
            studentFeignClient.getStudentById(studentId);
        } catch (FeignException.NotFound ex) {
            throw new RemoteStudentNotFoundException(studentId);
        } catch (FeignException ex) {
            throw new StudentServiceCommunicationException("Could not validate student-service response.");
        }
    }

    private StudentDto fetchStudentWithRestTemplate(Long studentId) {
        String url = studentServiceBaseUrl + "/api/v1/students/" + studentId;
        try {
            log.debug("Fetching student {} via RestTemplate", studentId);
            return restTemplate.getForObject(url, StudentDto.class);
        } catch (HttpClientErrorException.NotFound ex) {
            throw new RemoteStudentNotFoundException(studentId);
        } catch (RestClientException ex) {
            throw new StudentServiceCommunicationException("Could not fetch student details from student-service.");
        }
    }

    private Course findCourseOrThrow(Long id) {
        log.debug("Looking up course {}", id);
        return courseRepository.findById(id).orElseThrow(() -> new CourseNotFoundException(id));
    }

    private void validatePrerequisiteReference(Long prerequisiteCourseId, Long currentCourseId) {
        if (prerequisiteCourseId == null) {
            return;
        }

        if (currentCourseId != null && prerequisiteCourseId.equals(currentCourseId)) {
            throw new InvalidCoursePrerequisiteException(currentCourseId);
        }

        findCourseOrThrow(prerequisiteCourseId);
    }

    private void validatePrerequisiteEligibility(Course course, Long studentId) {
        Long prerequisiteCourseId = course.getPrerequisiteCourseId();
        if (prerequisiteCourseId == null) {
            return;
        }

        boolean prerequisiteSatisfied = enrollmentRepository.findByStudentIdAndCourseId(studentId, prerequisiteCourseId)
                .stream()
                .map(Enrollment::getStatus)
                .anyMatch(status -> status == EnrollmentStatus.ACTIVE || status == EnrollmentStatus.COMPLETED);

        if (!prerequisiteSatisfied) {
            throw new PrerequisiteNotSatisfiedException(studentId, course.getId(), prerequisiteCourseId);
        }
    }

    private EnrolledStudentDto toEnrolledStudentDto(Enrollment enrollment) {
        StudentDto student = fetchStudentWithRestTemplate(enrollment.getStudentId());
        return new EnrolledStudentDto(
                student.getId(),
                student.getFirstName(),
                student.getLastName(),
                student.getEmail(),
                student.getAge(),
                enrollment.getEnrollmentDate(),
                enrollment.getStatus().name()
        );
    }

    private EnrollmentResponseDto toEnrollmentResponseDto(Enrollment enrollment) {
        return new EnrollmentResponseDto(
                enrollment.getId(),
                enrollment.getCourseId(),
                enrollment.getStudentId(),
                enrollment.getEnrollmentDate(),
                enrollment.getStatus().name(),
                "Student enrolled successfully."
        );
    }

    private CourseResponseDto toCourseResponseDto(Course course) {
        return new CourseResponseDto(
                course.getId(),
                course.getTitle(),
                course.getCode(),
                course.getCredits(),
                course.getPrerequisiteCourseId()
        );
    }
}
