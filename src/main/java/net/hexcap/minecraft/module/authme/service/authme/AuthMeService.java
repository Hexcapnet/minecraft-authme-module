package net.hexcap.minecraft.module.authme.service.authme;

public interface AuthMeService {
    Boolean register(String username, String password);

    Boolean updatePassword(String username, String password);

    Boolean unRegister(String username);

}
