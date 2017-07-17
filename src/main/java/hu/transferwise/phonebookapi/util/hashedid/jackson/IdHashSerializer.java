package hu.transferwise.phonebookapi.util.hashedid.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import hu.transferwise.phonebookapi.util.hashedid.generator.HashedIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class IdHashSerializer extends JsonSerializer<Long> {

    @Autowired
    private HashedIdGenerator hashedIdGenerator;

    @Override
    public void serialize(Long id, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if(id != null) {
            gen.writeString(hashedIdGenerator.toHash(id));
        }
    }
}
