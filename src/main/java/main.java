import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.Vector;
import java.lang.String;
import java.util.ArrayList;
import java.io.IOException;

import static java.lang.Double.parseDouble;


public class main {

    static void waitForInvisibility(WebElement webElement, int maxSeconds) {
        Long startTime = System.currentTimeMillis();
        try {
            while (System.currentTimeMillis() - startTime < maxSeconds * 1000 && webElement.isDisplayed()) {
            }
        } catch (StaleElementReferenceException e) {
            return;
        }
    }

    public static void saveImage(String imageUrl, String destinationFile) throws IOException {
        URL url = new URL(imageUrl);
        InputStream is = url.openStream();
        OutputStream os = null;
        try{
            os = new FileOutputStream("images/" + destinationFile + ".jpg");

            byte[] b = new byte[2048];
            int length;

            while ((length = is.read(b)) != -1) {
                os.write(b, 0, length);
            }
            System.out.println(destinationFile + " " + imageUrl);
            is.close();
            os.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException, IOException {

        ChromeDriver driver = null;
        String currentURL = null;
        ArrayList<String> tabs2 = null;
        WebElement element2 = null;
        WebElement element3 = null;
        WebElement element5 = null;
        String results = null;
        String[] results2;
        Vector<String> urls = new Vector<String>();
        int numberOfResults, pocetfarieb;
        List<WebElement> refList;
        PrintWriter writer = null;
        /*String csvFile = "C:/Users/Martin/Documents/smartbuyglasses/file.csv";
        try {
            writer = new PrintWriter("file.csv", "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        writer.println("name,size,upc");
        //String ids = new String (driver.getWindowHandle());*/

        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver3.exe");
        driver = new ChromeDriver();
        WebElement element;
        driver.get("https://account.mymarchon.com/bpm/AccountHome/");

        Thread.sleep(5000);
        //driver.findElement(By.xpath("//*[@id=\"login-wrap\"]/div[3]/input")).sendKeys("info@colarc.com");
        //driver.findElement(By.xpath("//*[@id=\"login-wrap\"]/div[4]/input")).sendKeys("@Amiata2018");
        driver.findElement(By.xpath("//*[@id=\"loginForm\"]/div[1]/input")).sendKeys("1755901");
        driver.findElement(By.xpath("//*[@id=\"loginForm\"]/div[2]/input")).sendKeys("Monte@Amiata2021!");

        driver.findElement(By.xpath("//*[@id=\"loginForm\"]/div[3]/button")).click();


        //driver.get("https://account.mymarchon.com/baw/MVP2/it/#/baw/MVP2/it/brand-collection/LA/eyewear");
        driver.get("https://account.mymarchon.com/baw/MVP2/it/#/baw/MVP2/it/brand-collection/NI/eyewear");

        Thread.sleep(5000);
        while(driver.findElements(By.xpath("/html/body/app-root/app-spinner/div/i")).size()!=0){
            Thread.sleep(1000);
        }

        int noOfProducts=driver.findElements(By.xpath("/html/body/app-root/div/app-brand-collection/app-eyewear/app-product-list/div[5]/div[2]/div/div")).size();
        System.out.println(noOfProducts);
        int offset =0;


        for(int i = offset+1; i<= noOfProducts; i++){
            driver.findElement(By.xpath("/html/body/app-root/div/app-brand-collection/app-eyewear/app-product-list/div[5]/div[2]/div/div["+i+"]/app-frame-image/div")).click();
            Thread.sleep(5000);
            System.out.println(i+"/"+ noOfProducts +" noOfColors: "+(Integer.valueOf(driver.findElements(By.xpath("//*[@id=\"stock-order\"]/app-stock-order/form/div[2]/div")).size())-1));
            //*[@id="stock-order"]/app-stock-order/form/div[2]/div[5]/div[1]/img
            //*[@id="stock-order"]/app-stock-order/form/div[2]/div[4]/div[1]/img
            try {
                for (int k = 2; k <= driver.findElements(By.xpath("//*[@id=\"stock-order\"]/app-stock-order/form/div[2]/div")).size(); k++) {
                    String msku = driver.findElement(By.xpath("/html/body/app-root/div/app-brand-collection/app-eyewear/app-order-detail/div/app-product-detail/div[1]/div[2]/div[2]")).getText()+" "+ driver.findElement(By.xpath("//*[@id=\"stock-order\"]/app-stock-order/form/div[2]/div["+k+"]/div[1]/span")).getText().split(" ")[0];

                    //try to download image
                    saveImage(driver.findElement(By.xpath("//*[@id=\"stock-order\"]/app-stock-order/form/div[2]/div["+k+"]/div[1]/img")).getAttribute("src"), msku);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            driver.navigate().back();

        }




    }
}