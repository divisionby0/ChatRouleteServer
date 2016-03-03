package dev.div0;

public class BaseLogger {
	protected void log(String value)
    {
    	System.out.println(value);
    } 
	protected void error(String value)
    {
    	System.err.println(value);
    } 
}
