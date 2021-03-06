package com.vmw.assignment.ng.utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.vmw.assignment.ng.model.EmployeeEntry;

/** This class contains operations related to file read.
 * @author adarsh
 *
 */
@Component
public class CsvReader {

	/**Read the file and parse it. Convert the parsed results into required type.
	 * 
	 * @param file
	 * @return employeeEntry
	 * @throws IOException
	 */
	public List<EmployeeEntry> readFromFile(MultipartFile file) throws IOException {
		CSVReaderWithColumnMapping<EmployeeEntry> csvReader = new CSVReaderWithColumnMapping<>(EmployeeEntry.class);
		return csvReader
				.parseCsvReturnBean(new InputStreamReader(file.getInputStream()));
	}

}
