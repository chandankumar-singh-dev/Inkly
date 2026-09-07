package com.inkly.inkly_backend.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Configuration
public class JwtKeyConfig {

    @Bean
    public PrivateKey privateKey() throws Exception{
        String key = Files.readString(
                Path.of("src/main/resources/certs/private.pem")
        );

        key = key
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", "");

        byte[] decoded = Base64.getDecoder().decode(key);

        PKCS8EncodedKeySpec keySpec =
                new PKCS8EncodedKeySpec(decoded);

        KeyFactory keyFactory =
                KeyFactory.getInstance("RSA");

        return keyFactory.generatePrivate(keySpec);
    }



    @Bean
    public PublicKey publicKey() throws Exception {

        String key = Files.readString(
                Path.of("src/main/resources/certs/public.pem")
        );

        key = key
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");

        byte[] decoded = Base64.getDecoder().decode(key);

        X509EncodedKeySpec keySpec =
                new X509EncodedKeySpec(decoded);

        KeyFactory keyFactory =
                KeyFactory.getInstance("RSA");

        return keyFactory.generatePublic(keySpec);
    }

}
