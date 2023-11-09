package com.fernandez.quart.dto;

import java.util.List;
import java.util.Map;

public class UrlInfoDTO {
    private Map<String, Long> countByFirstPart;
    private List<String> urls;
    private int totalUrls;

    private int totalUniques;

    // Constructor
    public UrlInfoDTO(Map<String, Long> countByFirstPart, List<String> urls , int totalUniques) {
        this.countByFirstPart = countByFirstPart;
        this.urls = urls;
        this.totalUrls = urls.size();
        this.totalUniques = totalUniques;
    }

    // Getters y setters
    public Map<String, Long> getCountByFirstPart() {
        return countByFirstPart;
    }

    public void setCountByFirstPart(Map<String, Long> countByFirstPart) {
        this.countByFirstPart = countByFirstPart;
    }

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
        this.totalUrls = urls.size();
    }

    public int getTotalUrls() {
        return totalUrls;
    }

    public void setTotalUrls(int totalUrls) {
        this.totalUrls = totalUrls;
    }

    @Override
    public String toString() {
        return "UrlInfoDTO{" +
                "countByFirstPart=" + countByFirstPart +
                ", urls=" + urls +
                ", totalUrls=" + totalUrls +
                ", totalUniques=" + totalUniques +
                '}';
    }
}

