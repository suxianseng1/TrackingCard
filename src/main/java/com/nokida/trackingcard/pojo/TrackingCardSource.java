package com.nokida.trackingcard.pojo;

import lombok.ToString;

import java.util.Date;

@ToString
public class TrackingCardSource {
    private String orderNo;

    private String wbs;

    private String drawingNum;

    private String materialName;

    private String materialNo;

    private String jobNumber;

    private Short planCount;

    private String wipId;

    private String templateId;

    private String cardName;

    private String wordNo;

    private String jobNo;

    private String processNo;

    private String stepName;

    private String productNo;

    private String jjrXm;

    private String createUserName;

    private String hjrName;

    private String yclshGyyName;

    private String yclshBzzName;

    private String department;

    private Date createTime;

    private String processNoNum;

    public TrackingCardSource(String orderNo, String wbs, String drawingNum, String materialName, String materialNo, String jobNumber, Short planCount, String wipId, String templateId,String cardName, String wordNo, String jobNo, String processNo, String stepName, String productNo, String jjrXm, String createUserName, String hjrName, String yclshGyyName, String yclshBzzName, String department, Date createTime, String processNoNum) {
        this.orderNo = orderNo;
        this.wbs = wbs;
        this.drawingNum = drawingNum;
        this.materialName = materialName;
        this.materialNo = materialNo;
        this.jobNumber = jobNumber;
        this.planCount = planCount;
        this.wipId = wipId;
        this.templateId = templateId;
        this.cardName = cardName;
        this.wordNo = wordNo;
        this.jobNo = jobNo;
        this.processNo = processNo;
        this.stepName = stepName;
        this.productNo = productNo;
        this.jjrXm = jjrXm;
        this.createUserName = createUserName;
        this.hjrName = hjrName;
        this.yclshGyyName = yclshGyyName;
        this.yclshBzzName = yclshBzzName;
        this.department = department;
        this.createTime = createTime;
        this.processNoNum = processNoNum;
    }

    public TrackingCardSource() {
        super();
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public String getWbs() {
        return wbs;
    }

    public void setWbs(String wbs) {
        this.wbs = wbs == null ? null : wbs.trim();
    }

    public String getDrawingNum() {
        return drawingNum;
    }

    public void setDrawingNum(String drawingNum) {
        this.drawingNum = drawingNum == null ? null : drawingNum.trim();
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName == null ? null : materialName.trim();
    }

    public String getMaterialNo() {
        return materialNo;
    }

    public void setMaterialNo(String materialNo) {
        this.materialNo = materialNo == null ? null : materialNo.trim();
    }

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber == null ? null : jobNumber.trim();
    }

    public Short getPlanCount() {
        return planCount;
    }

    public void setPlanCount(Short planCount) {
        this.planCount = planCount;
    }

    public String getWipId() {
        return wipId;
    }

    public void setWipId(String wipId) {
        this.wipId = wipId == null ? null : wipId.trim();
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId == null ? null : templateId.trim();
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getWordNo() {
        return wordNo;
    }

    public void setWordNo(String wordNo) {
        this.wordNo = wordNo == null ? null : wordNo.trim();
    }

    public String getJobNo() {
        return jobNo;
    }

    public void setJobNo(String jobNo) {
        this.jobNo = jobNo == null ? null : jobNo.trim();
    }

    public String getProcessNo() {
        return processNo;
    }

    public void setProcessNo(String processNo) {
        this.processNo = processNo == null ? null : processNo.trim();
    }

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName == null ? null : stepName.trim();
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo == null ? null : productNo.trim();
    }

    public String getJjrXm() {
        return jjrXm;
    }

    public void setJjrXm(String jjrXm) {
        this.jjrXm = jjrXm == null ? null : jjrXm.trim();
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName == null ? null : createUserName.trim();
    }

    public String getHjrName() {
        return hjrName;
    }

    public void setHjrName(String hjrName) {
        this.hjrName = hjrName == null ? null : hjrName.trim();
    }

    public String getYclshGyyName() {
        return yclshGyyName;
    }

    public void setYclshGyyName(String yclshGyyName) {
        this.yclshGyyName = yclshGyyName == null ? null : yclshGyyName.trim();
    }

    public String getYclshBzzName() {
        return yclshBzzName;
    }

    public void setYclshBzzName(String yclshBzzName) {
        this.yclshBzzName = yclshBzzName == null ? null : yclshBzzName.trim();
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department == null ? null : department.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getProcessNoNum() {
        return processNoNum;
    }

    public void setProcessNoNum(String processNoNum) {
        this.processNoNum = processNoNum == null ? null : processNoNum.trim();
    }
}