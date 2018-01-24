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

    private UserInfoPrincipal(Object id, Object name, Object firstName, Object lastName, Object email, Object picture, Object profile, boolean emailVerified, String infoProvider) {
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
                userInfo.get("picture"),
                userInfo.get("profile"),
                Boolean.valueOf(userInfo.get("email_verified").toString()), InfoProvider.GOOGLE.name());
    }
    public static  UserInfoPrincipal fromFacebook(Map<String, ?> userInfo){
        Assert.notNull(userInfo, "User info map cannot be null");
        Map pictureMap = (Map) userInfo.get("picture");
        String pictureUrl = null;
        if (pictureMap != null) {
            Map pictureData = (Map) pictureMap.get("data");
            pictureUrl = (String) pictureData.get("url");
        }
        return new UserInfoPrincipal(
                userInfo.get("id"),
                userInfo.get("name"),
                userInfo.get("first_name"),
                userInfo.get("last_name"),
                userInfo.get("email"),
                pictureUrl,
                userInfo.get("profile"),
                false, InfoProvider.FACEBOOK.name());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
