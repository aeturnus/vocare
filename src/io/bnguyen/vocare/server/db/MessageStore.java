package io.bnguyen.vocare.server.db;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import io.bnguyen.vocare.data.Message;

public class MessageStore implements Store<Message>
{
    private Map<Integer,Message> byId;
    private Map<String,Message> bySenderUserName;
    public MessageStore()
    {
        byId = new HashMap<Integer,Message>();
        bySenderUserName = new HashMap<String,Message>();
    }
    
    public void clear()
    {
        byId.clear();
    }
    
    public void add(Message chat)
    {
        byId.put(chat.getId(), chat);
        bySenderUserName.put(chat.getSender().getUserName(), chat);
    }
        
    public Message findBySenderUserName(int id)
    {
        return bySenderUserName.get(id);
    }
    
    public Message findById(int id)
    {
        return byId.get(id);
    }

    public Collection<Message> values()
    {
        return byId.values();
    }

}
