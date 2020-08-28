package com.covid.dashboard.security.jwt;

import com.covid.dashboard.security.dto.UserDetailInfo;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private String secret = "dfg7s5fa9fajkada";
    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    public String createToken(Authentication authentication) {
        UserDetailInfo userDetailInfo = (UserDetailInfo) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(userDetailInfo.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(getExpiryDate())
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
        }
        return false;
    }


    public String getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    private Date getExpiryDate() {
        long extraTime = 3600000;
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + extraTime);
        return expiryDate;
    }
}
