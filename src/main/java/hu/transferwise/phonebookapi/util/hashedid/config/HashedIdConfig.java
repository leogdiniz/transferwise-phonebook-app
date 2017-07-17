package hu.transferwise.phonebookapi.util.hashedid.config;

import org.hashids.Hashids;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HashedIdConfig {

    @Value("${hu.transferwise.hashedid.salt}")
    private String salt;
    private String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

    @Bean
    public Hashids hashIds() {
        return new Hashids(salt, 10, alphabet);
    }
}
