package dev.oskarraul.challenge01.database;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.oskarraul.challenge01.domain.Player;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.io.Reader;
import java.net.URISyntaxException;
import java.util.List;

@AllArgsConstructor
public class JsonDatabaseReader {
    private ObjectMapper objectMapper;

    public List<Player> loadInitialCatalog(Reader jsonReader) throws IOException, URISyntaxException {
        return loadPlayersDatabase(jsonReader);
    }

    private List<Player> loadPlayersDatabase(Reader jsonReader) throws IOException {
        final JavaType listType = objectMapper
                .getTypeFactory()
                .constructCollectionType(List.class, Player.class);
        return objectMapper.readValue(jsonReader, listType);
    }
}
