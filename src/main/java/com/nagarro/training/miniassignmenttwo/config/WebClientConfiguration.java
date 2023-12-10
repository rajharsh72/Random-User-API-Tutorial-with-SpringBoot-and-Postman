package com.nagarro.training.miniassignmenttwo.config;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import reactor.netty.http.client.HttpClient;

/**
 * @author harshraj01
 * Configuration class that has all the configurations for the web client 
 * such as Request timeout, Write timeout and Read timeout
 */
@Configuration
public class WebClientConfiguration {
	
	
	//bean config for Api1 
	@Bean(name = "api1WebClient")
    public WebClient api1WebClient() {
        return WebClient.builder()
                .baseUrl("https://randomuser.me/api/") 
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024)) // Adjust codec settings if needed
                .defaultHeader("Content-Type", "application/json")
                .defaultHeader("Accept", "application/json")
                .clientConnector(getConnector(2000,2000,2000))
                .build();
    }

	
	//Bean config for Api2
    @Bean(name = "api2WebClient")
    public WebClient api2WebClient() {
        return WebClient.builder()
                .baseUrl("https://api.nationalize.io/") 
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024))
                .defaultHeader("Content-Type", "application/json")
                .defaultHeader("Accept", "application/json")
                .clientConnector(getConnector(1000,1000,1000))
                .build();
    }

    
    //Bean config for Api3
    @Bean(name = "api3WebClient")
    public WebClient api3WebClient() {
        return WebClient.builder()
                .baseUrl("https://api.genderize.io/") 
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024))
                .defaultHeader("Content-Type", "application/json")
                .defaultHeader("Accept", "application/json")
                .clientConnector(getConnector(1000,1000,1000))
                .build();
    }
    
    
    //Returns a HttpConnector with proper request, write and read timeout settings. 
    private ClientHttpConnector getConnector(int connectionTimeout, int readTimeout, int writeTimeout) {
    	HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectionTimeout)
                .doOnConnected(conn -> conn
                        .addHandlerFirst(new ReadTimeoutHandler(readTimeout, TimeUnit.SECONDS))
                        .addHandlerFirst(new WriteTimeoutHandler(writeTimeout, TimeUnit.SECONDS)));
    	
        ClientHttpConnector connector = new ReactorClientHttpConnector(httpClient.wiretap(true));
        return connector;
    }
}



