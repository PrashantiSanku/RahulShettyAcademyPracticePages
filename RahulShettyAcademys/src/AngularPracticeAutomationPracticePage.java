import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.openqa.selenium.support.locators.RelativeLocator.*;

import io.github.bonigarcia.wdm.WebDriverManager;

public class AngularPracticeAutomationPracticePage {

	WebDriver driver;

	@BeforeClass
	public void InvokeBrowser() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.get("https://rahulshettyacademy.com/angularpractice/");
		driver.manage().window().maximize();

	}

	@Test(priority = 1)
	public void Selenium4RelativeLocatorAction() {
		WebElement nameEditBox = driver.findElement(By.cssSelector("[name = 'name']"));
		System.out.println(driver.findElement(with(By.tagName("label")).above(nameEditBox)).getText());

		WebElement dateOfBirth = driver.findElement(By.cssSelector("[for='dateofBirth']"));
		driver.findElement(with(By.tagName("input")).below(dateOfBirth)).click();

		WebElement iceCreamLabel = driver
				.findElement(By.xpath("//label[text()='Check me out if you Love IceCreams!']"));
		driver.findElement(with(By.tagName("input")).toLeftOf(iceCreamLabel)).click();

		WebElement radiobutton = driver.findElement(By.id("inlineRadio1"));
		System.out.println(driver.findElement(with(By.tagName("label")).toRightOf(radiobutton)).getText());
	}

	@Test(priority = 2)
	public void Execution() throws IOException {
		driver.switchTo().newWindow(WindowType.TAB);
		Set<String> handles = driver.getWindowHandles();
		Iterator<String> it = handles.iterator();
		String parentWindowId = it.next();
		String childWindow = it.next();
		driver.switchTo().window(childWindow);
		driver.get("https://rahulshettyacademy.com/");
		System.out.println(
				driver.findElement(By.xpath("//li[normalize-space()='contact@rahulshettyacademy.com']")).getText());

		String name1 = driver.findElement(By.xpath("//li[normalize-space()='contact@rahulshettyacademy.com']"))
				.getText().split("@")[1];
		String name2 = name1.split("\\.")[0].trim();
		driver.switchTo().window(parentWindowId);
		WebElement Name = driver.findElement(By.name("name"));
		Name.sendKeys(name2);
		//Screenshot
		File file = Name.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(file, new File("logo.png"));
		
		//Get Weight & Height
		System.out.println(Name.getRect().getDimension().getHeight());
		System.out.println(Name.getRect().getDimension().getWidth());
		
		driver.findElement(By.name("email")).sendKeys(name1);
		
		driver.findElement(By.xpath("//input[@type='password']")).sendKeys("12345");
		
		WebElement dropdown = driver.findElement(By.id("exampleFormControlSelect1"));
		Select sc = new Select(dropdown);
		sc.selectByVisibleText("Female");
		
		System.out.println(driver.findElements(By.cssSelector("div[class='form-group']")).size());
		driver.findElement(By.id("inlineRadio2")).click();
		driver.findElement(By.name("bday")).sendKeys("19-09-1994");
		driver.findElement(By.xpath("//input[@type='submit']")).click();
		System.out.println(driver.findElement(By.xpath("//div[contains(@class,'alert-success')]")).getText());
		
		
		

	}
	
	@AfterClass
	public void TerminateBrowser()
	{
		driver.quit();
	}

}
