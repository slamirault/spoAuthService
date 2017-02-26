package authService.Utility;

public enum Messages
{
    NONE(null),
    AUTH_FAIL("Authentication failed; Invalid email or password"),
    AUTH_SUCCESS("Authentication success!"),
    CREATE_FAIL("Creation failed; User was not created"),
    CREATE_FAIL_EML("Creation failed; Invalid email"),
    CREATE_FAIL_PWD("Creation failed; Invalid password"),
    CREATE_SUCCESS("Creation success!"),
    TEST("Hello, world!");

    private String name;

    Messages( String name ){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
}
