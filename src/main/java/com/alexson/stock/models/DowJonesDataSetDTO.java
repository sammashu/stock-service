package com.alexson.stock.models;


import lombok.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;

import javax.validation.constraints.NotNull;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DowJonesDataSetDTO {
    @NotNull(message = "quarter can not be null!")
    private String quarter;
    @NotNull(message = "stock can not be null!")
    private String stock;
    @NotNull(message = "date can not be null! example 18/11/2020")
    private String date;
    private String open = null;
    private String high = null;
    private String low = null;
    private String close = null;
    private String volume = null;
    private String percent_change_price = null;
    private String percent_change_volume_over_last_wk = null;
    private String previous_weeks_volume = null;
    private String next_weeks_open = null;
    private String next_weeks_close = null;
    private String percent_change_next_weeks_price = null;
    private String days_to_next_dividend = null;
    private String percent_return_next_dividend = null;

    public DowJonesDataSet convertFromDto(DowJonesDataSetDTO dto){
        DowJonesDataSet dowJonesDataSet = new DowJonesDataSet();
        dowJonesDataSet.setQuarter(Integer.parseInt(dto.getQuarter()));
        dowJonesDataSet.setStock(dto.getStock());
        dowJonesDataSet.setDate(dto.getDate());
        if(!StringUtils.isEmpty(dto.getOpen())) {
            dowJonesDataSet.setOpen(dto.getOpen());
        }
        if(!StringUtils.isEmpty(dto.getHigh())) {
            dowJonesDataSet.setHigh(dto.getHigh());
        }
        if(!StringUtils.isEmpty(dto.getLow())) {
            dowJonesDataSet.setLow(dto.getLow());
        }
        if(!StringUtils.isEmpty(dto.getClose())) {
            dowJonesDataSet.setClose(dto.getClose());
        }
        if(!StringUtils.isEmpty(dto.getVolume())) {
            dowJonesDataSet.setVolume(Long.parseLong(dto.getVolume()));
        }
        if(!StringUtils.isEmpty(dto.getPercent_change_price())) {
            dowJonesDataSet.setPercent_change_price(Float.parseFloat(dto.getPercent_change_price()));
        }
        if(!StringUtils.isEmpty(dto.getPercent_change_volume_over_last_wk())) {
            dowJonesDataSet.setPercent_change_volume_over_last_wk(Float.parseFloat(dto.getPercent_change_volume_over_last_wk()));
        }
        if(!StringUtils.isEmpty(dto.getPrevious_weeks_volume())) {
            dowJonesDataSet.setPrevious_weeks_volume(dto.getPrevious_weeks_volume());
        }
        if(!StringUtils.isEmpty(dto.getNext_weeks_open())) {
            dowJonesDataSet.setNext_weeks_open(dto.getNext_weeks_open());
        }
        if(!StringUtils.isEmpty(dto.getNext_weeks_close())) {
            dowJonesDataSet.setNext_weeks_close(dto.getNext_weeks_close());
        }
        if(!StringUtils.isEmpty(dto.getPercent_change_next_weeks_price())) {
            dowJonesDataSet.setPercent_change_next_weeks_price(Float.parseFloat(dto.getPercent_change_next_weeks_price()));
        }
        if(!StringUtils.isEmpty(dto.getDays_to_next_dividend())) {
            dowJonesDataSet.setDays_to_next_dividend(Integer.parseInt(dto.getDays_to_next_dividend()));
        }
        if(!StringUtils.isEmpty(dto.getPercent_return_next_dividend())) {
            dowJonesDataSet.setPercent_return_next_dividend(Float.parseFloat(dto.getPercent_return_next_dividend()));
        }
        return dowJonesDataSet;
    }
}
