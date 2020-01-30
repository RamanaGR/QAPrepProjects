//1.open https://www.cisco.com/
//2. goto Products(left side top corner)
//3. goto Networking
//4. goto Wireless
//5. take header text(Wireless and mobility)
//6.click on the search and send header text
//7.click search and goto first result
//8.get page title and Assert it (Title-Wireless Network, Wi-Fi Networking and Mobility Solutions - Cisco)

package com.wbl.QAPrepProject;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SeleniumTask2 {

	WebDriver driver;

	@Test
	public void seearchHeader() throws InterruptedException {
		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\cvheg\\eclipse-workspace\\QAPrepProject\\Resource\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("https://www.cisco.com/");

		driver.findElement(By.xpath("//button[text()='Products']")).click();

		// finding elements from shadow root using shadow host//
		WebElement root1 = driver.findElement(By.tagName("cdc-nav")); // shadow host
		// open shadow root element
		WebElement shadowroot1 = expandRootElement(root1);

		// access inner elements of the shadow root (in this case, inner shadow root)
		WebElement root2 = shadowroot1.findElement(By.tagName("cdc-nav-products"));
		WebElement shadowroot2 = expandRootElement(root2);

		shadowroot2.findElement(By.id("Networking")).click();
		shadowroot2.findElement(By.cssSelector(
				"#Networking-tab > div.wc-row.wc-halves > div:nth-child(1) > div:nth-child(1) > div > ul > li:nth-child(6) > a"))
				.click();

		System.out.println(driver.getCurrentUrl());

		Thread.sleep(3000);

		// take header text
		String headerText = driver.findElement(By.xpath("//h1[contains(text(),'Wireless and mobility')]")).getText();

		// search
		driver.findElement(By.xpath("//button[@title='Search']")).click();

		// shadow root in wireless and mobility page
		WebElement root21 = driver.findElement(By.tagName("cdc-search"));

		WebElement shadowroot21 = expandRootElement(root21);
		shadowroot21.findElement(By.cssSelector("input[id='swc-search-phrase']")).sendKeys(headerText);
		shadowroot21.findElement(By.cssSelector("button[id='swc-search-button']")).click();

		// search results page
		driver.findElement(By.cssSelector(
				"#results > div > div.col-md-8.col-lg-8 > div > div > div:nth-child(1) > div.visible-md.visible-lg.visible-sm.ng-scope > div > div.col-md-8.col-sm-8 > div:nth-child(1) > div > a:nth-child(2)"))
				.click();
		Thread.sleep(3000);
		String pageTitle = driver.getTitle();
		System.out.println(pageTitle);

		Assert.assertEquals(pageTitle, "Wireless Network, Wi-Fi Networking and Mobility Solutions - Cisco");
		driver.quit();
	}

	public WebElement expandRootElement(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement element1 = (WebElement) js.executeScript("return arguments[0].shadowRoot", element);
		return element1;
	}

}
