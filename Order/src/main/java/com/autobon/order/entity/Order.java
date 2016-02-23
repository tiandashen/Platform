package com.autobon.order.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by yuh on 2016/2/22.
 */
@Entity
@Table(name="t_order")
public class Order {

    private int id;
    private String orderNum;
    private int orderType;
    private String photo;
    private Date orderTime;
    private Date addTime;
    private int status;
    private int customerType;
    private int customerId;
    private String remark;
    private int mainTechId;
    private int secondTechId;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name="order_num",nullable = false, insertable = true, updatable = true)
    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    @Column(name="order_type", insertable = true, updatable = true)
    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    @Column(name="photo",nullable = true, insertable = true, updatable = true)
    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Column(name="order_time",nullable = true, insertable = true, updatable = true)
    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    @Column(name="add_time",nullable = true, insertable = true, updatable = true)
    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    @Column(name="status", insertable = true, updatable = true)
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Column(name="customer_type", insertable = true, updatable = true)
    public int getCustomerType() {
        return customerType;
    }

    public void setCustomerType(int customerType) {
        this.customerType = customerType;
    }

    @Column(name="customer_id", insertable = true, updatable = true)
    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    @Column(name="remark",nullable = true, insertable = true, updatable = true)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name="main_tech_id", insertable = true, updatable = true)
    public int getMainTechId() {
        return mainTechId;
    }

    public void setMainTechId(int mainTechId) {
        this.mainTechId = mainTechId;
    }

    @Column(name="second_tech_id", insertable = true, updatable = true)
    public int getSecondTechId() {
        return secondTechId;
    }

    public void setSecondTechId(int secondTechId) {
        this.secondTechId = secondTechId;
    }
}
