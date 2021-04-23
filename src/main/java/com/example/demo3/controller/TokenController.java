package com.example.demo3.controller;

import com.example.demo3.domain.TokenClaims;
import com.example.demo3.service.TokenGeneratorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "Jaas token generator service")
public class TokenController {

    private static final String KID = "KID";
    private static final String PRIVATE_KEY = "PRIVATE_KEY";

    @Autowired
    private Environment environment;

    @Autowired
    private TokenGeneratorService tokenGeneratorService;

    @PostMapping(value = "/token")
    @ApiOperation("Generate token")
    public String getToken(@RequestBody TokenClaims tokenClaims) {
        String kid = environment.getProperty(KID);
        String privateKey = environment.getProperty(PRIVATE_KEY);
        if (kid == null) {
            throw new RuntimeException("Missing kid");
        }
        if (privateKey == null) {
            throw new RuntimeException("Missing private key");
        }
        return tokenGeneratorService.getToken(tokenClaims, kid, privateKey);
    }
}
