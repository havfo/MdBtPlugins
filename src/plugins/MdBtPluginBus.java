package plugins;

import net.fosstveit.mdbt.utils.MdBtPlugin;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class MdBtPluginBus extends MdBtPlugin {

	WebDriver driver = new FirefoxDriver();
	WebElement query;
	WebElement resultsDiv;
	String lastAnswer = "";

	public MdBtPluginBus() {
		setTriggerRegex("bus:(\\s).*");

		driver.get("https://www.atb.no");
		query = driver.findElement(By.id("busoracle-question"));
		resultsDiv = driver.findElement(By.id("busoracle-answer"));
	}

	@Override
	public String onMessage(String channel, String sender, String message) {
		String question = message.replaceFirst("bus: ", "");

		query.clear();
		query.sendKeys(question);
		query.submit();

		String answer = "";
		
		long timeout = 10000;
		long time = 0;
		
		while (time < timeout) {
			answer = resultsDiv.getText();
			
			if (!answer.isEmpty() && !answer.equals(lastAnswer)) {
				break;
			}
			
			try {
				Thread.sleep(100);
				time += 100;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		lastAnswer = answer;

		return answer;
	}

	@Override
	public void cleanup() {
		driver.close();
		driver = null;
	}

}
