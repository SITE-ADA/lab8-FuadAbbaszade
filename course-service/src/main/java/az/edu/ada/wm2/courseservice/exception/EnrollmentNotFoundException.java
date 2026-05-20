package az.edu.ada.wm2.courseservice.exception;

public class EnrollmentNotFoundException extends RuntimeException {

    public EnrollmentNotFoundException(Long courseId, Long studentId) {
        super("Enrollment was not found for course id " + courseId + " and student id " + studentId);
    }
}
