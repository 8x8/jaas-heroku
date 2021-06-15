package com.eght.token_generator.domain;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;

import java.security.interfaces.RSAPrivateKey;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static java.time.Instant.now;

public class JaaSJwtBuilder {
    /**
     * To be used with exp value.
     * The time after which the JWT expires.
     */
    private static final long EXP_TIME_DELAY_SEC = 7200;

    /**
     * To be used with nbf value.
     * The time before which the JWT must not be accepted for processing.
     */
    private static final long NBF_TIME_DELAY_SEC = 10;


    private JWTCreator.Builder jwtBuilder;
    private Map<String, Object> userClaims;
    private Map<String, Object> featureClaims;
    private Map<String, Object> roomClaims;

    private JaaSJwtBuilder() {
        userClaims = new HashMap<>();
        featureClaims = new HashMap<>();
        roomClaims = new HashMap<>();
        jwtBuilder = JWT.create();
    }

    /**
     * Creates a new JaaSJwtBuilder.
     *
     * @return Returns a new builder that needs to be setup.
     */
    public static JaaSJwtBuilder builder() {
        return new JaaSJwtBuilder();
    }

    /**
     * Sets the value for the kid header claim. Represents the JaaS api key.
     * You can find the api key here : https://jaas.8x8.vc/#/apikeys
     *
     * @param kid
     * @return Returns a new builder with kid claim set.
     */
    public JaaSJwtBuilder withKID(String kid) {
        jwtBuilder.withKeyId(kid);
        return this;
    }

    /**
     * Sets the value for the user avatar url as a string.
     *
     * @param url The publicly available URL that points to the user avatar picture.
     * @return Returns a new builder with avatar claim set.
     */
    public JaaSJwtBuilder withUserAvatar(String url) {
        userClaims.put("avatar", url);
        return this;
    }

    /**
     * Sets the value for the moderator claim. If value is true user is moderator, false otherwise.
     *
     * @param isModerator
     * @return Returns a new builder with moderator claim set.
     */
    public JaaSJwtBuilder withModerator(boolean isModerator) {
        userClaims.put("moderator", isModerator);
        return this;
    }

    /**
     * Sets the value for the user name to be displayed in the meeting.
     *
     * @param userName
     * @return Returns a new builder with name claim set.
     */
    public JaaSJwtBuilder withUserName(String userName) {
        userClaims.put("name", userName);
        return this;
    }

    /**
     * Sets the value for the user email claim.
     *
     * @param userEmail
     * @return Returns a new builder with email claim set.
     */
    public JaaSJwtBuilder withUserEmail(String userEmail) {
        userClaims.put("email", userEmail);
        return this;
    }

    /**
     * Sets the allowed features
     *
     * @param permissions List of allowed features
     * @return Returns a new builder after setting the allowed features
     */
    public JaaSJwtBuilder withPermissions(List<Permissions> permissions) {
        if (permissions == null) {
            return this;
        }
        featureClaims.put("livestreaming", permissions.contains(Permissions.LIVESTREAMING));
        featureClaims.put("recording", permissions.contains(Permissions.RECORDING));
        featureClaims.put("transcription", permissions.contains(Permissions.TRANSCRIPTION));
        featureClaims.put("inbound-call", permissions.contains(Permissions.INBOUND_CALL));
        featureClaims.put("outbound-call", permissions.contains(Permissions.OUTBOUND_CALL));
        featureClaims.put("sip-inbound-call", permissions.contains(Permissions.SIP_INBOUND_CALL));
        featureClaims.put("sip-outbound-call", permissions.contains(Permissions.SIP_OUTBOUND_CALL));
        return this;
    }

    /**
     * Sets the value for the exp claim representing the time until the token expires.
     *
     * @param expTime Unix timestamp is expected.
     * @return Returns a new builder with exp claim set.
     */
    public JaaSJwtBuilder withExpTime(Long expTime) {
        jwtBuilder.withClaim("exp", expTime != null ? expTime : now().getEpochSecond() + EXP_TIME_DELAY_SEC);
        return this;
    }

    /**
     * Sets the value for the nbf claim.
     *
     * @param nbfTime Unix timestamp is expected.
     * @return Returns a new builder with nbf claim set.
     */
    public JaaSJwtBuilder withNbfTime(Long nbfTime) {
        jwtBuilder.withClaim("nbf", nbfTime != null ? nbfTime : now().getEpochSecond() - NBF_TIME_DELAY_SEC);
        return this;
    }

    /**
     * Allows regex matching for the room name
     *
     * @param regexRoom
     * @return Returns a new builder with room claim set.
     */
    public JaaSJwtBuilder withRegexRoom(boolean regexRoom) {
        roomClaims.put("regex", regexRoom);
        return this;
    }

    /**
     * Sets the value for the room.
     *
     * @param roomName The meeting room value for which the token is issued;
     *                 this field supports also wildcard ("*") if the token is issued for all rooms.
     * @return Returns a new builder with room claim set.
     */
    public JaaSJwtBuilder withRoomName(String roomName) {
        jwtBuilder.withClaim("room", roomName);
        return this;
    }

    /**
     * Sets the value for the sub claim representing the tenant name/unique identifier.
     *
     * @param tenantName The tenant unique identifier.
     * @return Returns a new builder with sub claim set.
     */
    public JaaSJwtBuilder withTenantName(String tenantName) {
        jwtBuilder.withClaim("sub", tenantName);
        return this;
    }

    /**
     * Sets the value for the id claim.
     *
     * @param userId The user's unique identifier.
     * @return Returns a new builder with kid claim set.
     */
    public JaaSJwtBuilder withUserId(String userId) {
        userClaims.put("id", userId != null ? userId : UUID.randomUUID().toString());
        return this;
    }

    /**
     * Returns a signed JaaS JWT token string.
     *
     * @param privateKey The private key used to sign the JWT.
     * @return A signed JWT.
     */
    public String signWith(RSAPrivateKey privateKey) {
        Algorithm algorithm = Algorithm.RSA256(null, privateKey);
        Map<String, Object> context = new HashMap<>();
        context.put("user", userClaims);
        context.put("features", featureClaims);
        context.put("room", roomClaims);
        return jwtBuilder
                .withClaim("iss", "chat")
                .withClaim("aud", "jitsi")
                .withClaim("context", context)
                .sign(algorithm);
    }
}
