package hu.transferwise.phonebookapi.entity;

import lombok.*;
import org.hibernate.validator.constraints.Email;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class ContactDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    private String company;

    private String jobTitle;

    @Email
    private String email;

    private String phone;

    private String notes;
}
