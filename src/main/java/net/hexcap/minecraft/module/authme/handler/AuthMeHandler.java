package net.hexcap.minecraft.module.authme.handler;

import fr.xephi.authme.api.v3.AuthMeApi;
import fr.xephi.authme.api.v3.AuthMePlayer;
import fr.xephi.authme.datasource.DataSource;
import net.hexcap.minecraft.core.dto.auth.RegisterDTO;
import net.hexcap.minecraft.core.service.auth.AuthService;
import net.hexcap.minecraft.core.service.auth.impl.IAuthService;
import net.hexcap.minecraft.module.authme.Module;
import net.hexcap.minecraft.module.authme.service.logger.Logger;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AuthMeHandler {
    private final Logger logger = Module.getHexLogger();
    private final DataSource dataSource = Module.getDataSource();

    public void checkUsers() {
        logger.info("players are syncing...");
        AuthMeApi authMeApi = AuthMeApi.getInstance();
        AuthService authService = new IAuthService();
        List<RegisterDTO> registerDTOS = authMeApi.getRegisteredNames().stream()
                .filter(s -> {
                    Optional<AuthMePlayer> player = authMeApi.getPlayerInfo(s);
                    return player.isPresent();
                })
                .map(s -> {
                    Optional<AuthMePlayer> player = authMeApi.getPlayerInfo(s);
                    AuthMePlayer authMePlayer = player.get();
                    String username = authMePlayer.getName();
                    String email = authMePlayer.getEmail().orElse(null);
                    String password = dataSource.getPassword(username).getHash();
                    return RegisterDTO.builder()
                            .username(username)
                            .email(email)
                            .password("hexauth+" + password)
                            .confirmPassword("hexauth+" + password)
                            .build();
                }).collect(Collectors.toList());
        authService.registerAll(registerDTOS);
    }
}
