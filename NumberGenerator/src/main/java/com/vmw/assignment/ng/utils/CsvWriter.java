package com.vmw.assignment.ng.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.opencsv.CSVWriter;
import com.vmw.assignment.ng.constants.Constants;

@Component
public class CsvWriter {

	private void csvWriterOneByOne(List<String[]> stringArray, Path path) throws IOException {
		CSVWriter writer = new CSVWriter(new FileWriter(path.toString()));
		for (String[] array : stringArray) {
			writer.writeNext(array);
		}
		writer.close();
	}

	public File writeValuesToFile(List<String> employees) throws IOException {
		String tempDir;
		if (System.getenv().get("GAE_ENV") != null) {
			// Running in GAE Standard
			tempDir = "/tmp" + File.separator;
		} else {
			// Running in other env
			tempDir = System.getProperty(Constants.JAVA_IO_TMPDIR);
		}

		String pathname = tempDir + UUID.randomUUID().toString() + Constants.CSV;
		Path path = Path.of(pathname);

		employees.add(0, Constants.NAME_AGE);
		List<String[]> employeeListCSV = employees.stream().map(e -> e.split(":")).collect(Collectors.toList());

		csvWriterOneByOne(employeeListCSV, path);
		return path.toFile();
	}

}
