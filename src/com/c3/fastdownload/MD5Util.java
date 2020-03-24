package com.c3.fastdownload;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
    private static final char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f'};
    /**
     * Get MD5 of a file (lower case)
     * @return empty string if I/O error when get MD5
     */
    public static String getFileMD5( File file) {

        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            FileChannel ch = in.getChannel();
            return MD5(ch.map(FileChannel.MapMode.READ_ONLY, 0, file.length()));
        } catch (FileNotFoundException e) {
            return "";
        } catch (IOException e) {
            return "";
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    // �ر��������Ĵ���??�㶼���Ժ���
                }
            }
        }

    }

    /**
     * MD5У���ַ�??
     * @param s String to be MD5
     * @return 'null' if cannot get MessageDigest
     */

    private static String getStringMD5( String s) {
        MessageDigest mdInst;
        try {
            // ���MD5ժҪ�㷨?? MessageDigest ����
            mdInst = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }

        byte[] btInput = s.getBytes();
        // ʹ��ָ�����ֽڸ���ժ??
        mdInst.update(btInput);
        // �������
        byte[] md = mdInst.digest();
        // ������ת����ʮ�����Ƶ��ַ�����ʽ
        int length = md.length;
        char str[] = new char[length * 2];
        int k = 0;
        for (byte b : md) {
            str[k++] = hexDigits[b >>> 4 & 0xf];
            str[k++] = hexDigits[b & 0xf];
        }
        return new String(str);
    }

    /**
     * ����MD5У��
     * @param buffer
     * @return �մ�������޷���?? MessageDigestʵ��
     */

    private static String MD5(ByteBuffer buffer) {
        String s = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(buffer);
            byte tmp[] = md.digest(); // MD5 �ļ�������???? 128 λ�ĳ�������
            // ���ֽڱ�ʾ��?? 16 ����??
            char str[] = new char[16 * 2]; // ÿ���ֽ�?? 16 ���Ʊ�ʾ�Ļ���ʹ�������ַ���
            // ??�Ա�ʾ�� 16 ����???? 32 ����??
            int k = 0; // ��ʾת������ж�Ӧ���ַ�λ��
            for (int i = 0; i < 16; i++) { // �ӵ�??���ֽڿ�ʼ��?? MD5 ��ÿ??����??
                // ת��?? 16 �����ַ���ת??
                byte byte0 = tmp[i]; // ȡ�� i ����??
                str[k++] = hexDigits[byte0 >>> 4 & 0xf]; // ȡ�ֽ���?? 4 λ������ת��, >>>,
                // �߼����ƣ�������λһ����??
                str[k++] = hexDigits[byte0 & 0xf]; // ȡ�ֽ���?? 4 λ������ת��
            }
            s = new String(str); // ����Ľ��ת��Ϊ�ַ�??

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return s;
    }
}
