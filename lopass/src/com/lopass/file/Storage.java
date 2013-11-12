package com.lopass.file;


import com.lopass.crypto.Coder;

import java.io.*;
import java.security.GeneralSecurityException;

public class Storage {

    private static final String APP_DIR = System.getProperty("user.dir");
    private static final String DB_DIR = APP_DIR + "/data";
    private static final String DEFAULT_DB_FILE_NAME = "db_main.dat";
    public static final String DB_MAIN = DB_DIR + "/" + DEFAULT_DB_FILE_NAME;
    private static final String TEMP_FILE = APP_DIR + "/data/temp";

    private String currentDBFile = "";

    public Storage(String dbName) {
        currentDBFile = DB_DIR + "/" + dbName + ".dat";
    }

    public byte[] loadDB() {
        File db = new File(currentDBFile);
        if (!db.exists()) {
            System.out.println(db.getAbsolutePath() + " is not exist it will be create");
            createDB();
            byte[] empty = new byte[0];
            return empty;
        }

        byte[] dbContent = new byte[(int) db.length()];
        try (InputStream fis = new FileInputStream(currentDBFile)) {

            int buf = 0;
            boolean empty = true;
            for (int i = 0; (buf = fis.read()) != -1; i++) {
                dbContent[i] = (byte) buf;
            }

            if (dbContent.length == 0) {
                System.out.println("DB is empty");
            }

        } catch (IOException e) {
            System.out.println("Failed to load db from file " + currentDBFile);
            e.printStackTrace();
        }
        return dbContent;
    }

    public String loadDecryptedDB(Coder coder) {
        byte[] data = loadDB();
        try {
            return coder.decryptString(data);
        } catch (GeneralSecurityException e) {
//            e.printStackTrace();
            System.out.println("Wrong pass");
            return null;
        }
    }

    public void saveToDB(String data, Coder coder) {
        try {
            StoreToDB(coder.encryptString(data));
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
    }

    public void StoreToDB(byte[] data) {
        File db = new File(currentDBFile);
        if (!db.exists()) {
            System.out.println(db.getAbsolutePath() + " is not exist it will be create");
            createDB();
        }

        try (OutputStream fout = new FileOutputStream(currentDBFile)) {

            fout.write(data);

        } catch (IOException e) {
            System.out.println("Failed to wright data to db file " + currentDBFile);
            e.printStackTrace();
        }
    }

    private void createDB() {
        File dir = new File(DB_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
            System.out.println("Dir " + dir.getAbsolutePath() + " created");
        }

        File file = new File(currentDBFile);
        try {
            boolean fileCreated = file.createNewFile();

            if (fileCreated) {
                System.out.println("Created empty file " + file.getAbsolutePath());
            } else {
                System.out.println("Could not create file " + file.getAbsolutePath());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getDBPath() {
        return DB_DIR + "/" + DEFAULT_DB_FILE_NAME;
    }

    public void setDBFile(String file) {
        currentDBFile = DB_DIR + "/" + file + ".dat";
        System.out.println("DB changed to" + currentDBFile);
    }
}

