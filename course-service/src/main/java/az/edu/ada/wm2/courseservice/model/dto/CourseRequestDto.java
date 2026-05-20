package az.edu.ada.wm2.courseservice.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseRequestDto {

    @Schema(description = "Fennin adi", example = "Data Structures")
    @NotBlank(message = "Title is required")
    private String title;

    @Schema(description = "Fennin kodu", example = "CS201")
    @NotBlank(message = "Code is required")
    private String code;

    @Schema(description = "Kredit sayı", example = "4")
    @Positive(message = "Credits must be positive")
    private Integer credits;

    @Schema(description = "Əgər tələb olunursa, ilkin şərt olan fənnin identifikatoru", example = "1", nullable = true)
    @Positive(message = "Prerequisite course id must be positive")
    private Long prerequisiteCourseId;
}
