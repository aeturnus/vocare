package io.bnguyen.vocare.server.db;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import io.bnguyen.vocare.data.Chat;

public class ChatStore implements Store<Chat>
{
    private Map<Integer,Chat> byId;
    public ChatStore()
    {
        byId = new HashMap<Integer,Chat>();
    }
    
    public void clear()
    {
        byId.clear();
    }
    
    public void add(Chat chat)
    {
        byId.put(chat.getId(), chat);
    }
        
    public Chat findById(int id)
    {
        return byId.get(id);
    }

    public Collection<Chat> values()
    {
        return byId.values();
    }
    
    

}
