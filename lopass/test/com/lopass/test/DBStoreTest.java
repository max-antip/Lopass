package com.lopass.test;


import com.lopass.file.Storage;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

public class DBStoreTest {

    Storage st;

    public static final String TEST_DB = "test.dat";

    @Before
    public void init() {
       st = new Storage(TEST_DB);
        String filePath = st.getDBPath();

        delDBFile(filePath);
    }


    @Test
    public void testCreateDB() {
        File f = new File(st.getDBPath());
        Assert.assertTrue(f.exists());
        String filePath = st.getDBPath();
        delDBFile(filePath);
    }

    private void delDBFile(String filePath) {
        File f = new File(filePath);

        if (f.exists()) {
            boolean deleted = f.delete();

            if (deleted) {
                System.out.println("DB file " + filePath + " is deleted");
            }
        }
    }


}
