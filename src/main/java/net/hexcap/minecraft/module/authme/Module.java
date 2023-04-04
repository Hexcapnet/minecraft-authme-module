package net.hexcap.minecraft.module.authme;

import net.hexcap.minecraft.module.authme.event.AuthMeEvent;
import net.hexcap.minecraft.module.authme.handler.AuthMeHandler;
import net.hexcap.minecraft.module.authme.service.logger.Logger;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class Module extends JavaPlugin {
    public static Plugin instance;

    public static Logger getHexLogger() {
        return new Logger();
    }

    @Override
    public void onEnable() {
        instance = this;
        _init();
        _registerEvents();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void _init() {
        AuthMeHandler authMeHandler = new AuthMeHandler();
        authMeHandler.checkUsers();
    }

    private void _registerEvents() {
        getServer().getPluginManager().registerEvents(new AuthMeEvent(), this);
    }
}
