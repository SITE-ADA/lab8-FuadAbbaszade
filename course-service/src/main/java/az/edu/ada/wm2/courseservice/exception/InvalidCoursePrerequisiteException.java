package az.edu.ada.wm2.courseservice.exception;

public class InvalidCoursePrerequisiteException extends RuntimeException {

    public InvalidCoursePrerequisiteException(Long courseId) {
        super("Course id " + courseId + " cannot be its own prerequisite.");
    }
}
