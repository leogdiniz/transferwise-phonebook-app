package hu.transferwise.phonebookapi.util.hashedid.generator;

import hu.transferwise.phonebookapi.util.hashedid.exception.HashedIdException;
import org.hashids.Hashids;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class HashedIdGenerator {

    @Autowired
    private Hashids hashids;

    public HashedIdGenerator(Hashids hashids) {
        this.hashids = hashids;
    }

    public String toHash(Long id) throws HashedIdException {
        try {
            return hashids.encode(id);
        } catch (Exception e) {
            throw new HashedIdException("Error trying to encode id [" + id + "]", e);
        }
    }

    public Long toId(String hash) throws HashedIdException {
        if (StringUtils.isEmpty(hash)) {
            throw new HashedIdException("Error trying to decode hash. The hash must not be empty or null." );
        }

        long[] decoded;

        try {
            decoded = hashids.decode(hash);
        } catch (Exception e) {
            throw new HashedIdException("Error trying to decode hash [" + hash + "]", e);
        }

        if (decoded != null && decoded.length > 0) {
            StringBuilder decodedString = new StringBuilder();
            for (long digit : decoded) {
                decodedString.append(digit);
            }
            return Long.valueOf(decodedString.toString());
        } else {
            throw new HashedIdException("Error trying to decode hash [" + hash + "]. Invalid hash.");
        }
    }
}
