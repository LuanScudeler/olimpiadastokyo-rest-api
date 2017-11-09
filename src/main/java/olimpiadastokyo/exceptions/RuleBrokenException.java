package olimpiadastokyo.exceptions;

/**
 * Created by lnsr on 11/8/2017.
 */
public class RuleBrokenException extends Exception {
    public RuleBrokenException(Class clazz, RuleErrorMessagesEnum rule) {
        super(String.format("Error for creating entity '%s'. %s ", clazz.getSimpleName(), rule.getMessage()));
    }
}
