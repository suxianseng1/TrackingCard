package com.nokida.trackingcard.service;

import com.nokida.trackingcard.TestBase;
import com.nokida.trackingcard.service.impl.DispatchCreateWordServiceImpl;
import com.nokida.trackingcard.word.WordAction;
import com.spire.doc.Document;
import com.spire.doc.FileFormat;
import org.apache.commons.lang3.text.WordUtils;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class DispatchCteateWordServiceTest extends TestBase {



    @Test
    public void testCreateWord() {
        try {
            DispatchCreateWordServiceImpl service = new DispatchCreateWordServiceImpl();
            service.createWordByJobNumber("00100000136700187-2-8-1","00100000136700187-2-8-1");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCreateWordByWbs() {
        try {
            DispatchCreateWordServiceImpl service = new DispatchCreateWordServiceImpl();
            service.createWordBySingleCode("定18859","t013","1");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        Map<String,Object> data = new HashMap<>();
        data.put("PRODUCT_NO","gjflkagfa");
        data.put("WBS","432");
        data.put("DRAWING_NUM","gfd564");
        data.put("MATERIAL_NAME","gklfd");
        data.put("year", Calendar.getInstance().get(Calendar.YEAR));
        data.put("WORD_NO","test");
        data.put("ORDER_CODE","gfs");
        WordAction wordAction = new WordAction();
        String filePath = wordAction.createWord(null,data,"D:\\test.doc");
    }
}
