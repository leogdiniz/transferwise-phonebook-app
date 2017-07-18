package hu.transferwise.phonebookapi.converter;


import hu.transferwise.phonebookapi.controller.vo.SaveContactDetailVO;
import hu.transferwise.phonebookapi.entity.ContactDetail;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SaveContactDetailVoToContactDetailConverter implements Converter<SaveContactDetailVO, ContactDetail> {

    @Override
    public ContactDetail convert(SaveContactDetailVO source) {
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
