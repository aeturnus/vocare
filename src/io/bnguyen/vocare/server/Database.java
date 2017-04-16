package io.bnguyen.vocare.server;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import io.bnguyen.vocare.data.Account;
import io.bnguyen.vocare.data.Chat;
import io.bnguyen.vocare.data.InvalidAccountNameException;
import io.bnguyen.vocare.data.InvalidEmailException;
import io.bnguyen.vocare.data.Message;
import io.bnguyen.vocare.data.User;
import io.bnguyen.vocare.io.DOMable;
import io.bnguyen.vocare.io.DOMmer;

public class Database implements DOMable
{
    private int accountIdPool;
    private int userIdPool;
    private int chatIdPool;
    private int messageIdPool;
    
    private Map<Integer,Account> accountsById;
    private Map<String,Account> accountsByAccountName;
    private Map<String,Account> accountsByEmail;
    
    private Map<Integer,User> users;
    private Map<Integer,Chat> chats;
    private Map<Integer,Message> messages;
    
    private static final String ACCOUNTIDPOOL_TAG = "accountIdPool";
    private static final String USERIDPOOL_TAG = "userIdPool";
    private static final String CHATIDPOOL_TAG = "chatIdPool";
    private static final String MESSAGEIDPOOL_TAG = "messageIdPool";
    
    public Database()
    {
        accountIdPool = 0;
        userIdPool = 0;
        chatIdPool = 0;
        messageIdPool = 0;
        setupDataStructures();
    }
    
    private void addAccount(Account acc)
    {
        accountsById.put(acc.getId(), acc);
        accountsByAccountName.put(acc.getAccountName(), acc);
        accountsByEmail.put(acc.getEmail(), acc);
    }
    
    public Account createAccount(String accountName, String email, String password)
        throws InvalidAccountNameException, InvalidEmailException
    {
        Account exist = accountsByAccountName.get(accountName);
        if(exist != null)
            throw new InvalidAccountNameException(accountName);
        
        Account acc = new Account(accountIdPool, accountName, email, password);
        addAccount(acc);
        return acc;
    }
    
    public Account getAccount(int id)
    {
        return accountsById.get(id);
    }
    
    public Account getAccount(String key, boolean isAccountName )
    {
        if(isAccountName)
        {
            return accountsByAccountName.get(key);
        }
        return accountsByEmail.get(key);
    }
    
    public User getUser(int id)
    {
        return users.get(id);
    }
    
    public Message getMessage(int id)
    {
        return messages.get(id);
    }
    
    
    @Override
    public Element generateElement(Document doc)
    {
        Element base = doc.createElement("Database");
        base.setAttribute(ACCOUNTIDPOOL_TAG, Integer.toString(accountIdPool));
        base.setAttribute(USERIDPOOL_TAG, Integer.toString(userIdPool));
        base.setAttribute(CHATIDPOOL_TAG, Integer.toString(chatIdPool));
        base.setAttribute(MESSAGEIDPOOL_TAG, Integer.toString(messageIdPool));
        
        Element accountsNode = DOMmer.generateParentContainer(doc, "Accounts", accountsById.values());
        Element usersNode = DOMmer.generateParentContainer(doc, "Users", users.values());
        Element chatsNode = DOMmer.generateParentContainer(doc, "Chats", chats.values());
        Element messagesNode = DOMmer.generateParentContainer(doc, "Messages", messages.values());
        base.appendChild(accountsNode);
        base.appendChild(usersNode);
        base.appendChild(chatsNode);
        base.appendChild(messagesNode);
        
        return base;
    }

    @Override
    public void fromElement(Element ele, Database db)
    {
        setupDataStructures();
        accountIdPool = Integer.parseInt(ele.getAttribute(ACCOUNTIDPOOL_TAG));
        userIdPool = Integer.parseInt(ele.getAttribute(USERIDPOOL_TAG));
        chatIdPool = Integer.parseInt(ele.getAttribute(CHATIDPOOL_TAG));
        messageIdPool = Integer.parseInt(ele.getAttribute(MESSAGEIDPOOL_TAG));
        NodeList nodes = ele.getChildNodes();
        for(int i = 0, length = nodes.getLength(); i < length; i++)
        {
            Node node = nodes.item(i);
            switch(node.getNodeName())
            {
            case "Accounts":
                NodeList accs = node.getChildNodes();
                break;
            case "Users":
                break;
            case "Chats":
                break;
            case "Messages":
                break;
            }
        }
    }
    
    private void setupDataStructures()
    {
        accountsById            = new HashMap<Integer,Account>();
        accountsByAccountName   = new HashMap<String,Account>();
        accountsByEmail         = new HashMap<String,Account>();
        users    = new HashMap<Integer,User>();
        chats    = new HashMap<Integer,Chat>();
        messages = new HashMap<Integer,Message>();
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
