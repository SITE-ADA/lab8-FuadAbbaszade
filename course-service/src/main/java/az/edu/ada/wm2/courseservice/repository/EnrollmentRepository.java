package az.edu.ada.wm2.courseservice.repository;

import az.edu.ada.wm2.courseservice.model.entity.Enrollment;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    boolean existsByCourseIdAndStudentId(Long courseId, Long studentId);

    List<Enrollment> findByCourseId(Long courseId);

    List<Enrollment> findByStudentId(Long studentId);

    List<Enrollment> findByStudentIdAndCourseId(Long studentId, Long courseId);

    Optional<Enrollment> findByCourseIdAndStudentId(Long courseId, Long studentId);
}
