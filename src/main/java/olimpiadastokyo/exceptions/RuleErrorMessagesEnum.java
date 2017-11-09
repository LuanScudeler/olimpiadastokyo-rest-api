package olimpiadastokyo.exceptions;

/**
 * Created by lnsr on 11/9/2017.
 */
public enum RuleErrorMessagesEnum {

    COD_0(0, "A match already exists for the time specified"),
    COD_1(1, "Error message");

    private int code;
    private String message;

    RuleErrorMessagesEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
