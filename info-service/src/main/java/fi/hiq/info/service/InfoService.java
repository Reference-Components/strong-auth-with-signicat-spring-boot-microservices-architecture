package fi.hiq.info.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.hiq.info.utils.ResourceRestTemplateClient;
import fi.hiq.info.utils.UserContextHolder;

@Service
public class InfoService {
	
	private static final Logger logger = LoggerFactory.getLogger(InfoService.class);
	
	@Autowired
	ResourceRestTemplateClient resourceDiscoveryClient;
	
	public String getMessageFromResourceService() {
		String message = this.resourceDiscoveryClient.getResourceMessage();
		if (message == null) {
			logger.debug("getMessageFromResourceService Correlation id: {}",
					UserContextHolder.getContext().getCorrelationId());
		}
		return message;
	}
	
	
}
