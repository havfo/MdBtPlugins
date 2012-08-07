package plugins;

import java.util.Date;

import net.fosstveit.mdbt.utils.MdBtPlugin;

public class MdBtPluginTime extends MdBtPlugin {

	public MdBtPluginTime() {
		setTriggerRegex("time(\\s*)");
	}
	
	@Override
	public String onMessage(String channel, String sender, String message) {
		return new Date().toString();
	}

	@Override
	public void cleanup() {
		
	}
}
