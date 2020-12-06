package ShoppingCart_main;

public class Account 
{
    public Account(String aUsername, String aPassword)
    {
        this.username = aUsername;
        this.password = aPassword;
    }

    //username
    public String getUsername()
    {
        return username;
    }
    //testing for matching passwords
    public boolean isPasswordMatch(String iPassword)
    {
        return (this.password.equals(iPassword));
    }
    
    private final String username;
    private final String password;
}