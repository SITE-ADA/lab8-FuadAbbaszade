package az.edu.ada.wm2.courseservice.model.dto;

import az.edu.ada.wm2.courseservice.model.entity.EnrollmentStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentStatusUpdateRequestDto {

    @Schema(description = "Qeydiyyat üçün yeni status", example = "COMPLETED")
    @NotNull(message = "Status is required")
    private EnrollmentStatus status;
}
