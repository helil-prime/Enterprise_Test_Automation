package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utilities.Driver;

public class AmazonTestsPage {
	
	public AmazonTestsPage() {
		PageFactory.initElements(Driver.getDriver(), this);
	}
	
	@FindBy (id = "searchDropdownBox")
	public WebElement departmentsDropdown;
	
	@FindBy (id = "twotabsearchtextbox")
	public WebElement searchField;
	
	@FindBy (id = "nav-search-submit-button")
	public WebElement searchButton;

}
