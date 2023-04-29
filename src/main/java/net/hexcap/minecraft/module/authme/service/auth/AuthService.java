package net.hexcap.minecraft.module.authme.service.auth;

import java.io.IOException;

public interface AuthService {
    Boolean register(String username,String password) throws IOException, InterruptedException;

    Boolean unRegister(String username) throws IOException, InterruptedException;
}
