import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.apache.logging.log4j.LogManager;

public class HomeworkTests{
    private WebDriver driver;

    //Открыть Chrome в headless режиме
    //Перейти на https://duckduckgo.com/
    //В поисковую строку ввести ОТУС
    //Проверить что в поисковой выдаче первый результат Онлайн‑курсы для профессионалов, дистанционное обучение
    @Test
    public void headlessDuckTest(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("headless");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(options);

        driver.get("https://duckduckgo.com/");
        driver.findElement(By.id("search_form_input_homepage")).sendKeys("ОТУС");
        driver.findElement(By.id("search_button_homepage")).submit();

        WebElement element = driver.findElement(By.xpath("//div[@class='nrn-react-div']//h2//a"));
        String actualText = element.getText();
        String expectedText = "Онлайн‑курсы для профессионалов, дистанционное обучение";
        Assert.assertTrue(actualText.contains(expectedText));

        if(driver!=null)
            driver.quit();
    }

    //Открыть Chrome в режиме киоска
    //Перейти на https://demo.w3layouts.com/demos_new/template_demo/03-10-2020/photoflash-liberty-demo_Free/685659620/web/index.html?_ga=2.181802926.889871791.1632394818-2083132868.1632394818
    //Нажать на любую картинку
    //Проверить что картинка открылась в модальном окне
    @Test
    public void kioskPictureTest(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().fullscreen();

        driver.get("https://demo.w3layouts.com/demos_new/template_demo/03-10-2020/photoflash-liberty-demo_Free/685659620/web/index.html?_ga=2.181802926.889871791.1632394818-2083132868.1632394818");
        //driver.findElement(By.id("vdo_ai_cross")).submit();
        driver.findElement(By.xpath("//li[@data-id='id-2']//span")).submit();

        Assert.assertTrue(driver.findElement(By.className("pp_hoverContainer")).isDisplayed());

        if(driver!=null)
            driver.quit();
    }

    //Открыть Chrome в режиме полного экрана
    //Перейти на https://otus.ru
    //Авторизоваться под каким-нибудь тестовым пользователем(можно создать нового)
    //Вывести в лог все cookie
    @Test
    public void maximizeOtusTest(){
        Logger logger = LogManager.getLogger(Logger.class);
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        String login = "fivaxed799@runfons.com";
        String password = "testTEST1!";
        driver.get("https://otus.ru");
        auth(login, password);
        logger.info("Cookies = " + driver.manage().getCookies());

        if(driver!=null)
            driver.quit();
    }

    private void auth(String login, String password){
        driver.findElement(By.cssSelector("button.header2__auth")).click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.findElement(By.xpath("//form[@action = '/login/']//input[@name = 'email']")).sendKeys(login);
        driver.findElement(By.xpath("//form[@action = '/login/']//input[@name = 'password']")).sendKeys(password);
        driver.findElement(By.xpath("//form[@action = '/login/']//button[@type = 'submit']")).submit();
    }
}
