package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.commonMethods;

public class loginPage  extends commonMethods {

        @FindBy(id = "txtUsername")
        public WebElement usernameBox;

        @FindBy(id = "txtPassword")
        public WebElement passwordBox;

        @FindBy(id = "btnLogin")
        public WebElement loginButton;

        @FindBy(xpath="//*[@id=\"divLogo\"]/img")
        public  WebElement logo;

        @FindBy(id="spanMessage")
         public WebElement errorMessage;

        public loginPage() {
            PageFactory.initElements(driver, this);
        }

        public void login(String username, String password) {
            sendText(usernameBox, username);
            sendText(passwordBox, password);
            click(loginButton);
        }

    }


