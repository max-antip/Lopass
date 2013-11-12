package com.lopass.file;


import com.lopass.crypto.Coder;
import com.lopass.main.Record;

import java.util.List;

public class StorageService {
    private static StorageService instance;

    private Coder coder;
    private Storage storage;

    List<Record> recordList;

    public static void init(String dbName, char[] pass) {
        instance = new StorageService(dbName, pass);
    }

    public List<Record> getAllRecords() {
        return recordList;
    }

    public static StorageService getInstance() {
        if (instance == null) {
            throw new IllegalStateException
                    (StorageService.class.getName() + " must be init");
        }
        return instance;
    }

    public static boolean isInit() {
        return instance != null;
    }

    public void saveBase() {
        String allData = Parser.unParse(recordList);
        if (allData != null) {
            saveToDB(allData);
        }
    }

    public boolean addRecord(Record item) {
        return recordList.add(item);
    }

    public boolean addSubRecord(String passItemTitle, Record.LoginPass lp) {
        if (lp == null) throw new NullPointerException("LoginPass must be not null");
        for (Record ps : recordList) {
            if (ps.getTitle().equals(passItemTitle)) {
                return ps.addLoginPass(lp);
            }
        }
        System.out.println("Item with title " +
                passItemTitle + " was not found");
        return true;
    }

    private StorageService(String dbName, char[] pass) {
        String passStr = String.valueOf(pass);
        coder = new Coder(passStr);
        storage = new Storage(dbName);
    }

    public void saveToDB(String data) {
        storage.saveToDB(data, coder);
    }

    public boolean loadDB() {
        String decryptedData = storage.loadDecryptedDB(coder);
        if (decryptedData == null) {
            return false;
        }
        recordList = Parser.parseFromDB(decryptedData);
        return true;
    }

    public static void main(String[] args) {
        StorageService.init("maximus", "popolius".toCharArray());
        StorageService st = StorageService.getInstance();

//        st.saveToDB("$T$Server<DD>$S$Server-War<DD>$P$popolius321<DD>$L$Maximus<DD>$SUB_END$<DD>$END$");

        System.out.println(st.loadDB());
    }

}
