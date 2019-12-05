package com.okcoin.commons.okex.open.api.test;

import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class readLine {
    public static void main(String[] args) throws IOException {
        // 文件夹路径
        String path = "K:\\btc.txt";
        List<String> scanListPath = readFile(path);
    }


    /**
     * 读取一个文本 一行一行读取
     *
     * @param path
     * @return
     * @throws IOException
     */
    public static List<String> readFile(String path) throws IOException {
        // 使用一个字符串集合来存储文本中的路径 ，也可用String []数组
        List<String> list = new ArrayList<String>();
        FileInputStream fis = new FileInputStream(path);
        // 防止路径乱码   如果utf-8 乱码  改GBK     eclipse里创建的txt  用UTF-8，在电脑上自己创建的txt  用GBK
        InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
        BufferedReader br = new BufferedReader(isr);
        String line = "";
        String jsonStr="";
        String qtyStr="";
        Integer temp =0;
        Integer count =0;
        String string="qty\'"+":"+" \'";

        while ((line = br.readLine()) != null) {
            // 如果 t x t文件里的路径 不包含---字符串       这里是对里面的内容进行一个筛选
            if (line.contains("2019-11-10T16:19:") && line.contains("table")) {

                jsonStr =  line.substring(line.indexOf("{"),line.lastIndexOf("},"));
                //System.out.println(jsonStr);
                qtyStr=jsonStr.substring(jsonStr.indexOf(string),jsonStr.lastIndexOf("\', \'instrument_id"));
                //System.out.println(qtyStr);
                qtyStr = qtyStr.substring(7);

               temp=Integer.parseInt(qtyStr);

                count=temp+count;


            }

        }
        System.out.println(count);
        br.close();
        isr.close();
        fis.close();
        return list;
    }
}
