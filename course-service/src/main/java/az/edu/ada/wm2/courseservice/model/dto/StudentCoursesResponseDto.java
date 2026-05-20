package az.edu.ada.wm2.courseservice.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentCoursesResponseDto {

    @Schema(description = "Matched student")
    private StudentDto student;

    @Schema(description = "Courses associated with the matched student")
    private List<CourseEnrollmentSummaryDto> courses;
}
