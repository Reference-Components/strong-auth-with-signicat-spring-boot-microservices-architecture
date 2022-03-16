package fi.hiq.identity.oidc.domain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.apache.http.HttpHost;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import fi.hiq.identity.oidc.domain.idp.IdentityProviderList;
import fi.hiq.identity.oidc.exceptions.CommonOidcException;

public class IdentityProviderListBuilder {
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(IdentityProviderListBuilder.class);

    private String idpUrl;

    public IdentityProviderListBuilder(String url) {
        if (url == null) {
            String defaultUrl = OidcClientConfiguration.IDP_LIST_URL;
            if (!defaultUrl.endsWith("/")) {
                defaultUrl += "/";
            }
            defaultUrl += OidcClientConfiguration.CLIENT_ID;
            idpUrl = defaultUrl;
        } else {
            idpUrl = url;
        }
    }

    private String get(HttpHost target, HttpGet request) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = httpclient.execute(target, request);

        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        String jwt = reader.readLine().replaceAll("\\<.*?>", ""); // strip possible html
        logger.info("Identity provider list: {}", jwt);

        reader.close();
        response.close();
        httpclient.close();
        return jwt;
    }

    public IdentityProviderList build(String language) {
        try {
            logger.info("Listing identity providers from {}", this.idpUrl);
            URL url = new URL(this.idpUrl);
            String idTokenHost = url.getHost();
            String idTokenPath = url.getPath();
            String httpsProtocol = url.getProtocol();

            HttpHost target = new HttpHost(idTokenHost, 443, httpsProtocol);

            HttpGet request = new HttpGet(idTokenPath+"?lang="+language);

            return parse(get(target, request));
        } catch (Exception e) {
            throw new CommonOidcException("Building the list of identity providers failed!", e);
        }
    }

    static IdentityProviderList parse(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, IdentityProviderList.class);
    }
}
