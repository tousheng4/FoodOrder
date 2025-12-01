package cugb.ai.foodorder.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

public class JwtUtil {

    // 生产项目请放配置 / 环境变量，这里写死方便你先跑起来
    private static final String SECRET = "foodorder-demo-secret-CHANGE-ME";
    private static final String ISSUER = "foodorder";
    // 7 天过期
    private static final long EXPIRE_MILLIS = 7L * 24 * 60 * 60 * 1000;

    public static String generateToken(Long userId, Integer role) {
        Date now = new Date();
        Date expireAt = new Date(now.getTime() + EXPIRE_MILLIS);

        return JWT.create()
                .withIssuer(ISSUER)
                .withIssuedAt(now)
                .withExpiresAt(expireAt)
                .withSubject(String.valueOf(userId))
                .withClaim("role", role)
                .sign(Algorithm.HMAC256(SECRET));
    }

    public static DecodedJWT verify(String token) {
        return JWT.require(Algorithm.HMAC256(SECRET))
                .withIssuer(ISSUER)
                .build()
                .verify(token);
    }

    public static Long getUserId(DecodedJWT jwt) {
        return Long.valueOf(jwt.getSubject());
    }

    public static Integer getRole(DecodedJWT jwt) {
        return jwt.getClaim("role").asInt();
    }
}
