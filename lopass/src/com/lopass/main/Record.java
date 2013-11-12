package com.lopass.main;


import java.util.ArrayList;
import java.util.List;

public class Record {
    List<LoginPass> loginPassList;
    String title;


    public Record(String title) {
        this.title = title;
        loginPassList = new ArrayList<>();
    }

    public Record() {
        loginPassList = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public boolean addLoginPass(LoginPass loginPass) {
        loginPass.setParentTitle(title);
        return loginPassList.add(loginPass);
    }

    public void removeLoginPass(LoginPass loginPass) {
        loginPassList.remove(loginPass);
    }

    public int getPassSize() {
        return loginPassList.size();
    }

    public void setTitle(String title) {
        this.title = title;
        for (LoginPass lp : loginPassList) {
            lp.setParentTitle(title);
        }
    }

    public List<LoginPass> getLoginPassList() {
        return loginPassList;
    }

    public static class LoginPass {
        String parentTitle;
        String subTitle;
        String login;
        String pass;

        public LoginPass(String subTitle, String login, String pass) {
            this.subTitle = subTitle;
            this.login = login;
            this.pass = pass;
        }

        public void setParentTitle(String parentTitle) {
            this.parentTitle = parentTitle;
        }

        public String getParentTitle() {
            return parentTitle;
        }

        private void setSubTitle(String subTitle) {
            this.subTitle = subTitle;
        }

        private void setLogin(String login) {
            this.login = login;
        }

        private void setPass(String pass) {
            this.pass = pass;
        }

        public LoginPass(String login, String pass) {
            this(null, login, pass);
        }

        public String getSubTitle() {
            return subTitle;
        }

        public String getLogin() {
            return login;
        }

        public String getPass() {
            return pass;
        }
    }

    public boolean equals(Object obj) {
        if (obj instanceof Record) {
            Record record = (Record) obj;
            return record.getTitle().equals(title);
        }
        return false;
    }

    public String toString() {
        StringBuffer stb = new StringBuffer();

        stb.append(title).append("\n");
        for (LoginPass lp : loginPassList) {
            stb.append("Subtitle : ").append(lp.getSubTitle()).append("\n");
            stb.append("Login    : ").append(lp.getLogin()).append("\n");
            stb.append("Password : ").append(lp.getPass()).append("\n");
        }

        return stb.toString();
    }

}
