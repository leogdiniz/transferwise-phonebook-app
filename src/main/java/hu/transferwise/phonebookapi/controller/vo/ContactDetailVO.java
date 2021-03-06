package hu.transferwise.phonebookapi.controller.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import hu.transferwise.phonebookapi.util.hashedid.jackson.IdHashDeserializer;
import hu.transferwise.phonebookapi.util.hashedid.jackson.IdHashSerializer;
import lombok.*;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactDetailVO {

    @NotNull
    @JsonSerialize(using = IdHashSerializer.class)
    @JsonDeserialize(using = IdHashDeserializer.class)
    private Long id;

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
