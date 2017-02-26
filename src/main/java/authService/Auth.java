package authService;

public class Auth
{
    private final boolean authenticated;
    private final String message;


    public Auth(boolean authenticated, String message)
    {
        this.authenticated = authenticated;
        this.message = message;
    }

    public boolean getAuthenticated()
    {
        return authenticated;
    }

    public String getMessage() { return message; }
}