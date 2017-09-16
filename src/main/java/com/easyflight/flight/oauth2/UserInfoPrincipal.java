package com.easyflight.flight.oauth2;

import com.easyflight.flight.enums.InfoProvider;
import org.springframework.util.Assert;

import java.util.Map;

/**
 * Created by Victor Ikoro on 7/29/2017.
 */
public class UserInfoPrincipal {

    private String id;
    private String name;
    private String firstName;
    private String lastName;
    private String email;
    private String profile;
    private String picture;
    private boolean emailVerified;
    private String infoProvider;

    private  UserInfoPrincipal(Object id, Object name, Object firstName, Object lastName, Object email, Object profile, Object picture, boolean emailVerified, String infoProvider){
        this.id = (String) id;
        this.name = (String) name;
        this.firstName = (String) firstName;
        this.lastName = (String) lastName;
        this.email = (String) email;
        this.profile = (String) profile;
        this.picture = (String) picture;
        this.emailVerified = emailVerified;
        this.infoProvider = infoProvider;
    }

    public static UserInfoPrincipal fromGoogle(Map<String, ?> userInfo) {
        Assert.notNull(userInfo, "User info map cannot be null");
        return new UserInfoPrincipal(
                userInfo.get("sub"),
                userInfo.get("name"),
                userInfo.get("given_name"),
                userInfo.get("family_name"),
                userInfo.get("email"),
                userInfo.get("profile"),
                userInfo.get("picture"),Boolean.valueOf(userInfo.get("email_verified").toString()), InfoProvider.GOOGLE.name());
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public boolean getEmailVerified() {
        return emailVerified;
    }

    public String getInfoProvider() {
        return infoProvider;
    }

    public String getProfile() {
        return profile;
    }

    public String getPicture() {
        return picture;
    }
}
