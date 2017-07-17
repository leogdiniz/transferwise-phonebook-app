package hu.transferwise.phonebookapi.controller;

import hu.transferwise.phonebookapi.controller.vo.ContactDetailVO;
import hu.transferwise.phonebookapi.controller.vo.SearchVO;
import hu.transferwise.phonebookapi.converter.ContactDetailToContactDetailVOConverter;
import hu.transferwise.phonebookapi.converter.ContactDetailVoToContactDetailConverter;
import hu.transferwise.phonebookapi.entity.ContactDetail;
import hu.transferwise.phonebookapi.service.ContactDetailService;
import hu.transferwise.phonebookapi.util.hashedid.HashedId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/v1/contact")
public class ContactDetailController {

    @Autowired
    private ContactDetailService contactDetailService;

    @Autowired
    private ContactDetailToContactDetailVOConverter contactDetailVOConverter;

    @Autowired
    private ContactDetailVoToContactDetailConverter contactDetailConverter;

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ResponseEntity<?> search(SearchVO searchVO){
        PageRequest pageable = new PageRequest(searchVO.getPage(), searchVO.getPageSize());
        Page<ContactDetail> contactDetails = contactDetailService.findContactDetails(searchVO.getQuery(), pageable);

        return ResponseEntity.ok(contactDetails.map(contactDetailVOConverter));
    }

    @RequestMapping(value = "/{hashedId}", method = RequestMethod.GET)
    public ResponseEntity<?> show(@PathParam("hashedId") HashedId hashedId){
        ContactDetail contactDetail = contactDetailService.findById(hashedId.getId());
        if (contactDetail == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(contactDetailVOConverter.convert(contactDetail));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> save(@RequestBody ContactDetailVO contactDetailVO){

        ContactDetail contactDetail = contactDetailService.save(contactDetailConverter.convert(contactDetailVO));

        return ResponseEntity.ok(contactDetailVOConverter.convert(contactDetail));
    }
}
