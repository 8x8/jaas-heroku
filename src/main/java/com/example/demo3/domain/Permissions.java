package com.example.demo3.domain;

import lombok.Getter;

@Getter
public enum Permissions {
    LOBBY("lobby"),
    LIVESTREAMING("livestreaming"),
    RECORDING("recording"),
    TRANSCRIPTION("transcription"),
    OUTBOUND_CALL("outbound-call");

    String permission;

    Permissions(String permission) {
        this.permission = permission;
    }
}
