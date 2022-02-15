package utils;

import org.testng.ITestListener;
import org.testng.ITestResult;

public class listerners  implements ITestListener {

//    whenever the test case has passed
    @Override
    public void onTestSuccess(ITestResult result) {

        System.out.println("the test case is pass");
    }

}

