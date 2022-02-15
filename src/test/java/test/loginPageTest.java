package test;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.loginPage;
import utils.commonMethods;

public class loginPageTest extends commonMethods {
//    validate that syntax logo is present on the login page
    @Test(groups = "smoke")
    public void logoIsPresent(){

        Assert.assertTrue(loginpage.logo.isDisplayed());
    }
// verify the wrong credenitals

    @DataProvider(name = "Credentials")
    public Object [][] data(){

        Object[][] login={
                {"Admin","abc","Invalid credentials"},
                {"Admin","xyz","Invalid credentials"},
                {"Admin"," ","password can not be empty"},
                {" ","hum","username can not be empty"}
        };
        return  login;
    }

    @Test(dataProvider ="Credentials" ,groups = "smoke")
    public void invalidCrdentials(String userName , String password , String errorMsg){

        loginpage.login(userName,password);
        Assert.assertEquals(errorMsg,loginpage.errorMessage.getText());

    }

}
