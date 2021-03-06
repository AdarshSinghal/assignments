package com.vmw.assignment.ng.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.vmw.assignment.ng.constants.SwaggerConstants;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger related configuration should be maintained here.
 * 
 * @author adarsh
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration extends WebMvcConfigurationSupport {

	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage(SwaggerConstants.BASE_PACKAGE_NAME))
				.paths(PathSelectors.any()).build().apiInfo(apiInfo());
	}

	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler(SwaggerConstants.SWAGGER_UI_PAGE_NAME)
				.addResourceLocations(SwaggerConstants.SWAGGER_UI_RESOURCE_LOCATION);

		registry.addResourceHandler(SwaggerConstants.WEBJARS)
				.addResourceLocations(SwaggerConstants.WEBJAR_RESOURCE_LOCATION);
	}

	private ApiInfo apiInfo() {
		return new ApiInfo(SwaggerConstants.API_INFO_STRING_1, SwaggerConstants.API_INFO_STRING_2, null, null, null,
				null, null, Collections.emptyList());
	}

}