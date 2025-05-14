package com.mihailov.xlsx_min_service.controller;

import com.mihailov.xlsx_min_service.service.ExcelNumberFinderService;
import com.mihailov.xlsx_min_service.validator.ExcelFileValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/numbers")
@RequiredArgsConstructor
@Tag(name = "Excel Number Operations", description = "API for working with numbers in Excel files")
public class ExcelNumberController {

    private static final Logger log = LoggerFactory.getLogger(ExcelNumberController.class);

    private final ExcelNumberFinderService excelNumberFinderService;
    private final ExcelFileValidator excelFileValidator;

    @Operation(
            summary = "Find Nth smallest number in XLSX file",
            description =
                    """
                            Finds the Nth smallest number in a single-column Excel file.
                                       \s
                                        **Requirements:**
                                        - File must be in XLSX format
                                        - Must contain exactly one column with integer numbers
                                        - N must be positive integer
                            """,
            parameters = {
                    @Parameter(
                            name = "filePath",
                            description = "Absolute or relative path to the Excel file",
                            examples = {
                                    @ExampleObject(name = "Windows", value = "C:/data/numbers.xlsx"),
                                    @ExampleObject(name = "Linux/Mac", value = "/home/user/data/numbers.xlsx")
                            },
                            required = true
                    ),
                    @Parameter(
                            name = "number",
                            description = "The position of the smallest number to find (1-based index)",
                            example = "3",
                            required = true
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully found Nth smallest number",
                            content = @Content(examples = @ExampleObject(value = "5"))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input (N is not positive, file is not XLSX, etc.)"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "File not found"),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error")
            })
    @GetMapping("/findNthSmallest")
    public ResponseEntity<Integer> findNthMinimumNumber(
            @RequestParam
            @NotBlank
            @Pattern(regexp = ".+\\.xlsx$", message = "File must have .xlsx extension")
            String filePath,
            @RequestParam
            @Min(value = 1, message = "N must be at least 1")
            int number) {
        log.info("Received request to find {}th smallest number in file: {}", number, filePath);
        excelFileValidator.validateFileExists(filePath);
        return ResponseEntity.ok(excelNumberFinderService.findNthSmallestNumber(filePath, number));
    }
}