package com.youfarm.citronix.common.config;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@ConfigurationProperties(prefix = "farm.constraints")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Primary
public class FarmConfigProperties {

    private double fieldMinAreaHa;
    private double fieldFarmPercentage;
    private int maxFieldsInFarm;
    private int maxTreesByHectare;
    private int maxLifespanProductivity;

    private TreeProdPerSeason treeProdPerSeason;


    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TreeProdPerSeason {
        private Double young;
        private Double mature;
        private Double old;
    }



}
