package fi.hiq.gateway.filters;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

@Component
public class FilterUtils {

	public static final String CORRELATION_ID = "correlation-id";
	public static final String AUTH_TOKEN = "auth-token";
	public static final String AUTHORIZATION = "Authorization";
	public static final String USER_ID = "user-id";
	public static final String PRE_FILTER_TYPE = "pre";
	public static final String POST_FILTER_TYPE = "post";
	public static final String ROUTE_FILTER_TYPE = "route";

	public String getCorrelationId(HttpHeaders requestHeaders){
		if (requestHeaders.get(CORRELATION_ID) != null) {
			List<String> header = requestHeaders.get(CORRELATION_ID);
			return header.stream().findFirst().get();
		}
		else{
			return null;
		}
	}
	
	public String getAuthorizationToken(HttpHeaders requestHeaders){
		if (requestHeaders.get(AUTHORIZATION) != null) {
			List<String> header = requestHeaders.get(AUTHORIZATION);
			return header.stream().findFirst().get().replace("Bearer ", "");
		} else{
			return null;
		}
	}

	public ServerWebExchange setRequestHeader(ServerWebExchange exchange, String name, String value) {
		return exchange.mutate().request(
							exchange.getRequest().mutate()
							.header(name, value)
							.build())
						.build();	
	}
	
	public ServerWebExchange setCorrelationId(ServerWebExchange exchange, String correlationId) {
		return this.setRequestHeader(exchange, CORRELATION_ID, correlationId);
	}

}
