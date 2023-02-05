package com.prgrms.kream.common.config;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

	@Bean
	public Docket apiV1() {
		return new Docket(DocumentationType.SWAGGER_2)
				.consumes(getConsumeContentTypes())
				.produces(getProduceContentTypes())
				.useDefaultResponseMessages(false)
				.groupName("api v1")
				.select()
				.apis(RequestHandlerSelectors
						.basePackage("com.prgrms.kream"))
				.paths(PathSelectors.ant("/api/v1/**"))
				.build()
				.apiInfo(apiInfo())
				.securityContexts(List.of(securityContext()))
				.securitySchemes(List.of(apiKey()))
				;
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("Shoe-Kream")
				.description("명품 중고거래 플랫폼 서비스인 크림의 클론 프로젝트")
				.version("version 1.0")
				.build();
	}

	private ApiKey apiKey() {
		return new ApiKey("JWT", "access_token", "header");
	}

	private SecurityContext securityContext() {
		return SecurityContext.builder()
				.securityReferences(defaultAuth())
				.build();
	}

	private List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope =
				new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes =
				new AuthorizationScope[1];

		authorizationScopes[0] = authorizationScope;
		return List.of(new SecurityReference("JWT", authorizationScopes));
	}

	private Set<String> getConsumeContentTypes() {
		Set<String> consumes = new HashSet<>();
		consumes.add(MediaType.APPLICATION_JSON_VALUE);
		consumes.add(MediaType.MULTIPART_FORM_DATA_VALUE);
		return consumes;
	}

	private Set<String> getProduceContentTypes() {
		Set<String> produces = new HashSet<>();
		produces.add(MediaType.APPLICATION_JSON_VALUE);
		return produces;
	}
}
