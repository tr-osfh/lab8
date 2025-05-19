package dataBase;

import collection.ServerLogger;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class PasswordKeeper {
    public static String hash(String pswd){
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-384");
            byte[] hashedPswd = md.digest(pswd.getBytes());
            String hexHash = String.format("%064x", new BigInteger(1, hashedPswd));
            return hexHash;

        } catch (NoSuchAlgorithmException e) {
            ServerLogger.getLogger().warning("Ошибка хеширования пароля.");
            return null;
        }
    }

    public static String salt(){
        int length = 6;
        String symbols = "!@#$%^&*()_+-=[]{}|;:,.<>?abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        SecureRandom secureRandom = new SecureRandom();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = secureRandom.nextInt(symbols.length());
            sb.append(symbols.charAt(index));
        }
        return sb.toString();
    }
}
