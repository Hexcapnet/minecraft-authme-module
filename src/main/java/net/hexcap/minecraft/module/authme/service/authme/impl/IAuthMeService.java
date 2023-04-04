package net.hexcap.minecraft.module.authme.service.authme.impl;

import net.hexcap.minecraft.core.service.auth.AuthService;
import net.hexcap.minecraft.core.service.auth.impl.IAuthService;
import net.hexcap.minecraft.module.authme.Module;
import net.hexcap.minecraft.module.authme.service.authme.AuthMeService;
import net.hexcap.minecraft.module.authme.service.logger.Logger;
import net.hexcap.minecraft.module.authme.util.generator.PasswordGenerator;

import java.io.IOException;

public class IAuthMeService implements AuthMeService {
    private final Logger logger = Module.getHexLogger();
    private final AuthService authService = new IAuthService();

    @Override
    public Boolean register(String username) throws IOException, InterruptedException {
        String generatedPassword = new PasswordGenerator()
                .useSpecialChars(true)
                .useNumbers(true)
                .useUpperCase(true)
                .setLength(16)
                .build();
        boolean registered = authService.register(username, null, "authme-module-" + generatedPassword);
        if (!registered) {
            logger.error("Failed to register " + username);
            return false;
        }
        logger.info("Registered " + username);
        return true;
    }


    @Override
    public Boolean updateEmail(String username, String email) throws IOException, InterruptedException {
        boolean updated = authService.updateEmail(username, email);
        if (!updated) {
            logger.error("Failed to update " + username + "'s email to " + email);
            return false;
        }
        logger.info("Updated " + username + "'s email to " + email);
        return true;
    }

    @Override
    public Boolean unRegister(String username) throws IOException, InterruptedException {
        boolean unRegistered = authService.unRegister(username);
        if (!unRegistered) {
            logger.error("Failed to unregister " + username);
            return false;
        }
        logger.info("Unregistered " + username);
        return true;
    }
}
