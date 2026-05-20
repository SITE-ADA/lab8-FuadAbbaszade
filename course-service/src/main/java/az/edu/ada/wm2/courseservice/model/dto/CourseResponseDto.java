package az.edu.ada.wm2.courseservice.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseResponseDto {

    @Schema(description = "Fennin identifikatoru", example = "1")
    private Long id;

    @Schema(description = "Fennin adi", example = "Data Structures")
    private String title;

    @Schema(description = "Fennin kodu", example = "CS201")
    private String code;

    @Schema(description = "Kredit sayı", example = "4")
    private Integer credits;

    @Schema(description = "İlkin şərt olan fənnin identifikatoru", example = "1", nullable = true)
    private Long prerequisiteCourseId;
}
