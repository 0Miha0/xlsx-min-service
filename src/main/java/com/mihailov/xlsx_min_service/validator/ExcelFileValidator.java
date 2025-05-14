package com.mihailov.xlsx_min_service.validator;

import com.mihailov.xlsx_min_service.exception.DataValidationException;
import com.mihailov.xlsx_min_service.exception.InvalidExcelDataException;
import com.mihailov.xlsx_min_service.exception.NExceedsNumbersCountException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Component
public class ExcelFileValidator {

    private static final Logger log = LoggerFactory.getLogger(ExcelFileValidator.class);

    public void validateFileExists(String filePath) {
        File file = new File(filePath);
        log.debug("Validating file existence: {}", filePath);
        if (!file.exists()) {
            log.error("File does not exist: {}", filePath);
            throw new DataValidationException("File not found: " + filePath);
        }
        if (!file.isFile()) {
            log.error("Path is not a file: {}", filePath);
            throw new DataValidationException("Provided path is not a file: " + filePath);
        }
        log.info("File exists and is valid: {}", filePath);
    }

    public void validateNumbersCount(List<Integer> numbers, int n) {
        log.debug("Validating number count. Numbers found: {}, requested N: {}", numbers.size(), n);

        if (numbers.isEmpty()) {
            log.error("No valid integers found in the Excel sheet.");
            throw new InvalidExcelDataException("Excel sheet contains no valid integers");
        }

        if (n > numbers.size()) {
            log.error("Requested N={} exceeds number of valid entries ({})", n, numbers.size());
            throw new NExceedsNumbersCountException(String.format("N (%d) exceeds numbers count in file (%d)", n, numbers.size()));
        }

        log.info("Valid number count. Proceeding with N-th smallest calculation.");
    }
}
