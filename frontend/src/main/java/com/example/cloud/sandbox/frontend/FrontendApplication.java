package com.example.cloud.sandbox.frontend;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Random;

@SpringBootApplication
@Configuration
@EnableDiscoveryClient
@RestController
@RequiredArgsConstructor
public class FrontendApplication {

	@Autowired
	private final DiscoveryClient discoveryClient;

	@Autowired
	private final RestTemplateBuilder builder;

	public static void main(String[] args) {
		SpringApplication.run(FrontendApplication.class, args);
	}

	@GetMapping
	public String defaultEndpoint(){
		String list = discoveryClient.getServices().stream().reduce((t, k) -> t + "<br/>\n" + k).orElse("No services found");
		return "Services found: " + list + "<br/>\n" + new Date();
	}

	@GetMapping(value = "/{path}")
	public String defaultEndpointWithPath(@PathVariable String path){
		return "default with path: " + path;
	}

	@GetMapping(value = "/getsimple/{path}")
	public String getSimple(@PathVariable String path){
		List<ServiceInstance>  instances = discoveryClient.getInstances("sandbox-simpleapp-svc");
		URI serviceInstanceUri = instances.get(new Random().nextInt(instances.size())).getUri();
		String endpointUrl = serviceInstanceUri.toString() + "/" + path;
		RestTemplate restTemplate = builder.build();
		ResponseEntity<String> response = restTemplate.exchange(endpointUrl, HttpMethod.GET,null, String.class);
		return "get simple, parameter: " + path + ", url: " + endpointUrl + "<br/>\n" + response.getBody();
	}

	@GetMapping(value = "/getprops/{path}")
	public String getProps(@PathVariable String path){
		URI serviceInstanceUri = discoveryClient.getInstances("sandbox-properties-svc").get(0).getUri();
		RestTemplate restTemplate = builder.build();
		ResponseEntity<String> response = restTemplate.exchange(serviceInstanceUri.toString(), HttpMethod.GET,null, String.class);
		return "get props " + path + ":" + serviceInstanceUri.toString() + "<br/>\n" + response.getBody();
	}

}
