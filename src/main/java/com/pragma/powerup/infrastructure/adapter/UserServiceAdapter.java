package com.pragma.powerup.infrastructure.adapter;

import com.pragma.powerup.domain.api.IUserServicePort;
import com.pragma.powerup.infrastructure.client.UserFeignClient;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceAdapter implements IUserServicePort {

    private final UserFeignClient userFeignClient;

    @Override
    public boolean isOwner(Long userId) {
        try {
            userFeignClient.isOwner(userId);
            return true;
        } catch (FeignException e) {
            return false;
        }
    }
}
