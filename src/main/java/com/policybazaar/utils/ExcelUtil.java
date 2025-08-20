package com.policybazaar.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.policybazaar.models.TravelInsuranceData;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;

public class ExcelUtil {
    
    private static final Logger logger = LogManager.getLogger(ExcelUtil.class);

    /**
     * Writes a map of data to Excel file
     * @param mapData - Map containing key-value pairs to write
     * @param fileName - Excel file name
     * @param sheetName - Sheet name to create/update
     */
    public static void writeToExcel(LinkedHashMap<String, String> mapData, String fileName, String sheetName) {
        logger.info("Starting Excel write operation");
        
        try {
            Workbook workbook;
            File file = new File(fileName);
            
            // Check if file exists - append sheet or create new workbook
            if (file.exists()) {
                logger.info("Excel file exists, opening existing workbook");
                FileInputStream fis = new FileInputStream(file);
                workbook = new XSSFWorkbook(fis);
                fis.close();
            } else {
                logger.info("Creating new Excel workbook");
                workbook = new XSSFWorkbook();
            }
            
            // Check if sheet already exists, if so remove it
            if (workbook.getSheet(sheetName) != null) {
                logger.info("Sheet {} already exists, removing old version", sheetName);
                int sheetIndex = workbook.getSheetIndex(sheetName);
                workbook.removeSheetAt(sheetIndex);
            }
            
            logger.info("Creating sheet: {}", sheetName);
            Sheet sheet = workbook.createSheet(sheetName);
            
            // Create header row
            logger.info("Creating header row");
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Provider Name");
            headerRow.createCell(1).setCellValue("Price");
            logger.info("Header row created successfully");
            
            // Write data
            logger.info("Writing data rows to Excel");
            int rowIndex = 1;
            for (String providerName : mapData.keySet()) {
                Row dataRow = sheet.createRow(rowIndex);
                dataRow.createCell(0).setCellValue(providerName);
                dataRow.createCell(1).setCellValue(mapData.get(providerName));
                rowIndex++;
            }
            logger.info("Successfully written {} data rows", mapData.size());
            
            // Auto-size columns
            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            
            // Write to file
            FileOutputStream fos = new FileOutputStream(fileName);
            workbook.write(fos);
            fos.close();
            workbook.close();
            logger.info("File written and resources closed successfully");
            
            logger.info("Excel write operation completed successfully - File: {}, Sheet: {}", fileName, sheetName);
            
        } catch (IOException e) {
            logger.error("Error writing to Excel file: {}", fileName, e);
            logger.error("Exception details: {}", e.getMessage());
            System.out.println("❌ Error writing to Excel: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error during Excel write operation", e);
            System.out.println("❌ Unexpected error: " + e.getMessage());
        }
    }

    /**
     * Writes a list of strings to Excel file
     * @param items - List of strings to write
     * @param fileName - Excel file name
     * @param sheetName - Sheet name to create
     */
    public static void writeListToExcel(List<String> items, String fileName, String sheetName) {
        logger.info("Starting Excel write operation for list");
        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet(sheetName);

            // Header
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Name");

            int rowIndex = 1;
            for (String item : items) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(item);
            }

            sheet.autoSizeColumn(0);

            FileOutputStream fos = new FileOutputStream(fileName);
            workbook.write(fos);
            fos.close();
            workbook.close();
            logger.info("Excel list write completed - File: {}, Sheet: {}, Rows: {}", fileName, sheetName, items.size());
        } catch (IOException e) {
            logger.error("Error writing list to Excel: {}", fileName, e);
        } catch (Exception e) {
            logger.error("Unexpected error during list Excel write operation", e);
        }
    }

    /**
     * Reads the first column from an Excel file and returns as a list of strings
     * @param fileName - Excel file name
     * @param sheetName - Sheet name to read from
     * @return List of strings from the first column (skipping header row)
     */
    public static List<String> readFirstColumn(String fileName, String sheetName) {
        logger.info("Reading first column from Excel - File: {}, Sheet: {}", fileName, sheetName);
        List<String> values = new ArrayList<>();
        
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                logger.warn("Excel file not found: {}", fileName);
                return values; // Return empty list if file doesn't exist
            }
            
            FileInputStream fis = new FileInputStream(fileName);
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheet(sheetName);
            
            if (sheet == null) {
                logger.warn("Sheet not found: {}", sheetName);
                workbook.close();
                fis.close();
                return values;
            }
            
            int firstRow = sheet.getFirstRowNum();
            int lastRow = sheet.getLastRowNum();
            
            // Skip header row (start from firstRow + 1)
            for (int i = firstRow + 1; i <= lastRow; i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;
                
                Cell cell = row.getCell(0);
                if (cell == null) continue;
                
                // Format cell value to string
                DataFormatter formatter = new DataFormatter();
                String cellValue = formatter.formatCellValue(cell).trim();
                
                if (!cellValue.isEmpty()) {
                    values.add(cellValue);
                }
            }
            
            workbook.close();
            fis.close();
            
        } catch (IOException e) {
            logger.error("Error reading Excel: {}", e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error while reading Excel file: {}", fileName, e);
        }
        
        logger.info("Read {} values from Excel first column", values.size());
        return values;
    }

    /**
     * Reads travel insurance data from Excel file
     * @param filePath - Path to Excel file
     * @param sheetName - Sheet name to read from
     * @return TravelInsuranceData object with read data
     */
    public static TravelInsuranceData readExcelData(String filePath, String sheetName) {
        
        String[] excelData = null;
        TravelInsuranceData data = null;

        logger.info("Reading data from Excel file.");

        try {
            // Open Excel file
            FileInputStream file = new FileInputStream(filePath);
            XSSFWorkbook workBook = new XSSFWorkbook(file);
            XSSFSheet sheet = workBook.getSheet(sheetName);
            XSSFRow row = sheet.getRow(0);

            // Read all cells in the first row
            int cellCount = row.getLastCellNum();
            excelData = new String[cellCount];
            DataFormatter formatter = new DataFormatter();

            // Store formatted cell values in array
            for (int i = 0; i < cellCount; i++) {
                XSSFCell cell = row.getCell(i);
                excelData[i] = (cell != null) ? formatter.formatCellValue(cell) : "";
            }

            // Close resources
            workBook.close();
            logger.info("Workbook closed");
            file.close();
            logger.info("File closed");

            // Create TravelInsuranceData object using read data
            data = new TravelInsuranceData(
                excelData[0], excelData[1], excelData[2],
                Integer.parseInt(excelData[3]), Integer.parseInt(excelData[4]), Integer.parseInt(excelData[5]), Boolean.parseBoolean(excelData[6])
            );

        } catch (Exception e) {
            logger.error("Error reading travel insurance data from Excel: {}", e.getMessage(), e);
        }

        return data;
    }
}