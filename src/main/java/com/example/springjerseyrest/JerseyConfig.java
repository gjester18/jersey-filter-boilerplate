package com.example.springjerseyrest;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class JerseyConfig extends ResourceConfig {


    public JerseyConfig(){
        register(AuthenticationFilter.class);

        register(UserController.class);
    }

}
