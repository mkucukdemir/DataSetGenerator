/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.datamining.datasetgenerator.pojo;

import java.util.Date;

/**
 *
 * @author Asus
 */
public class MemberDisclosureQueryPayload {
    private String fromDate;
    private String toDate;
    private String year;
    private String prd;
    private String term;
    private String ruleType;
    private String bdkReview;
    private String disclosureClass;
    private String index;
    private String market;
    private String isLate;
    private String[] subjectList;
    private String[] mkkMemberOidList;
    private String[] inactiveMkkMemberOidList;
    private String[] bdkMemberOidList;
    private String mainSector;
    private String sector;
    private String subSector;
    private String memberType;
    private String fromSrc;
    private String srcCategory;
    private String[] discIndex;

    public MemberDisclosureQueryPayload(String fromDate, String toDate, String year, String prd, String term, String ruleType, String bdkReview, String disclosureClass, String index, String market, String isLate, String[] subjectList, String[] mkkMemberOidList, String[] inactiveMkkMemberOidList, String[] bdkMemberOidList, String mainSector, String sector, String subSector, String memberType, String fromSrc, String srcCategory, String[] discIndex) {
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.year = year;
        this.prd = prd;
        this.term = term;
        this.ruleType = ruleType;
        this.bdkReview = bdkReview;
        this.disclosureClass = disclosureClass;
        this.index = index;
        this.market = market;
        this.isLate = isLate;
        this.subjectList = subjectList;
        this.mkkMemberOidList = mkkMemberOidList;
        this.inactiveMkkMemberOidList = inactiveMkkMemberOidList;
        this.bdkMemberOidList = bdkMemberOidList;
        this.mainSector = mainSector;
        this.sector = sector;
        this.subSector = subSector;
        this.memberType = memberType;
        this.fromSrc = fromSrc;
        this.srcCategory = srcCategory;
        this.discIndex = discIndex;
    }
    
    
}
