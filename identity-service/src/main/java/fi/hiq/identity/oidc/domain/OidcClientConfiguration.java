package fi.hiq.identity.oidc.domain;

public class OidcClientConfiguration {
    public static final String KEYSTORE_LOCATION = "saippuakauppias.jks";

    public static final String CLIENT_ID = "saippuakauppias";
    public static final String RESPONSE_TYPE = "code";
    public static final String AUTHORIZE_URL = "https://isb-test.op.fi/oauth/authorize";
    public static final String JWKS_PROXY = "https://isb-test.op.fi/jwks/broker";
    public static final String TOKEN_URL = "https://isb-test.op.fi/oauth/token";
    public static final String TOKEN_PROXY = "https://isb-test.op.fi/oauth/token";
    public static final String IDP_LIST_URL = "https://isb-test.op.fi/api/embedded-ui/";
    public static final String PROMPT = "auto";
    public static final String REDIRECT_URI = "http://localhost:3000/redirect";
    public static final String SCOPE = "openid profile personal_identity_code";

    private OidcClientConfiguration() {
        // Does nothing but required by SonarQube
    }
}
