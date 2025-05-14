package com.mihailov.xlsx_min_service.service;

public interface ExcelNumberFinderService {
    int findNthSmallestNumber(String filePath, int n);
}