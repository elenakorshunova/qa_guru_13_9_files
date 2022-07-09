//Реализовать разбор json  файла библиотекой Jackson
package guru.qa;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;

import static org.assertj.core.api.Assertions.assertThat;

public class FileJsonJacksonTest {
    ClassLoader classLoader = FileJsonJacksonTest.class.getClassLoader();

    @Test
    @DisplayName("Jackson parse json")
    void jsonJackson() throws Exception {
        InputStream inputStream = classLoader.getResourceAsStream("files/json_example.json");
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(new InputStreamReader(inputStream));

        assertThat(jsonNode.get("bookSeriesName").asText()).isEqualTo("The Lord of the Rings");
        assertThat(jsonNode.get("author").asText()).isEqualTo("J.R.R. Tolkien");
        assertThat(jsonNode.withArray("firstBook").findValue("name").asText()).isEqualTo("The Hobbit");
        assertThat(jsonNode.withArray("firstBook").findValue("year").asInt()).isEqualTo(1937);
        assertThat(jsonNode.withArray("firstBook").findValue("paperBook").asBoolean()).isEqualTo(true);
        assertThat(jsonNode.withArray("firstBook").findValue("eBook").asBoolean()).isEqualTo(true);
    }
}
