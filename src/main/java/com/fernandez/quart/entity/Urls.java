package com.fernandez.quart.entity;

import javax.persistence.*;

@Entity
@Table(name = "urls")
public class Urls {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

    public Urls() {
    }

    public Urls(Long id, String url) {
        this.id = id;
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Urls{" +
                "id=" + id +
                ", url='" + url + '\'' +
                '}';
    }
}
