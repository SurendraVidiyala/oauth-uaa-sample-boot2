package org.bk.web;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class GreetingsController {
    
    
    private final OAuth2RestTemplate clientCredsOAuth2RestTemplate;

    public GreetingsController(OAuth2RestTemplate clientCredsOAuth2RestTemplate) {
        this.clientCredsOAuth2RestTemplate = clientCredsOAuth2RestTemplate;
    }

    @PreAuthorize("#oauth2.hasScope('resource.read')")
    @RequestMapping(method = RequestMethod.GET, value = "/secured/read")
    @ResponseBody
    public String read(Authentication authentication) {
        return String.format("Read Called: Hello %s", authentication.getCredentials());
    }

    @PreAuthorize("#oauth2.hasScope('resource.write')")
    @RequestMapping(method = RequestMethod.GET, value = "/secured/write")
    @ResponseBody
    public String write(Authentication authentication) {
        ResponseEntity<String> responseEntity = this.clientCredsOAuth2RestTemplate.exchange("http://localhost:8081/_search", HttpMethod.GET, new HttpEntity<String>(new HttpHeaders()), String.class);
        return String.format("Write Called: Hello %s", authentication.getCredentials() + ": " + responseEntity.getBody());
    }
    
    
}
