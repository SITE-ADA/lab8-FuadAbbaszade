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

    @Schema(description = "Axtarışa uyğun gələn tələbə məlumatı")
    private StudentDto student;

    @Schema(description = "Həmin tələbəyə aid fənlər və qeydiyyat məlumatları")
    private List<CourseEnrollmentSummaryDto> courses;
}
