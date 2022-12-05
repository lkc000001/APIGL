package com.ApiGL.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

import com.ApiGL.exception.AesException;

@Component
public class AESUtil {

	/** The SPEC type */
	private final static String TYPE_SPEC = "AES";

	/** The INIT type. */
	private final static String TYPE_INIT = "AES/CFB/PKCS5PADDING";
	
	/** The aes iv. */
	private final static String SPEC_EIPKEY = "A3b97!azg3DB83En";
	
	/** The aes key.*/
	private final static String AES_EIPKEY = "ApiGuiLog2022Aes";
	
	/** The cipher. */
	private static Cipher cipher;

	/** The secretKeySpec. */
	private static SecretKeySpec eipsecretKeySpec;
	
	/** The ivParameterSpec. */
	private static IvParameterSpec eipivParameterSpec;
	
	public static void main(String[] args) throws Exception {
		
		String word = "111111"; // 要加密的字串
		AESUtil aes = new AESUtil();

		String aesEnCode = aes.AES_Encrypt(word);
		System.out.println("aesEnCode: "+aesEnCode);
		String aesdeCode = aes.AES_Decrypt(aesEnCode);
		System.out.println("aesdeCode: "+aesdeCode);
		
		String sha265enc = aes.SHA256Encrypt(word + "ApiGuiLog2022Aes");
		System.out.println("sha265enc: "+sha265enc);
		System.out.println("sha265enc: "+sha265enc.length());
	}
	
	/**
	 * Constructor.
	 * @throws Exception
	 */
	public AESUtil() throws Exception {
		eipivParameterSpec = new IvParameterSpec(SPEC_EIPKEY.getBytes("utf-8"));
		byte[] raw = AES_EIPKEY.getBytes("utf-8");
		eipsecretKeySpec = new SecretKeySpec(raw, TYPE_SPEC);
		cipher = Cipher.getInstance(TYPE_INIT); 
	}
	
	/**
     * AES加密
     * @param plaintext 明文
     * @return 該字串的AES密文值
     */
    public static String AES_Encrypt(String plaintext) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, eipsecretKeySpec, eipivParameterSpec);
            byte[] encrypted = cipher.doFinal(plaintext.getBytes("utf-8"));
            String encryptedStr=new String(Base64.encodeBase64(encrypted));
            return encryptedStr;
        } catch (Exception ex) {
        	throw new AesException("密碼加密失敗<BR> ", 500);
        }
    }

    /**
     * AES解密
     * @param cipertext 密文
     * @return 該密文的明文
     */
    public static String AES_Decrypt(String cipertext) {
        try {
            cipher.init(Cipher.DECRYPT_MODE, eipsecretKeySpec, eipivParameterSpec);
            byte[] encrypted1 = Base64.decodeBase64(cipertext);//先用base64解密
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original,"utf-8");
            return originalString;
        } catch (Exception ex) {
        	throw new AesException("密碼解密失敗<BR> " + ex.getMessage(), 500);
        }
    }
    
    public static String SHA256Encrypt(String strSrc) {
        String strDes = null;
        String encName="SHA-256";
        byte[] bt = strSrc.getBytes();
        try {
        	MessageDigest md = MessageDigest.getInstance(encName);
            md.update(bt);
            strDes = bytes2Hex(md.digest()); // to HexString
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        return strDes;
    }

    public static String bytes2Hex(byte[] bts) {
        String des = "";
        String tmp = null;
        for (int i = 0; i < bts.length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }
}
