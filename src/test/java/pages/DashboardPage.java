package pages;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.commonMethods;

public class DashboardPage extends commonMethods {
    public DashboardPage() {
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "menu_pim_viewPimModule")
    public WebElement pimOption;

    @FindBy(id = "menu_pim_viewEmployeeList")
    public WebElement employeeListOption;

    @FindBy(id = "menu_pim_addEmployee")
    public WebElement addEmployeeBtn;

    @FindBy(id = "menu_admin_Qualifications")
    public WebElement qualificationOption;

    @FindBy(id = "menu_admin_viewLicenses")
    public WebElement licenseOption;

    @FindBy(id = "menu_admin_viewAdminModule") // added by Olena
    public WebElement AdminTabBtn;

    @FindBy(id = "menu_admin_Qualifications")
    public WebElement QualificationTabBtn; // added by Olena

    @FindBy(id = "menu_admin_viewAdminModule")
    public WebElement adminOption;
    
    @FindBy(id = "menu_admin_Qualifications")
    public WebElement qualificationsOption;

    @FindBy (id ="welcome")
    public WebElement welcomeMessage;

    @FindBy(id = "menu_admin_viewSkills")
    public WebElement skillsOption;

    @FindBy(id = "menu_admin_membership")
    public WebElement membershipOption; // added by Elena

    @FindBy(id = "menu_admin_viewLanguages")// added by Olena
    public WebElement LanguagesTabBtn;

}

