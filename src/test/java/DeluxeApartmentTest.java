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
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;


public class DeluxeApartmentTest {
    private WebDriver driver;
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

        WebElement BookingBox = driver.findElement(By.xpath("//*[@id=\"clockdemo-page\"]/div[2]/div[2]/div/div/div/div[1]/div"));
        if(!BookingBox.isDisplayed()){
            // return error
            System.out.println("Booking Box not found");
        }

        // find required inputs
        WebElement ArrivalInput = BookingBox.findElement(By.id("date-start"));
        WebElement NightNoInput = BookingBox.findElement(By.id("to-place"));
        WebElement TravelersAdultNoInput = BookingBox.findElement(By.name("wbe_product[adult_count]"));
        WebElement TravelersChildrenNoInput = BookingBox.findElement(By.name("wbe_product[children_count]"));
        WebElement SubmitButton = BookingBox.findElement(By.xpath("//*[@id=\"flights\"]/form/div/div[5]/input"));

        // select a valid date , number of rooms and start booking process
        ArrivalInput.sendKeys("13-03-2023");
        NightNoInput.sendKeys("5");
        TravelersAdultNoInput.sendKeys("2");
        TravelersChildrenNoInput.sendKeys("0");
        SubmitButton.click();

        new WebDriverWait(driver, 5);

        //TODO: Under deluxe apartment , select the most expensive package
        //get room types
        List <WebElement> BookingContainer = driver.findElements(By.cssSelector(".bookable-container"));
        System.out.println("We have # Booking " + BookingContainer.size());
        //find the most expensive package
        for (WebElement Booking : BookingContainer) {
            String BookingTitle = Booking.findElement(By.cssSelector(".row > div > h2")).getText();
            if(BookingTitle.equals("Double Superior Room")) {
                List <WebElement> BookingRooms = Booking.findElements(By.className("room-type"));
                Integer MaxPrice = 0;
                WebElement SelectedRoom = BookingRooms.get(0);
                // check all rooms until max price is defined
                for (WebElement Room : BookingRooms) {
                    String RoomPriceStr = Room.findElement(By.cssSelector(".text-right > h4")).getText();
                    Integer RoomPrice = Integer.parseInt(RoomPriceStr);
                    // set room price to max price if greater
                    if(RoomPrice > MaxPrice){
                        MaxPrice = RoomPrice;
                        SelectedRoom = Room;
                    }
                }

                //click on selected Room button to move to next page
                SelectedRoom.findElement(By.cssSelector(".btn-success")).click();

                // TODO: selct any 2 addons
                // find all addon elements
                // select two random number between 0 and max addons list length
                // add the two random addon from list
                // select selected button
                //TODO: validate all the details - date , no of nights, rate, add on(extra service charges), total

                // check if details on page match predefined ones

                //TODO: add travelers details and set payment method to CC
                // use sendKeys to compile details and card selection
                //TODO: use dummy payment methods and complete payment
                //use sendkeys to add card details
                //TODO: validate booking complete message
                //read to validate booking
            } else {
                            System.out.println("No Expensive Room found available");

            }

        }
    }
}
