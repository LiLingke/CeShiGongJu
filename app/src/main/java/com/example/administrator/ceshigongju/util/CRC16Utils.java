package com.example.administrator.ceshigongju.util;

import java.io.UnsupportedEncodingException;

public class CRC16Utils {
	/**
	 * 字符串转十六进制字符串
	 * 
	 * @param str
	 * @return
	 */
	public static String str2HexStr(String str) {
		char[] chars = "0123456789ABCDEF".toCharArray();
		StringBuilder sb = new StringBuilder("");
		byte[] bs = null;
		try {
			bs = str.getBytes("gb2312");
		} catch (UnsupportedEncodingException e) {
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
	 * 把字符串转换成十六进制字节数组
	 * 
	 * @param hex
	 * @return byte[]
	 */
	public static byte[] stringToHexByte(String hex) {
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
	 * @param src
	 * @return
	 */
	public static byte[] HexString2Bytes(String src) {
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
		byte _b0 = Byte.decode("0x" + new String(new byte[] { src0 })).byteValue();
		_b0 = (byte) (_b0 << 4);
		byte _b1 = Byte.decode("0x" + new String(new byte[] { src1 })).byteValue();
		byte ret = (byte) (_b0 ^ _b1);
		return ret;
	}
	
	    public static void main(String[] args) {
			String data = "3a a3 00 00 00 00 00 01 00 47 0A C3 82 00 00 00 80 00 00 00 80 00 00 00 80 00 00 00 82 00 00 00 80 00 00 00 80 00 00 00 80 00 00 00 38 00 00 00 00 00 64 00 00 00 00 00 32 01 00 00 00 1F A4 10 00 00 00 1F A4 00 00 00 00 1F A4 10 00 00 00 1F A4";
			System.out.println("string1"+getCRC16("3AA3000000000001000A0A880102030405060001".toUpperCase()));
			System.out.println("string2"+getCRC16(data.replace(" ","").toUpperCase()));
			//getCRC16("3AA3000000000001000A0A880102030405060001");
    }

	/**
	 * CRC检验
	 * @param source
	 * @return
	 */
	public static String getCRC16(String source) {
		int crc = 0xA1EC; 		 				 // 初始值
		int polynomial = 0x1021;			     // 校验公式 0001 0000 0010 0001
		byte[] bytes = stringToHexByte(source);  //把普通字符串转换成十六进制字符串

		for (byte b : bytes) {
			for (int i = 0; i < 8; i++) {
				boolean bit = ((b >> (7 - i) & 1) == 1);
				boolean c15 = ((crc >> 15 & 1) == 1);
				crc <<= 1;
				if (c15 ^ bit)
					crc ^= polynomial;
			}
		}
		crc &= 0xffff;
		StringBuffer result = new StringBuffer(Integer.toHexString(crc));
		while (result.length() < 4) {			//CRC检验一般为4位，不足4位补0
			result.insert(0, "0");
		}
		return result.toString();
	}

}
