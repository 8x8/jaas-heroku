package com.eght.token_generator.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@ApiModel(value = "JaaS token claims")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenClaims {
    @ApiModelProperty(value = "Unique identifier for the participant", example = "ff31a3a0-cd02-11eb-b8bc-0242ac130003")
    String id;
    @ApiModelProperty(value = "Name of the participant", example = "testUser")
    String name;
    @ApiModelProperty(value = "Avatar of the participant")
    String avatar;
    @ApiModelProperty(value = "Email address of the participant", example = "testUser@jaas.com")
    String email;
    @ApiModelProperty(value = "The role of the participant", example = "true")
    boolean moderator;
    @ApiModelProperty(value = "Expiration timestamp in seconds for the jwt", notes = "Current time plus 7200 seconds", example = "1655194741")
    Long expTimestampSec;
    @ApiModelProperty(value = "Not before timestamp in seconds for the jwt", notes = "Current time minus 10 seconds", example = "1623572341")
    Long nbfTimestampSec;
    @ApiModelProperty(value = "Configuration for allowing regex check for room name", example = "false")
    boolean regexRoom;
    @ApiModelProperty(value = "Name of the room", notes = "Can be a regex if the regexRoom is set to true or * if we allow the jwt for all rooms", required = true, example = "*")
    @NotEmpty
    private String roomName;
    @ApiModelProperty(value = "List of permissions granted for the participant", allowableValues = "RECORDING, LIVESTREAMING, TRANSCRIPTION, INBOUND_CALL, OUTBOUND_CALL, SIP_INBOUND_CALL, SIP_OUTBOUND_CALL")
    List<Permissions> permissions;
}
