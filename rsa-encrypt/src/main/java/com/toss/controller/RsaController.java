package com.toss.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

@RestController
@RequestMapping("/security")
public class RsaController {
    @Autowired
    private HttpSession session;
    @Autowired
    private BASE64Encoder encoder;
    @Autowired
    private BASE64Decoder decoder;
    private final static String KEY = "key";

    /**
     * 颁发公钥
     */
    @GetMapping("/publicKey")
    public String publicKey() {
        KeyPair keyPair = EncryptUtil.generateKeyPair(512);
        session.setAttribute(KEY, keyPair);
        PublicKey publicKey = keyPair.getPublic();
        return encoder.encode(publicKey.getEncoded());
    }

    /**
     * 解密信息
     */
    @PostMapping("/decrypt")
    public String decrypt(String password) throws IOException {
        KeyPair keyPair = (KeyPair) session.getAttribute(KEY);
        PrivateKey privateKey = keyPair.getPrivate();
        byte[] cipherText = decoder.decodeBuffer(password);
        return EncryptUtil.decryptByRSA(cipherText, privateKey);
    }
}
