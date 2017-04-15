package io.bnguyen.vocare;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import io.bnguyen.vocare.data.Account;
import io.bnguyen.vocare.data.Chat;
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
    
    private Map<Integer,Account> accounts;
    private Map<Integer,User> users;
    private Map<Integer,Chat> chats;
    private Map<Integer,Message> messages;
    
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
    
    public Message getMessage(int id)
    {
        return messages.get(id);
    }
    
    
    @Override
    public Element generateElement(Document doc)
    {
        Element base = doc.createElement("Database");
        
        Element accountsNode = DOMmer.generateParentContainer(doc, "Accounts", accounts.values());
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
        accounts = new HashMap<Integer,Account>();
        users    = new HashMap<Integer,User>();
        chats    = new HashMap<Integer,Chat>();
        messages = new HashMap<Integer,Message>();
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
