package com.vmw.assignment.ng.utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.vmw.assignment.ng.model.EmployeeEntry;

@Component
public class CsvReader {

	public List<EmployeeEntry> readFromFile(MultipartFile file) throws IOException {
		CSVReaderWithColumnMapping<EmployeeEntry> csvReader = new CSVReaderWithColumnMapping<>(EmployeeEntry.class);
		return csvReader
				.parseCsvReturnBean(new InputStreamReader(file.getInputStream()));
	}

}
