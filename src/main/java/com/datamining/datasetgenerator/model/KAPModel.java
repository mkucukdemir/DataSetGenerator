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
public class KAPModel {
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private String companyName;
    private String stockNames;
    private String attachments;
    private Date date;
    private String notificationType;
    private String year;
    private String period;
    private String title;
    private String description;

    public KAPModel(String companyName, String stockNames, String attachments, Date date, String notificationType, String year, String period, String title, String description) {
        this.companyName = companyName;
        this.stockNames = stockNames;
        this.attachments = attachments;
        this.date = date;
        this.notificationType = notificationType;
        this.year = year;
        this.period = period;
        this.title = title;
        this.description = description;
    }

    /**
     * @return the companyName
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * @return the stockNames
     */
    public String getStockNames() {
        return stockNames;
    }

    /**
     * @return the attachments
     */
    public String getAttachments() {
        return attachments;
    }

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @return the notificationType
     */
    public String getNotificationType() {
        return notificationType;
    }

    /**
     * @return the year
     */
    public String getYear() {
        return year;
    }

    /**
     * @return the period
     */
    public String getPeriod() {
        return period;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }
    
    public String csvHeader() {
        return "Company Name,Stock Names,Attachments,Date,Notification Type,Year,Period,Title,Description";
    }
    
    public String csv() {
        return companyName + "," +
                stockNames + "," +
                attachments + "," +
                dateFormat.format(date) + "," +
                notificationType + "," +
                year + "," +
                period + "," +
                title + "," +
                description;
    }
    
}
