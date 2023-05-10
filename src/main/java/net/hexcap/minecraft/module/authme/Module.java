package net.hexcap.minecraft.module.authme;

import fr.xephi.authme.api.v3.AuthMeApi;
import fr.xephi.authme.datasource.DataSource;
import lombok.Getter;
import net.hexcap.minecraft.module.authme.event.AuthMeEvent;
import net.hexcap.minecraft.module.authme.handler.AuthMeHandler;
import net.hexcap.minecraft.module.authme.service.logger.Logger;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;

public final class Module extends JavaPlugin {
    @Getter
    private static Plugin instance;
    @Getter
    private static Module plugin;
    @Getter
    private static DataSource dataSource;

    public static Logger getHexLogger() {
        return new Logger();
    }

    @Override
    public void onEnable() {
        _init();
        _registerEvents();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void _init() {
        instance = this;
        plugin = this;

        try {
            Field field = AuthMeApi.getInstance().getClass().getDeclaredField("dataSource");
            field.setAccessible(true);
            dataSource = (DataSource) field.get(AuthMeApi.getInstance());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            getHexLogger().error("Not connected to AuthMe!");
            Bukkit.getPluginManager().disablePlugin(this);
        }
        AuthMeHandler authMeHandler = new AuthMeHandler();
        authMeHandler.checkUsers();
    }

    private void _registerEvents() {
        getServer().getPluginManager().registerEvents(new AuthMeEvent(), this);
    }
}
