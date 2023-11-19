/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.datamining.datasetgenerator.pojo;

/**
 *
 * @author Asus
 */
public class MemberDisclosureQueryResponse {
    private String publishDate;
    private String kapTitle;
    private boolean isOldKap;
    private String disclosureClass;
    private String disclosureType;
    private String summary;
    private String subject;
    private String relatedStocks;
    private Integer disclosureIndex;
    private boolean isLate;
    private boolean hasMultiLanguageSupport;
    private Integer attachmentCount;

    public MemberDisclosureQueryResponse(String publishDate, String kapTitle, boolean isOldKap, String disclosureClass, String disclosureType, String summary, String subject, String relatedStocks, Integer disclosureIndex, boolean isLate, boolean hasMultiLanguageSupport, Integer attachmentCount) {
        this.publishDate = publishDate;
        this.kapTitle = kapTitle;
        this.isOldKap = isOldKap;
        this.disclosureClass = disclosureClass;
        this.disclosureType = disclosureType;
        this.summary = summary;
        this.subject = subject;
        this.relatedStocks = relatedStocks;
        this.disclosureIndex = disclosureIndex;
        this.isLate = isLate;
        this.hasMultiLanguageSupport = hasMultiLanguageSupport;
        this.attachmentCount = attachmentCount;
    }

    /**
     * @return the publishDate
     */
    public String getPublishDate() {
        return publishDate;
    }

    /**
     * @return the kapTitle
     */
    public String getKapTitle() {
        return kapTitle;
    }

    /**
     * @return the isOldKap
     */
    public boolean isIsOldKap() {
        return isOldKap;
    }

    /**
     * @return the disclosureClass
     */
    public String getDisclosureClass() {
        return disclosureClass;
    }

    /**
     * @return the disclosureType
     */
    public String getDisclosureType() {
        return disclosureType;
    }

    /**
     * @return the summary
     */
    public String getSummary() {
        return summary;
    }

    /**
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @return the relatedStocks
     */
    public String getRelatedStocks() {
        return relatedStocks;
    }

    /**
     * @return the disclosureIndex
     */
    public Integer getDisclosureIndex() {
        return disclosureIndex;
    }

    /**
     * @return the isLate
     */
    public boolean isIsLate() {
        return isLate;
    }

    /**
     * @return the hasMultiLanguageSupport
     */
    public boolean isHasMultiLanguageSupport() {
        return hasMultiLanguageSupport;
    }

    /**
     * @return the attachmentCount
     */
    public Integer getAttachmentCount() {
        return attachmentCount;
    }
    
    
}
