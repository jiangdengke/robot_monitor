package com.robotmonitor.flight.domain;

import com.robotmonitor.common.core.domain.BaseEntity;

public class Complaint extends BaseEntity {
    private Long id;
    private String userName;
    private String roomCode;
    private String cardService;
    private String cardNo;
    private String complaintContent;
    private String complaintFeedback;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public String getCardService() {
        return cardService;
    }

    public void setCardService(String cardService) {
        this.cardService = cardService;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getComplaintContent() {
        return complaintContent;
    }

    public void setComplaintContent(String complaintContent) {
        this.complaintContent = complaintContent;
    }

    public String getComplaintFeedback() {
        return complaintFeedback;
    }

    public void setComplaintFeedback(String complaintFeedback) {
        this.complaintFeedback = complaintFeedback;
    }
}
