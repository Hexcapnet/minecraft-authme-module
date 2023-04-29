package net.hexcap.minecraft.module.authme.service.authme.impl;

import fr.xephi.authme.api.v3.AuthMeApi;
import net.hexcap.minecraft.module.authme.service.authme.AuthMeService;

public class IAuthMeService implements AuthMeService {

    private final AuthMeApi authMeApi = AuthMeApi.getInstance();

    @Override
    public Boolean register(String username, String password) {
        return authMeApi.registerPlayer(username, password);
    }

    @Override
    public Boolean updatePassword(String username, String password) {
        try {
            authMeApi.changePassword(username, password);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Boolean unRegister(String username) {
        try {
            authMeApi.forceUnregister(username);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
