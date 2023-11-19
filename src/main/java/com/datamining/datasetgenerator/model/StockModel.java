/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.datamining.datasetgenerator.model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Asus
 */
public class StockModel {
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private Date date;
    private String abbreviation;
    private String change;
    private Double closingPrice;
    private Double yesterdayClosingPrice;
    private Double percentageChange;
    private Double topPrice;
    private Double bottomPrice;
    private Double weightedAverage;
    private Long volumeInLot;
    private Double volumeInKTL;

    public StockModel(Date date, String abbreviation, String change, Double closingPrice, Double yesterdayClosingPrice, Double percentageChange, Double topPrice, Double bottomPrice, Double weightedAverage, Long volumeInLot, Double volumeInKTL) {
        this.date = date;
        this.abbreviation = abbreviation;
        this.change = change;
        this.closingPrice = closingPrice;
        this.yesterdayClosingPrice = yesterdayClosingPrice;
        this.percentageChange = percentageChange;
        this.topPrice = topPrice;
        this.bottomPrice = bottomPrice;
        this.weightedAverage = weightedAverage;
        this.volumeInLot = volumeInLot;
        this.volumeInKTL = volumeInKTL;
    }
    
    public String csvHeader() {
        return "Date,Abbreviation,Change,ClosingPrice,AdjacentClose,PercentageChange,TopPrice,BottomPrice,WeightedAverage,Volume(LOT),Volume(1K TL)";
    }
    
    public String csv() {
        return dateFormat.format(date) + "," +
                abbreviation + "," +
                change + "," +
                closingPrice + "," +
                yesterdayClosingPrice + "," +
                percentageChange + "," +
                topPrice + "," +
                bottomPrice + "," +
                weightedAverage + "," +
                volumeInLot + "," +
                volumeInKTL;
    }

    /**
     * @return the abbreviation
     */
    public String getAbbreviation() {
        return abbreviation;
    }

    /**
     * @return the change
     */
    public String getChange() {
        return change;
    }

    /**
     * @return the closingPrice
     */
    public Double getClosingPrice() {
        return closingPrice;
    }

    /**
     * @return the yesterdayClosingPrice
     */
    public Double getYesterdayClosingPrice() {
        return yesterdayClosingPrice;
    }

    /**
     * @return the percentageChange
     */
    public Double getPercentageChange() {
        return percentageChange;
    }

    /**
     * @return the topPrice
     */
    public Double getTopPrice() {
        return topPrice;
    }

    /**
     * @return the bottomPrice
     */
    public Double getBottomPrice() {
        return bottomPrice;
    }

    /**
     * @return the weightedAverage
     */
    public Double getWeightedAverage() {
        return weightedAverage;
    }

    /**
     * @return the volumeInLot
     */
    public Long getVolumeInLot() {
        return volumeInLot;
    }

    /**
     * @return the volumeInKTL
     */
    public Double getVolumeInKTL() {
        return volumeInKTL;
    }

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }
    
}