package hu.transferwise.phonebookapi.util.hashedid.converter;


import hu.transferwise.phonebookapi.util.hashedid.HashedId;
import hu.transferwise.phonebookapi.util.hashedid.generator.HashedIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LongToHashedIdConverter implements Converter<Long, HashedId> {

    @Autowired
    private HashedIdGenerator hashedIdGenerator;

    @Override
    public HashedId convert(Long id) {
        return new HashedId(id, hashedIdGenerator.toHash(id));
    }

}