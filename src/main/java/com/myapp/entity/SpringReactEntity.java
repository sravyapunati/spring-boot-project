package com.myapp.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity //it says class should be mapped with database table
@Table(name ="Employees", schema ="sravya",
uniqueConstraints = @UniqueConstraint(columnNames={"email_Id","mobile"}))
// it specifies the name of table name to be mapped.
// if @table is not mentioned it takes class name as table name converting to lowercase.
public class SpringReactEntity {
    @Id
    @Column(name ="Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "First_Name")
    private String firstName;

    @Column(name = "Last_Name")
    private String lastName;

    @Column(name = "Email_Id", length = 50)
    private String emailId;

    @Column(name = "Mobile")
    private String mobile;

    @Column(name = "Created_Date")
    private LocalDate createdDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }
}
//@Id
//@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq_gen")
//@SequenceGenerator(name = "user_seq_gen", sequenceName = "user_sequence", allocationSize = 1)