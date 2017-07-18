package hu.transferwise.phonebookapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.transferwise.phonebookapi.controller.vo.ContactDetailVO;
import hu.transferwise.phonebookapi.controller.vo.SaveContactDetailVO;
import hu.transferwise.phonebookapi.converter.ContactDetailToContactDetailVOConverter;
import hu.transferwise.phonebookapi.converter.ContactDetailVoToContactDetailConverter;
import hu.transferwise.phonebookapi.converter.SaveContactDetailVoToContactDetailConverter;
import hu.transferwise.phonebookapi.entity.ContactDetail;
import hu.transferwise.phonebookapi.service.ContactDetailService;
import hu.transferwise.phonebookapi.util.hashedid.converter.LongToHashedIdConverter;
import hu.transferwise.phonebookapi.util.hashedid.converter.StringToHashedIdConverter;
import hu.transferwise.phonebookapi.util.hashedid.generator.HashedIdGenerator;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(value = ContactDetailController.class)
public class ContactDetailControllerTest {

    private String url = "/v1/contact";

    @Autowired
    protected MockMvc mvc;

    @MockBean
    private ContactDetailService contactDetailService;

    @SpyBean
    private ObjectMapper objectMapper;

    @SpyBean
    private ContactDetailToContactDetailVOConverter contactDetailVOConverter;

    @SpyBean
    private ContactDetailVoToContactDetailConverter contactDetailVoToContactDetailConverter;

    @SpyBean
    private SaveContactDetailVoToContactDetailConverter saveContactDetailVoToContactDetailConverter;

    @SpyBean
    private StringToHashedIdConverter stringToIdHashConverter;

    @SpyBean
    private LongToHashedIdConverter longToIdHashConverter;

    @MockBean
    private HashedIdGenerator hashedIdGenerator;

    @Before
    public void setup() {
        when(hashedIdGenerator.toHash(anyLong())).then((InvocationOnMock i) -> {
            Long id = i.getArgumentAt(0, Long.class);
            return id + "_hash";
        });

        when(hashedIdGenerator.toId(anyString())).then((InvocationOnMock i) -> {
            return new Long(i.getArgumentAt(0, String.class).replace("_hash", ""));
        });
    }

    @Test
    public void show() throws Exception {
        long id = 1L;
        String hashedId = "1_hash";
        String company = "Company name";
        String email = "email@email.com";
        String jobTitle = "job title";
        when(contactDetailService.findById(id))
                .thenReturn(ContactDetail.builder()
                        .id(id)
                        .company(company)
                        .email(email)
                        .jobTitle(jobTitle)
                        .build()
                );

        mvc.perform(
                get(url + "/" + hashedId).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.*", Matchers.hasSize(7)))
                .andExpect(jsonPath("$.id").value(hashedId))
                .andExpect(jsonPath("$.company").value(company))
                .andExpect(jsonPath("$.email").value(email))
                .andExpect(jsonPath("$.job_title").value(jobTitle));

    }

    @Test
    public void showContactNotFound() throws Exception {
        mvc.perform(
                get(url + "/1_hash").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());
    }

    @Test
    public void saveContact() throws Exception {
        when(contactDetailService.save(any(ContactDetail.class)))
                .then((i) -> {
                            ContactDetail contactDetail = i.getArgumentAt(0, ContactDetail.class);
                            contactDetail.setId(1L);
                            return contactDetail;
                        }
                );
        String name = "Contact name";
        String company = "Company name";
        String email = "email@email.com";
        String jobTitle = "job title";

        SaveContactDetailVO saveContactDetailVO = SaveContactDetailVO.builder()
                .name(name)
                .company(company)
                .email(email)
                .jobTitle(jobTitle)
                .build();

        MockHttpServletRequestBuilder resquest = post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(saveContactDetailVO));

        mvc.perform(resquest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", Matchers.hasSize(7)))
                .andExpect(jsonPath("$.id").value("1_hash"))
                .andExpect(jsonPath("$.company").value(company))
                .andExpect(jsonPath("$.email").value(email))
                .andExpect(jsonPath("$.job_title").value(jobTitle));
    }

    @Test
    public void saveContactWrongEmail() throws Exception {
        SaveContactDetailVO saveContactDetailVO = SaveContactDetailVO.builder()
                .name("Name")
                .email("invalidEmail")
                .build();

        MockHttpServletRequestBuilder request = post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(saveContactDetailVO));

        mvc.perform(request)
                .andExpect(status().isBadRequest());

    }

    @Test
    public void saveContactBlankName() throws Exception {
        SaveContactDetailVO saveContactDetailVO = SaveContactDetailVO.builder()
                .company("Company")
                .build();

        MockHttpServletRequestBuilder request = post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(saveContactDetailVO));

        mvc.perform(request)
                .andExpect(status().isBadRequest());

    }

    @Test
    public void updateContactName() throws Exception {
        long id = 1L;
        String company = "Company name";
        String email = "email@email.com";
        String jobTitle = "job title";
        when(contactDetailService.findById(id))
                .thenReturn(ContactDetail.builder()
                        .name("My name")
                        .id(id)
                        .company(company)
                        .email(email)
                        .jobTitle(jobTitle)
                        .build()
                );

        when(contactDetailService.save(any(ContactDetail.class))).then(i -> i.getArgumentAt(0, ContactDetail.class));

        String name = "Jon Smith";
        ContactDetailVO vo = ContactDetailVO.builder()
                .name(name)
                .id(id)
                .company(company)
                .email(email)
                .jobTitle(jobTitle)
                .build();

        mvc.perform(
                put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(vo))
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.*", Matchers.hasSize(7)))
                .andExpect(jsonPath("$.id").value("1_hash"))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.company").value(company))
                .andExpect(jsonPath("$.email").value(email))
                .andExpect(jsonPath("$.job_title").value(jobTitle));
    }

    @Test
    public void updateContactNotFound() throws Exception {
        ContactDetailVO vo = ContactDetailVO.builder()
                .id(1L)
                .name("Name")
                .build();

        mvc.perform(
                put(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(vo))
        ).andExpect(status().isNotFound());
    }
}
