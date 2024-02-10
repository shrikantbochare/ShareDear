package com.ShareDear.project.pojo;

import com.ShareDear.project.validation.PasswordValidator;
import jakarta.validation.constraints.*;

import java.sql.Date;

public class UpdateUserPojo {


    private int id;

    @NotNull()
    @Pattern(regexp = "^[a-zA-Z ]{5,}$", message = "Name can contain small letters, capital letters " +
            "and spaces with minimum size of 5 letters" )
    private String name;

    @NotNull()
    @Pattern(regexp = "^(?=.*[a-z])[a-z0-9_]{5,}$" ,message = "Username can contain at least one " +
            "small letter, may or may not contain numbers and special characters like _  with minimum size of 5 ")
    private String username;

    @NotNull()
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@_/*$])[A-Za-z\\d@_/*$]{8,}$",message = "Password must contain" +
            " at least one small letter, capital letter, number, and special character like @ or _  " +
            "with minimum size of 8 letters" )
    private String password;

    @Email()
    private String email;

    @Min(value = 16 , message = "Your age must be greater than or equals to 16")
    private int age;

    @Past( message = "birthdate must be in past")
    private Date birthDate;


    private String education;

    private String occupation;

    private String  address;

    private String city;

    private String state;

    public UpdateUserPojo() {
    }

    public UpdateUserPojo(int id, String name, String username, String email, int age,
                          Date birthDate, String education, String occupation, String address, String city, String state, String password) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.age = age;
        this.birthDate = birthDate;
        this.education = education;
        this.occupation = occupation;
        this.address = address;
        this.city = city;
        this.state = state;
        this.password = password;
    }





    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

//    @Override
//    public String toString() {
//        return "UpdateUserPojo{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", username='" + username + '\'' +
//                ", password='" + password + '\'' +
//                ", email='" + email + '\'' +
//                ", age=" + age +
//                ", birthDate=" + birthDate +
//                ", education='" + education + '\'' +
//                ", occupation='" + occupation + '\'' +
//                ", address='" + address + '\'' +
//                ", city='" + city + '\'' +
//                ", state='" + state + '\'' +
//                '}';
//    }
}
