package fi.hiq.identity.oidc.domain;

import java.security.PrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;

import org.slf4j.LoggerFactory;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEDecrypter;
import com.nimbusds.jose.crypto.RSADecrypter;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.EncryptedJWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import fi.hiq.identity.oidc.dto.IdentityResponseDTO;
import fi.hiq.identity.oidc.exceptions.CommonOidcException;

public class IdTokenHandler {
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(IdTokenHandler.class);

    private final KeystoreLoader keyLoader;

    public IdTokenHandler(KeystoreLoader keyLoader) {
        this.keyLoader = keyLoader;
    }

    public IdentityResponseDTO extractIdentity(String idToken, KeystoreLoader keystore, JwksLoader jwksLoader) {
        try {
        	String decryptedIdToken = this.decryptIdToken(idToken, keystore);
        	
            SignedJWT signedJwt = SignedJWT.parse(decryptedIdToken);
            RSAPublicKey signatureVerificationKey = jwksLoader.getKeyById(signedJwt.getHeader().getKeyID(), true)
                    .getPublicKey();
            verifySignature(signedJwt, signatureVerificationKey);
            JWTClaimsSet claims = signedJwt.getJWTClaimsSet();

            String idRawData = signedJwt.getPayload().toString();
            logger.info("Payload: " + idRawData);
            idRawData = idRawData.replace(",", ",\n\t");
            idRawData = idRawData.replace("{", "{\n\t");
            idRawData = idRawData.replace("}", "\n}");
            IdentityResponseDTO identity = new IdentityResponseDTO();
            identity.setIdToken(decryptedIdToken);
            identity.setExp(claims.getExpirationTime().getTime());
            identity.setIdentityRawData(idRawData);
            identity.setName(claims.getStringClaim("name"));
            return identity;
        } catch (JOSEException | ParseException e) {
            logger.error("Error extracting identity from id token!", e);
            throw new CommonOidcException("Error extracting identity from id token!");
        }
    }

    public String decryptIdToken(String idToken, KeystoreLoader keystore) {
        try {
            EncryptedJWT e = EncryptedJWT.parse(idToken);
            String keyId = e.getHeader().getKeyID();
            PrivateKey decryptionKey = keystore.getKeyById(keyId, true).getPrivateKey();
            JWEDecrypter d = new RSADecrypter(decryptionKey);
            e.decrypt(d);
            logger.info("Decrypted id token: {}", e.getPayload().toString());
            
            return e.getPayload().toString();
        } catch (JOSEException | ParseException e) {
            logger.error("Error decrypting id token!", e);
            throw new CommonOidcException("Error decrypting id token!");
        }
    }

    public void verifySignature(SignedJWT jwt, RSAPublicKey signatureVerificationKey) throws JOSEException {
        RSASSAVerifier very = new RSASSAVerifier(signatureVerificationKey);
        boolean isMatching = jwt.verify(very);
        logger.info("ID Token signature matches: {}", isMatching);
        if (!isMatching) {
            throw new CommonOidcException("ID Token signature verification failed!");
        }
    }
}
