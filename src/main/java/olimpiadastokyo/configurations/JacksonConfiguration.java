package olimpiadastokyo.configurations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import org.joda.time.LocalTime;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;

@Configuration
public class JacksonConfiguration {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();

        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.setDateFormat(new SimpleDateFormat("dd-MM-yyyy hh:mm:ss"));
        mapper.registerModule(new JodaModule());

        SimpleModule module = new SimpleModule();
        module.addSerializer(LocalTime.class, new CustomLocalTimeSerializer());
        module.addDeserializer(LocalTime.class, new CustomLocalTimeDeserializer());
        mapper.registerModule(module);

        return mapper;
    }
}
