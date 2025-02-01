package com.pragma.powerup.infrastructure.adapter;

import com.pragma.powerup.domain.api.IMessageServicePort;
import com.pragma.powerup.infrastructure.client.MessageFeignClient;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageServiceAdapter implements IMessageServicePort {

    private final MessageFeignClient messageFeignClient;

    @Override
    public boolean sendCode(String to) {
        try {
            messageFeignClient.sendCode(to);
            return true;
        } catch (FeignException e) {
            return false;
        }
    }

    @Override
    public boolean verifyCode(String to, String code) {
        try {
            messageFeignClient.verifyCode(to, code);
            return true;
        } catch (FeignException e) {
            return false;
        }
    }

}
