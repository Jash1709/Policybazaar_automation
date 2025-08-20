@echo off
echo ============================================
echo   PolicyBazaar Test Execution with Allure
echo ============================================

echo.
echo [1/3] Cleaning previous results...
if exist "target\allure-results" rmdir /s /q "target\allure-results"
if exist "target\allure-report" rmdir /s /q "target\allure-report"

echo.
echo [2/3] Running tests...
call mvn clean test

echo.
echo [3/3] Generating Allure HTML report...
call mvn allure:generate

echo.
echo ============================================
echo   Test Execution Complete!
echo ============================================
echo.
echo Report Location: target\allure-report\index.html
echo.
echo Opening report in browser...
start "" "target\allure-report\index.html"

echo.
echo Press any key to exit...
pause >nul 