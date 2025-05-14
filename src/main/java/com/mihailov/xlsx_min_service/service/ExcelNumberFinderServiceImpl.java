package com.mihailov.xlsx_min_service.service;

import com.mihailov.xlsx_min_service.exception.FileProcessingException;
import com.mihailov.xlsx_min_service.validator.ExcelFileValidator;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExcelNumberFinderServiceImpl implements ExcelNumberFinderService {

    private static final Logger log = LoggerFactory.getLogger(ExcelNumberFinderServiceImpl.class);

    private final ExcelFileValidator excelFileValidator;

    @Override
    public int findNthSmallestNumber(String filePath, int n) {
        log.debug("Starting search for {}th smallest number in file: {}", n, filePath);
        File file = new File(filePath);

        try (Workbook workbook = WorkbookFactory.create(file)) {
            log.debug("Workbook successfully opened: {}", filePath);

            Sheet sheet = workbook.getSheetAt(0);
            log.debug("Sheet loaded with {} rows", sheet.getPhysicalNumberOfRows());

            List<Integer> numbers = extractNumbersFromSheet(sheet);
            log.debug("Extracted {} valid integers from the sheet", numbers.size());

            excelFileValidator.validateNumbersCount(numbers, n);
            log.debug("Validation passed: enough numbers to find the {}th smallest", n);

            int result = quickSelect(numbers, n);
            log.debug("QuickSelect result: {}th smallest number is {}", n, result);

            return result;

        } catch (IOException e) {
            log.error("IOException while reading the Excel file: {}", e.getMessage(), e);
            throw new FileProcessingException("Error processing Excel file: " + e.getMessage(), e);
        } catch (Exception e) {
            log.error("Unexpected exception occurred: {}", e.getMessage(), e);
            throw e;
        }
    }

    private List<Integer> extractNumbersFromSheet(Sheet sheet) {
        List<Integer> numbers = new ArrayList<>();

        for (Row row : sheet) {
            int rowIndex = row.getRowNum();
            Cell cell = row.getCell(0, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
            if (cell != null) {
                if (cell.getCellType() == CellType.NUMERIC) {
                    double value = cell.getNumericCellValue();
                    if (value == (int) value) {
                        numbers.add((int) value);
                    }
                }
            }
        }

        return numbers;
    }

    private int quickSelect(List<Integer> nums, int k) {
        return quickSelect(nums, 0, nums.size() - 1, k - 1);
    }

    private int quickSelect(List<Integer> nums, int left, int right, int k) {
        if (left == right) {
            return nums.get(left);
        }

        int pivotIndex = partition(nums, left, right);

        if (k == pivotIndex) {
            return nums.get(k);
        } else if (k < pivotIndex) {
            return quickSelect(nums, left, pivotIndex - 1, k);
        } else {
            return quickSelect(nums, pivotIndex + 1, right, k);
        }
    }

    private int partition(List<Integer> nums, int left, int right) {
        int pivotValue = nums.get(right);
        int storeIndex = left;

        for (int i = left; i < right; i++) {
            if (nums.get(i) < pivotValue) {
                swap(nums, i, storeIndex);
                storeIndex++;
            }
        }

        swap(nums, storeIndex, right);
        return storeIndex;
    }

    private void swap(List<Integer> nums, int i, int j) {
        Collections.swap(nums, i, j);
    }
}
