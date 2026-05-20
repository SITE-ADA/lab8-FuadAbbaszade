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
public class EnrolledStudentDto {

    @Schema(description = "Student id", example = "15")
    private Long id;

    @Schema(description = "Student first name", example = "Nicat")
    private String firstName;

    @Schema(description = "Student last name", example = "Aliyev")
    private String lastName;

    @Schema(description = "Student email", example = "nicat.aliyev@example.com")
    private String email;

    @Schema(description = "Student age", example = "20")
    private Integer age;

    @Schema(description = "Enrollment date", example = "2026-05-20")
    private LocalDate enrollmentDate;

    @Schema(description = "Enrollment status", example = "ACTIVE")
    private String status;
}
