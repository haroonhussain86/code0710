import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;


public class DeluxeApartmentTestUpdate {
    public WebDriver driver;
    public WebElement BookingBox;
    public WebElement AvailabilityContainer;
    private Map<String, Object> vars;
    JavascriptExecutor js;
    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        js = (JavascriptExecutor) driver;
        vars = new HashMap<String, Object>();
    }
    @After
    public void tearDown() {
        driver.quit();
    }
    @Test
    public void example() throws IOException, InterruptedException {
        String url = "https://www.clock-software.com/demo-clockpms/index.html";
        driver.get(url);
        driver.manage().window().setSize(new Dimension(1050, 660));
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        boolean hasDeluxe = false;
        boolean isHomepage = IamHomePage();

        if(!isHomepage){
            // return error
            System.out.println("Booking Box not found");
            return;
        }

        // find required inputs
        WebElement ArrivalInput = BookingBox.findElement(By.id("date-start"));
        WebElement NightNoInput = BookingBox.findElement(By.id("to-place"));
        WebElement TravelersAdultNoInput = BookingBox.findElement(By.name("wbe_product[adult_count]"));
        WebElement TravelersChildrenNoInput = BookingBox.findElement(By.name("wbe_product[children_count]"));
        WebElement SubmitButton = BookingBox.findElement(By.xpath("//*[@id=\"flights\"]/form/div/div[5]/input"));

        // select a valid date , number of rooms and start booking process
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String date = simpleDateFormat.format(new Date());
        System.out.println("Arrival date :" + date);
        ArrivalInput.sendKeys("13-03-2023"); // include dateformat as this changes with dates moving forward
        NightNoInput.clear();
        NightNoInput.sendKeys("4"); // find consecutive dates 4 nights
        TravelersAdultNoInput.clear();
        TravelersAdultNoInput.sendKeys("2");
        TravelersChildrenNoInput.clear();
        TravelersChildrenNoInput.sendKeys("0");
        SubmitButton.click();

        List <WebElement> BookingContainer = driver.findElements(By.cssSelector(".bookable-container"));
        System.out.println("We have # " + BookingContainer.size() + " Booking options");
        //find the most expensive package
        for (WebElement Booking : BookingContainer) {
            String BookingTitle = Booking.findElement(By.cssSelector(".row > div > h2")).getText();
            System.out.println(BookingTitle);
            if(BookingTitle.equals("Deluxe Appartment")) {
                System.out.println("Deluxe Apartment found");
                return;
            }

        }

        // if no deluxe apartment found
        System.out.println("Deluxe Apartment not found");
        return;

//        while(!hasDeluxe) {
//            //find deluxe
//            boolean foundDeluxeApartment = hasDeluxeApartment();
//
//            if(foundDeluxeApartment) {
//                // return error
//                hasDeluxe = true;
//            } else {
//                // click check availability
//                searchDeluxeApartment();
//            }
//
//        }


        // check for delexue apartment find maximum price add it only if available
        // if its not available click check available availablity calender , check dates availability and find 4 consecutive days when search button is clicked
        // only hard code names details etc
        //TODO: Under deluxe apartment , select the most expensive package
        //get room types
    }

    public boolean IamHomePage() {
        BookingBox = driver.findElement(By.xpath("//*[@id=\"clockdemo-page\"]/div[2]/div[2]/div/div/div/div[1]/div"));
        return BookingBox.isDisplayed();
    }

    public void Homepage(WebElement BookingBox) {
        // find required inputs
        WebElement ArrivalInput = BookingBox.findElement(By.id("date-start"));
        WebElement NightNoInput = BookingBox.findElement(By.id("to-place"));
        WebElement TravelersAdultNoInput = BookingBox.findElement(By.name("wbe_product[adult_count]"));
        WebElement TravelersChildrenNoInput = BookingBox.findElement(By.name("wbe_product[children_count]"));
        WebElement SubmitButton = BookingBox.findElement(By.xpath("//*[@id=\"flights\"]/form/div/div[5]/input"));

        // select a valid date , number of rooms and start booking process
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String date = simpleDateFormat.format(new Date());
        System.out.println("Arrival date :" + date);
        ArrivalInput.sendKeys("13-03-2023"); // include dateformat as this changes with dates moving forward
        NightNoInput.clear();
        NightNoInput.sendKeys("4"); // find consecutive dates 4 nights
        TravelersAdultNoInput.clear();
        TravelersAdultNoInput.sendKeys("2");
        TravelersChildrenNoInput.clear();
        TravelersChildrenNoInput.sendKeys("0");
        SubmitButton.click();
    }

    public boolean hasDeluxeApartment(){
        List <WebElement> BookingContainer = driver.findElements(By.cssSelector(".bookable-container"));
        System.out.println("We have # " + BookingContainer.size() + " Booking options");
        //find the most expensive package
        for (WebElement Booking : BookingContainer) {
            String BookingTitle = Booking.findElement(By.cssSelector(".row > div > h2")).getText();
            System.out.println(BookingTitle);
            if(BookingTitle.equals("Deluxe Appartment")) {
                System.out.println("Deluxe Apartment found");
                return true;
            }

        }

        // if no deluxe apartment found
        System.out.println("Deluxe Apartment not found");
        return false;
    }


    public void searchDeluxeApartment() {
        // click check availability
        clickCheckAvailabilityBtn();
        // click 24 days view
        WebElement AvDaysSelect =  driver.findElement(By.xpath("//*[@id=\"form_period_days\"]"));
        Select AvDropdown = new Select(AvDaysSelect);
        AvDropdown.selectByValue("24");
        // wait 3 seconds
        new WebDriverWait(driver,3);
        // find deluxe row
        WebElement DeluxeApartmentRow = driver.findElement(By.xpath("//*[@id=\"products_calendar\"]/div[4]/div/div/div[2]"));

        List <WebElement> DeluxeApartmentDays = DeluxeApartmentRow.findElements(By.className("wbe_rate_availability_calendar"));
        System.out.println("We have # " + DeluxeApartmentDays.size() + " Deluxe Apartment options");

        // check if four consecutive days are deluxe
        // select start date
    }

    public void clickCheckAvailabilityBtn(){
//        WebElement CheckAvailabilityBtn = driver.findElements(By.className("left-cell"));
        System.out.print(driver.findElements(By.className("calendar_link_section")));
//        CheckAvailabilityBtn.click();
    }

}
