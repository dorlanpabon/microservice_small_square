package com.pragma.powerup.domain.api;

public interface IMessageServicePort {

    boolean sendCode(String to);

    boolean verifyCode(String to, String code);
}
