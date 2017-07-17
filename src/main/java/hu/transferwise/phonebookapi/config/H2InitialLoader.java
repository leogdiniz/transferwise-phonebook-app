package hu.transferwise.phonebookapi.config;

import hu.transferwise.phonebookapi.dao.ContactDetailDAO;
import hu.transferwise.phonebookapi.entity.ContactDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("default")
public class H2InitialLoader implements ApplicationRunner{

    @Autowired
    private ContactDetailDAO contactDetailDAO;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        ContactDetail jonSmith = ContactDetail.builder()
                .name("Jon Smith")
                .company("Transferwise")
                .jobTitle("Engineer")
                .build();

        ContactDetail jonSnow = ContactDetail.builder()
                .name("Jon Snow")
                .email("snow@gmail.com")
                .jobTitle("Engineer")
                .notes("The king of the north")
                .build();

        ContactDetail jonConnor = ContactDetail.builder()
                .name("Jon Connor")
                .email("connor@yahoo.hu")
                .company("Skynet")
                .jobTitle("Developer")
                .build();

        contactDetailDAO.save(jonSmith);
        contactDetailDAO.save(jonSnow);
        contactDetailDAO.save(jonConnor);
    }
}
