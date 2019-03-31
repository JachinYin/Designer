package com.jachin.des.config;

import org.springframework.context.annotation.Configuration;

/**
 * @author Jachin
 * @since 2019/3/30 17:00
 */
@Configuration
public class JwtProperties {

    private String header = "TOKEN";
    /**
     * JWT  加密的密钥
     */
    private String secret = "B9C54768DFD44C37982687C28636BF51";

//    private long expiration = 15 * 60L;
    private long expiration = 7 * 24 * 60 * 60L;

    private String authPath = "login";

    private String md5Key = "SecretKey";

    private String ignoreUrl = "/login,/register,/img,/logout";

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Long getExpiration() {
        return expiration;
    }

    public void setExpiration(Long expiration) {
        this.expiration = expiration;
    }

    public String getAuthPath() {
        return authPath;
    }

    public void setAuthPath(String authPath) {
        this.authPath = authPath;
    }

    public String getMd5Key() {
        return md5Key;
    }

    public void setMd5Key(String md5Key) {
        this.md5Key = md5Key;
    }

    public String getIgnoreUrl() {
        return ignoreUrl;
    }

    public void setIgnoreUrl(String ignoreUrl) {
        this.ignoreUrl = ignoreUrl;
    }
}
