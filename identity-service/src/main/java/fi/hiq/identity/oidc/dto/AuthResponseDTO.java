package fi.hiq.identity.oidc.dto;

public class AuthResponseDTO {
	private String endpointUrl;
	private String request;
	
	public AuthResponseDTO(String endpointUrl, String request) {
		this.endpointUrl = endpointUrl;
		this.request = request;
	}

	public String getEndpointUrl() {
		return endpointUrl;
	}

	public void setEndpointUrl(String endpointUrl) {
		this.endpointUrl = endpointUrl;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}
	
}
