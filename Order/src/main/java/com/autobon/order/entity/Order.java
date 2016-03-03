package com.autobon.order.entity;

import com.autobon.shared.VerifyCode;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Created by yuh on 2016/2/22.
 */
@Entity
@Table(name="t_order")
public class Order {
    public enum Status {
        NEWLY_CREATED(0), // 新建
        TAKEN_UP(10), // 已有人抢单
        SEND_INVITATION(20), // 已发送合作邀请并等待结果
        INVITATION_ACCEPTED(30), // 合作邀请已接受
        INVITATION_REJECTED(40), // 合作邀请已拒绝
        IN_PROGRESS(50), // 订单开始工作中
        FINISHED(60), // 订单已结束
        COMMENTED(70), // 订单已评论
        CANCELED(200); // 订单已取消
        private int statusCode;

        Status(int statusCode) {
            this.statusCode = statusCode;
        }

        public static Status getStatus(int statusCode) {
            for (Status s : Status.values()) {
                if (s.getStatusCode() == statusCode) return s;
            }
            return null;
        }
        public int getStatusCode() {
            return this.statusCode;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    private int id;

    @Column private String orderNum;

    @Column private int orderType; // 订单类型(1-隔热膜 2-隐形车衣 3-车身改色 4-美容清洁)

    @Column private String photo;

    @Column private Date orderTime;

    @Column private Date addTime;

    @JsonIgnore
    @Column(name="status")
    private int statusCode;

    @Column private int creatorType; // 下单人类型(1-合作商户 2-后台 3-用户)

    @Column private int creatorId;

    @Column private String creatorName;

    @Column private String contactPhone;

    @Column private String positionLon;

    @Column private String positionLat;

    @Column private String remark;

    @Column private int mainTechId;

    @Column private int secondTechId;

    public Order() {
        this.orderNum = generateOrderNum();
        this.setStatus(Status.NEWLY_CREATED);
        this.addTime = new Date();
    }

    public static String generateOrderNum() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) +
                VerifyCode.generateVerifyCode(6);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getCreatorType() {
        return creatorType;
    }

    public void setCreatorType(int creatorType) {
        this.creatorType = creatorType;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getPositionLon() {
        return positionLon;
    }

    public void setPositionLon(String positionLon) {
        this.positionLon = positionLon;
    }

    public String getPositionLat() {
        return positionLat;
    }

    public void setPositionLat(String positionLat) {
        this.positionLat = positionLat;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getMainTechId() {
        return mainTechId;
    }

    public void setMainTechId(int mainTechId) {
        this.mainTechId = mainTechId;
    }

    public int getSecondTechId() {
        return secondTechId;
    }

    public void setSecondTechId(int secondTechId) {
        this.secondTechId = secondTechId;
    }

    public Status getStatus() {
        return Status.getStatus(this.statusCode);
    }

    public void setStatus(Status status) {
        this.statusCode = status.getStatusCode();
    }
}
