package fi.hiq.identity.oidc.domain;

import java.security.PrivateKey;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

public class AuthorizationRequestHandler {

    private final KeystoreLoader keyLoader;

    public AuthorizationRequestHandler(KeystoreLoader keyLoader) {
        this.keyLoader = keyLoader;
    }

    public OidcRequestParameters sign(OidcRequestParameters params) throws JOSEException {
        PrivateKey signingKey = keyLoader.getSigningKey().getPrivateKey();

        // Authentication request parameters documented here:
        // https://github.com/CheckoutFinland/Identity-Service-Broker-API
        JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder() //
                .claim("client_id", params.getClientId()) //
                .claim("redirect_uri", params.getRedirectUri()) //
                .claim("response_type", params.getResponseType()) //
                .claim("scope", params.getScope()) //
                .claim("state", params.getState()) //
                .claim("nonce", params.getNonce()) //
                .claim("prompt", params.getPrompt()) //
                .claim("ui_locales", params.getUiLocales());

        if (params.getFtnIdpId() != null) {
            builder = builder.claim("ftn_idp_id", params.getFtnIdpId());
        }

        JWTClaimsSet claimsSet = builder.build();

        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.RS256).type(JOSEObjectType.JWT).build();

        SignedJWT jwt = new SignedJWT(header, claimsSet);
        JWSSigner signer = new RSASSASigner(signingKey);
        jwt.sign(signer);
        String result = jwt.getHeader().toBase64URL() + "." + jwt.getPayload().toBase64URL() + "."
                + jwt.getSignature().toString();
        params.setRequest(result);
        return params;
    }
}
