package com.rainng.massproxyscan.core.crawl;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 通用爬虫
 */
@Component
public class GenericCrawler {
    /**
     * 爬取代理
     *
     * @param url url
     * @return 代理列表
     */
    public List<String> crawl(String url) {
        try {
            return innerCrawl(url);
        } catch (IOException ex) {
            System.out.println("Crawl Error:" + ex.getMessage());
            return new ArrayList<>();
        }
    }

    private List<String> innerCrawl(String url) throws IOException {
        String content = request(url);
        List<String> matches = match(content, "(\\d{1,3}\\.){3}\\d{1,3}[\\s\\S]*?\\d{1,5}");

        List<String> proxyList = new ArrayList<>();
        for (String match : matches) {
            String ip = matchOne(match, "^(\\d{1,3}\\.){3}\\d{1,3}");
            String port = matchOne(match, "\\d{1,5}$");
            if (ip.equals("") || port.equals("")) {
                continue;
            }
            proxyList.add(ip + ":" + port);
        }

        return proxyList;
    }

    private String request(String targetUrl) throws IOException {
        URL url = new URL(targetUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/537.36");

        StringBuilder builder = new StringBuilder();
        try (InputStream inputStream = conn.getInputStream()) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                    builder.append("\r\n");
                }
            }
        }

        return builder.toString();
    }

    private List<String> match(String content, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);

        List<String> list = new ArrayList<>();
        while (matcher.find()) {
            list.add(matcher.group());
        }

        return list;
    }

    private String matchOne(String content, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);

        if (matcher.find()) {
            return matcher.group();
        }

        return "";
    }
}
