package library;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class Utility {
	
	//Common variables used by methods
	public static final int	default_timeout = 15000;
	
	//Method to open a browser and navigate to a URL
	//This method can be further modified to pick the browser info from an external file, so that it can provide
	//an option to the user to select the browser under test
	public WebDriver openURL(String url){
		WebDriver wd = new FirefoxDriver();
		wd.get(url);
		wd.manage().window().maximize();
		return wd;		
	}
	
	//Method to wait for element to load (individual). This is to avoid use of implicit sleep() method
	public void syncElement(WebDriver wd, WebElement we){
		
			WebDriverWait wait = new WebDriverWait(wd, default_timeout);
			wait.until(ExpectedConditions.elementToBeClickable(we));
			//wait.until(ExpectedConditions.elementSelectionStateToBe(we, true));
	}
	
	//Method to get list of elements based on a parent element id
	public List<WebElement> getItemList(WebDriver wd, String elementId, String tagName){
		
		WebElement parentElement = wd.findElement(By.id(elementId));
		List<WebElement> itemList = parentElement.findElements(By.tagName(tagName));
		return itemList;
	}
	
	//Method to get individual element from an element list
	public WebElement getItem(List<WebElement> itemList, String text){
		WebElement requiredElement = null;
		for(int i = 1; i< itemList.size(); i ++){
			String itemValue;
			String roleValue = itemList.get(i).getAttribute("role");
			if(roleValue.equals("presentation")){
				itemValue = itemList.get(i).findElement(By.tagName("a")).getText();
				if (itemValue.equals(text)){
					requiredElement = itemList.get(i);
					break;
				}
			}
		}
		return requiredElement;
	}
	
	//Method to switch to a child/new window
	public String getChildWindowHandle(WebDriver wd, String parentHandle){
		String childWinHandle = null;
		for (String winHandle : wd.getWindowHandles()) {
			
			if(winHandle != parentHandle){
				childWinHandle = winHandle;
			}
		 }
		return childWinHandle; 
	}
	
	//Method to handle page elements load. This helps in avoiding implicit sleep method call
	/*public boolean waitForPageLoad(WebDriver wd, int waitTimeInSec, ExpectedCondition<Boolean>... conditions) {
	    boolean isLoaded = false;
	    FluentWait<WebDriver> wait = new FluentWait<WebDriver>(wd).withTimeout(waitTimeInSec, TimeUnit.SECONDS).ignoring(StaleElementReferenceException.class).pollingEvery(2, TimeUnit.SECONDS);
	    for (ExpectedCondition<Boolean> condition : conditions) {
	        isLoaded = (boolean) wait.until(condition);
	        if (isLoaded == false) {
	            //Stop checking on first condition returning false.
	            break;
	        }
	    }
	    return isLoaded;
	}*/

}
