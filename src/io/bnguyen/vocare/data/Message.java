package io.bnguyen.vocare.data;

import java.util.Date;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import io.bnguyen.vocare.Database;
import io.bnguyen.vocare.io.DOMable;
import io.bnguyen.vocare.io.DOMmer;

public class Message implements Comparable<Message>, DOMable
{
    private int id;
    private User sender;    // who the sender was
    private String message; // payload
    private long time;      // time since epoch
    
    // generate a new message at this time
    public Message(int id, User sender, String message)
    {
        this.id = id;
        this.sender = sender;
        this.message = message;
        this.time = (new Date()).getTime();
    }
    public Message(int id, User sender, String message, long time)
    {
        this.id = id;
        this.sender = sender;
        this.message = message;
        this.time = time;
    }
    
    public int compareTo(Message other)
    {
        long diff = this.time - other.time;
        return (int)(diff);
    }
    
    public Element generateElement(Document doc)
    {
        Element base = DOMmer.generateParentWithValue(doc, "Message", "id", Integer.toString(id));
        DOMmer.addChildElementValue(doc, base, "senderId", Integer.toString(sender.getId()));
        DOMmer.addChildElementValue(doc, base, "message", message);
        DOMmer.addChildElementValue(doc, base, "time", Long.toString(time));
        return base;
    }
    
    public void fromElement(Element ele, Database db)
    {
        id = Integer.parseInt(ele.getAttribute("id"));
        sender = db.getUser(Integer.parseInt(DOMmer.getElementTagValue(ele, "senderId")));
        message = DOMmer.getElementTagValue(ele, "message");
        time = Long.parseLong(DOMmer.getElementTagValue(ele, "time"));
    }
    
    // getters and setters
    public int getId()
    {
        return id;
    }
    public void setId(int id)
    {
        this.id = id;
    }
    
    public User getSender()
    {
        return sender;
    }
    
    public void setSender(User sender)
    {
        this.sender = sender;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public long getTime()
    {
        return time;
    }

    public void setTime(long time)
    {
        this.time = time;
    }
}
