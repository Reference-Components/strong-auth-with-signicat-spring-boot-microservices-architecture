package fi.hiq.identity.oidc.controller;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import fi.hiq.identity.oidc.domain.Identity;
import fi.hiq.identity.oidc.domain.IdentityProviderListBuilder;
import fi.hiq.identity.oidc.domain.OidcRequestParameters;
import fi.hiq.identity.oidc.domain.OidcResponseParameters;
import fi.hiq.identity.oidc.domain.idp.IdentityProvider;
import fi.hiq.identity.oidc.domain.idp.IdentityProviderList;
import fi.hiq.identity.oidc.facade.OidcDemoFacade;
import net.minidev.json.JSONObject;

@Controller
public class AuthController {
    private final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private OidcDemoFacade facade;


    @RequestMapping("/")
    public String welcome(HttpServletRequest request, Map<String, Object> model) throws UnsupportedEncodingException {

    	request.getSession().setAttribute("backurlprefix", "");

        return "welcome";
    }

    public void prepareEmbeddedMode(String language, Map<String, Object> model, HttpServletRequest request) {

        IdentityProviderList idpList = new IdentityProviderListBuilder(null).build(language);

    	List<IdentityProvider> idps = idpList.getIdentityProviders();

    	if (idpList.getDisturbanceInfo()!=null) {
        	String dInfo = idpList.getDisturbanceInfo().getAsString("text");
        	String dInfo2 = dInfo.replace("\n", "<br> <br>"); 					// Make sure that if multiple disturbances they are their own lines
        	idpList.getDisturbanceInfo().put("text", dInfo2);
    		request.getSession().setAttribute("disturbanceinfo", "yes");
    	}
    	else {
    		request.getSession().setAttribute("disturbanceinfo", "no");
    	}

        model.put("identityProviders", idps);
        JSONObject disturbanceInfo = idpList.getDisturbanceInfo();
        model.put("disturbanceInfo", disturbanceInfo);
        String isbProviderInfo = idpList.getIsbProviderInfo();
        model.put("isbProviderInfo", isbProviderInfo);
        String isbConcent = idpList.getIsbConsent();
        model.put("isbConsent", isbConcent);
        String privacyNoticeLink = idpList.getPrivacyNoticeLink();
        model.put("privacyNoticeLink", privacyNoticeLink);
        String privacyNoticeText = idpList.getPrivacyNoticeText();
        model.put("privacyNoticeText", privacyNoticeText);
    }

    @RequestMapping("/embedded")
    public String embedded(HttpServletRequest request, Map<String, Object> model) throws UnsupportedEncodingException {

    	String language = (String) request.getSession().getAttribute("language");

    	if (language==null) {
    		language = request.getParameter("language");

    	}
    	if (language==null) {
    		language="fi"; // Default language is fi
    	}

    	prepareEmbeddedMode(language, model, request);

        // But default GUI language, and GUI type to session (for render)
        request.getSession().setAttribute("language", language);
        request.getSession().setAttribute("backurlprefix", "embedded");

        return "embedded";
    }

    @RequestMapping("/initFlow")
    public String initFlow(HttpServletRequest request, Map<String, Object> model) {

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
        return "post";
    }

    @RequestMapping("/finishFlow")
    public String finishFlow(HttpServletRequest request, Map<String, Object> model) {
        OidcRequestParameters originalParams = (OidcRequestParameters) request.getSession().getAttribute("initParams");
        OidcResponseParameters response = new OidcResponseParameters();
        response.setError(request.getParameter("error"));
        response.setState(request.getParameter("state"));
        response.setCode(request.getParameter("code"));

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.YYYY HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String timenow = dtf.format(now);

        if (response.getError() == null || response.getError().length() == 0) {
            Identity identity = getFacade().extractIdentity(response, originalParams);
            model.put("timenow", timenow);
            model.put("identity", identity);
            model.put("backurlprefix", request.getSession().getAttribute("backUrlPost"));
            return "identity";
        } else if (response.getError().equals("cancel")) {
        	return "welcome";
        } else {
            model.put("error", response.getError());
            return "error";
        }
    }

    private OidcDemoFacade getFacade() {
        if (facade == null) {
            facade = new OidcDemoFacade();
        }
        return facade;
    }
}
