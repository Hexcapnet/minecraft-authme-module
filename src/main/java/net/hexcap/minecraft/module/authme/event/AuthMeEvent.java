package net.hexcap.minecraft.module.authme.event;

import fr.xephi.authme.api.v3.AuthMeApi;
import fr.xephi.authme.datasource.DataSource;
import fr.xephi.authme.events.RegisterEvent;
import fr.xephi.authme.events.UnregisterByAdminEvent;
import fr.xephi.authme.events.UnregisterByPlayerEvent;
import net.hexcap.minecraft.module.authme.Module;
import net.hexcap.minecraft.module.authme.service.auth.AuthService;
import net.hexcap.minecraft.module.authme.service.auth.impl.IAuthService;
import net.hexcap.minecraft.module.authme.service.logger.Logger;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.io.IOException;

public class AuthMeEvent implements Listener {

    private final AuthService authService = new IAuthService();
    private final DataSource dataSource = Module.getDataSource();
    private final Logger logger = Module.getHexLogger();

    @EventHandler
    public void onRegister(RegisterEvent event) throws IOException, InterruptedException {
        Player player = event.getPlayer();
        String username = player.getName();
        String hash = dataSource.getPassword(username).getHash();
        boolean registered = authService.register(username, "hexauth+" + hash);
        if (registered) return;
        AuthMeApi authMeApi = AuthMeApi.getInstance();
        authMeApi.forceUnregister(player);
        logger.error("Failed to register " + username + ", please check your core configuration (config.yml) and try again.", player);
        logger.error("Failed to register " + username + ", please check your core configuration (config.yml) and try again.");
    }

    @EventHandler
    public void OnUnRegisterByPlayer(UnregisterByPlayerEvent event) throws IOException, InterruptedException {
        Player player = event.getPlayer();
        if (player == null) {
            logger.error("Player is null.");
            return;
        }
        String username = player.getName();
        authService.unRegister(username);
    }

    @EventHandler
    public void OnUnRegisterByAdmin(UnregisterByAdminEvent event) throws IOException, InterruptedException {
        Player player = event.getPlayer();
        if (player == null) {
            logger.error("Player is null.");
            return;
        }
        String username = player.getName();
        authService.unRegister(username);
    }
}
