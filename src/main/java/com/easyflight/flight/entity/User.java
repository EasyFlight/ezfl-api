package com.easyflight.flight.entity;

import com.querydsl.core.annotations.QueryEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Created by Victor.Ikoro on 8/8/2017.
 */
@QueryEntity
@Document(collection = "users")
public class User {

    @Id
    private String id;
    private String fullName;
    private String firstName;
    private String lastName;
    @Indexed(unique = true)
    private String email;
    private String picture;
    private String address;
    private String mobile;
    private Date createdAt;

    public User() {
    }

    @PersistenceConstructor
    public User(String id, String fullName, String firstName, String lastName, String email, String picture, String address, String mobile, Date createdAt) {
        this.id = id;
        this.fullName = fullName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.picture = picture;
        this.address = address;
        this.mobile = mobile;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
