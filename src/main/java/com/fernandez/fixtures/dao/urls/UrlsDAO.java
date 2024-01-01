package com.fernandez.fixtures.dao.urls;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "URLS")
public class UrlsDAO {
    @Id
    private String urls;

    @Field("isOpened")
    private boolean isOpened;

    public UrlsDAO(String urls) {
        this.urls = urls;
    }
}
