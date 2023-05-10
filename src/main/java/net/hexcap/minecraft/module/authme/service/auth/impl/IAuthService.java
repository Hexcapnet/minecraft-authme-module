package net.hexcap.minecraft.module.authme.service.auth.impl;

import net.hexcap.minecraft.module.authme.Module;
import net.hexcap.minecraft.module.authme.service.auth.AuthService;
import net.hexcap.minecraft.module.authme.service.logger.Logger;

import java.io.IOException;

public class IAuthService implements AuthService {
    private final Logger logger = Module.getHexLogger();
    private final net.hexcap.minecraft.core.service.auth.AuthService authService = new net.hexcap.minecraft.core.service.auth.impl.IAuthService();

    @Override
    public Boolean register(String username, String password) {
        boolean registered = authService.register(username, null, password);
        if (!registered) {
            logger.error("Failed to register " + username);
            return false;
        }
        logger.info("Registered " + username);
        return true;
    }


    @Override
    public Boolean unRegister(String username) {
        boolean unRegistered = authService.unRegister(username);
        if (!unRegistered) {
            logger.error("Failed to unregister " + username);
            return false;
        }
        logger.info("Unregistered " + username);
        return true;
    }
}
