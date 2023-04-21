package com.example.AdminPanel.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.time.LocalDateTime;

@Entity
@Table(name="driver")
public class Driver {
    @Id
    @GeneratedValue
    private int id;
    @NotNull(message = "Email can't be Null")
    @Email(message = "Enter valid email!!!")
    private  String email;
    @Length(min=3,max=5,message = "Length of password should be in b/w 3 and 5 characters")
    @NotNull(message = "Password can't be Null")
    private String password;
    @NotNull(message = "FirstName can't be Null")
    private String firstName;
    @NotNull(message = "LastName can't be Null")
    private String lastName;
    @NotNull(message = "PhoneNumber can't be Null")
    @Length(min=10,max = 10,message = "Length of Phone number should be 10 characters exactly")
    private String phoneNumber;

    @NotNull(message = "Status can't be null")
    private String Status;
    private String city;
    @Column(columnDefinition = "boolean default false")
    private boolean softDelete;

    public String getStatus() {
        return Status;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCity() {
        return city;
    }

    public boolean isSoftDelete() {
        return softDelete;
    }

    public void setSoftDelete(boolean softDelete) {
        this.softDelete = softDelete;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setCity(String city) {
        this.city = city;
    }
    @Override
    public String toString() {
        return "Driver{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}

