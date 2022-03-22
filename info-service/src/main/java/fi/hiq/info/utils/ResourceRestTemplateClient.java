package fi.hiq.info.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import fi.hiq.info.dto.HelloResponseDTO;

@Component
public class ResourceRestTemplateClient {

	@Autowired
	RestTemplate restTemplate;
	
	public HelloResponseDTO getResourceMessage(String token){
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", token);

		HttpEntity<Void> entityReq = new HttpEntity<>(headers);
		
        ResponseEntity<HelloResponseDTO> exchange = 
                restTemplate.exchange(
                        "http://resource-service/hello",
                        HttpMethod.GET,
                        entityReq, HelloResponseDTO.class);

        return exchange.getBody();
    }
}
