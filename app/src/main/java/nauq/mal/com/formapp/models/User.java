package nauq.mal.com.formapp.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Admin on 4/13/2017.
 */

public class User implements Serializable {
    @SerializedName("id")
    private int id;
    @SerializedName("userName")
    private String userName = "";
    @SerializedName("fullName")
    private String fullName = "";
    // -1, 1 male, 0 female
    @SerializedName("gender")
    private int gender = -1;
    @SerializedName("phoneNumber")
    private String phoneNumber = "";
    @SerializedName("address")
    private String address = "";
    @SerializedName("avatar")
    private String avatar = "";
    @SerializedName("email")
    private String email = "";
    @SerializedName("birthDate")
    private long birthDate = -1;



    public User(){}

    public User(String userName, String avatar) {
        this.userName = userName;
        this.avatar = avatar;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }


    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public long getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(long birthDate) {
        this.birthDate = birthDate;
    }
}
