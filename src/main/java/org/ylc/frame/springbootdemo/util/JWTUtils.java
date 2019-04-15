package org.ylc.frame.springbootdemo.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.impl.PublicClaims;
import com.auth0.jwt.interfaces.Claim;

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
 * @date 2019/4/10 22:33
 */
public class JWTUtils {

    /**
     * 签名秘钥
     */
    private static final String SECRET = "!@#$%YLC*&^%()";

    /**
     * 生成token
     * 这里不设置token 过期时间，
     * 过期时间通过redis 去设置和更新过期时间
     * <p>
     *
     * @param userId 用户ID
     */
    public static String createJWT(String userId) {
        // 设置签名算法和签名秘钥
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        return JWT.create()
                // JWT 唯一ID
                .withJWTId(UUID.randomUUID().toString())
                // 信息主体
                .withClaim(PublicClaims.SUBJECT, userId)
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
    public static String parseJWT(String token) {
        Claim subClaim = JWT.decode(token).getClaim(PublicClaims.SUBJECT);
        if (subClaim == null) {
            return null;
        }
        return subClaim.asString();
    }

}
