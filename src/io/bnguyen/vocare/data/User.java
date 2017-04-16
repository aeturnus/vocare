package io.bnguyen.vocare.data;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import io.bnguyen.vocare.io.DOMable;
import io.bnguyen.vocare.io.DOMmer;
import io.bnguyen.vocare.server.Database;

public class User implements DOMable
{
    private int    id;
    private String userName;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private ArrayList<User> contacts;
    
    private static final String USERNAME_TAG = "userName";
    private static final String FIRSTNAME_TAG = "firstName";
    private static final String LASTNAME_TAG = "lastName";
    private static final String PHONE_TAG = "phone";
    private static final String EMAIL_TAG = "email";
    private static final String CONTACTS_TAG = "Contacts";
    private static final String USERID_TAG = "userId";
    
    public User( int id, String username, String firstName, String lastName, String phone, String email )
    {
        setId(id);
        setUserName(username);
        setFirstName(firstName);
        setLastName(lastName);
        setPhone(phone);
        setEmail(email);
        contacts = new ArrayList<User>();
    }
    
    public User(Element ele, Database db)
    {
        fromElement(ele,db);
    }
    
    @Override
    public int hashCode()
    {
        return id;
    }

    public Element generateElement(Document doc)
    {
        Element base = DOMmer.generateParentWithValue(doc, "User", "id", Integer.toString(id));
        DOMmer.addChildElementValue(doc, base, USERNAME_TAG, userName);
        DOMmer.addChildElementValue(doc, base, FIRSTNAME_TAG, firstName);
        DOMmer.addChildElementValue(doc, base, LASTNAME_TAG, lastName);
        DOMmer.addChildElementValue(doc, base, PHONE_TAG, phone);
        DOMmer.addChildElementValue(doc, base, EMAIL_TAG, email);
        Element contactsNode = doc.createElement(CONTACTS_TAG);
        for(User user: contacts)
        {
            DOMmer.addChildElementValue(doc, contactsNode, USERID_TAG, Integer.toString(user.getId()));
        }
        return base;
    }

    public void fromElement(Element ele, Database db)
    {
        id = Integer.parseInt(ele.getAttribute("id"));
        userName = DOMmer.getElementTagValue(ele, USERNAME_TAG);
        firstName = DOMmer.getElementTagValue(ele, FIRSTNAME_TAG);
        lastName = DOMmer.getElementTagValue(ele, LASTNAME_TAG);
        phone = DOMmer.getElementTagValue(ele, PHONE_TAG);
        email = DOMmer.getElementTagValue(ele, EMAIL_TAG);
        Element usersNode = (Element) DOMmer.getNodeByName(ele, CONTACTS_TAG);
        String[] userIds = DOMmer.getElementTagValues(usersNode, USERID_TAG);
        for( String userId : userIds )
        {
            contacts.add(db.getUser(Integer.parseInt(userId)));
        }
        
    }

    // Getters and Setters
    public int getId()
    {
        return id;
    }
    public void setId(int id)
    {
        this.id = id;
    }
    public String getUsername()
    {
        return userName;
    }
    public void setUserName(String username)
    {
        this.userName = username;
    }
    public String getFirstName()
    {
        return firstName;
    }
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }
    public String getLastName()
    {
        return lastName;
    }
    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }
    public String getPhone()
    {
        return phone;
    }
    public void setPhone(String phone)
    {
        this.phone = phone;
    }
    public String getEmail()
    {
        return email;
    }
    public void setEmail(String email)
    {
        this.email = email;
    }
    
}
