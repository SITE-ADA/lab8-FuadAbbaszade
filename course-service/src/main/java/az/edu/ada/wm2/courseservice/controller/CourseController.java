package az.edu.ada.wm2.courseservice.controller;

import az.edu.ada.wm2.courseservice.model.dto.CourseRequestDto;
import az.edu.ada.wm2.courseservice.model.dto.CourseResponseDto;
import az.edu.ada.wm2.courseservice.model.dto.CoursesByStudentNameResponseDto;
import az.edu.ada.wm2.courseservice.model.dto.CourseStudentsResponseDto;
import az.edu.ada.wm2.courseservice.model.dto.EnrollmentStatusUpdateRequestDto;
import az.edu.ada.wm2.courseservice.model.dto.EnrollmentResponseDto;
import az.edu.ada.wm2.courseservice.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
@Validated
@Tag(name = "Courses", description = "Fənlərin idarə olunması və qeydiyyat əməliyyatları")
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    @Operation(summary = "Fənn yarat", description = "Yeni fənn yaradır.")
    public ResponseEntity<CourseResponseDto> createCourse(@Valid @RequestBody CourseRequestDto requestDto) {
        CourseResponseDto createdCourse = courseService.createCourse(requestDto);
        return new ResponseEntity<>(createdCourse, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Bütün fənləri gətir", description = "Sistemdə olan bütün fənləri qaytarır.")
    public ResponseEntity<List<CourseResponseDto>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Fənni id ilə gətir", description = "Verilmiş identifikatora uyğun bir fənni qaytarır.")
    public ResponseEntity<CourseResponseDto> getCourseById(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.getCourseById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Fənni yenilə", description = "Verilmiş identifikatora uyğun fənni yeniləyir.")
    public ResponseEntity<CourseResponseDto> updateCourse(
            @PathVariable Long id,
            @Valid @RequestBody CourseRequestDto requestDto) {
        return ResponseEntity.ok(courseService.updateCourse(id, requestDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Fənni sil", description = "Verilmiş identifikatora uyğun fənni silir.")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{courseId}/students/{studentId}")
    @Operation(
            summary = "Tələbəni fənnə yaz",
            description = "Tələbənin mövcudluğunu və ilkin şərtləri yoxladıqdan sonra onu fənnə qeydiyyatdan keçirir."
    )
    public ResponseEntity<EnrollmentResponseDto> enrollStudent(
            @PathVariable Long courseId,
            @PathVariable Long studentId) {
        EnrollmentResponseDto responseDto = courseService.enrollStudent(courseId, studentId);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PatchMapping("/{courseId}/students/{studentId}/status")
    @Operation(
            summary = "Qeydiyyat statusunu yenilə",
            description = "Tələbənin seçilmiş fənn üzrə qeydiyyat statusunu yeniləyir."
    )
    public ResponseEntity<EnrollmentResponseDto> updateEnrollmentStatus(
            @PathVariable Long courseId,
            @PathVariable Long studentId,
            @Valid @RequestBody EnrollmentStatusUpdateRequestDto requestDto) {
        return ResponseEntity.ok(courseService.updateEnrollmentStatus(courseId, studentId, requestDto.getStatus()));
    }

    @GetMapping("/{courseId}/students")
    @Operation(
            summary = "Fənn üzrə tələbələri gətir",
            description = "Seçilmiş fənnə yazılan tələbələri qeydiyyat tarixi və statusu ilə birlikdə qaytarır."
    )
    public ResponseEntity<CourseStudentsResponseDto> getCourseStudents(@PathVariable Long courseId) {
        return ResponseEntity.ok(courseService.getCourseStudents(courseId));
    }

    @GetMapping("/search/by-student-name")
    @Operation(
            summary = "Tələbə adına görə fənnləri axtar",
            description = "Tələbə adını student-service üzərindən axtarır və uyğun tələbələrin fənnlərini qruplaşdırılmış şəkildə qaytarır."
    )
    public ResponseEntity<CoursesByStudentNameResponseDto> getCoursesByStudentName(
            @RequestParam("name") @NotBlank(message = "Name is required") String name) {
        return ResponseEntity.ok(courseService.getCoursesByStudentName(name));
    }
}
