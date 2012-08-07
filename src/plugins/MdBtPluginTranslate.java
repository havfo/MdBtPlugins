package plugins;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import net.fosstveit.mdbt.utils.MdBtPlugin;

public class MdBtPluginTranslate extends MdBtPlugin {

	WebDriver driver = new FirefoxDriver();
	WebElement query;
	WebElement resultsSpan;

	public MdBtPluginTranslate() {
		setTriggerRegex("translate:(\\s).*");

		driver.get("http://translate.google.com/#auto/no/");
		query = driver.findElement(By.id("source"));
		resultsSpan = driver.findElement(By.id("result_box"));
	}

	@Override
	public String onMessage(String channel, String sender, String message) {
		String translate = message.replaceFirst("translate: ", "");

		query.clear();
		query.sendKeys(translate);

		String translation = "";
		
		long timeout = 10000;
		long time = 0;
		
		while (time < timeout) {
			translation = resultsSpan.getText();
			
			if (!translation.isEmpty()) {
				break;
			}
			
			try {
				Thread.sleep(100);
				time += 100;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		return translation;
	}

	@Override
	public void cleanup() {
		driver.close();
		driver = null;
	}

}
