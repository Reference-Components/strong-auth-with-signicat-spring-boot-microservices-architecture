package fi.hiq.identity.oidc.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class AuthRestController {
    
    @RequestMapping(value="/auth", method = RequestMethod.GET)
    public ResponseEntity<String> getHello() {
        return ResponseEntity.ok("Auth response");
    }
}
