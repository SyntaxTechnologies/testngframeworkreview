package test;

import org.testng.Assert;
import org.testng.annotations.Test;
import utils.commonMethods;

public class dashboardTestCases extends commonMethods {

    @Test(groups = "smoke")
    public void welcomeIsDispalyed(){
        loginpage.login("Admin","Hum@nhrm123");
        Assert.assertTrue(displayed(dash.welcomeMessage));
    }

}
