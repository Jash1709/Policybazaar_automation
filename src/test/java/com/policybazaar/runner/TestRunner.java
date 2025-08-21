package com.policybazaar.runner;
 
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
 
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
 
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.policybazaar.stepDefinations"},        
        plugin = {
                "pretty",
                "html:target/cucumber-reports/cucumber.html",
                "json:target/cucumber-reports/cucumber.json"
        }
)
public class TestRunner extends AbstractTestNGCucumberTests {
	
	@BeforeClass
	@Parameters("browser")
    public void setUp(String browser) {
        System.setProperty("browser", browser);
    }
}
 
 