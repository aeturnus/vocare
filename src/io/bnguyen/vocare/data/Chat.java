package io.bnguyen.vocare.data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Chat
{
    private int id;
    private Set<User> users;
    private List<Message> messages;
    public Chat()
    {
        users = new HashSet<User>();
        messages = new ArrayList<Message>();
    }
}
