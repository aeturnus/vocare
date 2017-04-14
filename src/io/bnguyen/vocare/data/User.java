package io.bnguyen.vocare.data;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import io.bnguyen.vocare.Database;
import io.bnguyen.vocare.io.DOMable;
import io.bnguyen.vocare.io.DOMmer;

public class User implements DOMable
{
    private int    id;
    private String userName;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    
    public User( int id, String username, String firstName, String lastName, String phone, String email )
    {
        setId(id);
        setUserName(username);
        setFirstName(firstName);
        setLastName(lastName);
        setPhone(phone);
        setEmail(email);
    }
    
    @Override
    public int hashCode()
    {
        return id;
    }

    public Element generateElement(Document doc)
    {
        Element base = DOMmer.generateParentWithValue(doc, "User", "id", Integer.toString(id));
        DOMmer.addChildElementValue(doc, base, "userName", userName);
        DOMmer.addChildElementValue(doc, base, "firstName", firstName);
        DOMmer.addChildElementValue(doc, base, "lastName", lastName);
        DOMmer.addChildElementValue(doc, base, "phone", phone);
        DOMmer.addChildElementValue(doc, base, "email", email);
        return base;
    }

    public void fromElement(Element ele, Database db)
    {
        id = Integer.parseInt(ele.getAttribute("id"));
        userName = DOMmer.getElementTagValue(ele, "userName");
        firstName = DOMmer.getElementTagValue(ele, "firstName");
        lastName = DOMmer.getElementTagValue(ele, "lastName");
        phone = DOMmer.getElementTagValue(ele, "phone");
        email = DOMmer.getElementTagValue(ele, "email");
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
