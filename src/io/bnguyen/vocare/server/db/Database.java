package io.bnguyen.vocare.server.db;

import java.io.File;

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
    
    private AccountStore    accounts;
    private UserStore       users;
    private ChatStore       chats;
    private MessageStore    messages;
    
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
        accounts = new AccountStore();
        users = new UserStore();
        chats = new ChatStore();
        messages = new MessageStore();
    }
    
    public AccountStore getAccounts()
    {
        return accounts;
    }
    
    public UserStore getUsers()
    {
        return users;
    }
    
    public ChatStore getChats()
    {
        return chats;
    }
    
    public MessageStore getMessages()
    {
        return messages;
    }
    
    public Account createAccount(String accountName, String email, String password)
        throws InvalidAccountNameException, InvalidEmailException
    {
        Account exist = accounts.findByAccountName(accountName);
        if(exist != null)
            throw new InvalidAccountNameException(accountName);
        
        Account acc = new Account(accountIdPool, accountName, email, password);
        accounts.add(acc);
        ++accountIdPool;
        return acc;
    }
    
    public Chat createChat(User user)
    {
        Chat chat = new Chat(chatIdPool);
        chat.addUser(user);
        chat.setPaired(false);
        ++chatIdPool;
        return chat;
    }
    
    public Chat createChat(User user1, User user2)
    {
        Chat chat = new Chat(chatIdPool);
        chat.addUser(user1);
        chat.addUser(user2);
        chat.setPaired(true);
        ++chatIdPool;
        return chat;
    }
    
    public Message createMessage(Chat chat, User sender, String message)
    {
        Message msg = new Message(messageIdPool, sender, message);
        chat.addMessage(msg);
        ++messageIdPool;
        return msg;
    }
 
    @Override
    public Element generateElement(Document doc)
    {
        Element base = doc.createElement("Database");
        base.setAttribute(ACCOUNTIDPOOL_TAG, Integer.toString(accountIdPool));
        base.setAttribute(USERIDPOOL_TAG, Integer.toString(userIdPool));
        base.setAttribute(CHATIDPOOL_TAG, Integer.toString(chatIdPool));
        base.setAttribute(MESSAGEIDPOOL_TAG, Integer.toString(messageIdPool));
        
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
