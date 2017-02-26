package authService.Models;

public class Action
{
    private final boolean success;
    private final String message;


    public Action(boolean success, String message)
    {
        this.success = success;
        this.message = message;
    }

    public boolean getSuccess()
    {
        return success;
    }

    public String getMessage() { return message; }
}