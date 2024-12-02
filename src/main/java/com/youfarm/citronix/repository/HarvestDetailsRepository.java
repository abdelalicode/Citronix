package com.youfarm.citronix.repository;

import com.youfarm.citronix.domain.entity.HarvestDetails;
import com.youfarm.citronix.domain.enums.Season;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface HarvestDetailsRepository extends JpaRepository<HarvestDetails, Long> {

    @Query("SELECT COUNT(hd) > 0 " +
            "FROM HarvestDetails hd " +
            "WHERE hd.tree.field.id = :fieldID " +
            "AND hd.harvest.season = :season " +
            "AND EXTRACT(YEAR FROM hd.harvest.harvestDate) = :year")
    boolean existsByFieldSeasonAndYear(@Param("fieldID") Long fieldID, @Param("season") Season season, @Param("year") int year);


    @Query("SELECT hd FROM HarvestDetails hd WHERE hd.tree.id IN :treeIds AND hd.harvest.harvestDate >= :threeMonthsAgo")
    List<HarvestDetails> findRecentByTreeIds(
            @Param("treeIds") List<Long> treeIds,
            @Param("threeMonthsAgo") LocalDate threeMonthsAgo
            );


}
