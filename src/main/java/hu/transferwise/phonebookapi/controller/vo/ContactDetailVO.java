package hu.transferwise.phonebookapi.controller.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import hu.transferwise.phonebookapi.util.hashedid.jackson.IdHashDeserializer;
import hu.transferwise.phonebookapi.util.hashedid.jackson.IdHashSerializer;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactDetailVO {

    @JsonSerialize(using = IdHashSerializer.class)
    @JsonDeserialize(using = IdHashDeserializer.class)
    private Long id;

    private String name;

    private String company;

    @JsonProperty("job-title")
    private String jobTitle;

    private String email;

    private String phone;

    private String notes;

}
