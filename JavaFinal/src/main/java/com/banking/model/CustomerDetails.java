package com.banking.model;

import com.sun.istack.NotNull;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.persistence.*;
import java.sql.Date;
import java.util.Iterator;


@Entity
@Table(name="customer_details")
public class CustomerDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "full_name")
    private String name;

    @Column(name="dob")
    private String dob;

    @Column(name = "acc_type")
    private String accType;

    @Column(name="mobile")
    private long mobile;

   @Column(name="acc_No")
    private long accNo;

   @Column(name = "balance")
    private double bal;


    @Column(name = "createdate")
    private String date;

    public CustomerDetails() {
    }

    public CustomerDetails(Iterator<Cell> cellsInRow) {

//        System.out.println("sfdysfdghsHHH");
//        cellsInRow.next();

        this.name = cellsInRow.next().getStringCellValue();
        this.dob = cellsInRow.next().getStringCellValue();
        this.accType = cellsInRow.next().getStringCellValue();
        this.mobile = (int) cellsInRow.next().getNumericCellValue();
        this.accNo = (int) cellsInRow.next().getNumericCellValue();
        this.bal = (int) cellsInRow.next().getNumericCellValue();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAccType() {
        return accType;
    }

    public void setAccType(String accType) {
        this.accType = accType;
    }

    public long getMobile() {
        return mobile;
    }

    public void setMobile(long mobile) {
        this.mobile = mobile;
    }

    public long getAccNo() {
        return accNo;
    }

    public void setAccNo(long accNo) {
        this.accNo = accNo;
    }

    public double getBal() {
        return bal;
    }

    public void setBal(double bal) {
        this.bal = bal;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "CustomerDetails{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dob='" + dob + '\'' +
                ", accType='" + accType + '\'' +
                ", mobile=" + mobile +
                ", accNo=" + accNo +
                ", bal=" + bal +
                ", date=" + date +
                '}';
    }
}
