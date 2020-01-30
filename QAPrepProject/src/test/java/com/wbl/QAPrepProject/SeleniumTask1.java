//1.Go to whiteboxQA.com. login with your credentials. go to presentations
//go to books and download any one textbook.

package com.wbl.QAPrepProject;

import java.awt.AWTException;
import java.io.IOException;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

public class SeleniumTask1 {

	@Test
	public void downloadTextBook() throws InterruptedException, IOException, AWTException {

		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\cvheg\\eclipse-workspace\\QAPrepProject\\Resource\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("http://whiteboxqa.com/");

		driver.findElement(By.id("loginButton")).click();
		driver.findElement(By.id("username")).sendKeys("Chitra.hgd@gmail.com");
		driver.findElement(By.id("password")).sendKeys("Vinchi12");
		driver.findElement(By.id("login")).sendKeys(Keys.ENTER);

		Actions a = new Actions(driver);
		WebElement Resources = driver.findElement(By.xpath("//*[@id=\"navbar-collapse\"]/ul/li[4]/a"));
		WebElement presentations = driver.findElement(By.xpath("//*[@id=\"navbar-collapse\"]/ul/li[4]/ul/li[2]/a"));
		a.moveToElement(Resources).perform();
		a.moveToElement(presentations).click().perform();

		driver.findElement(By.xpath("//a[text()='Books']")).click();
		driver.findElement(By.xpath("//a[text()='Apache Jmeter']")).click();

		String parentWindow = driver.getWindowHandle();
		System.out.println(driver.getCurrentUrl());

		Set<String> windows = driver.getWindowHandles();

		for (String win : windows) {
			if (!win.equals(parentWindow)) {
				driver.switchTo().window(win);

			}
		}

		driver.findElement(By.xpath("/html/body/div[3]/div[3]/div/div[3]/div[2]/div[2]/div[3]/div")).click();
		driver.close();

		driver.switchTo().window(parentWindow);
		driver.close();

	}
}
