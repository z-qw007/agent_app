package com.zqw.agent_app.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {

    private static final Key KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private static final long EXPIRATION_TIME = 24 * 60 * 60 * 1000L; // 24小时

    public static String generateToken(Integer userId, String userName) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + EXPIRATION_TIME);

        // 载荷 (Claims): 存储自定义信息
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("userName", userName);

        return Jwts.builder()
                .setClaims(claims) // 设置载荷
                .setSubject(userName) // 主题（通常是用户名）
                .setIssuedAt(now) // 签发时间
                .setExpiration(expiration) // 过期时间
                .signWith(KEY, SignatureAlgorithm.HS256) // 签名算法和密钥
                .compact();
    }
}
