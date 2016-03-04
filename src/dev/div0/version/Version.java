package dev.div0.version;

public class Version implements IVersion{

	private String version = "0.0.0";
	
	@Override
	public String getVersion() {
		return version;
	}

}
