package com.rainng.massproxyscan.core.mass;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * Masscan结果解析器
 */
@Component
public class MasscanParser {
    /**
     * 解析结果
     *
     * @param path 文件路径
     * @return 代理列表
     * @throws IOException 异常
     */
    public List<String> parse(String path) throws IOException {
        List<String> lines = Files.readAllLines(new File(path).toPath());
        List<String> proxies = new ArrayList<>(lines.size() / 2);
        for (String line : lines) {
            String[] split = line.split("\\s");
            if (split.length != 5) {
                continue;
            }

            proxies.add(split[3] + ":" + split[2]);
        }

        return proxies;
    }
}
