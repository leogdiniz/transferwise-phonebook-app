package hu.transferwise.phonebookapi.converter;


import hu.transferwise.phonebookapi.controller.vo.ContactDetailVO;
import hu.transferwise.phonebookapi.entity.ContactDetail;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ContactDetailVoToContactDetailConverter implements Converter<ContactDetailVO, ContactDetail> {

    @Override
    public ContactDetail convert(ContactDetailVO source) {
        return ContactDetail.builder()
                .name(source.getName())
                .company(source.getCompany())
                .email(source.getEmail())
                .jobTitle(source.getJobTitle())
                .phone(source.getPhone())
                .notes(source.getNotes())
                .build();
    }
}
