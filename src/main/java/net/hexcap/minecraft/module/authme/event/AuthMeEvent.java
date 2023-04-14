package net.hexcap.minecraft.module.authme.event;

import fr.xephi.authme.api.v3.AuthMeApi;
import fr.xephi.authme.datasource.DataSource;
import fr.xephi.authme.events.EmailChangedEvent;
import fr.xephi.authme.events.RegisterEvent;
import fr.xephi.authme.events.UnregisterByAdminEvent;
import fr.xephi.authme.events.UnregisterByPlayerEvent;
import net.hexcap.minecraft.module.authme.Module;
import net.hexcap.minecraft.module.authme.service.authme.AuthMeService;
import net.hexcap.minecraft.module.authme.service.authme.impl.IAuthMeService;
import net.hexcap.minecraft.module.authme.service.logger.Logger;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.io.IOException;

public class AuthMeEvent implements Listener {

    private final AuthMeService authMeService = new IAuthMeService();
    private final DataSource dataSource = Module.getDataSource();
    private final Logger logger = Module.getHexLogger();

    @EventHandler
    public void onRegister(RegisterEvent event) throws IOException, InterruptedException {
        Player player = event.getPlayer();
        String username = player.getName();
        String hash = dataSource.getPassword(username).getHash();
        String salt = dataSource.getPassword(username).getSalt();

        boolean registered = authMeService.register(username, "authme-module+" + hash);
        if (registered) return;
        AuthMeApi authMeApi = AuthMeApi.getInstance();
        authMeApi.forceUnregister(player);
        logger.error("Failed to register " + username + ", please check your core configuration (config.yml) and try again.", player);
        logger.error("Failed to register " + username + ", please check your core configuration (config.yml) and try again.");
    }

    @EventHandler
    public void onChangeEmail(EmailChangedEvent event) throws IOException, InterruptedException {
        Player player = event.getPlayer();
        String username = player.getName();
        String email = event.getNewEmail();
        authMeService.updateEmail(username, email);
    }

    @EventHandler
    public void OnUnRegisterByPlayer(UnregisterByPlayerEvent event) throws IOException, InterruptedException {
        Player player = event.getPlayer();
        String username = player.getName();
        authMeService.unRegister(username);
    }

    @EventHandler
    public void OnUnRegisterByAdmin(UnregisterByAdminEvent event) throws IOException, InterruptedException {
        String username = event.getPlayerName();
        authMeService.unRegister(username);
    }
}
