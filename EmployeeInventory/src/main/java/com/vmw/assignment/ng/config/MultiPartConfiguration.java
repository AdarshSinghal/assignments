package com.vmw.assignment.ng.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * This configuration file lets you set the file size. File related
 * configuration should be added to this file.
 * 
 * @author adarsh
 *
 */
@Configuration
public class MultiPartConfiguration {

	// 1000 records occupying 43 kb. Hence, File Size limit set to 50 kb.
	private static final int CSV_FILE_SIZE_LIMIT = 51200;

	@Bean
	public MultipartResolver multipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();

		multipartResolver.setMaxUploadSize(CSV_FILE_SIZE_LIMIT);
		return multipartResolver;
	}

}
