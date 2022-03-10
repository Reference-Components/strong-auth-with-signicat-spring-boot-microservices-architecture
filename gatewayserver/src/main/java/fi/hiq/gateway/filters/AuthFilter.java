package fi.hiq.gateway.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.nimbusds.openid.connect.sdk.claims.IDTokenClaimsSet;

import reactor.core.publisher.Mono;

@Order(0)
@RefreshScope
@Component
public class AuthFilter implements GlobalFilter {

	@Autowired
	FilterUtils filterUtils;
	
	@Autowired
    private JwtUtil jwtUtil;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		HttpHeaders requestHeaders = exchange.getRequest().getHeaders();
		String authToken = filterUtils.getAuthorizationToken(requestHeaders);
		if (authToken != null) {
			IDTokenClaimsSet claims = jwtUtil.getAllClaimsFromToken(authToken);
			if (claims == null) {
				System.out.println("Authorization header is invalid");
				return this.onError(exchange, "Authorization header is invalid", HttpStatus.UNAUTHORIZED);
			}
			System.out.println("All is well");
			this.populateRequestWithHeaders(exchange, claims);
		} else {
			System.out.println("Authorization header is missing in request");
			return this.onError(exchange, "Authorization header is missing in request", HttpStatus.UNAUTHORIZED);
		}
		
		return chain.filter(exchange);
	}

	private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }
	
	private void populateRequestWithHeaders(ServerWebExchange exchange, IDTokenClaimsSet claims) {
        exchange.getRequest().mutate()
                .header("subject", String.valueOf(claims.getSubject()))
                .build();
    }

}
