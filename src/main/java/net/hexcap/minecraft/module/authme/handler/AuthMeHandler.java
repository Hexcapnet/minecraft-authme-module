package net.hexcap.minecraft.module.authme.handler;

import fr.xephi.authme.api.v3.AuthMeApi;
import fr.xephi.authme.api.v3.AuthMePlayer;
import fr.xephi.authme.datasource.DataSource;
import net.hexcap.minecraft.core.service.auth.AuthService;
import net.hexcap.minecraft.core.service.auth.impl.IAuthService;
import net.hexcap.minecraft.module.authme.Module;
import net.hexcap.minecraft.module.authme.service.logger.Logger;

import java.util.Optional;

public class AuthMeHandler {
    private final Logger logger = Module.getHexLogger();
    private final DataSource dataSource = Module.getDataSource();
    private int synced = 0;

    public void checkUsers() {
        logger.info("players are syncing...");
        AuthMeApi authMeApi = AuthMeApi.getInstance();
        authMeApi.getRegisteredNames().forEach(s -> {
            AuthService authService = new IAuthService();
            Optional<AuthMePlayer> player = authMeApi.getPlayerInfo(s);
            if (player.isEmpty()) {
                logger.error(s + " is not find in authme database.");
                return;
            }
            AuthMePlayer authMePlayer = player.get();
            String username = authMePlayer.getName();
            String email = authMePlayer.getEmail().orElse(null);
            String password = dataSource.getPassword(username).getHash();
            boolean registered = authService.register(username, email, password);
            if (registered) synced++;
            else logger.error(username + " has not been synced.");
        });
        logger.info(synced + " players have been synced.");
    }


}
