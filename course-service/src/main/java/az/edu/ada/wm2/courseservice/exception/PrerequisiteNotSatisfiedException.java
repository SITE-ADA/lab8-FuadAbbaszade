package az.edu.ada.wm2.courseservice.exception;

public class PrerequisiteNotSatisfiedException extends RuntimeException {

    public PrerequisiteNotSatisfiedException(Long studentId, Long courseId, Long prerequisiteCourseId) {
        super("Student id " + studentId + " must be actively enrolled in or have completed prerequisite course id "
                + prerequisiteCourseId + " before enrolling in course id " + courseId);
    }
}
