package com.ShareDear.project.pojo;
import jakarta.validation.constraints.*;


public class CreateUserPojo {

    @NotNull()
    @Pattern(regexp = "^[a-zA-Z ]{5,}$", message = "Name can contain small letters, capital letters " +
            "and spaces with minimum size of 5 letters" )
    private String name;

    @NotNull()
    @Pattern(regexp = "^(?=.*[a-z])[a-z0-9_]{5,}$" ,message = "Username can contain at least one " +
            "small letter, may or may not contain numbers and special characters like _  with minimum size of 5 ")
    private String username;

    @NotNull()
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[@_])[A-Za-z0-9@_]{5,}$" , message = "Password must contain" +
            " at least one small letter, capital letter, number, and special character like @ or _  " +
            "with minimum size of 5 letters" )
    private String password;

    @Email()
    private String email;

    public CreateUserPojo() {
    }

    public CreateUserPojo(String name, String username, String password, String email) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
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
}
