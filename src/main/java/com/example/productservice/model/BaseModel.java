package com.example.productservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@Getter
@Setter
public class BaseModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @Column(nullable = false)
    private boolean isDeleted=false;//soft delete flag

    private String createdByUserName;

    // Auto set createdAt before inserting
    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
        updatedAt = new Date();
    }

    // Auto update updatedAt before updating
    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }
}
