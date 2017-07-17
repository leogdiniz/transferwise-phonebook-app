package hu.transferwise.phonebookapi.util.hashedid.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import hu.transferwise.phonebookapi.util.hashedid.generator.HashedIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class IdHashDeserializer extends JsonDeserializer<Long> {

    @Autowired
    private HashedIdGenerator hashedIdGenerator;

    @Override
    public Long deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.readValueAsTree();

        return hashedIdGenerator.toId(node.asText());
    }
}
