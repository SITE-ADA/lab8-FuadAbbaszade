package az.edu.ada.wm2.courseservice.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseEnrollmentSummaryDto {

    @Schema(description = "Fennin identifikatoru", example = "1")
    private Long courseId;

    @Schema(description = "Fennin adi", example = "Data Structures")
    private String title;

    @Schema(description = "Fennin kodu", example = "CS201")
    private String code;

    @Schema(description = "Kredit sayı", example = "4")
    private Integer credits;

    @Schema(description = "Qeydiyyat tarixi", example = "2026-05-20")
    private LocalDate enrollmentDate;

    @Schema(description = "Qeydiyyat statusu", example = "ACTIVE")
    private String status;
}
