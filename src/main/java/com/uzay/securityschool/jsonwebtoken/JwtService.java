package com.uzay.securityschool.jsonwebtoken;

import com.nimbusds.jose.shaded.gson.Gson;
import com.nimbusds.jose.shaded.gson.reflect.TypeToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@Service
public class JwtService {

private final String SECRET =
        "sCIywUW2BpCpWarGzifsz99w1x0nODLxbw66ofPy1ZTtKlLGfLTfd1dkgXONKwGxdc+jvfnhnWPqFF4dC+6xkw==";

public String generateToken(UserDetails userDetails) {
    Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
    List<String> rol = authorities.stream().map(val -> val.getAuthority()).toList();
    return
            Jwts
                    .builder()
                    .subject(userDetails.getUsername())
                    .issuedAt(new Date())
                    .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                    .signWith(getSignKey(SECRET))
                    .claim("role",rol)
                    .compact();
}

public SecretKey getSignKey(String secret) {
    byte[] decode = Decoders.BASE64.decode(secret);
    SecretKey secretKey1 = Keys.hmacShaKeyFor(decode);
    return secretKey1;
}

public String extractUsername(String token){
    return Jwts
            .parser()
            .verifyWith(getSignKey(SECRET))
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .getSubject();
}
    public List<String> extractRols(String token){
        Claims claims = Jwts
                .parser()
                .verifyWith(getSignKey(SECRET))
                .build()
                .parseSignedClaims(token)
                .getPayload();


        // "rol" claim'ini JSON string olarak al
        String rolesJson = claims.get("rol", String.class);

        // JSON string'i List<String> olarak parse et
        Gson gson = new Gson();
        List<String> roles = gson.fromJson(rolesJson, new TypeToken<List<String>>(){}.getType());

        return roles;

    }

public Date getExpirationDate(String token){
    return Jwts
            .parser()
            .verifyWith(getSignKey(SECRET))
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .getExpiration();
}

public boolean isExpired(String token){
    return getExpirationDate(token).before(new Date());
}

public boolean isValid(String token, UserDetails userDetails) {
    return !isExpired(token) && userDetails.getUsername().equals(extractUsername(token));
}









}
