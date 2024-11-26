package Launch;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class TestRunner {
	private Keywords keywords;

    @BeforeMethod
    public void setUp() {
    	keywords = new Keywords();
        keywords.openBrowser();
    }

    @Test(priority = 1)
    public void VerifyHomeTitle()
    {
    	//giving actual title as a argument
    	System.out.println("Navigate to the FitPeo Homepage:");
    	keywords.verifyURL("actual_Home_URL");
    }
    @Test(priority = 2)
    public void NavigateAndVerifyRevenuePageTitle() throws Throwable
    {
    	//giving actual title as a argument
    	System.out.println("Navigate to the Revenue Calculator Page:");
    	keywords.verifyURL("actual_Home_URL");
    	keywords.clickElement("revenueCalculatorButton");
    	keywords.verifyTitle("actual_Revenue_Title");   	
    	
    }
    
    @Test(priority = 3)
    public void VerifySlider() throws Throwable {
    	NavigateAndVerifyRevenuePageTitle();
    	System.out.println("Scroll Down to the Slider section:");
    	keywords.scrollToElement("Slider_section_xpath");
    	// screenshots will saved in screenshots folder with below png 
    	keywords.takeScreenshot("slider.png");  
    	
    }
    
    @Test(priority = 4)
    public void AdjustSlider() throws Throwable {
    	NavigateAndVerifyRevenuePageTitle();
    	keywords.SliderTest("Slider_section_xpath", 820, "SliderBar");
    	
    }

    @Test(priority = 5)
    public void ClickCPTcodes() throws Throwable {
    	System.out.println("Validating CPT Codes");
    	NavigateAndVerifyRevenuePageTitle();
    	String[] checkboxesToClick = {"CPT-99091", "CPT-99453", "CPT-99454","CPT-99474"};
    	keywords.CheckCPTcodes(checkboxesToClick);
    }
    
    
    @Test(priority = 6)  
    public void Validate_Recurring_Amount() throws Throwable {
    	System.out.println("Validating Recurring Amount");
    	ClickCPTcodes();
    	keywords.Validate_Recurring_Reimbursement("Recurring_Reimbursement","$27000");
    }
    
    
    @AfterMethod
    public void tearDown() throws Throwable {
        keywords.tearDown();
    }
}
