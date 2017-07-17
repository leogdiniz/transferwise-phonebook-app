package hu.transferwise.phonebookapi.dao;

import hu.transferwise.phonebookapi.entity.ContactDetail;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ContactDetailDAOTest {

    @Autowired
    private ContactDetailDAO dao;

    @Before
    public void setUp(){
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

        dao.save(jonSmith);
        dao.save(jonSnow);
        dao.save(jonConnor);
    }

    @After
    public void cleanDB(){
        dao.deleteAll();
    }

    @Test
    public void findAll(){
        Page<ContactDetail> contactDetails = dao.findContactDetails(null, new PageRequest(0, 10));
        assertNotNull(contactDetails);
        assertEquals(3, contactDetails.getNumberOfElements());
    }

    @Test
    public void findByName(){
        Page<ContactDetail> contactDetails = dao.findContactDetails("jon", new PageRequest(0, 10));
        assertNotNull(contactDetails);
        assertEquals(3, contactDetails.getNumberOfElements());

        contactDetails = dao.findContactDetails("snow", new PageRequest(0, 10));
        assertNotNull(contactDetails);
        assertEquals(1, contactDetails.getNumberOfElements());
        Optional<ContactDetail> jonSnow = contactDetails.getContent().stream().findFirst();
        assertTrue(jonSnow.isPresent());
        assertEquals("Jon Snow", jonSnow.get().getName());
    }

    @Test
    public void findByEmail(){
        Page<ContactDetail> contactDetails = dao.findContactDetails("connor@", new PageRequest(0, 10));
        assertNotNull(contactDetails);
        assertEquals(1, contactDetails.getNumberOfElements());
        Optional<ContactDetail> jonConnor = contactDetails.getContent().stream().findFirst();
        assertTrue(jonConnor.isPresent());
        assertEquals( "connor@yahoo.hu", jonConnor.get().getEmail());
    }

    @Test
    public void findByJobTitle(){
        Page<ContactDetail> engineers = dao.findContactDetails("engi", new PageRequest(0, 10));
        assertNotNull(engineers);
        assertEquals(2, engineers.getNumberOfElements());
    }

    @Test
    public void findByCompany(){
        Page<ContactDetail> skynetEmployees = dao.findContactDetails("skynet", new PageRequest(0, 10));
        assertNotNull(skynetEmployees);
        assertEquals(1, skynetEmployees.getNumberOfElements());
    }

    @Test
    public void findAllPageSizeTwo(){
        Page<ContactDetail> contactDetails = dao.findContactDetails(null, new PageRequest(0, 2));
        assertNotNull(contactDetails);
        assertEquals(2, contactDetails.getNumberOfElements());
        assertEquals(2, contactDetails.getTotalPages());
    }

}
