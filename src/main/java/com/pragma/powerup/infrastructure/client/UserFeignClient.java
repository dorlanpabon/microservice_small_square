package com.pragma.powerup.infrastructure.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service", url = "http://localhost:8081/users")
public interface UserFeignClient {

    @GetMapping("/validate-owner/{userId}")
    void isOwner(@PathVariable("userId") Long userId);
}
