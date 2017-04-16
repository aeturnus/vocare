package io.bnguyen.vocare.server.db;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import io.bnguyen.vocare.data.User;

public class UserStore implements Store<User>
{
    private Map<Integer,User> byId;
    private Map<String,User> byUserName;
    public UserStore()
    {
        byId = new HashMap<Integer,User>();
        byUserName = new HashMap<String,User>();
    }
    
    public void clear()
    {
        byId.clear();
        byUserName.clear();
    }
    
    public void add(User user)
    {
        byId.put(user.getId(), user);
        byUserName.put(user.getUserName(), user);
    }
    
    public User findById(int id)
    {
        return byId.get(id);
    }
    
    public User findByUserName(String userName)
    {
        return byUserName.get(userName);
    }
    
    public Collection<User> values()
    {
        return byId.values();
    }
    
}
