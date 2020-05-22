package org.superhelt.raidplanner2.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileWriter {

    private static final Logger log = LoggerFactory.getLogger(FileWriter.class);
    private static final Path dataFolder = getDataFolder();

    private static Path getDataFolder() {
        String dataFolder = System.getenv("RAIDPLANNER_DATA_FOLDER");

        return dataFolder == null ? Paths.get("") : Paths.get(dataFolder);
    }

    public static <T> List<T> readFromFile(Path jsonFile, Class<T[]> tClass) throws IOException {
        Path file = dataFolder.resolve(jsonFile);

        if (Files.exists(file)) {
            String json = readFile(file);

            ObjectMapper mapper = new ObjectMapper();
            T[] storedObjects = mapper.readerFor(tClass).readValue(json);

            List<T> result = Arrays.asList(storedObjects);
            log.info("Read {} objects of type {} from {}", result.size(), tClass.getSimpleName(), file);

            return result;
        } else {
            log.info("Found no file to read ({})", file);
            return new ArrayList<>();
        }
    }

    public static <T> void writeToFile(Path jsonFile, List<T> list) {
        Path file = dataFolder.resolve(jsonFile);

        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(list);

            Files.deleteIfExists(file);
            Files.write(file, json.getBytes());
        } catch (Exception e) {
            log.error("Unable to write file {}", file, e);
        }
    }

    private static String readFile(Path playerJson) throws IOException {
        return String.join("", Files.readAllLines(playerJson));
    }
}
