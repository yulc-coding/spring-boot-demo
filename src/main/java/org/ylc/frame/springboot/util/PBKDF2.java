package org.ylc.frame.springboot.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ylc.frame.springboot.setting.exception.OperationException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.SecureRandom;
import java.security.spec.KeySpec;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 * <p>
 * PBKDF2加密实现
 *
 * @author yulc
 * @version 1.0.0
 * @date 2019/9/28
 */
public class PBKDF2 {

    private static final Logger logger = LoggerFactory.getLogger(PBKDF2.class);

    /**
     * 算法名称
     */
    private static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA256";

    /**
     * 盐的长度
     */
    private static final int SALT_SIZE = 16;

    /**
     * 生成密文的长度
     */
    private static final int HASH_SIZE = 32;

    /**
     * 迭代次数
     */
    private static final int PBKDF2_ITERATIONS = 1000;

    /**
     * 对输入的密码进行验证
     *
     * @param password          待验证密码
     * @param encryptedPassword 密文
     * @param salt              盐值
     */
    public static boolean verify(String password, String encryptedPassword, String salt) {
        // 用相同的盐值对用户输入的密码进行加密
        String result = getPBKDF2(password, salt);
        // 把加密后的密文和原密文进行比较，相同则验证成功，否则失败
        return result != null && result.equals(encryptedPassword);
    }

    /**
     * 根据password和salt生成密文
     *
     * @param password 未加密密码
     * @param salt     加密盐
     * @return 加密后密码
     */
    public static String getPBKDF2(String password, String salt) {
        //将16进制字符串形式的salt转换成byte数组
        byte[] bytes = DatatypeConverter.parseHexBinary(salt);
        KeySpec spec = new PBEKeySpec(password.toCharArray(), bytes, PBKDF2_ITERATIONS, HASH_SIZE * 4);
        byte[] hash;
        try {
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
            hash = secretKeyFactory.generateSecret(spec).getEncoded();
        } catch (Exception e) {
            logger.error("密码加密失败,{}", e.getMessage());
            return null;
        }
        //将byte数组转换为16进制的字符串
        return DatatypeConverter.printHexBinary(hash);
    }

    /**
     * 生成随机盐值
     */
    public static String generateSalt() {
        SecureRandom random;
        try {
            random = SecureRandom.getInstance("SHA1PRNG");
        } catch (Exception e) {
            logger.error("生成随机盐失败,{}", e.getMessage());
            throw new OperationException("生成随机盐失败," + e.getMessage());
        }
        byte[] bytes = new byte[SALT_SIZE / 2];
        random.nextBytes(bytes);
        //将byte数组转换为16进制的字符串
        return DatatypeConverter.printHexBinary(bytes);
    }

}
