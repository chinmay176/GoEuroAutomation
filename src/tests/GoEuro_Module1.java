package tests;

import java.util.*;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import library.ObjLibrary;
import library.Utility;

public class GoEuro_Module1 {
	
	
	@Test
	public void verfiy_SortingByPrice() {
		try{
			String searchText_To = "Madrid, Spain";
			String searchText_From = "Barcelona, Spain";
			ArrayList<Float> list_Original = new ArrayList<Float>(); //variable for original price sequence shown by the website
			ArrayList<Float> list_Sorted = new ArrayList<Float>();   //variable for sorted pricing
			Utility util = new Utility();
			
			//open browser and navigate to goeuro website
			WebDriver wd = util.openURL(ObjLibrary.url_goeuro);
			
			//Enter the source place and select from the suggestion list
			wd.findElement(By.id(ObjLibrary.id_searchBox_from)).sendKeys(searchText_To);
			util.syncElement(wd, wd.findElement(By.id(ObjLibrary.id_searchSuggestion_from)));
			List<WebElement> suggestionList1 = util.getItemList(wd, ObjLibrary.id_searchSuggestion_from, "li");
			util.getItem(suggestionList1, searchText_To).click();
			
			//Enter the destination place and select from the suggestion list
			wd.findElement(By.id(ObjLibrary.id_searchBox_to)).sendKeys(searchText_From);
			util.syncElement(wd, wd.findElement(By.id(ObjLibrary.id_searchSuggestion_to)));
			List<WebElement> suggestionList2 = util.getItemList(wd, ObjLibrary.id_searchSuggestion_to, "li");
			util.getItem(suggestionList2, searchText_From).click();
			
			//opt out from Airbnb accommodation search
			wd.findElement(By.xpath("//div[@data-partner = 'airbnb']")).click();
			
			wd.findElement(By.id(ObjLibrary.id_searchButton)).click();
			Thread.sleep(10000);  //Thread.sleep() should be avoided with an advanced syncElement or waitForPageLoad method
			
			//Get the list of Elements which corresponds to individual search result
			util.syncElement(wd, wd.findElement(By.xpath("//div[@id='results-train']")));
			List<WebElement> liResults = wd.findElements(By.xpath("//div[@id='results-train']/div/div"));
			WebElement soloItem;
			
			//Loop through the Element list to fetch the pricing sequence shown by the website
			for(int i= 0; i < liResults.size(); i++){
				soloItem = liResults.get(i);
				String price = soloItem.findElement(By.xpath("div//span[contains(@class,'price-no')]")).getText();
				list_Original.add(new Float(price.split(" ")[1]));
				System.out.println("price for " + i + "position: $" + list_Original.get(i));
			}
			
			list_Sorted = list_Original;
			//Perform the sorting on the sequence and verify if the list was already sorted
			Collections.sort(list_Original);
			if(list_Original == list_Sorted){
				System.out.println("price sequence shown is sorted");
			}
			else{
				System.out.print("price sequence is not sorted");
				Assert.assertTrue(false); //fail the test case
			}
			
			wd.quit();	
			//In order to avoid out of memory issues caused due to unexpected exceptions, this method call can be 
			//further added to a tear down method. This will ensure release of WebDriver instance from memory
		}
		catch(Exception e){
			System.out.println("Test case failed with exception: "+ e.getMessage());
			Assert.assertTrue(false); //fail the test case
		}
	}
	
}
