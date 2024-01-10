package com.fernandez.fixtures.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasketballConfigDTO {
    private String country;
    private String competition;
    private Boolean hasSummary;
    private Boolean hasPlayerStatics;
    private Boolean hasStats0;
    private Boolean hasStats1;
    private Boolean hasStats2;
    private Boolean hasStats3;
    private Boolean hasStats4;
    private Boolean hasStats5;
    private Boolean hasLineUps;
    private Boolean hasMatchHistory1;
    private Boolean hasMatchHistory2;
    private Boolean hasMatchHistory3;
    private Boolean hasMatchHistory4;


}
