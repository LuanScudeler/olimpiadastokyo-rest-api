package olimpiadastokyo.configurations;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;

public class CustomLocalTimeSerializer extends StdSerializer<LocalTime> {

    DateTimeFormatter fmt = DateTimeFormat.forPattern("HH:mm:ss");

    public CustomLocalTimeSerializer() {
        this(null);
    }

    protected CustomLocalTimeSerializer(Class<LocalTime> t) {
        super(t);
    }

    @Override
    public void serialize(LocalTime value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeString(fmt.print(value));
    }
}
