package olimpiadastokyo.exceptions;

/**
 * Created by lnsr on 11/9/2017.
 */
public enum RuleErrorMessagesEnum {

    COD_0(0, "A match already exists for the time specified."),
    COD_1(1, "Minimum match duration not reached for the period specified. A match must have a minimum duration of 30 minutes."),
    COD_2(2, "Maximum number of matches exceeded for the date specified. No more than 4 matches can be registered for the same date"),
    COD_3(3, "Duplicated match found for the teams specified. Only property {etapa} 'Semifinal' and 'Final' allows duplicates");

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
