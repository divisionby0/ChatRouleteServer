package dev.div0;

import org.red5.server.adapter.ApplicationAdapter;

public class LoggingAppAdapter extends ApplicationAdapter {
	protected void log(String value)
    {
    	System.out.println(value);
    } 
	protected void error(String value)
    {
    	System.err.println(value);
    } 
}
