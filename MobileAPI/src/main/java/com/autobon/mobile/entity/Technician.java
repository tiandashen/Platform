package com.autobon.mobile.entity;

import javax.persistence.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 * Created by dave on 16/2/5.
 */
@Entity
@Table
public class Technician {
    public enum Status {
        NOTVERIFIED(0), VERIFIED(1), REJECTED(2), BANNED(3);
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
    private int id;

    @Column private String phone;

    @Column private String password;

    @Column private String name;

    @Column private String gender;

    @Column private String avatar;

    @Column private String idNo;

    @Column private String idPhoto;

    @Column private String bank;

    @Column private String bankAddress;

    @Column private String bankCardNo;

    @Column private Date verifyAt;

    @Column private Date lastLoginAt;

    @Column private String lastLoginIp;

    @Column private Date createAt;

    @Column private int star;

    @Column private float voteRate;

    @Column(name = "status")
    private int statusCode;

    public Technician() {
        this.setStatus(Status.NOTVERIFIED);
        this.createAt = new Date();
    }

    public static String encryptPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(password.getBytes());
            byte[] message = digest.digest();
            StringBuffer sb = new StringBuffer();
            for (byte b : message) {
                String hex = Integer.toHexString(b & 0xFF);
                if (hex.length() < 2) {
                    sb.append('0');
                }
                sb.append(hex);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
            return "";
        }
    }

    public boolean isAvailable() {
        return this.getStatus() == Status.VERIFIED;
    }

    public boolean isActivated() {
        return this.getStatus() != Status.NOTVERIFIED;
    }

    public boolean isBanned() {
        return this.getStatus() == Status.BANNED;
    }

    public void setBanned() {
        this.setStatus(Status.BANNED);
    }

    public void setActived() {
        this.setStatus(Status.VERIFIED);
    }

    public void setDeactived() {
        this.setStatus(Status.NOTVERIFIED);
    }

    public Status getStatus() {
        return Status.getStatus(this.getStatusCode());
    }

    public void setStatus(Status s) {
        this.setStatusCode(s.getStatusCode());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getIdPhoto() {
        return idPhoto;
    }

    public void setIdPhoto(String idPhoto) {
        this.idPhoto = idPhoto;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBankAddress() {
        return bankAddress;
    }

    public void setBankAddress(String bankAddress) {
        this.bankAddress = bankAddress;
    }

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    public Date getVerifyAt() {
        return verifyAt;
    }

    public void setVerifyAt(Date verifyAt) {
        this.verifyAt = verifyAt;
    }

    public Date getLastLoginAt() {
        return lastLoginAt;
    }

    public void setLastLoginAt(Date lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public float getVoteRate() {
        return voteRate;
    }

    public void setVoteRate(float voteRate) {
        this.voteRate = voteRate;
    }

    protected int getStatusCode() {
        return statusCode;
    }

    protected void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
