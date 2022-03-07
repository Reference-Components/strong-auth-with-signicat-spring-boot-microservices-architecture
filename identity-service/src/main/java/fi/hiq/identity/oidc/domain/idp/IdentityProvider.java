package fi.hiq.identity.oidc.domain.idp;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IdentityProvider {
    private String name;
    private String imageUrl;
    private String ftnIdpId;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    @JsonProperty("ftn_idp_id")
    public void setFtnIdpId(String ftnIdpId) {
        this.ftnIdpId = ftnIdpId;
    }

    public String getFtnIdpId() {
        return ftnIdpId;
    }
}
