package fi.hiq.info.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fi.hiq.info.service.InfoService;

@RestController
@RequestMapping(value="v1/info")
public class InfoController {
    
	@Autowired
	private InfoService infoService;
	
	
    @RequestMapping(value="/hello",method = RequestMethod.GET)
    public ResponseEntity<String> get() {
        return ResponseEntity.ok("Response from info-service");
    }
    
    @RequestMapping(value="/resource-message",method = RequestMethod.GET)
    public ResponseEntity<String> getMessageFromResourceService() {
    	return ResponseEntity.ok(this.infoService.getMessageFromResourceService());
    }
    
}
