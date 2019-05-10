package nauq.mal.com.formapp.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Admin on 4/13/2017.
 */

public class User implements Serializable {
    @SerializedName("_id")
    private String id;

    @SerializedName("username")
    private String username = "";
    @SerializedName("password")
    private String password = "";
    @SerializedName("account")
    private String account = "";
    @SerializedName("name")
    private String name = "";
    // -1, 1 male, 0 female
    @SerializedName("gender")
    private boolean gender ;
    @SerializedName("birthday")
    private long birthday = -1;
    @SerializedName("address")
    private String address = "";
    @SerializedName("img")
    private String avatar = "";
    @SerializedName("email")
    private String email = "";
    @SerializedName("phone")
    private String phone = "";



    public User(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public long getBirthday() {
        return birthday;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
