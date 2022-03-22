package fi.hiq.resource.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fi.hiq.resource.dto.HelloResponseDTO;

@RestController
@RequestMapping
public class ResourceController {
	
	@RequestMapping(value="/hello",method = RequestMethod.GET)
	public ResponseEntity<HelloResponseDTO> get() {
		return ResponseEntity.ok(new HelloResponseDTO("Hello from resource-service"));
	}

}