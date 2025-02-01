package com.pragma.powerup.infrastructure.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.websocket.server.PathParam;

@FeignClient(name = "message-service", url = "http://localhost:8083/")
public interface MessageFeignClient {

    @GetMapping("/message/send")
    void sendCode(@RequestParam("to") String to);

    @GetMapping("/message/verify")
    void verifyCode(@RequestParam("to") String to, @RequestParam("code") String code);

}
