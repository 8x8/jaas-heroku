package com.eght.token_generator.controller;

import com.eght.token_generator.domain.TokenClaims;
import com.eght.token_generator.error.DependencyException;
import com.eght.token_generator.service.TokenGeneratorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.util.StringUtils.isEmpty;

@RestController
@Api(tags = "Jaas token generator controller")
@Validated
public class TokenController {

    private static final String KID = "KID";
    private static final String PRIVATE_KEY = "PRIVATE_KEY";

    @Autowired
    private Environment environment;

    @Autowired
    private TokenGeneratorService tokenGeneratorService;

    @PostMapping(value = "/token", consumes = APPLICATION_JSON_VALUE)
    @ApiOperation("Generate JaaS token")
    public String getToken(@Valid @RequestBody TokenClaims tokenClaims) throws Exception {
        String kid = environment.getProperty(KID);
        String privateKey = environment.getProperty(PRIVATE_KEY);
        if (isEmpty(kid)) {
            throw new DependencyException("Missing kid");
        }
        if (isEmpty(privateKey)) {
            throw new DependencyException("Missing private key");
        }
        return tokenGeneratorService.getToken(tokenClaims, kid, privateKey);
    }
}
