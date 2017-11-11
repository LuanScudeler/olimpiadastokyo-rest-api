package olimpiadastokyo.exceptions;

/**
 * Created by lnsr on 11/8/2017.
 */
public class EntityNotFoundException extends Exception {
    public EntityNotFoundException(Class clazz, String param, String value) {
        super(String.format("Entity '%s' not found for parameter {'%s'='%s'}", clazz.getSimpleName(), param, value));
    }
}
