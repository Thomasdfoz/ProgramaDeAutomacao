/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webdriver.programadeautomacao;

import java.awt.AWTException;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import java.awt.Robot;

/**
 *
 * @author Thomas
 */
public class Selenium {

    private WebDriver driver = new ChromeDriver();
    private Robot robot;

    //exemplo
    /*public void robo()throws AWTException{
    robot = new Robot();
    }*/
    public void OpenPage(String url) {
        driver.get(url);
        driver.manage().window().maximize();
    }
    public void Sair(){
        driver.quit();
    }
    public void GetRobot(int key) throws AWTException {
        robot = new Robot();
        robot.keyPress(key);
    }

    public void SendKeys(String xpath, String value) {
        driver.findElement(By.xpath(xpath)).sendKeys(value);
    }

    public void Click(String xpath) {
        driver.findElement(By.xpath(xpath)).click();
    }
    public void ClickId(String id){
        driver.findElement(By.id(id)).click();
    }

    public void SelectValue(String xpath, String value) {
        Select select = new Select(driver.findElement(By.xpath(xpath)));
        select.selectByValue(value);
    }

    public String GetText(String xpath) {
        return driver.findElement(By.xpath(xpath)).getText();
    }

    public boolean isDisplayed(String xpath) {
        return driver.findElement(By.xpath(xpath)).isDisplayed();
    }

    public String GetValue(String xpath) {
        return driver.findElement(By.xpath(xpath)).getAttribute("value");
    }

}
