package net.hexcap.minecraft.module.authme.service.authme;

import java.io.IOException;

public interface AuthMeService {
    Boolean register(String username,String password) throws IOException, InterruptedException;

    Boolean updateEmail(String username, String email) throws IOException, InterruptedException;

    Boolean unRegister(String username) throws IOException, InterruptedException;
}
