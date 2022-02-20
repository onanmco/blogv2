package com.cemonan.blog;

import com.cemonan.blog.domain.Token;
import com.cemonan.blog.domain.User;
import com.cemonan.blog.factory.TokenFactory;
import com.cemonan.blog.factory.UserFactory;
import com.cemonan.blog.service.AuthTokenService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import java.time.Instant;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
@ComponentScan(basePackages = {"com.cemonan.blog.service"})
public class AuthTokenServiceIntegrationTests {

    private final AuthTokenService authTokenService;
    private final TokenFactory tokenFactory;

    @Value("${token.expires.after.seconds}")
    private String DEFAULT_EXPIRATION;


    @Autowired
    public AuthTokenServiceIntegrationTests(AuthTokenService authTokenService, TokenFactory tokenFactory) {
        this.authTokenService = authTokenService;
        this.tokenFactory = tokenFactory;
    }

    @Test
    void testCreateToken() {
        Token token = tokenFactory.create();

        assertThat(token).isNotNull();
        assertThat(token.getToken()).isNotNull();
        assertThat(token.getExpiresAt()).isNotNull();

        try {
            Thread.sleep(1000 * 3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertThat(token.getExpiresAt() - Instant.now().getEpochSecond())
                .isGreaterThan(0L)
                .isLessThan(Long.valueOf(DEFAULT_EXPIRATION));
    }

    @Test
    void testExtendExpirationTimeOfToken() {
        Token token = tokenFactory.create();

        Token extendedToken = authTokenService.extendTokensExpiration(token);

        assertThat(extendedToken.getExpiresAt()).isGreaterThan(token.getExpiresAt());

    }
}
