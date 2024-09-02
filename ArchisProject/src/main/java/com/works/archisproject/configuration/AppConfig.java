package com.works.archisproject.configuration;

import org.modelmapper.ModelMapper;
import org.modelmapper.record.RecordModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class AppConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //Spring'e bir ModelMapper bean'i ekler ve bu sayede uygulama genelinde kolayca kullanılabilir.
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper().registerModule(new RecordModule());
    }

}
