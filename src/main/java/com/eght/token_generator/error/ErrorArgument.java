package com.eght.token_generator.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
class ErrorArgument implements Serializable {
    private String key;
    private Object value;
}
