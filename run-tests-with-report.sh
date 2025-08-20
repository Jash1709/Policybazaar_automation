#!/bin/bash

echo "============================================"
echo "  PolicyBazaar Test Execution with Allure"
echo "============================================"

echo ""
echo "[1/3] Cleaning previous results..."
rm -rf target/allure-results target/allure-report

echo ""
echo "[2/3] Running tests..."
mvn clean test

echo ""
echo "[3/3] Generating Allure HTML report..."
mvn allure:generate

echo ""
echo "============================================"
echo "  Test Execution Complete!"
echo "============================================"
echo ""
echo "Report Location: target/allure-report/index.html"
echo ""

# Try to open in browser (works on most systems)
if command -v xdg-open > /dev/null; then
    echo "Opening report in browser..."
    xdg-open target/allure-report/index.html
elif command -v open > /dev/null; then
    echo "Opening report in browser..."
    open target/allure-report/index.html
else
    echo "Please open target/allure-report/index.html in your browser"
fi

echo ""
echo "Press Enter to exit..."
read 