package io.bnguyen.vocare.data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import io.bnguyen.vocare.io.DOMable;
import io.bnguyen.vocare.io.DOMmer;
import io.bnguyen.vocare.server.db.Database;

public class Chat implements DOMable
{
    private int id;
    private String chatName;
    private Set<User> users;
    private List<Message> messages;
    
    public static final String CHAT_TAG = "Chat";
    public static final String CHATNAME_TAG = "chatName";
    public static final String USERS_TAG = "Users";
    public static final String USERID_TAG = "userId";
    public static final String MESSAGES_TAG = "Messages";
    public static final String MSGID_TAG = "msgId";
    
    public Chat()
    {
        initDataStructures();
    }
    
    public Chat(Element ele, Database db)
    {
        fromElement(ele,db);
    }
    
    
    public void addUser(User user)
    {
        users.add(user);
    }
    
    public void removeUser(User user)
    {
        users.remove(user);
    }
    
    
    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getChatName()
    {
        return chatName;
    }

    public void setChatName(String chatName)
    {
        this.chatName = chatName;
    }
    
    public Set<User> getUsers()
    {
        return users;
    }

    public void setUsers(Set<User> users)
    {
        this.users = users;
    }

    public List<Message> getMessages()
    {
        return messages;
    }

    public void setMessages(List<Message> messages)
    {
        this.messages = messages;
    }

    private void initDataStructures()
    {
        users = new HashSet<User>();
        messages = new ArrayList<Message>();
    }
    
    public Element generateElement(Document doc)
    {
        Element base = DOMmer.generateParentWithValue(doc, CHAT_TAG, "id", Integer.toString(id));
        DOMmer.addChildElementValue(doc, base, CHATNAME_TAG, chatName);
        Element usersNode = doc.createElement(USERS_TAG);
        for(User user : users)
        {
            DOMmer.addChildElementValue(doc, usersNode, USERID_TAG, Integer.toString(user.getId()));
        }
        Element messagesNode = doc.createElement(MESSAGES_TAG);
        for(Message message : messages)
        {
            DOMmer.addChildElementValue(doc, messagesNode, MSGID_TAG, Integer.toString(message.getId()));
        }
        base.appendChild(usersNode);
        base.appendChild(messagesNode);
        return base;
    }
    
    public void fromElement(Element ele, Database db)
    {
        initDataStructures();
        
        id = Integer.parseInt(ele.getAttribute("id"));
        
        Element usersNode = (Element) DOMmer.getNodeByName(ele, USERS_TAG);
        String[] userIds = DOMmer.getElementTagValues(usersNode, USERID_TAG);
        for( String userId : userIds )
        {
            users.add(db.getUsers().findById(Integer.parseInt(userId)));
        }
        
        Element messagesNode = (Element) DOMmer.getNodeByName(ele, MESSAGES_TAG);
        String[] messageIds = DOMmer.getElementTagValues(messagesNode, MSGID_TAG);
        for( String messageId : messageIds )
        {
            messages.add(db.getMessages().findById(Integer.parseInt(messageId)));
        }
    }
    
}
