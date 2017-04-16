package io.bnguyen.vocare.data;

import java.util.Date;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import io.bnguyen.vocare.io.DOMable;
import io.bnguyen.vocare.io.DOMmer;
import io.bnguyen.vocare.server.db.Database;

public class Message implements Comparable<Message>, DOMable
{
    private int id;
    private User sender;    // who the sender was
    private String message; // payload
    private long time;      // time since epoch
    
    public static final String MESSAGE_TAG = "Message";
    public static final String SENDERID_TAG = "senderId";
    public static final String CONTENT_TAG = "content";
    public static final String TIME_TAG = "time";
    
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
    public Message(Element ele, Database db)
    {
        fromElement(ele,db);
    }
    
    public int compareTo(Message other)
    {
        long diff = this.time - other.time;
        return (int)(diff);
    }
    
    public Element generateElement(Document doc)
    {
        Element base = DOMmer.generateParentWithValue(doc, MESSAGE_TAG, "id", Integer.toString(id));
        DOMmer.addChildElementValue(doc, base, SENDERID_TAG, Integer.toString(sender.getId()));
        DOMmer.addChildElementValue(doc, base, CONTENT_TAG, message);
        DOMmer.addChildElementValue(doc, base, TIME_TAG, Long.toString(time));
        return base;
    }
    
    public void fromElement(Element ele, Database db)
    {
        id = Integer.parseInt(ele.getAttribute("id"));
        sender = db.getUsers().findById(Integer.parseInt(DOMmer.getElementTagValue(ele, SENDERID_TAG)));
        message = DOMmer.getElementTagValue(ele, CONTENT_TAG);
        time = Long.parseLong(DOMmer.getElementTagValue(ele, TIME_TAG));
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
