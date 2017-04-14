package io.bnguyen.vocare.data;

import org.junit.Assert;
import org.junit.Test;

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

}
