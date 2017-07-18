package hu.transferwise.phonebookapi.controller.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaveContactDetailVO {

    @NotBlank
    private String name;

    private String company;

    @JsonProperty("job_title")
    private String jobTitle;

    @Email
    private String email;

    private String phone;

    private String notes;

}
