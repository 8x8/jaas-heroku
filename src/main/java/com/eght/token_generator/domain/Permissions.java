package com.eght.token_generator.domain;

import lombok.Getter;

@Getter
public enum Permissions {
    LIVESTREAMING("livestreaming"),
    RECORDING("recording"),
    TRANSCRIPTION("transcription"),
    SIP_INBOUND_CALL("sip-inbound-call"),
    SIP_OUTBOUND_CALL("sip-outbound-call"),
    INBOUND_CALL("inbound-call"),
    OUTBOUND_CALL("outbound-call");

    String permission;

    Permissions(String permission) {
        this.permission = permission;
    }
}
