package com.fernandez.fixtures.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UrlsDTO {

    private String url;
    private Boolean hasOpened;
}
