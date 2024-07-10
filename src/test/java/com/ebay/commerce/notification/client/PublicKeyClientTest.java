package com.ebay.commerce.notification.client;

import com.ebay.api.client.auth.oauth2.CredentialUtil;
import com.ebay.api.client.auth.oauth2.OAuth2Api;
import com.ebay.api.client.auth.oauth2.model.AccessToken;
import com.ebay.api.client.auth.oauth2.model.Environment;
import com.ebay.commerce.notification.constants.Constants;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PublicKeyClientTest {

    private OAuth2Api oAuthClient = new OAuth2Api();

    @Test
    void getAccessToken() throws IOException {
        CredentialUtil.load(new FileInputStream("samples/ebay-config.yaml"));
        Optional<AccessToken> accessToken = oAuthClient.getApplicationToken(Environment.PRODUCTION, Constants.APPLICABLE_SCOPES).getAccessToken();
        System.out.println(accessToken.get().getToken());
    }
}