package fi.hiq.identity.oidc.dto;

import java.io.Serializable;

public class IdentityResponseDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    // First & maybe middle / last name.
    private String name;
    private String identityRawData;
    private String idToken;

    public IdentityResponseDTO() {
        // Does nothing but required by SonarQube.
    }

    public IdentityResponseDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public String getIdentityRawData() {
		return identityRawData;
	}

	public void setIdentityRawData(String identityRawData) {
		this.identityRawData = identityRawData;
	}

	public String getIdToken() {
		return idToken;
	}

	public void setIdToken(String idToken) {
		this.idToken = idToken;
	}

}
