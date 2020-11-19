package com.alexson.stock.services.repository;

import com.alexson.stock.models.DowJonesDataSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DowJonesDataSetRepository extends JpaRepository<DowJonesDataSet, Long> {
    List<DowJonesDataSet> findByStockAndQuarterOrderById(String stockTicker, Integer quarter);
}
