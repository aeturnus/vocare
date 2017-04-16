package io.bnguyen.vocare.server.db;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import io.bnguyen.vocare.data.User;

public class UserStore implements Store<User>
{
    private Map<Integer,User> byId;
    public UserStore()
    {
        byId = new HashMap<Integer,User>();
    }
    
    public void clear()
    {
        byId.clear();
    }
    
    public void add(User user)
    {
        byId.put(user.getId(), user);
    }
    
    public User findById(int id)
    {
        return byId.get(id);
    }
    
    public Collection<User> values()
    {
        return byId.values();
    }
    
}
