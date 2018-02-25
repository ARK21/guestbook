package com.ark.models;

import java.util.Objects;

/**
 * Bean class contains information about user input
 */
public class UserData  {


    private int id; // id in database
    private String username; // entered name
    private String email; // entered email
    private String messageText; // text of message
    private String homePage; // website's address
    private String captchaAnswer;  // answer to captcha
    private String ip; // ip where the message come from
    private String browser; // browser name where the message come from
    private String date;  // publication date

    /**
     * Empty constructor for hibernate
     */
    public UserData() {

    }

    // Getters and setters for fields
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getHomePage() {
        return homePage;
    }

    public void setHomePage(String homepage) {
        this.homePage = homepage;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getCaptchaAnswer() {
        return captchaAnswer;
    }

    public void setCaptchaAnswer(String captchaText) {
        this.captchaAnswer = captchaText;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    // overridden methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserData userData = (UserData) o;
        return id == userData.id &&
                Objects.equals(username, userData.username) &&
                Objects.equals(email, userData.email) &&
                Objects.equals(messageText, userData.messageText) &&
                Objects.equals(homePage, userData.homePage) &&
                Objects.equals(ip, userData.ip) &&
                Objects.equals(browser, userData.browser) &&
                Objects.equals(captchaAnswer, userData.captchaAnswer) &&
                Objects.equals(date, userData.date);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, username, email, messageText, homePage, ip, browser, captchaAnswer, date);
    }

    @Override
    public String toString() {
        return username + ": " + messageText;
    }
}
