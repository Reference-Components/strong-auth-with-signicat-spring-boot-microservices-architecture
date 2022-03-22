package fi.hiq.identity.oidc.dto;

import java.io.Serializable;
import java.util.Date;

public class IdentityResponseDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name; // First & maybe middle / last name.
    private String identityRawData;
    private String idToken;
    private long exp;

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

	public long getExp() {
		return exp;
	}

	public void setExp(long exp) {
		this.exp = exp;
	}
	
}
