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
public class CourseStudentsResponseDto {

    @Schema(description = "Fennin identifikatoru", example = "1")
    private Long courseId;

    @Schema(description = "Fennin adi", example = "Data Structures")
    private String courseTitle;

    @Schema(description = "Bu fənnə yazılmış tələbələr və onların qeydiyyat məlumatları")
    private List<EnrolledStudentDto> students;
}
