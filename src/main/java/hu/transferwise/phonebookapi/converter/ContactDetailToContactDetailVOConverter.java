package hu.transferwise.phonebookapi.converter;


import hu.transferwise.phonebookapi.controller.vo.ContactDetailVO;
import hu.transferwise.phonebookapi.entity.ContactDetail;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ContactDetailToContactDetailVOConverter implements Converter<ContactDetail, ContactDetailVO> {

    @Override
    public ContactDetailVO convert(ContactDetail source) {
        return ContactDetailVO.builder()
                .id(source.getId())
                .name(source.getName())
                .company(source.getCompany())
                .email(source.getEmail())
                .jobTitle(source.getJobTitle())
                .phone(source.getPhone())
                .notes(source.getNotes())
                .build();
    }
}
