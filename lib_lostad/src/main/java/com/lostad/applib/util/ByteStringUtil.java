package com.lostad.applib.util;

public class ByteStringUtil {
	/**
	 * 将两个ASCII字符合成一个字节； 如："EF"–> 0xEF
	 * 
	 * @param src0
	 *            byte
	 * @param src1
	 *            byte
	 * @return byte
	 */
	private static byte uniteBytes(byte src0, byte src1) {
		String value = null;
		value = new String(new byte[] { src0 });
		byte _b0 = Byte.decode("0x" + value).byteValue();
		_b0 = (byte) (_b0 << 4);
		value = new String(new byte[] { src1 });
		byte _b1 = Byte.decode("0x" + value).byteValue();
		byte ret = (byte) (_b0 ^ _b1);// 异或运算 同0，不同1
		value = null;
		
		return ret;
	}

	/**
	 * 将指定字符串src，以每两个字符分割转换为16进制形式 如："2B44EFD9" –> byte[]{0x2B, 0×44, 0xEF,
	 * 0xD9}
	 * FFFE oxFF oxFE
	 * 
	 * @param
	 *
	 * @return byte[]
	 */
	public static byte[] hexStringToBytes(String str) {
		str = str.replaceAll(" ", "");
		int length = str.length() / 2;
		byte[] ret = new byte[length];
		byte[] tmp = str.getBytes();
		for (int i = 0; i < length; i++) {
			ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
		}
		str = null;
		return ret;
	}

	/**
	 * 将int转换为byte数组
	 * 
	 * @param
	 * @return
	 */
	public static byte[] intToBytes4(int i) {
		byte[] result = new byte[4];
		result[0] = (byte) ((i >> 24) & 0xFF);
		result[1] = (byte) ((i >> 16) & 0xFF);
		result[2] = (byte) ((i >> 8) & 0xFF);
		result[3] = (byte) (i & 0xFF);
		return result;
	}

}
