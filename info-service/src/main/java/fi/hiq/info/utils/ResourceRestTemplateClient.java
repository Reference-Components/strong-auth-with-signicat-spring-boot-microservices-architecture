package fi.hiq.info.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ResourceRestTemplateClient {

	@Autowired
	RestTemplate restTemplate;
	
	public String getResourceMessage(String token){
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", token);

		HttpEntity<Void> entityReq = new HttpEntity<>(headers);
		
        ResponseEntity<String> exchange =
                restTemplate.exchange(
                        "http://resource-service/v1/resource/hello",
                        HttpMethod.GET,
                        entityReq, String.class);

        return exchange.getBody();
    }
}
