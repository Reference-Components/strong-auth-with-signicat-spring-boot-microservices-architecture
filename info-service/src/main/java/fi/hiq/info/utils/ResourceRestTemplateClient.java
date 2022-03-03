package fi.hiq.info.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ResourceRestTemplateClient {

	@Autowired
	RestTemplate restTemplate;
	
	public String getResourceMessage(){
        ResponseEntity<String> exchange =
                restTemplate.exchange(
                        "http://resource-service/v1/resource/hello",
                        HttpMethod.GET,
                        null, String.class);

        return exchange.getBody();
    }
}
