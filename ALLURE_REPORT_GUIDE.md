# Allure Reporting Guide

This project is configured with **Allure Framework** for generating beautiful, comprehensive test reports.

## 🚀 Prerequisites

1. **Java 11+** installed
2. **Maven** installed
3. **Allure Command Line** (optional, for viewing reports)

## 📊 How to Generate Allure Reports

### Method 1: Using Maven (Recommended)

1. **Run tests and generate Allure results:**
   ```bash
   mvn clean test
   ```

2. **Generate and serve Allure report:**
   ```bash
   mvn allure:serve
   ```
   This will automatically open the report in your default browser.

### Method 2: Using Allure Command Line

1. **Install Allure CLI:**
   ```bash
   # Windows (using Scoop)
   scoop install allure
   
   # macOS (using Homebrew)
   brew install allure
   
   # Or download from: https://github.com/allure-framework/allure2/releases
   ```

2. **Run tests:**
   ```bash
   mvn clean test
   ```

3. **Generate report:**
   ```bash
   allure generate target/allure-results --clean -o target/allure-report
   ```

4. **Open report:**
   ```bash
   allure open target/allure-report
   ```

## 📈 What You'll See in Allure Reports

### 📋 **Overview Dashboard**
- Total tests executed
- Success/Failure rates  
- Test execution trends
- Environment information

### 🎯 **Test Organization**
- **Epics**: PolicyBazaar Automation
- **Features**: Travel Insurance, Car Insurance, Health Insurance
- **Stories**: Specific test scenarios
- **Severity Levels**: Blocker, Critical, Normal, Minor

### 📸 **Screenshots**
- Automatic screenshots attached to test steps
- Screenshots on test failures
- Custom screenshots with descriptive names

### 📊 **Detailed Analytics**
- Test execution timeline
- Test duration analysis
- Flaky test detection
- Failed test categorization

## 🛠️ Allure Annotations Used

```java
@Epic("PolicyBazaar Automation")           // High-level feature grouping
@Feature("Travel Insurance")               // Feature under test
@Story("Browser Verification")             // User story
@Severity(SeverityLevel.CRITICAL)          // Test importance
@Description("Detailed test description")  // Test details
@Step("Navigate to home page")             // Test step
@Attachment("Screenshot")                  // File attachments
```

## 📁 Report Files Location

- **Allure Results**: `target/allure-results/`
- **Allure Report**: `target/allure-report/`
- **Screenshots**: Embedded in Allure results

## 🔧 Customization

Edit `src/test/resources/allure.properties` to customize:
- Report title and description
- Environment variables
- Link patterns for issue tracking

## 📞 Troubleshooting

**Issue**: Allure dependencies not found
**Solution**: Run `mvn clean install` to download dependencies

**Issue**: Reports not generating
**Solution**: Ensure AspectJ weaver is working - check console for AspectJ logs

**Issue**: Screenshots not appearing
**Solution**: Verify WebDriver is not null when taking screenshots

## 🎉 Example Report Features

- **Test Results**: Pass/Fail status with detailed execution logs
- **Timeline**: Visual representation of test execution over time  
- **Behaviors**: Tests organized by Epic → Feature → Story
- **Packages**: Tests organized by Java package structure
- **Categories**: Failed tests categorized by error types
- **Environment**: Browser, OS, Framework versions
- **History**: Execution trends over multiple runs 