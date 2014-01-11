package com.lopass.main;


public class Record {
    String title;
    String login;
    String pass;

    public Record(){

    }

    public Record(String title, String login, String pass) {
        this.title = title;
        this.login = login;
        this.pass = pass;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    private void setLogin(String login) {
        this.login = login;
    }

    private void setPass(String pass) {
        this.pass = pass;
    }

    public String getTitle() {
        return title;
    }

    public String getLogin() {
        return login;
    }

    public String getPass() {
        return pass;
    }

    public boolean equals(Object obj) {
        if (obj instanceof Record) {
            Record record = (Record) obj;
            return record.getTitle().equals(title);
        }
        return false;
    }

    public String toString() {
        StringBuilder stb = new StringBuilder();
        stb.append(title).append("\n");
        stb.append("Subtitle : ").append(title).append("\n");
        stb.append("Login    : ").append(login).append("\n");
        stb.append("Password : ").append(pass).append("\n");

        return stb.toString();
    }

}
