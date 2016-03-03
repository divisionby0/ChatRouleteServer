package dev.div0;

import java.util.TimerTask;

public class LogableTimerTask extends TimerTask {

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

	protected void log(String value)
    {
    	System.out.println(value);
    } 
	protected void error(String value)
    {
    	System.err.println(value);
    } 
}
