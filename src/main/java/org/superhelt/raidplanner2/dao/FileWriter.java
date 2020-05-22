package org.superhelt.raidplanner2.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileWriter {

    private static final Logger log = LoggerFactory.getLogger(FileWriter.class);

    public static <T> List<T> readFromFile(Path jsonFile, Class tClass) throws IOException {
        if(Files.exists(jsonFile)) {
            String json = readFile(jsonFile);

            ObjectMapper mapper = new ObjectMapper();
            T[] storedObjects = mapper.readerFor(tClass).readValue(json);

            List<T> result = Arrays.asList(storedObjects);
            log.info("Read {} objects of type {} from {}", result.size(), tClass.getSimpleName(), jsonFile);

            return result;
        } else {
            log.info("Found no file to read ({})", jsonFile);
            return new ArrayList<>();
        }
    }

    public static <T> void writeToFile(Path jsonFile, List<T> list) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(list);

            Files.deleteIfExists(jsonFile);
            Files.write(jsonFile, json.getBytes());
        } catch(Exception e) {
            log.error("Unable to write file {}", jsonFile, e);
        }
    }

    private static String readFile(Path playerJson) throws IOException {
        return String.join("", Files.readAllLines(playerJson));
    }
}
