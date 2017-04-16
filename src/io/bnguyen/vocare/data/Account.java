package io.bnguyen.vocare.data;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import io.bnguyen.vocare.io.DOMable;
import io.bnguyen.vocare.io.DOMmer;
import io.bnguyen.vocare.server.Database;

public class Account implements DOMable
{
    int id;
    private String accountName;
    private String email;
    private String hashword;
    List<User> users;
    
    public static final String ACCOUNT_TAG = "Account";
    public static final String ACCOUNTNAME_TAG = "accountName";
    public static final String EMAIL_TAG = "email";
    public static final String HASHWORD_TAG = "hashword";
    public static final String USERS_TAG = "users";
    public static final String USERID_TAG = "userId";
   
    public Account( int id, String accountName, String email, String password )
            throws InvalidEmailException
    {
        this.id = id;
        this.accountName = accountName;
        this.email = validateEmail(email);
        try
        {
            this.hashword = Password.getSaltedHash(password);
        }
        catch(Exception e) { }
        users = new ArrayList<User>();
    }
    
    public Account(Element ele, Database db)
    {
        fromElement(ele,db);
    }
    
    public void addUser(User user)
    {
        users.add(user);
    }
    
    public static String validateEmail(String email) throws InvalidEmailException
    {
        String[] localAndDomain = email.split("@");
        
        // check that there's an @ separating the local from the domain
        if(localAndDomain.length != 2)
        {
            throw new InvalidEmailException(email);
        }
        
        // check that we have a *.<some extension>
        String[] domain = localAndDomain[1].split("\\.");
        if(domain.length == 0)
        {
            throw new InvalidEmailException(email);
        }
        return email;
    }
    
    // DOMable
    public Element generateElement(Document doc)
    {
        Element base = DOMmer.generateParentWithValue(doc, ACCOUNT_TAG, "id", Integer.toString(id));
        DOMmer.addChildElementValue(doc, base, ACCOUNTNAME_TAG, this.accountName);
        DOMmer.addChildElementValue(doc, base, EMAIL_TAG, this.email);
        DOMmer.addChildElementValue(doc, base, HASHWORD_TAG, this.hashword);
        Element usersNode = doc.createElement(USERS_TAG);
        for(User user : users)
        {
            DOMmer.addChildElementValue(doc, usersNode, USERID_TAG, Integer.toString(user.getId()));
        }
        base.appendChild(usersNode);
        return base;
    }
    
    public void fromElement(Element ele, Database db)
    {
        this.id = Integer.parseInt(ele.getAttribute("id"));
        this.accountName = DOMmer.getElementTagValue(ele, ACCOUNTNAME_TAG);
        this.email = DOMmer.getElementTagValue(ele, EMAIL_TAG);
        this.hashword = DOMmer.getElementTagValue(ele, HASHWORD_TAG);
        this.users = new ArrayList<User>();
        Element usersNode = (Element) DOMmer.getNodeByName(ele, USERS_TAG);
        String[] userIds = DOMmer.getElementTagValues(usersNode, USERID_TAG);
        for( String userId : userIds )
        {
            users.add(db.getUser(Integer.parseInt(userId)));
        }
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getAccountName()
    {
        return accountName;
    }

    public void setAccountName(String accountName)
    {
        this.accountName = accountName;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getHashword()
    {
        return hashword;
    }

    public void setHashword(String hashword)
    {
        this.hashword = hashword;
    }

    public User[] getUsers()
    {
        User[] out = new User[users.size()];
        return users.toArray(out);
    }
    
}
