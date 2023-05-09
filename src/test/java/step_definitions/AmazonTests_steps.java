package step_definitions;

import org.junit.Assert;
import org.openqa.selenium.Keys;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.AmazonTestsPage;
import utilities.BrowserUtils;
import utilities.Driver;

public class AmazonTests_steps {

	AmazonTestsPage amazonpage = new AmazonTestsPage();
	BrowserUtils utils = new BrowserUtils();

	@Given("I am on the amazon homepage")
	public void i_am_on_the_amazon_homepage() {
		Driver.getDriver().get("https://amazon.com");
		String homepageTitle = Driver.getDriver().getTitle();
		Assert.assertEquals(homepageTitle, "Amazon.com. Spend less. Smile more.");
	}

	@Given("The departments dropdown is {string}")
	public void the_departments_dropdown_is(String defaultOption) {
//	   Select letsSelect = new Select(ahomepage.departmentsDropdown);
//	   Assert.assertEquals(letsSelect.getFirstSelectedOption(), defaultOption);

		Assert.assertEquals(utils.getSelectedOption(amazonpage.departmentsDropdown), defaultOption);

	}

	@When("I select the department {string}")
	public void i_select_the_department(String optionToSelected) {
		utils.selectByVisibleText(amazonpage.departmentsDropdown, optionToSelected);
	}

	@When("I search {string}")
	public void i_search(String searchItem) {
		amazonpage.searchField.sendKeys(searchItem, Keys.ENTER);
	}

	@Then("I should be on {string} search result page")
	public void i_should_be_on_search_result_page(String searchedItem) {
		String searchPageTitle = Driver.getDriver().getTitle();
		Assert.assertTrue(searchPageTitle.contains(searchedItem));
	}
}
