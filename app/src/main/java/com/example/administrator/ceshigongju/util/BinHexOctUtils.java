package com.example.administrator.ceshigongju.util;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.regex.Pattern;

/**
 * Created by lilingke on 2017/5/8 0008.
 */
public class BinHexOctUtils {

    /**
     * 字符序列转换为16进制字符串
     *
     * @param src
     * @return
     */
    public static String bytes2HexString(byte[] src) {
        StringBuilder builder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        char[] buffer = new char[2];
        for (int i = 0; i < src.length; i++) {
            buffer[0] = Character.forDigit((src[i] >>> 4) & 0x0F, 16);
            buffer[1] = Character.forDigit(src[i] & 0x0F, 16);
            builder.append(buffer);
        }
        return builder.toString().toUpperCase();
    }

    /**
     * 十六进制字节数组转十进制数
     *
     * @param src
     * @return
     */
    public static long hexByte2Dec(byte[] src) {
        StringBuilder builder = new StringBuilder();
        String hex = bytes2HexString(src);
        for (int i = 0; i < hex.length() / 2; i++) {
            String str = hex.substring(hex.length() - 2 * (i + 1), hex.length() - 2 * i);
            builder.append(str);
        }
        return Long.parseLong(builder.toString(), 16);
    }

    /**
     * 十六进制String 转十进制数字
     *
     * @param strHex
     * @return
     */
    public static long hexStr2Dec(String strHex) {
        Log.i("BinHexOctuils", "strHex:" + strHex);
        return Long.parseLong(strHex, 16);
    }

    /**
     * hexStr to decimal
     *
     * @param hex
     * @return
     */
    public static long hexStr2Decimal(String hex) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < hex.length() / 2; i++) {
            String str = hex.substring(hex.length() - 2 * (i + 1), hex.length() - 2 * i);
            System.out.println(str);
            builder.append(str);
        }
        return Long.parseLong(builder.toString(), 16);
    }

    /**
     * 把十进制数字转换成足位的十六进制字符串 -->补全空位
     *
     * @param num
     * @return
     */
    public static String integer2HexString(int num) {
        String hex = Integer.toHexString(num);
        if (hex.length() % 2 != 0) {
            return "0" + hex;
        }
        return hex.toUpperCase();
    }

    /**
     * 把十进制数字转换成足位的十六进制字符串,并补全空位
     *
     * @param num        需要转换的数字
     * @param byteAmount 字节数
     * @return
     */
    public static String decimal2fitHex(long num, int byteAmount) {
        String hex = Long.toHexString(num).toUpperCase();
        StringBuilder stringBuilder = new StringBuilder(hex);
        int strLength = byteAmount * 2;
        while (stringBuilder.length() < strLength) {
            stringBuilder.insert(0, '0');
        }
        return stringBuilder.toString();
    }

    /**
     * 字符串转十六进制字符串
     *
     * @param str
     * @return
     */
    public static String str2HexString(String str) {
        char[] chars = "0123456789ABCDEF".toCharArray();
        StringBuilder sb = new StringBuilder();
        byte[] bs = null;
        try {
            bs = str.getBytes("gbk");
        } catch (Exception e) {
            e.printStackTrace();
        }
        int bit;
        for (int i = 0; i < bs.length; i++) {
            bit = (bs[i] & 0x0f0) >> 4;
            sb.append(chars[bit]);
            bit = bs[i] & 0x0f;
            sb.append(chars[bit]);
        }
        return sb.toString();
    }

    /**
     * 把十六进制字符串转换成十六进制字节数组
     *
     * @param
     * @return byte[]
     */
    public static byte[] hexStringToHexBytes(String hex) {
        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] achar = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
        }
        return result;
    }

    private static int toByte(char c) {
        byte b = (byte) "0123456789ABCDEF".indexOf(c);
        return b;
    }

    /**
     * 十六进制把字符串转换成十六进制字节数组
     *
     * @param src
     * @return
     */
    public static byte[] hexString2Bytes(String src) {
        if (null == src || 0 == src.length()) {
            return null;
        }
        byte[] ret = new byte[src.length() / 2];
        byte[] tmp = src.getBytes();
        for (int i = 0; i < (tmp.length / 2); i++) {
            ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
        }
        return ret;
    }

    public static byte uniteBytes(byte src0, byte src1) {
        byte _b0 = Byte.decode("0x" + new String(new byte[]{src0})).byteValue();
        _b0 = (byte) (_b0 << 4);
        byte _b1 = Byte.decode("0x" + new String(new byte[]{src1})).byteValue();
        byte ret = (byte) (_b0 ^ _b1);
        return ret;
    }

    /**
     * 十六进制字节数组转字符串
     *
     * @param bArray
     * @return
     */
    public final static String bytesToHexString(byte[] bArray) {
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2) sb.append(0);
            sb.append(sTemp);
        }
        return sb.toString().toUpperCase();
    }

    /**
     * 十六进制字节数组转字符串
     *
     * @param src    目标数组
     * @param dec    起始位置
     * @param length 长度
     * @return
     */
    public static String bytesToHexString(byte[] src, int dec, int length) {
        byte[] temp = new byte[length];
        System.arraycopy(src, dec, temp, 0, length);
        return bytesToHexString(temp);
    }

    /**
     * 十六进制字符串转十进制字符串
     *
     * @param hex
     * @return
     */
    public static String hex2String(String hex) {
        return bytes2String(hexString2Bytes(hex));
    }

    /**
     * 字节数组转字符串
     *
     * @param arr
     * @return
     */
    public static String bytes2String(byte[] arr) {
        try {
            return new String(arr, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 判断字符串是否是浮点数
     */
    public static boolean isDouble(String value) {
        try {
            Double.parseDouble(value);
            if (value.contains(".")) return true;
            return false;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 判断字符串是否是数字
     */
    public static boolean isNumber(String value) {
        return isInteger(value) || isDouble(value);
    }

    //验证字符串中包含数字，很简单。
    public void isContainNumber(String str) {
        boolean isNumber = true;
        char[] ch = str.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            isNumber = Character.isDigit(ch[i]);
            if (isNumber) break;
        }
        if (isNumber) System.out.println("输入的字符串中包含数字!");
    }

    /*
      * 判断是否为整数
      * @param str 传入的字符串
      * @return 是整数返回true,否则返回false
    */
    public static boolean iSInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    /**
     * 判断当前字符是否是整数或者是带一位小数的浮点数
     *
     * @param str
     * @return
     */
    public static boolean isIntegerOrFloat(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]+(\\.\\d)?$");
        return pattern.matcher(str).matches();
    }


    /**
     * 将16进制转换为二进制
     *
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    /**
     * 将二进制转换成16进制
     *
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 跟指定的key异或
     *
     * @param bytes
     * @return
     */
    public byte[] encrypt(byte[] bytes) {
        if (bytes == null) {
            return null;
        }

        int len = bytes.length;
        int key = 0xA65B8E;
        for (int i = 0; i < len; i++) {


            bytes[i] ^= key;
        }
        return bytes;
    }

    public static void main(String[] args) {

        //int count = CalendarUtils.getGapCount("2017-01-01", CalendarUtils.getCurrentDay());
        String value = exclusiveOrSelf("F0B5D1713693");
        System.out.println(value);
    }

    /**
     * 生成临时密码
     *
     * @param day
     * @param num
     * @return
     */
    public static int createTemporaryPw(int day, int num) {
        String password = "";
        int src = 0;
        src = day << 11;
        src = src | num;
        int dst = src ^ 0xa65b8e;
        int sort = 0;

        int mask1 = 0xfc0000; // 111111 00000000 00000 00000
        int mask2 = 0x3fc00; // 111111110000000000
        int mask3 = 0x3e0; // 1111100000
        int mask4 = 0x1f; // 11111

        sort = (dst & mask4) << 19;
        sort = (dst & mask2) << 1 | sort;
        sort = (dst & mask1) >> 13 | sort;
        sort = (dst & mask3) >> 5 | sort;

        String str = Integer.toBinaryString(sort);
        System.out.println("乱序之后的数据 == " + str);

        String str1 = str.replace("0", "");
        int count = str1.length();
        String count1 = Integer.toBinaryString(count);
        if (count1.length() == 1) {
            System.out.println("取到 == " + "0" + count1);
            password = Integer.toBinaryString(sort) + "0" + count1;
            System.out.println("password == " + password);
            return Integer.parseInt(password, 2);
        } else if (count1.length() >= 2) {
            System.out.println("取到的 == " + count1.substring(count1.length() - 2, count1.length()));
            password = Integer.toBinaryString(sort) + count1.substring(count1.length() - 2, count1.length());
            System.out.println("password == " + password);
            System.out.println(Long.parseLong(password, 2));

            return Integer.parseInt(password, 2);
        }
        return 0;
    }


    /**
     * 把十进制数字转换成足位的十六进制字符串,并补全空位
     *
     * @param value
     * @param strLength 字符串的长度
     * @return
     */
    public static String decimal2fitHex(String value, int strLength) {
        StringBuilder stringBuilder = new StringBuilder(value);
        while (stringBuilder.length() < strLength) {
            stringBuilder.insert(0, '0');
        }
        return stringBuilder.toString();
    }

    /**
     * 十六进制的string数组跟int异或
     *
     * @param str
     * @param count
     * @return
     */
    public static String stringXORInt(String str, int count) {
        byte[] arr = new byte[]{0x56, (byte) 0xa6, (byte) 0x85, (byte) 0xf9, (byte) 0xb5, 0x25};
        byte[] bytes = ByteUtil.hexStr2bytes(str);
        System.out.println();
        System.out.println();


        byte[] result = new byte[arr.length];

        for (int i = 0; i < result.length; i++) {
            result[i] = (byte) (bytes[i] ^ arr[i]);
        }

        return ByteUtil.bytes2HexStr(result);

    }

    /**
     * 十六进制的string数组按位异或
     *
     * @param str
     * @return
     */
    public static String exclusiveOrSelf(String str) {
        byte[] bytes = ByteUtil.hexStr2bytes(str);
        int result = (bytes[0] ^ bytes[1] ^ bytes[2] ^ bytes[3] ^ bytes[4] ^ bytes[5]) & 0xFF;
        String hex = Long.toHexString(result).toUpperCase();
        return hex;

    }

    /**
     * string数组跟十六进制数组异或
     *
     * @param str
     * @param str1
     * @return
     */
    public static String stringXORString(String str, String str1) {

        int arr1[] = new int[6];
        for (int i = 0; i < str.length(); i++) {
            arr1[i] = Integer.parseInt(str.substring(i, i + 1));
        }

        byte[] bytes = ByteUtil.hexStr2bytes(str1);
        byte[] result = new byte[bytes.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = (byte) (bytes[i] ^ arr1[i]);
        }

        System.out.println(ByteUtil.bytes2HexStr(result));
        System.out.println(str);
        System.out.println(str1);

        return ByteUtil.bytes2HexStr(result);

    }

    /**
     * 写设备时间
     *
     * @param
     * @return
     */
    public static String setDeviceTime() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        int weekDay = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        return setDeviceTime1(year, month, day, hour, minute, second, weekDay);
    }

    /**
     * 写设备时间
     *
     * @param year    年，2字节
     * @param month   月，1字节
     * @param day     日，1字节
     * @param hour    时，1字节
     * @param minute  分，1字节
     * @param second  秒，1字节
     * @param weekDay 星期，1字节
     * @return
     */
    public static String setDeviceTime1(int year, int month, int day, int hour, int minute, int second,
                                        int weekDay) {

        StringBuilder sb = new StringBuilder();
        sb.append(ByteUtil.decimal2fitHex(year, 4))
                .append(ByteUtil.decimal2fitHex(month, 2))
                .append(ByteUtil.decimal2fitHex(day, 2))
                .append(ByteUtil.decimal2fitHex(hour, 2))
                .append(ByteUtil.decimal2fitHex(minute, 2))
                .append(ByteUtil.decimal2fitHex(second, 2))
                .append(ByteUtil.decimal2fitHex(weekDay, 2));


        return sb.toString();
    }

    /**
     * 用递归把二进制字符串转成十进制
     *
     * @param str
     * @return
     */
    public static int parseByte2Hex(String str) {
        int sum = 0;
        if (str.length() == 1) {
            int s = Integer.parseInt(str);
            return s * (int) Math.pow(2, 0);
        } else {
            sum = Integer.parseInt(str.substring(0, 1)) * (int) Math.pow(2, str.length() - 1) + parseByte2Hex(str.substring(1, str.length()));
        }

        return sum;
    }
}

