package Launch;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.NoSuchElementException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Keywords {
    public WebDriver driver;
    public JavascriptExecutor js;
    private Properties properties;

    // Constructor to load the properties file
    public Keywords() {
        properties = new Properties();
        try (FileInputStream fis = new FileInputStream("config.properties")) {
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load configuration file.");
        }
    }

    // Setup WebDriver and Open Browser
    public void openBrowser() {
        String browser = properties.getProperty("browser");
        System.out.println("Launching "+browser.toUpperCase()+" Browser");
        try {
            switch (browser.toLowerCase()) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    driver = new ChromeDriver();
                    break;
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver();
                    break;
                case "edge":
                default:
                    WebDriverManager.edgedriver().setup();
                    driver = new EdgeDriver();
                    break;
            }
            js = (JavascriptExecutor) driver;
            String url = properties.getProperty("url");
            driver.get(url);
            driver.manage().window().maximize();
            System.out.println("Browser opened and navigated to the URL.");
        } catch (Exception e) {
            System.out.println("An error occurred while opening the browser: " + e.getMessage());
        }
    }

    // Verify URL
    public void verifyURL(String URL) {
    	String actualurl = properties.getProperty(URL);
        String expectedURL = driver.getCurrentUrl();
        System.out.println("Expected Title: " + actualurl);
        System.out.println("Actual Title: " + expectedURL);
        
        Assert.assertEquals(expectedURL, expectedURL, "The title does not match the expected value!");
    }
    // Verify URL
    public void verifyTitle(String Title) {
    	String actualtitle = properties.getProperty(Title);
        String expectedtitle = driver.getTitle();
        System.out.println("Expected Title: " + expectedtitle);
        System.out.println("Actual Title: " + actualtitle);
        
        Assert.assertEquals(actualtitle, expectedtitle, "The title does not match the expected value!");

    }

    // Click an element
    public void clickElement(String elementKey) throws InterruptedException {
        try {
            String xpath = properties.getProperty(elementKey);
            WebElement element = driver.findElement(By.xpath(xpath));
            element.click();
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println("Failed to click element: " + elementKey + ". Error: " + e.getMessage());
        }
    }

    public void scrollToElement(String xpath) {
    	System.out.println("Scrolling To Element :"+xpath);
        try {
            WebElement element = driver.findElement(By.xpath(xpath)); // Locate the element
            js.executeScript("arguments[0].scrollIntoView(true);", element); // Scroll to the element
            System.out.println("Scrolled to element with XPath: " + xpath);
        } catch (Exception e) {
            System.out.println("Failed to scroll to element. Error: " + e.getMessage());
        }
        System.out.println("Scrolling To Element :"+xpath +"done");
    }


    public void takeScreenshot(String fileName) {
        try {
            // Create a folder named "screenshots" in the project directory if it doesn't exist
            Path screenshotDir = Paths.get("screenshots");
            if (!Files.exists(screenshotDir)) {
                Files.createDirectories(screenshotDir);
                System.out.println("Created directory: " + screenshotDir.toAbsolutePath());
            }
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Path destinationPath = screenshotDir.resolve(fileName);
            Files.copy(screenshot.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);

            System.out.println("Screenshot saved to " + destinationPath.toAbsolutePath());
        } catch (Exception e) {
            System.out.println("Failed to take screenshot. Error: " + e.getMessage());
        }
    }


    // Select a checkbox based on its label
    public void selectCheckbox(String checkboxKey) {
        try {
            String xpath = properties.getProperty(checkboxKey);
            WebElement checkbox = driver.findElement(By.xpath(xpath));
            if (!checkbox.isSelected()) {
                checkbox.click();
            }
            System.out.println("Checkbox selected: " + checkboxKey);
        } catch (Exception e) {
            System.out.println("Failed to select checkbox: " + checkboxKey + ". Error: " + e.getMessage());
        }
    }
    
    
    public void SliderTest(String Xpath,int value,String Slidertype) throws Throwable {
    	String slider_xpath = properties.getProperty(Xpath);
    	WebElement slider = driver.findElement(By.xpath(slider_xpath));
    	scrollToElement(slider_xpath);
    	
    	if (Slidertype.equals("SliderBar")) {
    		 System.out.println("Adjusting slide bar with value :"+value);	    
		    js.executeScript("arguments[0].value = arguments[1]; arguments[0].dispatchEvent(new Event('change'));", slider, value);
    		    
    		 System.out.println("Slider value set to " + value);
    		
    	}
    	else if (Slidertype.equals("SliderTextBox")) {
			System.out.println("Entering Slider TextBox Value"+value);
			slider.clear();
			Thread.sleep(500);
			slider.sendKeys("560");
			Thread.sleep(500);
//			js.executeScript("arguments[0].value='" + value +"';", slider);
//			js.executeScript("arguments[0].dispatchEvent(new Event('change'));", slider); 
			
		} 

    	//verify value with text box
	    String actualValue = slider.getAttribute("value").trim(); // Trim any extra spaces
	    
	    String expectedValue = String.valueOf(value).trim();
		System.out.println("Actual Value :" +actualValue);
		System.out.println("Expected Value :" +expectedValue);
        Assert.assertEquals(actualValue, expectedValue, "Text box value does not match the expected value.");
    	   	
    }
    
    public void CheckCPTcodes(String[] checkboxLabels) throws Throwable {
        for (String checkboxText : checkboxLabels) {
            try {
            	String check_box = "//*[.='" + checkboxText + "']";
            	System.out.println(check_box);
            	Thread.sleep(1000);
            	scrollToElement(check_box);
                WebElement checkboxLabel = driver.findElement(By.xpath(check_box));
                
                ;
                // If the checkbox label is displayed
                if (checkboxLabel.isDisplayed()) {
                    // Locate the checkbox input element related to the label and click it
                    WebElement checkboxInput = driver.findElement(By.xpath("//*[.='" + checkboxText + "']/ancestor::div[@class='MuiBox-root css-1eynrej']/descendant::input[@class='PrivateSwitchBase-input css-1m9pwf3']"));
                    checkboxInput.click();
                    System.out.println("Checkbox for " + checkboxText + " clicked.");
                }
            } catch (NoSuchElementException e) {
                System.out.println("Element for " + checkboxText + " not found.");
            }
        }
    }
    
    public void Validate_Recurring_Reimbursement(String ActualAmount, String ExpectedAmount) {
    	System.out.println("Validating Recurring Amount");
        String value = properties.getProperty(ActualAmount);
        WebElement amount = driver.findElement(By.xpath(value));
        
        String Actual_Amount = amount.getText();
        
		System.out.println("Actual Value :" +Actual_Amount);
		System.out.println("Expected Value :" +ExpectedAmount);
    	
        Assert.assertEquals(Actual_Amount, ExpectedAmount, "The actual amount does not match the expected value.");

    }


    // Close the browser
    public void tearDown() throws Throwable {
        if (driver != null) {
            driver.quit();;
            Thread.sleep(1500);
            System.out.println("Browser closed.");
        }
    }
}
