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
public class CoursesByStudentNameResponseDto {

    @Schema(description = "Search query", example = "ali")
    private String query;

    @Schema(description = "Grouped course results for all matched students")
    private List<StudentCoursesResponseDto> results;
}
