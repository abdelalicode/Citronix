package com.youfarm.citronix.common.utils;

import com.youfarm.citronix.common.config.FarmConfigProperties;
import com.youfarm.citronix.domain.entity.Harvest;
import com.youfarm.citronix.domain.entity.Tree;
import com.youfarm.citronix.domain.enums.Season;
import com.youfarm.citronix.exception.NotFoundException;
import com.youfarm.citronix.exception.UnAuthorizedException;
import com.youfarm.citronix.repository.FieldRepository;
import com.youfarm.citronix.repository.TreeRepository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;

public class SeasonUtility {

    public static Season getSeasonFromDate(LocalDate date) {

        int month = date.getMonthValue();

        return switch (month) {
            case 3, 4, 5 -> Season.SPRING;
            case 6, 7, 8 -> Season.SUMMER;
            case 9, 10, 11 -> Season.AUTUMN;
            case 12, 1, 2 -> Season.WINTER;
            default -> throw new IllegalStateException("Invalid month: " + month);
        };
    }


    public static Double getTreeProductivityPerSeason(Tree tree) {

        long treeAge = ChronoUnit.YEARS.between(tree.getPlatingDate().toLocalDate(), LocalDate.now());

        if(treeAge > 20) {
            return 0.0;
        }
        else {
            if (treeAge < 3) {
                return  2.5;
            } else if (treeAge <= 10) {
                return 12.0;
            } else {
                return 20.0;
            }
        }

    }




}
