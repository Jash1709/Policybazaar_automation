package com.policybazaar.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
 
import java.time.Duration;
 
public class WaitUtils {
 
    public static void waitForVisibility(WebDriver driver, WebElement element, int seconds) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(seconds))
                .until(ExpectedConditions.visibilityOf(element));
        } catch (TimeoutException e) {
            throw new RuntimeException("Element not visible after " + seconds + " seconds");
        }
    }
 
    public static void waitForClickability(WebDriver driver, WebElement element, int seconds) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(seconds))
                .until(ExpectedConditions.elementToBeClickable(element));
        } catch (TimeoutException e) {
            throw new RuntimeException("Element not clickable after " + seconds + " seconds");
        }
    }
}
