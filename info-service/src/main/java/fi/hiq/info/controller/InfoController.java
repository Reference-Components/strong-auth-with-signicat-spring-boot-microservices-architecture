package fi.hiq.info.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="v1/info")
public class InfoController {
    
    @RequestMapping(value="/hello",method = RequestMethod.GET)
    public ResponseEntity<String> get() {
        return ResponseEntity.ok("Response from info-service");
    }

}
