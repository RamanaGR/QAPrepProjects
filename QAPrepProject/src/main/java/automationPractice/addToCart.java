////Assignment : Automate "Buy Product" Functionality for Dummy E-commerce Website using Selenium.
//Steps to Automate:
//1. Open link "http://automationpractice.com/index.php"
//2. Login to the website.
//    UN:training.qaprep@gmail.com
//    PW: Testing123
//3. Move your cursor over Women's link.
//4. Click on sub menu 'T-shirts'.
//5. Mouse hover on the product displayed.
//6. 'More' button will be displayed, click on 'More' button.
//7. Increase quantity to 2.
//8. Select size 'L'
//9. Select color.
//10. Click 'Add to Cart' button.
//11. Click 'Proceed to checkout' button.
//12. Complete the buy order process till payment.
//13. Make sure that Product is ordered.(Assert the text).

package automationPractice;

import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

public class addToCart {

	@Test
	public void buy() throws InterruptedException {
		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\cvheg\\eclipse-workspace\\QAPrepProject\\Resource\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("http://automationpractice.com/index.php");

		driver.findElement(By.cssSelector("#header > div.nav > div > div > nav > div.header_user_info > a")).click();
		Thread.sleep(9000);
		driver.findElement(By.cssSelector("#email")).sendKeys("training.qaprep@gmail.com");
		driver.findElement(By.xpath("//*[@id=\"passwd\"]")).sendKeys("Testing123");
		driver.findElement(By.id("SubmitLogin")).click();

		// Move your cursor over Women's link.
		WebElement linkWomen = driver.findElement(By.xpath("//a[text()='Women']"));
		// Click on sub menu 'T-shirts'.
		WebElement linkTshirts = driver.findElement(By.xpath("//a[text()='T-shirts']"));
		Actions act = new Actions(driver);
		act.moveToElement(linkWomen).perform();
		act.moveToElement(linkTshirts).click().perform();

		WebElement productImage = driver.findElement(By.className("product_img_link"));
		act.moveToElement(productImage).perform();
		driver.findElement(By.xpath("//*[@id=\"center_column\"]/ul/li/div/div[2]/div[2]/a[2]")).click();

		driver.findElement(By.cssSelector("#quantity_wanted_p > a.btn.btn-default.button-plus.product_quantity_up"))
				.click();
		WebElement size = driver.findElement(By.xpath("//select[@id='group_1']"));
		Select s = new Select(size);
		s.selectByVisibleText("L");
		driver.findElement(By.xpath("//*[@id=\"color_14\"]")).click();
		// Click 'Add to Cart' button.
		driver.findElement(By.xpath("//*[@id=\"add_to_cart\"]/button")).click();

		Thread.sleep(5000);
		// Click 'Proceed to checkout' button.
		String parentWindow = driver.getWindowHandle();

		Set<String> windows = driver.getWindowHandles();
		for (String w : windows) {
			if (!w.equals(parentWindow)) {
				driver.switchTo().window(w);
			}
		}
		driver.findElement(By.xpath("//*[@id=\"layer_cart\"]/div[1]/div[2]/div[4]/a")).click();

		driver.switchTo().window(parentWindow);

		driver.findElement(By.cssSelector(
				"#center_column > p.cart_navigation.clearfix > a.button.btn.btn-default.standard-checkout.button-medium"))
				.click();

		driver.findElement(By.xpath("//*[@id=\"center_column\"]/form/p/button")).click();

		driver.findElement(By.xpath("//input[@id='cgv']")).click();
		driver.findElement(By.xpath("//*[@id=\"form\"]/p/button")).click();

		driver.findElement(By.xpath("//a[@class='cheque']")).click();

		driver.findElement(By.xpath("//*[@id=\"cart_navigation\"]/button")).click();

		String confirmation = driver.findElement(By.xpath("//p[@class='alert alert-success']")).getText();
		Assert.assertEquals(confirmation, "Your order on My Store is complete.");

		driver.quit();
	}
}
