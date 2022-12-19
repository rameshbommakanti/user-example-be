package com.myproject.demo.user.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="Users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long id;
    
    private String firstName;
    
    private String lastName;
    
    private String email;
    
    private String mobileNumber;
    
}
