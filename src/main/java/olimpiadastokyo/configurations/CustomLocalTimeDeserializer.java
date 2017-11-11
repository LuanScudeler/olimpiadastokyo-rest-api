package olimpiadastokyo.configurations;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;

public class CustomLocalTimeDeserializer extends StdDeserializer<LocalTime> {

    DateTimeFormatter fmt = DateTimeFormat.forPattern("HH:mm:ss");

    public CustomLocalTimeDeserializer() {
        this(null);
    }

    protected CustomLocalTimeDeserializer(Class<LocalTime> t) {
        super(t);
    }

    @Override
    public LocalTime deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException  {
        String time = jp.getText();

        return fmt.parseLocalTime(time);

    }

}
