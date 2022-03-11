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
import fi.hiq.identity.oidc.facade.OidcDemoFacade;

@RestController(value = "v1/identity")
public class AuthRestController {
    
	private final Logger logger = LoggerFactory.getLogger(AuthRestController.class);

    private OidcDemoFacade facade;

    @RequestMapping(value="/authorize", method = RequestMethod.GET)
    public String initFlow(HttpServletRequest request, Map<String, Object> model) {

    	request.getSession().setAttribute("backurlprefix", "");
    	
        String language = "fi";
        String idp = request.getParameter("idp");
        String requestId = UUID.randomUUID().toString();
        String promptParam = "consent";
        String purpose = "strong";

        boolean prompt = promptParam != null && promptParam.equals("consent");
        OidcRequestParameters params = getFacade().oidcAuthMessage(idp, language, requestId, prompt, purpose);
        logger.info("Request: {}", params.getRequest());
        model.put("endpointUrl", params.getEndpointUrl());
        model.put("request", params.getRequest());
        request.getSession().setAttribute("initParams", params);
        return params.getEndpointUrl() + "?request=" + params.getRequest();
    }

    @RequestMapping(value="/token", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> finishFlow(HttpServletRequest request, Map<String, Object> model) {
        OidcRequestParameters originalParams = (OidcRequestParameters) request.getSession().getAttribute("initParams");
        OidcResponseParameters response = new OidcResponseParameters();
        response.setError(request.getParameter("error"));
        response.setState(request.getParameter("state"));
        response.setCode(request.getParameter("code"));

        if (response.getError() == null || response.getError().length() == 0) {
            Identity identity = getFacade().extractIdentity(response, originalParams);
            model.put("identity", identity);
            model.put("backurlprefix", request.getSession().getAttribute("backUrlPost"));
        } else if (response.getError().equals("cancel")) {
        	// handle cancel
        } else {
            model.put("error", response.getError());
        }
        
        return ResponseEntity.ok(model);
    }

    @RequestMapping(value = "/jwks", method = RequestMethod.GET)
    @ResponseBody
    public String jwks() {
        return getFacade().getJwks();
    }

    private OidcDemoFacade getFacade() {
        if (facade == null) {
            facade = new OidcDemoFacade();
        }
        return facade;
    }
}
