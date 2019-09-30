package org.ylc.frame.springboot.common.util;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.impl.PublicClaims;
import com.auth0.jwt.interfaces.Claim;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.UUID;

/**
 * JSON Web Token相关
 * <p>
 * Header  Base64Url encoded
 * {
 * "alg": "HS256",
 * "typ": "JWT"
 * }
 * Payload  Base64Url encoded
 * {
 * "sub": "1234567890",
 * "name": "John Doe",
 * "admin": true
 * }
 * Signature
 * HMACSHA256(
 * base64UrlEncode(header) + "." +
 * base64UrlEncode(payload),
 * secret)
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2019/9/20 22:33
 */
public class JWTUtils {

    private static final Logger logger = LoggerFactory.getLogger(JWTUtils.class);

    /**
     * 签名秘钥
     */
    private static final String SECRET = "!@#$%YLC*&^%()";

    /**
     * aes 加密
     * token中的信息加密
     * 其中秘钥随机生成
     */
    private static final AES AES_ALGORITHM = SecureUtil.aes(SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue()).getEncoded());


    /**
     * 生成token
     * 这里不设置token 过期时间，
     * 过期时间通过redis 去设置和更新过期时间
     * <p>
     *
     * @param json 存放的信息
     */
    public static String createJWT(JSONObject json) {
        // 设置签名算法和签名秘钥
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        return JWT.create()
                // JWT 唯一ID
                .withJWTId(UUID.randomUUID().toString())
                // 信息主体
                .withClaim(PublicClaims.SUBJECT, AES_ALGORITHM.encryptHex(json.toJSONString()))
                // 签发时间
                .withIssuedAt(new Date())
                .sign(algorithm);
    }

    /**
     * 解析token
     *
     * @param token token
     * @return 用户基本信息
     */
    public static JSONObject parseJWT(String token) {
        try {
            Claim subClaim = JWT.decode(token).getClaim(PublicClaims.SUBJECT);
            if (subClaim == null) {
                return null;
            }
            String subject = AES_ALGORITHM.decryptStr(subClaim.asString());
            return JSONObject.parseObject(subject);
        } catch (Exception e) {
            logger.error("解析token失败，{}", e.getMessage());
            return null;
        }
    }

}
