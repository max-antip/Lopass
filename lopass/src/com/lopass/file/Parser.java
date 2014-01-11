package com.lopass.file;

import com.lopass.main.Record;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    //todo  переписать парсер для более гибкого использоварния
    private static final String TITLE = "$T$";
    private static final String LOGIN = "$L$";
    private static final String PASS = "$P$";
    private static final String END = "$END$";
    public static final String DIVIDER = "<DD>";


    public static List<Record> parseFromDB(String data) {
        String[] splitData = data.split(DIVIDER);

        List<Record> passList = new ArrayList<>();
        Record record = new Record();
        String login = null;
        String pass = null;
        String title = null;

        if (splitData.length > 0) {
            for (String s : splitData) {
                if (s.startsWith(TITLE)) {
                    title = s.substring(3, s.length());
                } else if (s.startsWith(LOGIN)) {
                    login = s.substring(3, s.length());
                } else if (s.startsWith(PASS)) {
                    pass = s.substring(3, s.length());
                } else if (s.equals(END)) {
                    record = new Record(title, login, pass);
                    passList.add(record);
                }
            }
        }
        return passList;

    }

    public static String unParse(List<Record> items) {
        if (items == null) return null;
        StringBuilder stb = new StringBuilder();
        for (Record record : items) {
            stb.append(TITLE).append(record.getTitle()).append(DIVIDER);
            stb.append(LOGIN).append(record.getLogin()).append(DIVIDER);
            stb.append(PASS).append(record.getPass()).append(DIVIDER);
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
