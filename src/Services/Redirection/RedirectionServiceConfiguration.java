package Services.Redirection;

import java.util.List;

import Utilities.Configuration.IConfiguration;

public class RedirectionServiceConfiguration implements IConfiguration{
	public List<String> Redirections;
	
	@Override
	public String toString() {
		return "Redirections configuration:"+Redirections;
	}
}
