package com.example.isao.twoactivities2;

import retrofit2.Call;
import retrofit2.http.GET;

public class GetInfoRetrofit {

    public interface RequestInterface {
        @GET("/users/IhorPakharenko")
        Call<JSONResponse> getJSON();
    }


    public class UserInfo {

        private String login;
        private String email;
        private String location;
        private String avatar_url;

        public String getGitName() {
            return login;
        }
        public String getGitEmail() {
            return email;
        }
        public String getGitAddress() {
            return location;
        }
        public String getGitAvatar() {
            return avatar_url;
        }

    }

    public class JSONResponse {

        private UserInfo[] user;

        public UserInfo[] getUser() {

            return user;
        }

    }
}