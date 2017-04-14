package io.bnguyen.vocare.data;

import java.security.SecureRandom;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import com.sun.org.apache.xml.internal.security.utils.Base64;

public class Password
{
    public static final int saltLength = 32;
    public static final int ITERATIONS = 10000;
    public static final int KEYLENGTH  = 256;
    public static String getSaltedHash(String password) throws Exception
    {
        String output = "";
        byte[] salt = getSalt();
        output = Base64.encode(salt) + "$" + getHash(password,salt);
        return output;
    }
       private static byte[] getSalt() throws Exception
    {
        return SecureRandom.getInstance("SHA1PRNG").generateSeed(saltLength);
    }
    
    private static String getHash(String password, byte[] salt) throws Exception
    {
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        SecretKey key = keyFactory.generateSecret(new PBEKeySpec(
                        password.toCharArray(), salt, ITERATIONS, KEYLENGTH));
        return Base64.encode(key.getEncoded());
    }
    
    public static boolean checkPassword(String input, String stored) throws Exception
    {
        String[] saltAndPassword = stored.split("\\$");
        // TODO: exception handling
        String inputHash = getHash(input,Base64.decode(saltAndPassword[0]));
        return inputHash.equals(saltAndPassword[1]);
    }
}
