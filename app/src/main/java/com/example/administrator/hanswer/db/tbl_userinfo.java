package com.example.administrator.hanswer.db;

/**
 * Created by K on 2018/6/26 0026.
 */

public class tbl_userinfo {
    private int id;
    private String username;
    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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


    public tbl_userinfo() {
    }

    public tbl_userinfo(int id, String username, String password, String perssion) {
        this.id = id;
        this.username = username;
        this.password = password;
    }
}
