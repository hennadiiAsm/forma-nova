package com.formanova.integration.client.config;

import io.netty.resolver.DefaultAddressResolverGroup;
import org.springframework.boot.web.reactive.function.client.WebClientCustomizer;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Component
public class DnsFixingWebClientCustomizer implements WebClientCustomizer {

    @Override
    public void customize(WebClient.Builder webClientBuilder) {
        HttpClient nettyClient = HttpClient.create().resolver(DefaultAddressResolverGroup.INSTANCE);
        webClientBuilder.clientConnector(new ReactorClientHttpConnector(nettyClient));
    }

}

