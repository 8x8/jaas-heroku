package com.eght.token_generator.service;

import com.eght.token_generator.domain.JaaSJwtBuilder;
import com.eght.token_generator.domain.TokenClaims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;

import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

import static com.eght.token_generator.utils.TenantUtils.extractTenant;

@Service
@Slf4j
public class TokenGeneratorService {
    private static final String BEGIN_PRIVATE_KEY = "-----BEGIN (.*?)-----";
    private static final String END_PRIVATE_KEY = "-----END (.*)-----";
    private static final String EMPTY = "";
    private static final String RSA = "RSA";

    public String getToken(TokenClaims tokenClaims, String kid, String privateKey) throws Exception {
        return JaaSJwtBuilder.builder()
                .withUserId(tokenClaims.getId())
                .withUserName(tokenClaims.getName())
                .withUserEmail(tokenClaims.getEmail())
                .withUserAvatar(tokenClaims.getAvatar())
                .withModerator(tokenClaims.isModerator())
                .withPermissions(tokenClaims.getPermissions())
                .withTenantName(extractTenant(kid))
                .withExpTime(tokenClaims.getExpTimestampSec())
                .withNbfTime(tokenClaims.getNbfTimestampSec())
                .withRoomName(tokenClaims.getRoomName())
                .withRegexRoom(tokenClaims.isRegexRoom())
                .withKID(kid)
                .signWith(getPemPrivateKey(privateKey));
    }

    private static RSAPrivateKey getPemPrivateKey(String pem) throws Exception {
        String privateKey = stripBeginEnd(pem);
        BASE64Decoder b64 = new BASE64Decoder();
        byte[] decoded = b64.decodeBuffer(privateKey);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);
        KeyFactory kf = KeyFactory.getInstance(RSA);
        return (RSAPrivateKey) kf.generatePrivate(spec);
    }

    private static String stripBeginEnd(String pem) {
        String stripped = pem.replaceFirst(BEGIN_PRIVATE_KEY, EMPTY);
        stripped = stripped.replaceFirst(END_PRIVATE_KEY, EMPTY);
        stripped = stripped.replaceAll("\r", EMPTY);
        stripped = stripped.replaceAll("\\s+", EMPTY);
        stripped = stripped.replaceAll("\n", EMPTY);
        return stripped.trim();
    }
}
