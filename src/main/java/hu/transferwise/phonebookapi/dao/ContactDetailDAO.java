package hu.transferwise.phonebookapi.dao;

import hu.transferwise.phonebookapi.entity.ContactDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactDetailDAO extends JpaRepository<ContactDetail, Long> {

    /**
     * Find the contact details based on the query string.
     * This string will be used to search by the contact' name, company, email and job title
     * @param query - The query string that will be used to search
     * @param pageable - The pageable object that will be used sort and define the pages
     *
     * @return A page object with the contact details that matches the query
     */
    @Query("SELECT c FROM ContactDetail c " +
            "WHERE :queryString IS NULL " +
            "OR " +
            "(" +
                "UPPER(c.name) LIKE CONCAT('%', UPPER(:queryString), '%') " +
                "OR UPPER(c.company) LIKE CONCAT('%', UPPER(:queryString), '%') " +
                "OR UPPER(c.jobTitle) LIKE CONCAT('%', UPPER(:queryString), '%') " +
                "OR UPPER(c.email) LIKE CONCAT('%', UPPER(:queryString), '%') " +
            ")")
    Page<ContactDetail> findContactDetails(@Param("queryString") String query, Pageable pageable);
}
