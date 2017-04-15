package io.bnguyen.vocare.data;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import io.bnguyen.vocare.io.DOMmer;

public class AccountTest
{

    private boolean checkEmailValidation(String email)
    {
        try
        {
            Account.validateEmail(email);
        } catch (InvalidEmailException iee)
        {
            return false;
        }
        return true;
    }
    @Test
    public void testValidateEmail()
    {
        String[] emails = {
                "johnsmith123@email.com",
                "john.smith123@email.subodomain.com",
                "smith.com"
        };
        boolean[] results = {
                true,
                true,
                false
        };
        
        for(int i = 0; i < emails.length; i++)
        {
            Assert.assertTrue(emails[i] + " was incorrectly considered " + Boolean.toString(!results[i]), results[i]==checkEmailValidation(emails[i]));
        }
    }
    
    @Test
    public void testGenerateElement()
    {
        Account acc;
        User user = new User(10,"johnnyboy","John","Johnson","5555555555","john@email.com");
        try
        {
            acc = new Account(10, "johnnyboy", "john@email.com", "hunter2");
            acc.addUser(user);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = dbf.newDocumentBuilder();
            Document doc = builder.newDocument();
            Element ele = acc.generateElement(doc);
            
            // test it
            Assert.assertEquals(acc.getId(), Integer.parseInt(ele.getAttribute("id")));
            Assert.assertEquals(acc.getAccountName(), DOMmer.getElementTagValue(ele, Account.ACCOUNTNAME_TAG));
            Assert.assertEquals(acc.getEmail(), DOMmer.getElementTagValue(ele, Account.EMAIL_TAG));
            Assert.assertEquals(acc.getHashword(), DOMmer.getElementTagValue(ele, Account.HASHWORD_TAG));
            Assert.assertEquals(acc.getUsers().length,DOMmer.getElementTagValues(ele, Account.USERID_TAG).length);
            Assert.assertEquals(user.getId(), Integer.parseInt(DOMmer.getElementTagValues(ele, Account.USERID_TAG)[0]));
        }
        catch (Exception e)
        {
            Assert.fail();
        }
        
    }

}
