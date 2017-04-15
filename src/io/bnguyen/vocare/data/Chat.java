package io.bnguyen.vocare.data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import io.bnguyen.vocare.Database;
import io.bnguyen.vocare.io.DOMable;
import io.bnguyen.vocare.io.DOMmer;

public class Chat implements DOMable
{
    private int id;
    private Set<User> users;
    private List<Message> messages;
    public Chat()
    {
        initDataStructures();
    }
    
    public Chat(Element ele, Database db)
    {
        fromElement(ele,db);
    }
    
    private initDataStructures()
    {
        users = new HashSet<User>();
        messages = new ArrayList<Message>();
    }
    
    public Element generateElement(Document doc)
    {
        Element base = DOMmer.generateParentWithValue(doc, "User", "id", Integer.toString(id));
        Element usersNode = doc.createElement("Users");
        for(User user : users)
        {
            DOMmer.addChildElementValue(doc, usersNode, "userId", Integer.toString(user.getId()));
        }
        Element messagesNode = doc.createElement("Messages");
        for(Message message : messages)
        {
            DOMmer.addChildElementValue(doc, messagesNode, "msgId", Integer.toString(message.getId()));
        }
        base.appendChild(usersNode);
        base.appendChild(messagesNode);
        return base;
    }
    
    public void fromElement(Element ele, Database db)
    {
        initDataStructures();
        
        id = Integer.parseInt(ele.getAttribute("id"));
        
        Element usersNode = (Element) DOMmer.getNodeByName(ele, "Users");
        String[] userIds = DOMmer.getElementTagValues(usersNode, "userId");
        for( String userId : userIds )
        {
            users.add(db.getUser(Integer.parseInt(userId)));
        }
        
        Element messagesNode = (Element) DOMmer.getNodeByName(ele, "Messages");
        String[] messageIds = DOMmer.getElementTagValues(messagesNode, "msgId");
        for( String messageId : messageIds )
        {
            messages.add(db.getMessage(Integer.parseInt(messageId)));
        }
        
        
    }
    
    
}
