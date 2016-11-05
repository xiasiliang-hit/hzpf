package com.line.bqxd.platform.common.utils;

import java.io.*;

/**
 * Created by huangjianfei on 16/9/20.
 */
public class FileUtils {
    public static String readFileByLines(String fileName) throws Exception{
        File file = new File(fileName);
        if (!file.exists()) {
            return null;
        }
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                sb.append(tempString);
            }
        } catch (IOException e) {
            throw e;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    throw e1;
                }
            }
        }
        return sb.toString();
    }


    public static void writeFile(String content, String filePath) throws Exception {
        File file = new File(filePath);
        try {
            if (!file.exists())
                file.createNewFile(); // 创建文件
        } catch (IOException e) {
            throw e;
        }
        byte bt[] = content.getBytes();
        FileOutputStream in =null;
        try {
            in = new FileOutputStream(file);
            in.write(bt, 0, bt.length);
            in.close();
        } catch (IOException e) {
            throw e;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    throw e;
                }
            }
        }

    }


    public static void main(String[] args)throws Exception{
        String file="/Users/huangjianfei/config.txt";
        //writeFile("dsfsdfds",file);

        System.out.println(readFileByLines(file));
    }

}
