package com.example.springjerseyrest;

import org.glassfish.jersey.internal.util.Base64;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;
@Provider
public class AuthenticationFilter implements ContainerRequestFilter {


    @Context
    private ResourceInfo resourceInfo;

    private static final String AUTHORIZATION_PROPERTY = "Authorization";
    private static final String AUTHORIZATION_SCHEME = "Basic";
    private static final Response ACCESS_DENIED = Response.status(Response.Status.UNAUTHORIZED).
            entity("You are not authorized for this resource").build();
    private static final Response ACCESS_FORBIDDEN = Response.status(Response.Status.FORBIDDEN).
            entity("Access is blocked for all users").build();



    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {

        Method method = resourceInfo.getResourceMethod();

        // check if it's a universal resource
        if (!method.isAnnotationPresent(PermitAll.class)){

            //check if it is a forbidden resource
            if (method.isAnnotationPresent(DenyAll.class)){

                containerRequestContext.abortWith(ACCESS_FORBIDDEN);
                return;
            }

            //Get Request Headers
            final MultivaluedMap<String,String> headers = containerRequestContext.getHeaders();

            final List<String> authHeaders = headers.get(AUTHORIZATION_PROPERTY);

            // if no value for header param "authorization", access denied
            if (authHeaders == null || authHeaders.isEmpty()){
                containerRequestContext.abortWith(ACCESS_DENIED);
                return;
            }


            final String encodedUserPassword = authHeaders.get(0).replaceFirst(AUTHORIZATION_SCHEME + " ", "");

            final String userAndPassword = new String(Base64.decode(encodedUserPassword.getBytes()));

            final StringTokenizer tokenizer = new StringTokenizer(userAndPassword, ":");

            final String username = tokenizer.nextToken();
            final String password = tokenizer.nextToken();

            System.out.println("Username : "+ username);
            System.out.println("Username : " + password);

            //check if @RolesAllowed is present in the method
            if (method.isAnnotationPresent(RolesAllowed.class)){

                RolesAllowed rolesAllowed = method.getAnnotation(RolesAllowed.class);
                Set<String> roles = new HashSet<>(Arrays.asList(rolesAllowed.value()));

                //is user valid?
                if (!userValid(username,password,roles)){
                    containerRequestContext.abortWith(ACCESS_DENIED);
                    return;
                }
            }



        }


    }


    public boolean userValid(String username, String password, Set<String> roles){

        boolean valid = false;

        String tempRole = "ADMIN";

        if (roles.contains(tempRole) && "jes".equals(username) && "1234".equals(password)){

            valid = true;
        }

        return valid;

    }
}
