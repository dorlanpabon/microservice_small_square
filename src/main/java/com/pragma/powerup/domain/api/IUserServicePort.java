package com.pragma.powerup.domain.api;

public interface IUserServicePort {

    boolean isOwner(Long userId);

    Long getUserId();

}
