package com.policybazaar.tests;

import com.policybazaar.pages.HealthInsurancePage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HealthInsuranceTest extends BaseTest {

	private HealthInsurancePage healthPage;

	@BeforeClass
	public void setUp() {
		// Navigate to application (driver is already initialized by BaseTest)
		navigateToHome();
		
		healthPage = new HealthInsurancePage(driver);
	}

	@Test(priority = 1)
	public void retrieveAndDisplayHealthInsuranceMenuItems() {
		List<String> items = healthPage.getAllHealthInsuranceMenuItems();
		for (String item : items) {
			System.out.println(item);
		}
		Assert.assertTrue(items != null && !items.isEmpty(), "Health Insurance menu items should not be empty");
	}

	@Test(priority = 2)
	public void verifyNoDuplicatesAndMinimumCount() {
		List<String> items = healthPage.getAllHealthInsuranceMenuItems();
		Set<String> unique = new HashSet<>(items);
		Assert.assertEquals(unique.size(), items.size(), "Duplicate items found in Health Insurance menu");
		Assert.assertTrue(items.size() >= 8, "Expected at least 8 Health Insurance items, found: " + items.size());
	}

}