package cl.monsoon.s1next.model;

import android.text.TextUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import cl.monsoon.s1next.singleton.User;

@SuppressWarnings("UnusedDeclaration")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Account {

    @JsonProperty("member_username")
    private String username;

    @JsonProperty("member_uid")
    private String uid;

    @JsonProperty("formhash")
    private String authenticityToken;

    public String getUsername() {
        return username;
    }

    /**
     * Store user info when has logged in (just now or before).
     */
    public void setUsername(String username) {
        this.username = username;

        final boolean isUserExisted = !TextUtils.isEmpty(User.getName());
        if (TextUtils.isEmpty(username)) {
            if (isUserExisted) {
                User.setName(null);
                // user's cookie has expired
                User.sendLogoutOrExpirationBroadcast();
            }
        } else {
            if (!isUserExisted) {
                User.setName(username);
                // login in
                User.sendLoginBroadcast();
            }
        }
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;

        // uid.equals("0") = true when user hasn't logged in
        if (TextUtils.isEmpty(uid) || uid.equals("0")) {
            User.setUid(null);
        } else {
            User.setUid(uid);
        }
    }

    public String getAuthenticityToken() {
        return authenticityToken;
    }

    public void setAuthenticityToken(String authenticityToken) {
        this.authenticityToken = authenticityToken;

        if (TextUtils.isEmpty(authenticityToken)) {
            User.setAuthenticityToken(null);
        } else {
            User.setAuthenticityToken(authenticityToken);
        }
    }
}
