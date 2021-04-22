package com.nokida.trackingcard;

import com.spire.doc.Document;
import com.spire.doc.FileFormat;

public class DocUtil {



    public static void main(String[] args) throws Exception {
        test2();
    }


    public static void test2() {
        //获取第一个文档的路径
        String filePath1 = "E:\\MakeWords\\test\\_J-1512_20191120_84.doc";
        //获取第二个文档的路径
        String filePath2 = "E:\\MakeWords\\test\\_J-1507_20191120_36.doc";

        //加载第一个文档
        Document document = new Document(filePath1);

        //使用insertTextFromFile方法将第二个文档的内容插入到第一个文档
        document.insertTextFromFile(filePath2, FileFormat.Docx_2013);

        //保存文档
        document.saveToFile("E://合并后的文件.doc", FileFormat.Docx_2013);
    }
}
