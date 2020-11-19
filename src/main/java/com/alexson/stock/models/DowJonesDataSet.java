package com.alexson.stock.models;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "stock", uniqueConstraints = @UniqueConstraint(columnNames = {"quarter", "date", "stock"}))
public class DowJonesDataSet implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Long id;
    @Column(name = "quarter")
    private Integer quarter;
    @Column(name = "stock")
    private String stock;
    @Column(name = "date")
    private String date;
    @Column(name = "open")
    private String open;
    @Column(name = "high")
    private String high;
    @Column(name = "low")
    private String low;
    @Column(name = "close")
    private String close;
    @Column(name = "volume")
    private Long volume;
    @Column(name = "percent_change_price")
    private Float percent_change_price;
    @Column(name = "percent_change_volume_over_last_wk")
    private Float percent_change_volume_over_last_wk;
    @Column(name = "previous_weeks_volume")
    private String previous_weeks_volume;
    @Column(name = "next_weeks_open")
    private String next_weeks_open;
    @Column(name = "next_weeks_close")
    private String next_weeks_close;
    @Column(name = "percent_change_next_weeks_price")
    private Float percent_change_next_weeks_price;
    @Column(name = "days_to_next_dividend")
    private Integer days_to_next_dividend;
    @Column(name = "percent_return_next_dividend")
    private Float percent_return_next_dividend;


}
