package com.rainng.massproxyscan.util.ip2location;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Translator {
    private Map<String, String> countryMap;
    private Map<String, String> regionMap;
    private Map<String, String> cityMap;

    public Translator() throws IOException {
        countryMap = loadMap("data\\country");
        regionMap = loadMap("data\\region");
        cityMap = loadMap("data\\city");
    }

    public String getCountry(String name) {
        if (countryMap.containsKey(name)) {
            return countryMap.get(name);
        }

        return name;
    }

    public String getRegion(String name) {
        if (regionMap.containsKey(name)) {
            return regionMap.get(name);
        }

        return name;
    }

    public String getCity(String name) {
        if (cityMap.containsKey(name)) {
            return cityMap.get(name);
        }

        return name;
    }

    private Map<String, String> loadMap(String basePath) throws IOException {
        List<String> keys = Files.readAllLines(new File(basePath).toPath());
        List<String> values = Files.readAllLines(new File(basePath + "-cn").toPath());
        if (keys.size() != values.size()) {
            throw new IOException("Map load failed");
        }

        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < keys.size(); i++) {
            map.put(keys.get(i), values.get(i));
        }

        return map;
    }
}
