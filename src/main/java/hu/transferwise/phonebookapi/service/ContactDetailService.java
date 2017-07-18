package hu.transferwise.phonebookapi.service;

import hu.transferwise.phonebookapi.dao.ContactDetailDAO;
import hu.transferwise.phonebookapi.entity.ContactDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ContactDetailService {

    @Autowired
    private ContactDetailDAO contactDetailDAO;

    public ContactDetail findById(Long contactId){
        return contactDetailDAO.findOne(contactId);
    }

    public ContactDetail save(ContactDetail contactDetail){
        return contactDetailDAO.save(contactDetail);
    }

    public Page<ContactDetail> findContactDetails(String query, Pageable pageable){
        return contactDetailDAO.findContactDetails(query, pageable);
    }

    public void delete(Long contactId){
        contactDetailDAO.delete(contactId);
    }

}
