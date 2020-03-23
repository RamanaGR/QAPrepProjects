//Log in to Walmart
//→ Search for a product(Ex:iPad Pro)
//→ copy the first five Names
//→ copy the price
//→ close walmart
//→ log in to Amazon
//→ Search for the same products individually
//→ compare prices and produce results.

package automationPractice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class ComparePrice {

	@Test
	public void comparePrice() throws InterruptedException {
		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\cvheg\\eclipse-workspace\\QAPrepProject\\Resource\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		// Log in to Walmart
		driver.get("https://www.walmart.com/");

		// → Search for a product(Ex:iPad Pro)
		driver.findElement(By.cssSelector("input[id='global-search-input']")).sendKeys("iPad Pro");
		driver.findElement(By.cssSelector("button[id='global-search-submit']")).click();

		// → copy the first five Names
		List<WebElement> searchResult = driver.findElements(By.xpath("//a[@data-type='itemTitles']"));
		List<String> top5Result = new ArrayList<String>();
		int i = 0;
		for (WebElement e : searchResult) {
			if (i < 5) {
				top5Result.add(e.getText());
			}
			i++;
		}
		for (String s : top5Result) {
			System.out.println(s);
		}

		//// → copy the price
		List<WebElement> priceResult = driver.findElements(By.xpath("//span[@class='price-main-block']/span/span"));
		List<String> top5Price = new ArrayList<String>();
		for (int j = 0; j < priceResult.size(); j++) {
			if (j > 4) {
				break;
			}
			WebElement elem = priceResult.get(j);
			top5Price.add(elem.getAttribute("innerText"));
		}
		for (String s : top5Price) {
			System.out.println(s);
		}

		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put(top5Result.get(0), top5Price.get(0));
		hm.put(top5Result.get(1), top5Price.get(1));
		hm.put(top5Result.get(2), top5Price.get(2));
		hm.put(top5Result.get(3), top5Price.get(3));
		hm.put(top5Result.get(4), top5Price.get(4));

		Thread.sleep(5000);
		// close walmart

		// go to Amazon
		driver.get("https://www.amazon.com/");

		// Getting a set view of the Hashmap
		Set<Map.Entry<String, String>> m = hm.entrySet();

		// creating a softassert object
		SoftAssert s = new SoftAssert();

		// Traversing through each element of HashMap (each product with its price)
		for (Entry<String, String> e : m) {

			// search in amazon with each product name
			WebDriverWait wait = new WebDriverWait(driver, 20);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='twotabsearchtextbox']")));
			driver.findElement(By.xpath("//input[@id='twotabsearchtextbox']")).sendKeys(e.getKey());
			driver.findElement(By.cssSelector("input[value='Go']")).submit();
			Thread.sleep(1000);
			// Retrieve the name of the product in amazon
			String nameOfProduct = driver.findElement(By.xpath("//a[@class='a-link-normal a-text-normal']/span[1]"))
					.getText();
			System.out.println(nameOfProduct);
			Thread.sleep(1000);

			// soft assert the product name with the walmart product
			s.assertEquals(nameOfProduct, e.getKey(), "The products does not match");

			// retrieve the price of the product in amazon
			String priceOfProduct = driver.findElement(By.xpath("//span[@class='a-price']/span[1]"))
					.getAttribute("innerText");

			// soft assert the price with the walmart product price
			s.assertEquals(priceOfProduct, e.getValue(), "The product price does not match");

			// clear the search box for next product search
			driver.findElement(By.xpath("//input[@id='twotabsearchtextbox']")).clear();

		}
		s.assertAll();

		driver.close();
	}
}