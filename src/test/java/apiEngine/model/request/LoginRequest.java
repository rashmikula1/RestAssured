package apiEngine.model.request;

import utilities.LoggerLoad;

public class LoginRequest {

	public String userLoginEmail;
	public String password;
	
    public LoginRequest(String userLoginEmail, String password)
    {
        this.userLoginEmail = userLoginEmail;
        this.password = password;
        LoggerLoad.logInfo("Login Request: userId: "+userLoginEmail+ "password: "+ password);
    }

}
