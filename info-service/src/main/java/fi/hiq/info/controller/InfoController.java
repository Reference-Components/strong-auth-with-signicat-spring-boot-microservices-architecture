package fi.hiq.info.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fi.hiq.info.dto.HelloResponseDTO;
import fi.hiq.info.service.InfoService;

@RestController
@RequestMapping
public class InfoController {
    
	@Autowired
	private InfoService infoService;
	
	
    @RequestMapping(value="/hello", method = RequestMethod.GET)
    public ResponseEntity<HelloResponseDTO> getHello() {
        return ResponseEntity.ok(new HelloResponseDTO("Response from info-service"));
    }
    
    @RequestMapping(value="/resource-message", method = RequestMethod.GET)
    public ResponseEntity<HelloResponseDTO> getMessageFromResourceService(@RequestHeader("Authorization") String token) {
    	return ResponseEntity.ok(this.infoService.getMessageFromResourceService(token));
    }

}
