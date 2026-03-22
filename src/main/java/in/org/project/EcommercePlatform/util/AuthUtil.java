package in.org.project.EcommercePlatform.util;

import in.org.project.EcommercePlatform.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Getter
public class AuthUtil {

    @Value("${auth.secretKey}")
    private String secretKey;

    @Autowired
    ObjectMapper objectMapper;

    public SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(User user){
        return  Jwts.builder()
                .subject(user.getUsername())
                .addClaims(generateClaimForToken(user))
                .signWith(getSecretKey())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+60*1000*10))
                .compact();

    }

    public String getUserName(String token){
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public HashMap<String,Object> generateClaimForToken(User user){
        HashMap<String,Object>hash=new HashMap<>();
        hash.put("userId",user.getId());
        hash.put("userName",user.getName());
        hash.put("Roles",user.getRoles());
        return hash;
    }
}
