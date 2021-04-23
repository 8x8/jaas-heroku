package com.example.demo3.domain;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;

import java.security.interfaces.RSAPrivateKey;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
    private Algorithm algorithm;
    private Map<String, Object> userClaims;
    private Map<String, Object> featureClaims;

    public JaaSJwtBuilder() {
        userClaims = new HashMap<>();
        featureClaims = new HashMap<>();
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
     * @param apiKey
     * @return Returns a new builder with kid claim set.
     */
    public JaaSJwtBuilder withApiKey(String apiKey) {
        if (apiKey != null) {
            jwtBuilder.withKeyId(apiKey);
        }
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
     * Sets the value for the live streaming feature claim. If value is true recording is enabled, false otherwise.
     *
     * @param isEnabled
     * @return Returns a new builder with livestreaming claim set.
     */
    public JaaSJwtBuilder withLiveStreamingEnabled(boolean isEnabled) {
        featureClaims.put("livestreaming", isEnabled);
        return this;
    }

    /**
     * Sets the value for the recording feature claim. If value is true recording is enabled, false otherwise.
     *
     * @param isEnabled
     * @return Returns a new builder with recording claim set.
     */
    public JaaSJwtBuilder withRecordingEnabled(boolean isEnabled) {
        featureClaims.put("recording", isEnabled);
        return this;
    }

    /**
     * Sets the value for the outbound feature claim. If value is true outbound calls are enabled, false otherwise.
     *
     * @param isEnabled
     * @return Returns a new builder with outbound-call claim set.
     */
    public JaaSJwtBuilder withOutboundEnabled(boolean isEnabled) {
        featureClaims.put("outbound-call", isEnabled);
        return this;
    }

    /**
     * Sets the value for the outbound feature claim. If value is true outbound calls are enabled, false otherwise.
     *
     * @param isEnabled
     * @return Returns a new builder with outbound-call claim set.
     */
    public JaaSJwtBuilder withLobbyEnabled(boolean isEnabled) {
        featureClaims.put("lobby", isEnabled);
        return this;
    }

    /**
     * Sets the value for the transcription feature claim. If value is true transcription is enabled, false otherwise.
     *
     * @param isEnabled
     * @return Returns a new builder with transcription claim set.
     */
    public JaaSJwtBuilder withTranscriptionEnabled(boolean isEnabled) {
        featureClaims.put("transcription", String.valueOf(isEnabled));
        return this;
    }

    /**
     * Sets the value for the exp claim representing the time until the token expires.
     *
     * @param expTime Unix timestamp is expected.
     * @return Returns a new builder with exp claim set.
     */
    public JaaSJwtBuilder withExpTime(long expTime) {
        jwtBuilder.withClaim("exp", expTime);
        return this;
    }

    /**
     * Sets the value for the nbf claim.
     *
     * @param nbfTime Unix timestamp is expected.
     * @return Returns a new builder with nbf claim set.
     */
    public JaaSJwtBuilder withNbfTime(long nbfTime) {
        jwtBuilder.withClaim("nbf", nbfTime);
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
        jwtBuilder.withClaim("id", userId != null ? userId : UUID.randomUUID().toString());
        return this;
    }

    /**
     * Fills the default values for required claims.
     *
     * @return Returns a new builder with needed claim set to default values.
     */
    public JaaSJwtBuilder withDefaults() {
        return this.withExpTime(Instant.now().getEpochSecond() + EXP_TIME_DELAY_SEC) // Default value, no change needed.
                .withNbfTime(Instant.now().getEpochSecond() - NBF_TIME_DELAY_SEC) // Default value, no change needed.
                .withLiveStreamingEnabled(true)
                .withRecordingEnabled(true)
                .withModerator(true)
                .withRoomName("*")
                .withUserId(UUID.randomUUID().toString());
    }

    /**
     * Returns a signed JaaS JWT token string.
     *
     * @param privateKey The private key used to sign the JWT.
     * @return A signed JWT.
     */
    public String signWith(RSAPrivateKey privateKey) {
        algorithm = Algorithm.RSA256(null, privateKey);
        Map<String, Object> context = new HashMap<String, Object>() {{
            put("user", userClaims);
            put("features", featureClaims);
        }};

        return jwtBuilder
                .withClaim("iss", "chat")
                .withClaim("aud", "jitsi")
                .withClaim("context", context)
                .sign(this.algorithm);
    }
}
