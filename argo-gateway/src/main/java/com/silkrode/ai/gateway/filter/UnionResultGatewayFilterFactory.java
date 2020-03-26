package com.silkrode.ai.gateway.filter;

import com.silkrode.ai.common.response.ErrorResult;
import com.silkrode.ai.common.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.rewrite.ModifyResponseBodyGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.rewrite.RewriteFunction;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Objects;

/**
 * The filter which is rewriting the response body.
 */
@Component
@Slf4j
public class UnionResultGatewayFilterFactory extends ModifyResponseBodyGatewayFilterFactory {

    public UnionResultGatewayFilterFactory(ServerCodecConfigurer codecConfigurer) {
        super(codecConfigurer);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return new ModifyResponseGatewayFilter(this.getConfig());
    }


    private Config getConfig() {
        Config config = new Config();
        config.setRewriteFunction(Object.class, Object.class, getRewriteFunction());
        return config;
    }

    private RewriteFunction<Object, Object> getRewriteFunction() {
        return (exchange, resp) -> {
            if (exchange.getRequest().getPath().toString().contains("/v2/api-docs")) {
                return Mono.just(resp);
            }
            if ("OK".equals(Objects.requireNonNull(exchange.getResponse().getStatusCode()).getReasonPhrase())) {
                return Mono.just(Result.suc(resp));
            }
            Map respMap = (Map) resp;
            ErrorResult errorResult;
            if (respMap.containsKey("error")) {
                errorResult = ErrorResult.builder()
                        .status(exchange.getResponse().getStatusCode().value())
                        .message(respMap.get("error").toString())
                        .build();
            } else {
                errorResult = ErrorResult.builder()
                        .status((Integer) respMap.get("status"))
                        .message(respMap.get("message").toString())
                        .build();
            }
            return Mono.just(Result.fail(errorResult.getStatus(), errorResult.getMessage()));
        };
    }
}
