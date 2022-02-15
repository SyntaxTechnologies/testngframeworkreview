package utils;

import pages.*;

public class PageInitializer {

	public static loginPage loginpage;
	public static DashboardPage dash;


	public static void initializePageObjects() {
		loginpage = new loginPage();
		dash = new DashboardPage();

	}
}
