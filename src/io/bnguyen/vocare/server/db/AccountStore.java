package io.bnguyen.vocare.server.db;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import io.bnguyen.vocare.data.Account;

public class AccountStore implements Store<Account>
{
    private Map<Integer,Account> byId;
    private Map<String,Account> byAccountName;
    private Map<String,Account> byEmail;

    public AccountStore()
    {
        byId            = new HashMap<Integer,Account>();
        byAccountName   = new HashMap<String,Account>();
        byEmail         = new HashMap<String,Account>();
    }
    
    public void clear()
    {
        byId.clear();
        byAccountName.clear();
        byEmail.clear();
    }
    
    public void add(Account acc)
    {
        byId.put(acc.getId(), acc);
        byAccountName.put(acc.getAccountName(), acc);
        byEmail.put(acc.getEmail(), acc);
    }
    
    public Account findById(int id)
    {
        return byId.get(id);
    }
    
    public Account findByAccountName(String accountName)
    {
        return byAccountName.get(accountName);
    }
    
    public Account findByEmail(String email)
    {
        return byEmail.get(email);
    }
    
    public Collection<Account> values()
    {
        return byId.values();
    }
}
