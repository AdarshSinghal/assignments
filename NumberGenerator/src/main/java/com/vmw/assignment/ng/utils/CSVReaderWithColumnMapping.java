package com.vmw.assignment.ng.utils;

import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;

public class CSVReaderWithColumnMapping<T> {

	private Class<T> type;

	public CSVReaderWithColumnMapping(Class<T> type) {
		this.type = type;
	}

	public List<T> parseCsvReturnBean(Reader reader) throws IOException {
		try (CSVReader csvReader = new CSVReader(reader)) {
			if (isValidHeaders(csvReader)) {
				return getBean(csvReader);
			}
		}
		return Collections.emptyList();
	}

	private boolean isValidHeaders(CSVReader csvReader) throws IOException {
		String[] header = csvReader.peek();
		return Arrays.asList(header).contains("Name");
	}

	private List<T> getBean(CSVReader csvReader) {
		Map<String, String> mapping = getColumnMap();
		CsvToBeanBuilder<T> builder = new CsvToBeanBuilder<>(csvReader);
		HeaderColumnNameTranslateMappingStrategy<T> strategy = new HeaderColumnNameTranslateMappingStrategy<>();
		strategy.setType(type);
		strategy.setColumnMapping(mapping);
		return builder.withMappingStrategy(strategy).build().parse();
	}

	/**
	 * Define column mapping here
	 * 
	 * @return
	 */
	private Map<String, String> getColumnMap() {
		Map<String, String> mapping = new HashMap<>();
		mapping.put("Name", "name");
		mapping.put("Age", "age");
		return mapping;
	}

}
