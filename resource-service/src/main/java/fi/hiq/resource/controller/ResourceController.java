package fi.hiq.resource.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="v1/resource")
public class ResourceController {
	
	@RequestMapping(value="/hello",method = RequestMethod.GET)
	public ResponseEntity<String> get() {
		return ResponseEntity.ok("Hello from resource-service");
	}

}