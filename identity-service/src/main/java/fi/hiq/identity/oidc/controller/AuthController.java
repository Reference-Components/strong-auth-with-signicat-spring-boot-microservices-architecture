package fi.hiq.identity.oidc.controller;

import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import fi.hiq.identity.oidc.domain.Identity;
import fi.hiq.identity.oidc.domain.OidcRequestParameters;
import fi.hiq.identity.oidc.domain.OidcResponseParameters;
import fi.hiq.identity.oidc.facade.OidcFacade;

@RestController
public class AuthController {
    
	private final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private OidcFacade facade;

    @RequestMapping(value="/authorize", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> initFlow(HttpServletRequest request, Map<String, Object> model) {

        String language = "fi";
        String idp = request.getParameter("idp");
        String requestId = UUID.randomUUID().toString();
        String promptParam = "consent";
        String purpose = "strong";

        boolean prompt = promptParam != null && promptParam.equals("consent");
        OidcRequestParameters params = getFacade().oidcAuthMessage(idp, language, requestId, prompt, purpose);
        logger.info("Auth Request: {}", params.getRequest());
        model.put("endpointUrl", params.getEndpointUrl());
        model.put("request", params.getRequest());
        request.getSession().setAttribute("initParams", params);
        return ResponseEntity.ok(model);
    }

    @RequestMapping(value="/token", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> finishFlow(HttpServletRequest request, Map<String, Object> model) {
        OidcRequestParameters originalParams = (OidcRequestParameters) request.getSession().getAttribute("initParams");
        OidcResponseParameters response = new OidcResponseParameters();
        response.setError(request.getParameter("error"));
        response.setState(request.getParameter("state"));
        response.setCode(request.getParameter("code"));

        verifyStateParams(request, originalParams, response);        

        if (response.getError() == null || response.getError().length() == 0) {
            Identity identity = getFacade().extractIdentity(response, originalParams);
            model.put("identity", identity);
        } else if (response.getError().equals("cancel")) {
        	model.put("error", response.getError());
        } else {
            model.put("error", response.getError());
        }
        
        return ResponseEntity.ok(model);
    }

	private void verifyStateParams(HttpServletRequest request, OidcRequestParameters originalParams,
			OidcResponseParameters response) {
		if (originalParams.getState() == null || 
        	originalParams.getState().length() == 0 ||
        	request.getParameter("state") == null || 
        	request.getParameter("state").length() == 0) {
        	response.setError("Request missing state param");
        } else if (!originalParams.getState().equals(request.getParameter("state"))) {
        	response.setError("Invalid state");
        }
	}

    @RequestMapping(value = "/jwks", method = RequestMethod.GET)
    @ResponseBody
    public String jwks() {
        return getFacade().getJwks();
    }

    private OidcFacade getFacade() {
        if (facade == null) {
            facade = new OidcFacade();
        }
        return facade;
    }
}
