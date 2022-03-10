package fi.hiq.gateway;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.proc.BadJOSEException;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTParser;
import com.nimbusds.oauth2.sdk.id.ClientID;
import com.nimbusds.oauth2.sdk.id.Issuer;
import com.nimbusds.openid.connect.sdk.Nonce;
import com.nimbusds.openid.connect.sdk.claims.IDTokenClaimsSet;
import com.nimbusds.openid.connect.sdk.validators.IDTokenValidator;

@RestController
public class GatewayController {

	@RequestMapping(value="/test", method = RequestMethod.GET)
    public ResponseEntity<String> getTest(@RequestHeader("Authorization") String authHeader) throws MalformedURLException {
		// The required parameters
		Issuer iss = new Issuer("https://isb-test.op.fi");
		ClientID clientID = new ClientID("saippuakauppias");
		JWSAlgorithm jwsAlg = JWSAlgorithm.RS256;
		URL jwkSetURL = new URL("https://isb-test.op.fi/jwks/broker");
		
		// Create validator for signed ID tokens
		IDTokenValidator validator = new IDTokenValidator(iss, clientID, jwsAlg, jwkSetURL);
		
		Nonce expectedNonce = null;

		IDTokenClaimsSet claims;

		try {
			String token = authHeader.replace("Bearer ", ""); 
			JWT idToken = JWTParser.parse(token);
		    claims = validator.validate(idToken, expectedNonce);
		    System.out.println("Logged in user " + claims.getSubject());    
		} catch (BadJOSEException e) {
			System.out.println("Invalid signature or claims (iss, aud, exp...)");
		} catch (JOSEException e) {
			System.out.println("Internal processing exception");
		} catch (ParseException e) {
			System.out.println("Failed to parse token");
			e.printStackTrace();
		}
		

        return ResponseEntity.ok("Test");
    }
}
