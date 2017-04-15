package io.bnguyen.vocare.data;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import io.bnguyen.vocare.Database;
import io.bnguyen.vocare.io.DOMable;
import io.bnguyen.vocare.io.DOMmer;

public class Account implements DOMable
{
    int id;
    private String accountName;
    private String email;
    private String hashword;
    
    ArrayList<User> users;
    public Account( int id, String accountName, String email, String password ) throws InvalidDataException
    {
        this.id = id;
        this.accountName = accountName;
        this.email = validateEmail(email);
                
    }
    
    public Account(Element ele, Database db)
    {
        fromElement(ele,db);
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
        Element base = DOMmer.generateParentWithValue(doc, "Account", "id", Integer.toString(id));
        DOMmer.addChildElementValue(doc, base, "accountName", this.accountName);
        DOMmer.addChildElementValue(doc, base, "email", this.email);
        DOMmer.addChildElementValue(doc, base, "hashword", this.hashword);
        Element usersNode = doc.createElement("Users");
        for(User user : users)
        {
            DOMmer.addChildElementValue(doc, usersNode, "userId", Integer.toString(user.getId()));
        }
        base.appendChild(usersNode);
        return base;
    }
    
    public void fromElement(Element ele, Database db)
    {
        this.id = Integer.parseInt(ele.getAttribute("id"));
        this.accountName = DOMmer.getElementTagValue(ele, "accountName");
        this.email = DOMmer.getElementTagValue(ele, "email");
        this.hashword = DOMmer.getElementTagValue(ele, "hashword");
        this.users = new ArrayList<User>();
        Element usersNode = (Element) DOMmer.getNodeByName(ele, "Users");
        String[] userIds = DOMmer.getElementTagValues(usersNode, "userId");
        for( String userId : userIds )
        {
            users.add(db.getUser(Integer.parseInt(userId)));
        }
    }
}
