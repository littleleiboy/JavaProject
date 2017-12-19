package net.chenlin.dp.common.utils;

import sun.misc.BASE64Decoder;

import java.io.*;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Enumeration;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * <b>Rsa加解密工具</b><br>
 * <br>
 * 公钥采用X509,Cer格式的<br>
 * 私钥采用PKCS12加密方式的PFX私钥文件<br>
 * 加密算法为1024位的RSA，填充算法为PKCS1Padding<br>
 *
 * @author 行者
 * @version 4.1.0
 */
public final class RSAUtils {

    public final class RsaConst {

        /**
         * 编码
         */
        public final static String ENCODE = "UTF-8";

        public final static String KEY_X509 = "X509";
        public final static String KEY_PKCS12 = "PKCS12";
        public final static String KEY_ALGORITHM = "RSA";
        public final static String CER_ALGORITHM = "MD5WithRSA";

        public final static String RSA_CHIPER = "RSA/ECB/PKCS1Padding";

        public final static int KEY_SIZE = 1024;
        /**
         * 1024bit 加密块 大小
         */
        public final static int ENCRYPT_KEYSIZE = 117;
        /**
         * 1024bit 解密块 大小
         */
        public final static int DECRYPT_KEYSIZE = 128;
    }


    // ======================================================================================
    // 公钥加密私钥解密段
    // ======================================================================================

    /**
     * 指定Cer公钥路径加密
     *
     * @param src
     * @param pubCerPath
     * @return hex串
     */
    public static String encryptByPubCerFile(String src, String pubCerPath) {

        PublicKey publicKey = getPublicKeyFromFile(pubCerPath);
        if (publicKey == null) {
            return null;
        }
        return encryptByPublicKey(src, publicKey);
    }

    /**
     * 用公钥内容加密
     *
     * @param src
     * @param pubKeyText
     * @return hex串
     */
    public static String encryptByPubCerText(String src, String pubKeyText) {
        PublicKey publicKey = getPublicKeyByText(pubKeyText);
        if (publicKey == null) {
            return null;
        }
        return encryptByPublicKey(src, publicKey);
    }

    /**
     * 公钥加密返回
     *
     * @param src
     * @param publicKey
     * @return hex串
     */
    public static String encryptByPublicKey(String src, PublicKey publicKey) {
        byte[] destBytes = rsaByPublicKey(src.getBytes(), publicKey, Cipher.ENCRYPT_MODE);

        if (destBytes == null) {
            return null;
        }

        return byte2Hex(destBytes);

    }

    /**
     * 根据私钥文件解密
     *
     * @param src
     * @param pfxPath
     * @param priKeyPass
     * @return
     */
    public static String decryptByPriPfxFile(String src, String pfxPath, String priKeyPass) {
        if (src.isEmpty() || pfxPath.isEmpty()) {
            return null;
        }
        PrivateKey privateKey = getPrivateKeyFromFile(pfxPath, priKeyPass);
        if (privateKey == null) {
            return null;
        }
        return decryptByPrivateKey(src, privateKey);
    }

    /**
     * 根据私钥文件流解密
     *
     * @param src
     * @param pfxBytes
     * @param priKeyPass
     * @return
     */
    public static String decryptByPriPfxStream(String src, byte[] pfxBytes, String priKeyPass) {
        if (src.isEmpty()) {
            return null;
        }
        PrivateKey privateKey = getPrivateKeyByStream(pfxBytes, priKeyPass);
        if (privateKey == null) {
            return null;
        }
        return decryptByPrivateKey(src, privateKey);
    }

    /**
     * 私钥解密
     *
     * @param src
     * @param privateKey
     * @return
     */
    public static String decryptByPrivateKey(String src, PrivateKey privateKey) {
        if (src.isEmpty()) {
            return null;
        }
        try {
            byte[] destBytes = rsaByPrivateKey(hex2Bytes(src), privateKey, Cipher.DECRYPT_MODE);
            if (destBytes == null) {
                return null;
            }
            return new String(destBytes, RsaConst.ENCODE);
        } catch (UnsupportedEncodingException e) {
//			//log.error("解密内容不是正确的UTF8格式:", e);
        } catch (Exception e) {
//			//log.error("解密内容异常", e);
        }

        return null;
    }

    // ======================================================================================
    // 私钥加密公钥解密
    // ======================================================================================

    /**
     * 根据私钥文件加密
     *
     * @param src
     * @param pfxPath
     * @param priKeyPass
     * @return
     */
    public static String encryptByPriPfxFile(String src, String pfxPath, String priKeyPass) {

        PrivateKey privateKey = getPrivateKeyFromFile(pfxPath, priKeyPass);
        if (privateKey == null) {
            return null;
        }
        return encryptByPrivateKey(src, privateKey);
    }

    /**
     * 根据私钥文件流加密
     *
     * @param src
     * @param pfxBytes
     * @param priKeyPass
     * @return
     */
    public static String encryptByPriPfxStream(String src, byte[] pfxBytes, String priKeyPass) {
        PrivateKey privateKey = getPrivateKeyByStream(pfxBytes, priKeyPass);
        if (privateKey == null) {
            return null;
        }
        return encryptByPrivateKey(src, privateKey);
    }

    /**
     * 根据私钥加密
     *
     * @param src
     * @param privateKey
     */
    public static String encryptByPrivateKey(String src, PrivateKey privateKey) {

        byte[] destBytes = rsaByPrivateKey(src.getBytes(), privateKey, Cipher.ENCRYPT_MODE);

        if (destBytes == null) {
            return null;
        }
        return byte2Hex(destBytes);

    }

    /**
     * 指定Cer公钥路径解密
     *
     * @param src
     * @param pubCerPath
     * @return
     */
    public static String decryptByPubCerFile(String src, String pubCerPath) {
        PublicKey publicKey = getPublicKeyFromFile(pubCerPath);
        if (publicKey == null) {
            return null;
        }
        return decryptByPublicKey(src, publicKey);
    }

    /**
     * 根据公钥文本解密
     *
     * @param src
     * @param pubKeyText
     * @return
     */
    public static String decryptByPubCerText(String src, String pubKeyText) {
        PublicKey publicKey = getPublicKeyByText(pubKeyText);
        if (publicKey == null) {
            return null;
        }
        return decryptByPublicKey(src, publicKey);
    }

    /**
     * 根据公钥解密
     *
     * @param src
     * @param publicKey
     * @return
     */
    public static String decryptByPublicKey(String src, PublicKey publicKey) {

        try {
            byte[] destBytes = rsaByPublicKey(hex2Bytes(src), publicKey, Cipher.DECRYPT_MODE);
            if (destBytes == null) {
                return null;
            }
            return new String(destBytes, RsaConst.ENCODE);
        } catch (UnsupportedEncodingException e) {
//			//log.error("解密内容不是正确的UTF8格式:", e);
        }
        return null;
    }

    // ======================================================================================
    // 公私钥算法
    // ======================================================================================

    /**
     * 公钥算法
     *
     * @param srcData   源字节
     * @param publicKey 公钥
     * @param mode      加密 OR 解密
     * @return
     */
    public static byte[] rsaByPublicKey(byte[] srcData, PublicKey publicKey, int mode) {
        try {
            Cipher cipher = Cipher.getInstance(RsaConst.RSA_CHIPER);
            cipher.init(mode, publicKey);
            // 分段加密
            int blockSize = (mode == Cipher.ENCRYPT_MODE) ? RsaConst.ENCRYPT_KEYSIZE : RsaConst.DECRYPT_KEYSIZE;
            byte[] encryptedData = null;
            for (int i = 0; i < srcData.length; i += blockSize) {
                // 注意要使用2的倍数，否则会出现加密后的内容再解密时为乱码
                byte[] doFinal = cipher.doFinal(subarray(srcData, i, i + blockSize));
                encryptedData = addAll(encryptedData, doFinal);
            }
            return encryptedData;

        } catch (NoSuchAlgorithmException e) {
//			//log.error("公钥算法-不存在的解密算法:", e);
        } catch (NoSuchPaddingException e) {
//			//log.error("公钥算法-无效的补位算法:", e);
        } catch (IllegalBlockSizeException e) {
//			//log.error("公钥算法-无效的块大小:", e);
        } catch (BadPaddingException e) {
//			//log.error("公钥算法-补位算法异常:", e);
        } catch (InvalidKeyException e) {
//			//log.error("公钥算法-无效的私钥:", e);
        }
        return null;
    }

    /**
     * 私钥算法
     *
     * @param srcData    源字节
     * @param privateKey 私钥
     * @param mode       加密 OR 解密
     * @return
     */
    public static byte[] rsaByPrivateKey(byte[] srcData, PrivateKey privateKey, int mode) {
        try {
            Cipher cipher = Cipher.getInstance(RsaConst.RSA_CHIPER);
            cipher.init(mode, privateKey);
            // 分段加密
            int blockSize = (mode == Cipher.ENCRYPT_MODE) ? RsaConst.ENCRYPT_KEYSIZE : RsaConst.DECRYPT_KEYSIZE;
            byte[] decryptData = null;

            for (int i = 0; i < srcData.length; i += blockSize) {
                byte[] doFinal = cipher.doFinal(subarray(srcData, i, i + blockSize));

                decryptData = addAll(decryptData, doFinal);
            }
            return decryptData;
        } catch (NoSuchAlgorithmException e) {
//			//log.error("私钥算法-不存在的解密算法:", e);
        } catch (NoSuchPaddingException e) {
            //log.error("私钥算法-无效的补位算法:", e);
        } catch (IllegalBlockSizeException e) {
            //log.error("私钥算法-无效的块大小:", e);
        } catch (BadPaddingException e) {
            //log.error("私钥算法-补位算法异常:", e);
        } catch (InvalidKeyException e) {
            //log.error("私钥算法-无效的私钥:", e);
        }
        return null;
    }

    public static byte[] subarray(byte[] array, int startIndexInclusive, int endIndexExclusive) {
        if (array == null) {
            return null;
        }
        if (startIndexInclusive < 0) {
            startIndexInclusive = 0;
        }
        if (endIndexExclusive > array.length) {
            endIndexExclusive = array.length;
        }
        int newSize = endIndexExclusive - startIndexInclusive;

        if (newSize <= 0) {
            return new byte[0];
        }

        byte[] subarray = new byte[newSize];

        System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);

        return subarray;
    }

    public static byte[] addAll(byte[] array1, byte[] array2) {
        if (array1 == null) {
            return clone(array2);
        } else if (array2 == null) {
            return clone(array1);
        }
        byte[] joinedArray = new byte[array1.length + array2.length];
        System.arraycopy(array1, 0, joinedArray, 0, array1.length);
        System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
        return joinedArray;
    }

    public static byte[] clone(byte[] array) {
        if (array == null) {
            return null;
        }
        return (byte[]) array.clone();
    }

    /**
     * 根据Cer文件读取公钥
     *
     * @param pubCerPath
     * @return
     */
    public static PublicKey getPublicKeyFromFile(String pubCerPath) {
        FileInputStream pubKeyStream = null;
        try {
            pubKeyStream = new FileInputStream(pubCerPath);
            byte[] reads = new byte[pubKeyStream.available()];
            pubKeyStream.read(reads);
            return getPublicKeyByText(new String(reads));
        } catch (FileNotFoundException e) {
            // //log.error("公钥文件不存在:", e);
        } catch (IOException e) {
            // log.error("公钥文件读取失败:", e);
        } finally {
            if (pubKeyStream != null) {
                try {
                    pubKeyStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 根据公钥Cer文本串读取公钥
     *
     * @param pubKeyText
     * @return
     */
    public static PublicKey getPublicKeyByText(String pubKeyText) {
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance(RsaConst.KEY_X509);
            BufferedReader br = new BufferedReader(new StringReader(pubKeyText));
            String line = null;
            StringBuilder keyBuffer = new StringBuilder();
            while ((line = br.readLine()) != null) {
                if (!line.startsWith("-")) {
                    keyBuffer.append(line);
                }
            }
            Certificate certificate = certificateFactory.generateCertificate(new ByteArrayInputStream(new BASE64Decoder().decodeBuffer(keyBuffer.toString())));
            return certificate.getPublicKey();
        } catch (Exception e) {
            // log.error("解析公钥内容失败:", e);
        }
        return null;
    }

    /**
     * 根据私钥路径读取私钥
     *
     * @param pfxPath
     * @param priKeyPass
     * @return
     */
    public static PrivateKey getPrivateKeyFromFile(String pfxPath, String priKeyPass) {
        InputStream priKeyStream = null;
        try {
            priKeyStream = new FileInputStream(pfxPath);
            byte[] reads = new byte[priKeyStream.available()];
            priKeyStream.read(reads);
            return getPrivateKeyByStream(reads, priKeyPass);
        } catch (Exception e) {
            // log.error("解析文件，读取私钥失败:", e);
        } finally {
            if (priKeyStream != null) {
                try {
                    priKeyStream.close();
                } catch (Exception e) {
                    //
                }
            }
        }
        return null;
    }

    /**
     * 根据PFX私钥字节流读取私钥
     *
     * @param pfxBytes
     * @param priKeyPass
     * @return
     */
    public static PrivateKey getPrivateKeyByStream(byte[] pfxBytes, String priKeyPass) {
        try {
            KeyStore ks = KeyStore.getInstance(RsaConst.KEY_PKCS12);
            char[] charPriKeyPass = priKeyPass.toCharArray();
            ks.load(new ByteArrayInputStream(pfxBytes), charPriKeyPass);
            Enumeration<String> aliasEnum = ks.aliases();
            String keyAlias = null;
            if (aliasEnum.hasMoreElements()) {
                keyAlias = (String) aliasEnum.nextElement();
            }
            return (PrivateKey) ks.getKey(keyAlias, charPriKeyPass);
        } catch (IOException e) {
            // 加密失败
            // log.error("解析文件，读取私钥失败:", e);
        } catch (KeyStoreException e) {
            // log.error("私钥存储异常:", e);
        } catch (NoSuchAlgorithmException e) {
            // log.error("不存在的解密算法:", e);
        } catch (CertificateException e) {
            // log.error("证书异常:", e);
        } catch (UnrecoverableKeyException e) {
            // log.error("不可恢复的秘钥异常", e);
        }
        return null;
    }

    /**
     * 将16进制字符串转为转换成字符串
     */
    private static byte[] hex2Bytes(String source) {
        byte[] sourceBytes = new byte[source.length() / 2];
        for (int i = 0; i < sourceBytes.length; i++) {
            sourceBytes[i] = (byte) Integer.parseInt(source.substring(i * 2, i * 2 + 2), 16);
        }
        return sourceBytes;
    }

    /**
     * 将byte[] 转换成字符串
     */
    private static String byte2Hex(byte[] srcBytes) {
        StringBuilder hexRetSB = new StringBuilder();
        for (byte b : srcBytes) {
            String hexString = Integer.toHexString(0x00ff & b);
            hexRetSB.append(hexString.length() == 1 ? 0 : "").append(hexString);
        }
        return hexRetSB.toString();
    }

}
