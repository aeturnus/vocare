package io.bnguyen.vocare;

import java.io.File;
import java.util.HashMap;

import io.bnguyen.vocare.data.Account;
import io.bnguyen.vocare.data.Chat;
import io.bnguyen.vocare.data.Message;
import io.bnguyen.vocare.data.User;

public class Database
{
    private int accountIdPool;
    private int userIdPool;
    private int chatIdPool;
    private int messageIdPool;
    
    private HashMap<Integer,Account> accounts;
    private HashMap<Integer,User> users;
    private HashMap<Integer,Chat> chats;
    private HashMap<Integer,Message> messages;
    
    public Database()
    {
        accountIdPool = 0;
        userIdPool = 0;
        chatIdPool = 0;
        messageIdPool = 0;
        
        accounts = new HashMap<Integer,Account>();
    }
    
    public User getUser(int id)
    {
        return users.get(id);
    }
    
    public static class Loader
    {
        public static void saveToFile(File file)
        {
            
        }
        
        public static Database loadFromFile(File file)
        {
            Database db = new Database();
            
            return db;
        }
        
        public static Database loadFromFile(String path)
        {
            File file = new File(path);
            return loadFromFile(file);
        }
    }
}
