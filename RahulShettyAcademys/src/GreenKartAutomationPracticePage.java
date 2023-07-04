import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class GreenKartAutomationPracticePage {

	WebDriver driver;

	@BeforeClass
	public void BrowserExtension() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.get("https://rahulshettyacademy.com/seleniumPractise/#/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}

	@Test(priority = 1)
	public void CollectionVeggiesList() {
		String[] vegNames = { "Cucumber", "Carrot", "Potato" };
		addItems(driver, vegNames);
		driver.findElement(By.cssSelector("img[alt='Cart']")).click();
		driver.findElement(By.xpath("//button[normalize-space()='PROCEED TO CHECKOUT']")).click();
		driver.findElement(By.cssSelector("input[placeholder='Enter promo code']")).sendKeys("rahulshettyacademy");
		driver.findElement(By.cssSelector(".promoBtn")).click();
		System.out.println(driver.findElement(By.cssSelector(".promoInfo")).getText());
		driver.findElement(By.xpath("//button[normalize-space()='Place Order']")).click();
		WebElement Options = driver.findElement(By.cssSelector("div[class='wrapperTwo'] div select"));
		Select dropdown = new Select(Options);
		dropdown.selectByValue("India");
		driver.findElement(By.cssSelector("input[type='checkbox']")).click();
		driver.findElement(By.xpath("//button[normalize-space()='Proceed']")).click();
	}

	//@Test(priority = 2)
	public void addItems(WebDriver driver2, String[] vegNames) {
		int j = 0;
		List<WebElement> products = driver.findElements(By.cssSelector("h4[class='product-name']"));

		for (int i = 0; i < products.size(); i++) {

			String[] name = products.get(i).getText().split("-");
			String formattedName = name[0].trim();
			// format it to get actual vegetable name
			// check whether name you extracted is present in array or not-
			// convert array into array list for easy search
			// check whether name you extracted is present in arraylist or not-

			List itemsNeededList = Arrays.asList(vegNames);

			if (itemsNeededList.contains(formattedName)) {
				j++;
				// click on add to cart
				// driver.findElements(By.xpath("//button[text()='ADD TO
				// CART']")).get(i).click();

				driver.findElements(By.xpath("//div[@class='product-action']/button")).get(i).click();

				if (j == vegNames.length)
					break;
			}
		}
	}
	
	@Test(priority = 2)
	public void TopDeals()
	{
		driver.findElement(By.linkText("Top Deals")).click();
		
		Set<String> windows = driver.getWindowHandles();  //[parentid, childid, subChildID]
		Iterator<String> it = windows.iterator();
		String parentId = it.next();
		String childId = it.next();
		driver.switchTo().window(childId);
		
		//Click on column
		driver.findElement(By.xpath("//tr/th[1]")).click();
		
		//capture all web elements into list
		List<WebElement> elementsList = driver.findElements(By.xpath("//tr/td[1]"));
		
		//capture text of all webelements into new(original) list
		List<String> originalList = elementsList.stream().map(s -> s.getText()).collect(Collectors.toList());
		
		//sort on the original list of step 3 -> sorted list
		
		List<String> sortedList = originalList.stream().sorted().collect(Collectors.toList());
		
		//compare original list vs sorted list
		Assert.assertTrue(originalList.equals(sortedList));
		
		//scan the name column with getText -> Beans -> print the price of the Beans
		/*List<String> price = elementsList.stream().filter(s -> s.getText().contains("Beans")).map(s -> getPriceVeggie(s)).
				collect(Collectors.toList());
		price.forEach(a -> System.out.println(a)); */
		
		//scan the name column with getText -> Rice -> print the price of the Rice
		List<String> price;
		do
		{
			List<WebElement> rows = driver.findElements(By.xpath("//tr/td[1]"));
			price = rows.stream().filter(s -> s.getText().contains("Rice")).map(s -> getPriceVeggie(s)).
					collect(Collectors.toList());
			price.forEach(a -> System.out.println(a));
			
			if(price.size()<1)
			{	
				driver.findElement(By.cssSelector("[aria-label = 'Next']")).click();
				
			}
		}while(price.size()<1);
		
	}
	
	public static String getPriceVeggie(WebElement s)
	{
		String priceValue = s.findElement(By.xpath("following-sibling::td[1]")).getText();
		return priceValue;
		
	}
	
	@Test(priority = 3)
	public void SearchTable()
	{
		driver.findElement(By.id("search-field")).sendKeys("Rice");
		List<WebElement> veggies = driver.findElements(By.xpath("//tr/td[1]"));
		//1 results
		
		List<WebElement> filteredList = veggies.stream().filter(veggie -> veggie.getText().contains("Rice")).collect(Collectors.toList());
		//1 results
		
		Assert.assertEquals(veggies.size(), filteredList.size());
		
		
				
	}
	
	@AfterClass
	public void TerminateBrowser()
	{
		driver.quit();
	}

}
