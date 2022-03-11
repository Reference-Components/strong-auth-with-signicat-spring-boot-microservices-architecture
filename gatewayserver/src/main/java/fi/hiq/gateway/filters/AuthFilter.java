package fi.hiq.gateway.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.openid.connect.sdk.claims.IDTokenClaimsSet;

import fi.hiq.gateway.dto.ErrorResponseDto;
import reactor.core.publisher.Mono;

@Order(0)
@RefreshScope
@Component
public class AuthFilter implements GlobalFilter {

	private static final Logger logger = LoggerFactory.getLogger(AuthFilter.class);
	
	@Autowired
    private RouterValidator routerValidator;
	
	@Autowired
	FilterUtils filterUtils;
	
	@Autowired
    private JwtUtil jwtUtil;
	
	private ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		ServerHttpRequest request = exchange.getRequest();
			
		if (routerValidator.isSecured.test(request)) {
			String authToken = filterUtils.getAuthorizationToken(request.getHeaders());
			if (authToken != null) {
				IDTokenClaimsSet claims = jwtUtil.getAllClaimsFromToken(authToken);
				if (claims == null) {
					return this.onError(exchange, "Authorization token is invalid", HttpStatus.UNAUTHORIZED);
				}
			} else {
				return this.onError(exchange, "Authorization header is missing in request", HttpStatus.UNAUTHORIZED);
			}	
		}

		return chain.filter(exchange);
	}

	private Mono<Void> onError(ServerWebExchange exchange, String errMsg, HttpStatus httpStatus) {
        exchange.getResponse().setStatusCode(httpStatus);
        exchange.getResponse().getHeaders().add("Content-Type", "application/json; charset=UTF-8");
        
		try {
			ErrorResponseDto errorDto = new ErrorResponseDto(httpStatus, errMsg);
			byte[] bytes = objectMapper.writeValueAsBytes(errorDto);
			DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
			return exchange.getResponse().writeWith(Mono.just(buffer));
		} catch (JsonProcessingException e) {
			logger.debug("Failed to process json", e);
			exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            return exchange.getResponse().setComplete();
		}
         
    }
}
