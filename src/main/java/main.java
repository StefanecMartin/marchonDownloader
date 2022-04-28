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
            os = new FileOutputStream("images/" + destinationFile + ".png");

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
        driver.get("https://my.keringeyewear.com/keringeyewear/en/login/");

        Thread.sleep(5000);
        //driver.findElement(By.xpath("//*[@id=\"login-wrap\"]/div[3]/input")).sendKeys("info@colarc.com");
        //driver.findElement(By.xpath("//*[@id=\"login-wrap\"]/div[4]/input")).sendKeys("@Amiata2018");
        driver.findElement(By.xpath("//*[@id=\"login-wrap\"]/div[3]/input")).sendKeys("adam@eyerim.com");
        driver.findElement(By.xpath("//*[@id=\"login-wrap\"]/div[4]/input")).sendKeys("Ajrimka@2019");

        driver.findElement(By.xpath("//*[@id=\"login-wrap\"]/div[6]/button")).click();


        //driver.get("https://my.keringeyewear.com/keringeyewear/en//Brands/Saint-Laurent/c/SLP");
        //driver.get("https://my.keringeyewear.com/keringeyewear/en//Brands/Bottega-Veneta/c/BTV");
        driver.get("https://my.keringeyewear.com/keringeyewear/en//Brands/Gucci/c/GUC");
        //driver.get("https://my.keringeyewear.com/keringeyewear/en//Brands/Alexander-McQueen/c/AMQ");
        //driver.get("https://my.keringeyewear.com/keringeyewear/en//Brands/McQ/c/MCQ");
        //driver.get("https://my.keringeyewear.com/keringeyewear/en//Brands/Puma/c/PUM");
        //driver.get("https://my.keringeyewear.com/keringeyewear/en//Brands/Balenciaga/c/BAL");
        //driver.get("https://my.keringeyewear.com/keringeyewear/en//Brands/Montblanc/c/MMM");
        //driver.get("https://my.keringeyewear.com/keringeyewear/en//Brands/Chlo%C3%A9/c/CHL");


        Thread.sleep(5000);


        String noOfProducts=driver.findElement(By.xpath("//*[@id=\"ke-main\"]/div[4]/div/label[2]")).getText();
        System.out.println(noOfProducts);
        double numberOfProducts = parseDouble(noOfProducts);
        Thread.sleep(5000);
        for(int i= 1;i<=numberOfProducts/24;i++){
            Thread.sleep(10000);
            driver.findElement(By.xpath("//*[@id=\"ke-main\"]/div[6]/div[3]/div/button")).click();
            System.out.println((i*24+24)+"/" + numberOfProducts);
            Thread.sleep(10000);
        }

        int offset =0;
        int counter=offset*24+1;


        for(int i=offset+1;i<=numberOfProducts/24+1;i++){
            for(int j=1;j<=24;j++) {
                if(counter==numberOfProducts) break;
                driver.findElement(By.xpath("//*[@id=\"resultsList\"]/div[1]/div[" + i + "]/div[" + j + "]/a")).sendKeys(Keys.CONTROL, Keys.ENTER);
                tabs2 = new ArrayList<String>(driver.getWindowHandles());
                driver.switchTo().window(tabs2.get(1));
                System.out.println(counter+"/"+numberOfProducts+" noOfColors: "+(Integer.valueOf(driver.findElements(By.xpath("/html/body/div[2]/div[7]/div[3]/div")).size())-1));
                try {
                    for (int k = 2; k <= driver.findElements(By.xpath("/html/body/div[2]/div[7]/div[3]/div")).size(); k++) {
                        String msku = driver.findElement(By.xpath("/html/body/div[2]/div[7]/div[3]/div[" + k + "]/div[2]/div/form/div[1]/div[1]/h4")).getText();

                        //try to download image from clp
                        // /html/body/div[2]/div[7]/div[3]/div[++++++++++++k]/div[2]/div/form/div[1]/div[1]/div/div[2]/img
                        if (driver.findElements(By.xpath("/html/body/div[2]/div[7]/div[3]/div[" + k + "]/div[2]/div/form/div[1]/div[1]/div/div[2]/img")).size() != 0) {
                            saveImage(driver.findElement(By.xpath("/html/body/div[2]/div[7]/div[3]/div[" + k + "]/div[2]/div/form/div[1]/div[1]/div/div[2]/img")).getAttribute("src").replace("/l/","/xl/"), msku);
                        }

                        driver.findElement(By.xpath("/html/body/div[2]/div[7]/div[3]/div[" + k + "]/div[2]/div/form/div[1]/div[1]/div/div[2]/img")).click();
                        //try to download image from pdp
                        if (driver.findElements(By.xpath("//*[@id=\"ke-main\"]/div[3]/div[" + k + "]/div[1]/div/div/div[3]/div/div[1]/ul/li[2]/img")).size() != 0) {
                            saveImage(driver.findElement(By.xpath("//*[@id=\"ke-main\"]/div[3]/div[" + k + "]/div[1]/div/div/div[3]/div/div[1]/ul/li[2]/img")).getAttribute("src"), msku);
                        }
                        driver.findElement(By.xpath("//*[@id=\"ke-main\"]/div[3]/div[" + k + "]/div[1]/div/div/div[1]")).click();


                        Thread.sleep(1000);

                    }
                } catch (Exception e) {
                    //  Block of code to handle errors
                }
                counter++;
                driver.close();
                driver.switchTo().window(tabs2.get(0));
            }
        }




    }
}