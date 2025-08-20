package com.policybazaar.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for taking screenshots
 */
public class ScreenshotUtil {
    
    private static final Logger logger = LogManager.getLogger(ScreenshotUtil.class);
    
    /**
     * Takes screenshot and saves to specified path
     * @param driver WebDriver instance
     * @param testName Test method name for file naming
     * @return Screenshot file path
     */
    public static String takeScreenshot(WebDriver driver, String testName) {
        try {
            // Create screenshots directory if it doesn't exist
            File screenshotDir = new File("screenshots");
            if (!screenshotDir.exists()) {
                screenshotDir.mkdirs();
            }
            
            // Generate timestamp for unique filename
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
            String fileName = testName + "_" + timestamp + ".png";
            String filePath = screenshotDir.getAbsolutePath() + File.separator + fileName;
            
            // Take screenshot
            TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
            File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
            File destFile = new File(filePath);
            
            // Copy screenshot to destination
            Files.copy(sourceFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            
            logger.info("Screenshot saved successfully: {}", filePath);
            return filePath;
            
        } catch (IOException e) {
            logger.error("Failed to take screenshot: {}", e.getMessage(), e);
            return null;
        }
    }
    
    /**
     * Takes screenshot for validation error scenarios
     * @param driver WebDriver instance
     * @param testName Test method name
     * @param errorScenario Description of the error scenario
     * @return Screenshot file path
     */
    public static String takeErrorScreenshot(WebDriver driver, String testName, String errorScenario) {
        try {
            // Create screenshots directory if it doesn't exist
            File screenshotDir = new File("screenshots");
            if (!screenshotDir.exists()) {
                screenshotDir.mkdirs();
            }
            
            // Generate timestamp for unique filename
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
            String fileName = testName + "_" + errorScenario + "_" + timestamp + ".png";
            String filePath = screenshotDir.getAbsolutePath() + File.separator + fileName;
            
            // Take screenshot
            TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
            File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
            File destFile = new File(filePath);
            
            // Copy screenshot to destination
            Files.copy(sourceFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            
            logger.info("Error screenshot saved successfully: {}", filePath);
            return filePath;
            
        } catch (IOException e) {
            logger.error("Failed to take error screenshot: {}", e.getMessage(), e);
            return null;
        }
    }
} 