package com.lopass.file;

import com.lopass.main.Record;

import java.util.ArrayList;
import java.util.List;

public class Parser {

    private static final String TITLE = "$T$";
    private static final String SUBTITLE = "$S$";
    private static final String LOGIN = "$L$";
    private static final String PASS = "$P$";
    private static final String SUB_END = "$SUB_END$";
    private static final String END = "$END$";
    public static final String DIVIDER = "<DD>";


    public static List<Record> parseFromDB(String data) {
        String[] splitData = data.split(DIVIDER);

        List<Record> passList = new ArrayList<>();
        Record record = new Record();
        Record.LoginPass loginPass;

        String login = null;
        String pass = null;
        String title = null;
        String subTitle = null;

        if (splitData.length > 0) {
            for (String s : splitData) {
                if (s.startsWith(TITLE)) {
                    title = s.substring(3, s.length());
                } else if (s.startsWith(LOGIN)) {
                    login = s.substring(3, s.length());
                } else if (s.startsWith(PASS)) {
                    pass = s.substring(3, s.length());
                } else if (s.startsWith(SUBTITLE)) {
                    subTitle = s.substring(3, s.length());
                } else if (s.equals(SUB_END)) {
                    if (title != null && login != null && pass != null) {
                        loginPass = new Record.LoginPass(subTitle, login, pass);
                        record.setTitle(title);
                        record.addLoginPass(loginPass);
                    }
                } else if (s.equals(END)) {
                    passList.add(record);
                    record = new Record();
                }
            }
        }
        return passList;

    }

    public static String unParse(List<Record> items) {
        if (items == null) return null;
        StringBuilder stb = new StringBuilder();
        for (Record record : items) {
            List<Record.LoginPass> loginPassList =
                    record.getLoginPassList();
            for (Record.LoginPass loginPass : loginPassList) {
                stb.append(TITLE).append(loginPass.getParentTitle()).append(DIVIDER);
                stb.append(SUBTITLE).append(loginPass.getSubTitle()).append(DIVIDER);
                stb.append(LOGIN).append(loginPass.getLogin()).append(DIVIDER);
                stb.append(PASS).append(loginPass.getPass()).append(DIVIDER);
                stb.append(SUB_END).append(DIVIDER);
            }
            stb.append(END).append(DIVIDER);
        }
        return stb.toString();
    }

    public static void main(String[] args) {
        Parser parser = new Parser();
        String data = "$T$Server<DD>$S$Server-War<DD>$P$popolius321<DD>$L$Maximus<DD>$SUB_END$<DD>$END$";

        System.out.println(parser.parseFromDB(data));

    }


}
