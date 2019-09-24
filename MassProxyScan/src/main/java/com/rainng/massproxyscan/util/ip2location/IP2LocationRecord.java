package com.rainng.massproxyscan.util.ip2location;

import lombok.Data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * IP2Location记录
 */
@Data
public class IP2LocationRecord {
    private Long from;
    private Long to;
    private String countryCode;
    private String country;
    private String region;
    private String city;
    private Double latitude;
    private Double longitude;

    public static List<IP2LocationRecord> parseFromFile(String path) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            return parseFromReader(reader);
        }
    }

    public static List<IP2LocationRecord> parseFromReader(BufferedReader reader) throws IOException {
        List<IP2LocationRecord> list = new ArrayList<>();

        String line;
        while ((line = reader.readLine()) != null) {
            list.add(parse(line));
        }

        Translator translator = new Translator();
        for (IP2LocationRecord record : list) {
            record.setCountry(translator.getCountry(record.getCountry()));
            record.setRegion(translator.getRegion(record.getRegion()));
            record.setCity(translator.getCity(record.getCity()));
        }

        return list;
    }

    public static IP2LocationRecord parse(String text) {
        text = text.substring(1, text.length() - 1);
        String[] split = text.split("\",\"");

        IP2LocationRecord record = new IP2LocationRecord();

        record.setFrom(Long.valueOf(split[0]));
        record.setTo(Long.valueOf(split[1]));
        record.setCountryCode(split[2]);
        record.setCountry(split[3]);
        record.setRegion(split[4]);
        record.setCity(split[5]);
        record.setLatitude(Double.valueOf(split[6]));
        record.setLongitude(Double.valueOf(split[7]));

        return record;
    }
}
