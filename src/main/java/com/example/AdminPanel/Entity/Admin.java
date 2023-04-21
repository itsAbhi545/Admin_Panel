package com.example.AdminPanel.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name="admin")
public class Admin {
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
    private String Country;
    private String imageUrl;

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

    public String getCountry() {
        return Country;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String gtPassword() {
        return password;
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

    public void setCountry(String country) {
        Country = country;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", Country='" + Country + '\'' +
                '}';
    }
}
