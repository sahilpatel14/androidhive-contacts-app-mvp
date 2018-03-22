package info.androidhive.phonebook.models;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by sahil-mac on 13/03/18.
 */

public class PhoneBookUser {

    private int id;

    private String name;
    private String surname;
    private String gender;
    private String region;

    private int age;
    private String title;
    private String phone;
    private Map<String, String> birthday;
    private String email;

    private String photo;

    private static int sUserCount = 0;


    public PhoneBookUser() {
        birthday = new LinkedHashMap<>();
        id = ++sUserCount;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Map<String, String> getBirthday() {
        return birthday;
    }

    public void setBirthday(Map<String, String> birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        int i1 = photo.lastIndexOf("/");
        int i2 = photo.lastIndexOf(".");

        String extension = photo.substring(i2, photo.length());
        String relativePath = photo.substring(0, i1+1);
        this.photo = relativePath + id + extension;
    }
}
