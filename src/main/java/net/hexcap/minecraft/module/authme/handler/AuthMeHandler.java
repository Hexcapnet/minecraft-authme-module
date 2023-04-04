package net.hexcap.minecraft.module.authme.handler;

import fr.xephi.authme.api.v3.AuthMeApi;
import fr.xephi.authme.api.v3.AuthMePlayer;
import net.hexcap.minecraft.core.service.auth.AuthService;
import net.hexcap.minecraft.core.service.auth.impl.IAuthService;
import net.hexcap.minecraft.module.authme.Module;
import net.hexcap.minecraft.module.authme.service.logger.Logger;
import net.hexcap.minecraft.module.authme.util.generator.PasswordGenerator;

import java.io.IOException;
import java.util.Optional;

public class AuthMeHandler {
    private final Logger logger = Module.getHexLogger();
    public void checkUsers() {
        AuthMeApi authMeApi = AuthMeApi.getInstance();
        authMeApi.getRegisteredNames().forEach(s -> {
            AuthService authService = new IAuthService();
            try {
                if (authService.isRegistered(s)) {
                    logger.info(s + " is registered");
                    return;
                }
                Optional<AuthMePlayer> player = authMeApi.getPlayerInfo(s);
                if (player.isEmpty()) {
                    logger.error(s + " is not find in authme database");
                    return;
                }
                AuthMePlayer authMePlayer = player.get();
                String username = authMePlayer.getName();
                String email = authMePlayer.getEmail().orElse(null);
                String password = new PasswordGenerator()
                        .useNumbers(true)
                        .useUpperCase(true)
                        .useSpecialChars(true)
                        .setLength(32)
                        .build();
                boolean registered = authService.register(username, email, password);
                if (registered) logger.info("Registered " + username);
                else logger.error("Failed to register " + username);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });
    }


}
