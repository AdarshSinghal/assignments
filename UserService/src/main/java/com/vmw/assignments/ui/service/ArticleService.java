package com.vmw.assignments.ui.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vmw.assignments.ui.model.Article;

/**
 * @author adarsh
 *
 */
@Service
public class ArticleService {

	public List<Article> getAricles() throws URISyntaxException, IOException {
		String fileName = "static/json/iam-articles.json";
		URL resource = getClass().getClassLoader().getResource(fileName);
		if (resource == null) {
			throw new IllegalArgumentException(fileName + " not found! ");
		}
		return new ObjectMapper().readValue(resource, new TypeReference<List<Article>>() {
		});
	}

}
