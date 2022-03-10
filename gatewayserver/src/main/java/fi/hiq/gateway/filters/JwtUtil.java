package fi.hiq.gateway.filters;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.proc.BadJOSEException;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTParser;
import com.nimbusds.oauth2.sdk.id.ClientID;
import com.nimbusds.oauth2.sdk.id.Issuer;
import com.nimbusds.openid.connect.sdk.claims.IDTokenClaimsSet;
import com.nimbusds.openid.connect.sdk.validators.IDTokenValidator;

@Component
public class JwtUtil {

	private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);
	
	private final String JWK_SET_URL = "https://isb-test.op.fi/jwks/broker";
	private final String ISSUER_URL = "https://isb-test.op.fi";
	private final String CLIENT_ID = "saippuakauppias";
	
	private final Issuer iss = new Issuer(ISSUER_URL);
	private final ClientID clientID = new ClientID(CLIENT_ID);
	private final JWSAlgorithm jwsAlg = JWSAlgorithm.RS256;
	private URL jwkSetURL; 
	private IDTokenValidator validator;
	
	public JwtUtil() {
		setJwtSetUrl();
		this.validator = new IDTokenValidator(iss, clientID, jwsAlg, jwkSetURL);
	}

	private void setJwtSetUrl() {
		try {
			this.jwkSetURL = new URL(JWK_SET_URL);
		} catch (MalformedURLException e) {
			logger.debug("Failed to init jwkSetUrl {}: ", e.getMessage());
		}
	}
	
	public IDTokenClaimsSet getAllClaimsFromToken(String authToken) {
		
		IDTokenClaimsSet claims = null;

		try {
			String token = authToken.replace("Bearer ", ""); 
			JWT idToken = JWTParser.parse(token);
		    claims = validator.validate(idToken, null);
		} catch (BadJOSEException e) {
			logger.debug("Invalid signature or claims {}: ", e.getMessage());
		} catch (JOSEException e) {
			logger.debug("Internal processing exception {}: ", e.getMessage());
		} catch (ParseException e) {
			logger.debug("Failed to parse token {}: ", e.getMessage());
		}
		
		return claims;
	}
	
}
