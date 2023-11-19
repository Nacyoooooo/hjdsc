package com.chenzhihao.serviceuser.util;

import io.jsonwebtoken.*;
import org.springframework.util.StringUtils;

import java.util.Date;

public class JwtHelper {

    private static long tokenExpiration = 365*24*60*60*1000;
    private static String tokenSignKey = "hjdsc";

    //根据userId+userName生成token字符串
    public static String createToken(Integer userId,Long authority) throws JwtException{
        String token = Jwts.builder()
                .setSubject("hjdsc-USER")

                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))

                .claim("userId", userId)
                .claim("authority", authority)
                .signWith(SignatureAlgorithm.HS512, tokenSignKey)

                .compressWith(CompressionCodecs.GZIP)
                .compact();
        return token;
    }

    public static Integer getUserId(String token) throws JwtException{
        if(StringUtils.isEmpty(token)) return null;
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);

        Claims claims = claimsJws.getBody();
        Integer userId = (Integer)claims.get("userId");
        return userId;
    }
    public static Integer getUserAuthority(String token) throws JwtException{
        if(StringUtils.isEmpty(token)) return null;
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);

        Claims claims = claimsJws.getBody();
        Integer authority = (Integer)claims.get("authority");
        return authority;
    }

    public static String getUserName(String token) throws JwtException{
        if(StringUtils.isEmpty(token)) return "";
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        return (String)claims.get("userName");
    }

    public static void removeToken(String token) {
        //jwttoken无需删除，客户端扔掉即可。
    }


}
