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
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;

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
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void _init() {
        instance = this;
        plugin = this;
        loadAuthMe();
    }

    private void _registerEvents() {
        getServer().getPluginManager().registerEvents(new AuthMeEvent(), this);
    }

    private void loadAuthMe() {
        Runnable task = () -> {

        };

        new Thread(() -> {
            Plugin authMePlugin = Bukkit.getPluginManager().getPlugin("AuthMe");
            if (authMePlugin != null && authMePlugin.isEnabled()) {
                try {
                    Field field = AuthMeApi.getInstance().getClass().getDeclaredField("dataSource");
                    field.setAccessible(true);
                    dataSource = (DataSource) field.get(AuthMeApi.getInstance());
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    getHexLogger().error("Not connected to AuthMe!");
                    Bukkit.getPluginManager().disablePlugin(instance);
                }
                getHexLogger().info("Detected AuthMe!");
                _registerEvents();
                AuthMeHandler authMeHandler = new AuthMeHandler();
                authMeHandler.checkUsers();
            } else {
                try {
                    getHexLogger().info("Waiting for AuthMe to be enabled...");
                    Thread.sleep(TimeUnit.SECONDS.toMillis(10));
                    loadAuthMe();
                } catch (InterruptedException e) {
                    getHexLogger().error("AuthMe is not enabled!");
                }
            }
            Thread.currentThread().interrupt();
        }).start();
    }
}
