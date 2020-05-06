package com.bjfu.news.untils;

import java.io.*;

public class FileUtils {

    public static String saveFile(InputStream inputStream, String fileName, String path) {
        OutputStream os = null;
        try {
            // 1K的数据缓冲
            byte[] bs = new byte[1024];

            // 读取到的数据长度
            int len;
            // 输出的文件流保存到本地文件
            File tempFile = new File(path);
            if (!tempFile.exists()) {
                tempFile.mkdirs();
            }
            os = new FileOutputStream(tempFile.getPath() + File.separator + fileName);
            // 开始读取
            while ((len = inputStream.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 完毕，关闭所有链接
            try {
                os.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return path + "\\" + fileName;
    }

    public static InputStream readFile(String path) {
        File file = new File(path);
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return fileInputStream;
    }

}
