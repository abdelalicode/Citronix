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
    private int treeMaxLifeYears;
    private int maxTreesByHectare;
    private int maxLifespanProductivity;



}
