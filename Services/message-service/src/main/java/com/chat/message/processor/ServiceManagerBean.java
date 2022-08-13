package com.chat.message.processor;

import com.chat.message.Builder.MessageBodyBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceManagerBean {

    @Bean
    public ServiceManager getServiceManager() {
        return new ServiceManager(new MessageBodyBuilder());
    }
}
