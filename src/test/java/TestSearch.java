import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class TestSearch {
    private WebDriver driver;

    String baseurl = "https://www.service.nsw.gov.au/";
    String TestData = "C:\\Users\\Nikhil\\IdeaProjects\\AutomationTest_Quantas\\resources\\TestData.csv";

    @Test(dataProvider = "Test")
    public void serviceNow_Search(String suburbName, String filteredResult) {


        /* Launching Browser */
        WebDriverManager.chromedriver().version("2.36").setup();
        WebDriver driver = new ChromeDriver();
        // Launching webportal
        driver.get(baseurl);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        new WebDriverWait(driver, 50).until(ExpectedConditions.elementToBeClickable(By.id("edit-contains"))).sendKeys("Apply for a number plate");
        driver.findElement(By.id("edit-submit-site-search")).click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        new WebDriverWait(driver, 30).until(ExpectedConditions.elementToBeClickable(By.linkText("Apply for a number plate"))).click();
        String pageTitle = driver.getTitle();
        System.out.println(pageTitle);
        String expectedTitle = "Apply for a number plate | Service NSW";

        //Validate the navigation to appropriate page
        Assert.assertEquals(pageTitle, expectedTitle);

        driver.findElement(By.xpath("/html/body/div[3]/header/div/div[2]/div[1]/a")).click();

        driver.findElement(By.xpath("//*[@id=\"locatorTextSearch\"]")).sendKeys(suburbName);
        driver.findElement(By.xpath("//*[@id=\"locator\"]/div/div/form/div/div[2]/button")).click();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//*[@id=\"locatorTextSearch\"]")).clear();

        List<WebElement> links = driver
                .findElements(By.xpath("//*[@id=\"locatorListView\"]/div/div/div//a"));

        System.out.println(links.size());
        for (WebElement myElement : links) {
            if (myElement.getText().contains(filteredResult)) {
                myElement.click();

            }
        }


    }

    @DataProvider(name = "Test")
    public Object[][] passData() {
        Object[][] data = new Object[2][2];
        data[0][0] = "Sydney 2000";
        data[0][1] = "Marrickville Service Centre";
        data[1][0] = "Sydney Domestic Airport 2020";
        data[1][1] = "Rockdale Service Centre";

        return data;
    }
}
