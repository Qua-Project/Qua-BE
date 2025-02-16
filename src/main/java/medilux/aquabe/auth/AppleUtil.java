package medilux.aquabe.auth;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.jackson.io.JacksonDeserializer;
import medilux.aquabe.domain.user.dto.AppleResponse;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;

@Component
public class AppleUtil {


    @Value("${spring.apple.key-url}")
    private String keyUrl;

    public AppleResponse.AppleUser getUserInfoFromToken(String identityToken) {
        try {
            String[] tokenParts = identityToken.split("\\.");
            String headerJson = new String(Base64.getUrlDecoder().decode(tokenParts[0]));
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode headerNode = objectMapper.readTree(headerJson);
            String kid = headerNode.get("kid").asText();

            PublicKey applePublicKey = getApplePublicKey(kid);

            Claims claims = Jwts.parser()
                    .verifyWith(applePublicKey)
                    .json(new JacksonDeserializer<>())
                    .build()
                    .parseClaimsJws(identityToken)
                    .getBody();

            String email = claims.get("email", String.class);
            String sub = claims.getSubject();

            return new AppleResponse.AppleUser(email, sub);
        } catch (Exception e) {
            throw new RuntimeException("Invalid identityToken", e);
        }
    }

    private PublicKey getApplePublicKey(String kid) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String keysResponse = restTemplate.getForObject(keyUrl, String.class);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode keysNode = objectMapper.readTree(keysResponse).get("keys");

            for (JsonNode keyNode : keysNode) {
                if (kid.equals(keyNode.get("kid").asText())) {
                    String n = keyNode.get("n").asText();
                    String e = keyNode.get("e").asText();

                    BigInteger modulus = new BigInteger(1, Base64.getUrlDecoder().decode(n));
                    BigInteger exponent = new BigInteger(1, Base64.getUrlDecoder().decode(e));
                    RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(modulus, exponent);

                    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                    return keyFactory.generatePublic(publicKeySpec);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load Apple public key", e);
        }
        throw new RuntimeException("Matching Apple public key not found");
    }

}
