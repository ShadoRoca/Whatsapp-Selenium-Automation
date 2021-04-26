package com.project.sel;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.Keys;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;


public class WpAP {

    static WebDriver driver;

    static WebElement searchBox;

    private WebElement chatBox;

    private String contact;

    private String message;

    private int times;

    private Timer timer = new Timer();

    private DateFormat dateformatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    static Scanner input = new Scanner(System.in);


    public void simpleMessage(String contact, String message) throws InterruptedException{
        searchBox.click();
        searchBox.sendKeys(contact);
        new WebDriverWait(driver, 3).until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@title='"+ contact +"']")));
        WebElement target = driver.findElement(By.xpath("//span[@title='"+ contact +"']"));
        Thread.sleep(500);
        target.click();
        System.out.println("--Ubicando cuadro de chat...");
        new WebDriverWait(driver, 3).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"main\"]/footer/div[1]/div[2]/div/div[2]")));
        WebElement chatBox = driver.findElement(By.xpath("//*[@id=\"main\"]/footer/div[1]/div[2]/div/div[2]"));
        System.out.println("--Seleccionando cuadro de chat...");
        chatBox.click();
        chatBox.sendKeys(this.message + Keys.ENTER);
    }

    public void multipleMessages(String contact, String message, int times) throws InterruptedException{
        searchBox.click();
        searchBox.sendKeys(contact);
        new WebDriverWait(driver, 3).until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@title='"+ contact +"']")));
        WebElement target = driver.findElement(By.xpath("//span[@title='"+ contact +"']"));
        Thread.sleep(500);
        target.click();
        System.out.println("--Ubicando cuadro de chat...");
        new WebDriverWait(driver, 3).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"main\"]/footer/div[1]/div[2]/div/div[2]")));
        WebElement chatBox = driver.findElement(By.xpath("//*[@id=\"main\"]/footer/div[1]/div[2]/div/div[2]"));
        System.out.println("--Seleccionando cuadro de chat...");
        chatBox.click();
        for (int i = 0; i < times ; i++) {
            chatBox.sendKeys(this.message + Keys.ENTER);
        }  
    }

    public void multipleContacts(String contacts, String message) throws InterruptedException {
        String[] targets = contacts.split("//");
        for(int i = 0; i < targets.length; i++) {
            String dummyContact = targets[i].trim();
            searchBox.click();
            searchBox.sendKeys(dummyContact);
            new WebDriverWait(driver, 3).until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@title='"+ dummyContact +"']")));
            WebElement target = driver.findElement(By.xpath("//span[@title='"+ dummyContact +"']"));
            Thread.sleep(500);
            target.click();
            System.out.println("--Ubicando cuadro de chat...");
            new WebDriverWait(driver, 3).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"main\"]/footer/div[1]/div[2]/div/div[2]")));
            WebElement chatBox = driver.findElement(By.xpath("//*[@id=\"main\"]/footer/div[1]/div[2]/div/div[2]"));
            Thread.sleep(500);
            System.out.println("--Seleccionando cuadro de chat...");
            chatBox.click();
            chatBox.sendKeys(this.message + Keys.ENTER);
         }
    }

    class ProgrammedMessage extends TimerTask {

        String contact;
        String messagep;

        public ProgrammedMessage(String contact, String message) {
            this.contact = contact;
            messagep = message;
        }
        
        public void run() {
            try {
                message = this.messagep;
                simpleMessage(this.contact, message); 
            } catch (InterruptedException e) {
                System.out.println("Error");
                System.exit(0);
            }
            
        }
    }

    class ProgrammedMessageMultipleContacts extends TimerTask {

        String contacts;
        String messagep;

        public ProgrammedMessageMultipleContacts(String contacts, String message) {
            this.contacts = contacts;
            this.messagep = message;
        }

        public void run() {
            try {
                message = messagep;
                multipleContacts(contacts, message);
            } catch (InterruptedException e) {
                System.out.println("Error");
                System.exit(0);
            }
            
        }
    }

    public void programmedMessage(String contact, String message, Date d) {
        ProgrammedMessage pm = new ProgrammedMessage(contact, message);
      
        timer.schedule(pm,d);
    }

    public void programmedMessageMultipleContacts(String contacts, String message, Date d) {
        ProgrammedMessageMultipleContacts pm = new ProgrammedMessageMultipleContacts(contacts,message);
    
        timer.schedule(pm,d);
    }

    public void menu() throws ParseException, InterruptedException{
        System.out.println("What do you want to do next?");
        System.out.println("1. Send a simple message");
        System.out.println("2. Send a message multiple times");
        System.out.println("3. Schedule a message to be sent on a specific time");
        System.out.println("4. Send a message to multiple contacts");
        System.out.println("5. Schedule a message to be sent on a specific time to multiple contacts");
        System.out.println("6. Exit");
        int k = input.nextInt();
        switch (k) {
            case 1:
                System.out.println("Enter contact name");
                input.nextLine();
                this.contact = input.nextLine().trim();
                System.out.println("Enter message");
                this.message = input.nextLine();
                simpleMessage(contact, message);
                break;

            case 2:
                System.out.println("Enter contact name");
                input.nextLine();
                this.contact = input.nextLine().trim();
                System.out.println("Enter message");
                this.message = input.nextLine();
                System.out.println("Enter how many times the message will be sent");
                this.times = input.nextInt();
                multipleMessages(contact, message, times);
                break;

            case 3:
                String contactd;
                System.out.println("Enter contact name");
                input.nextLine();
                contactd = input.nextLine().trim();
                System.out.println("Enter message");
                String message1 = input.nextLine();
                System.out.println("Enter date (yyyy-MM-dd HH:mm:ss)");
                String d = input.nextLine();
                Date dated = dateformatter.parse(d);
                programmedMessage(contactd, message1, dated);
                break;

            case 4:
                System.out.println("Enter contacts separated with //");
                input.nextLine();
                String contacts = input.nextLine();
                System.out.println(contacts);
                System.out.println("Enter message");
                this.message = input.nextLine();
                multipleContacts(contacts, message);
                break;
            
            case 5:
                System.out.println("Enter contacts separated with //");
                input.nextLine();
                String contacts1 = input.nextLine();
                System.out.println("Enter message");
                String message2 = input.nextLine();
                System.out.println("Enter date (yyyy-MM-dd HH:mm:ss)");
                String d1 = input.nextLine();
                Date date1 = dateformatter.parse(d1);
                programmedMessageMultipleContacts(contacts1, message2, date1);
                break;
            case 6:
                System.exit(0);
            default:
                System.out.println("Not a valid option");
                break;
        }
    }

    public static void main( String[] args ) throws InterruptedException, ParseException {
        
        WpAP automatedwa = new WpAP();

        System.setProperty("webdriver.chrome.driver", "/home/rodrigo/Documentos/Selenium/wp-project/src/main/chromedriver");

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(100, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
        driver.get("https://web.whatsapp.com/");

        System.out.println("####################################\nPlease, scan QR code and press enter\n####################################");
        String dummy = input.nextLine();

        new WebDriverWait(driver, 3).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"side\"]/div[1]/div/label/div/div[2]")));
        searchBox = driver.findElement(By.xpath("//*[@id=\"side\"]/div[1]/div/label/div/div[2]"));
       
        while (true) {
            automatedwa.menu();
        }


    }

}