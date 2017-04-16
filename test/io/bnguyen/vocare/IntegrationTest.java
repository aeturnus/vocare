package io.bnguyen.vocare;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import io.bnguyen.vocare.client.ChatClient;
import io.bnguyen.vocare.data.Account;
import io.bnguyen.vocare.data.InvalidNameException;
import io.bnguyen.vocare.data.User;
import io.bnguyen.vocare.server.ChatServer;

public class IntegrationTest
{
    ChatServer server;
    ChatClient client;
    @Before
    public void setUp() throws Exception
    {
        server = new ChatServer();
        client = new ChatClient();
    }

    @After
    public void tearDown() throws Exception
    {
        client.shutdown();
        server.shutdown();
    }

    @Test
    public void testCreateAccount()
    {
        try
        {
            server.run();
            Thread.sleep(50);
            client.connect();
            try
            {
                client.createAccount("johnnyboy", "john@email.com", "hunter2");
            }catch(Exception e)
            {
                Assert.fail("Failed to create an account: ");
            }
            Thread.sleep(50);
            Account acc = server.getDB().getAccounts().findByAccountName("johnnyboy");
            
            Assert.assertNotEquals("Account was not created serverside",null, acc);
        }
        catch(Exception e)
        {
            Assert.fail("Exception occurred");
        }
    }
    
    @Test
    public void testLoginLogout()
    {
        try
        {
            server.run();
            server.getDB().createAccount("johnnyboy", "john@email.com", "hunter2");
            Thread.sleep(50);
            client.connect();
            
            Thread.sleep(50);
            
            ChatServer.ClientHandler handler = server.getListener().getHandlers()[0];
            client.login("johnnyboy", "hunter2");
            Assert.assertTrue("Account should be authenticated after login", handler.isAuthenticated());
            client.logout();
            Assert.assertFalse("Account should be unauthenticated after logout", handler.isAuthenticated());
            
        }
        catch(Exception e)
        {
            Assert.fail("Exception occurred");
        }
    }
    
    @Test
    public void testCreateUser()
    {
        try
        {
            server.run();
            server.getDB().createAccount("johnnyboy", "john@email.com", "hunter2");
            Thread.sleep(50);
            client.connect();
            client.login("johnnyboy", "hunter2");
            Thread.sleep(50);
            client.createUser("john", "John", "Johnson", "1234567890", "john@email.com");
            User newUser = server.getDB().getUsers().findByUserName("john");
            Account acc = server.getDB().getAccounts().findByAccountName("johnnyboy");
            
            Assert.assertNotEquals("New user should be created", null, newUser);
            Assert.assertEquals("New user should be in account", newUser, acc.getUsers()[0]);
            try
            {
                client.createUser("john", "John", "Johnson", "1234567890", "john@email.com");
            }
            catch(InvalidNameException ine)
            {
                return;
            }
            Assert.fail("Should have been caught in exception");
        }
        catch(Exception e)
        {
            Assert.fail("Exception occurred");
        }
    }
    
    @Test
    public void testBasic()
    {
        try
        {
            server.run();
            Thread.sleep(50);
            client.connect();
            try
            {
                client.createAccount("johnnyboy", "john@email.com", "hunter2");
            }catch(Exception e)
            {
                Assert.fail("Failed to create an account: ");
            }
            Thread.sleep(50);
            Account acc = server.getDB().getAccounts().findByAccountName("johnnyboy");
            
            Assert.assertNotEquals("Account was not created serverside",null, acc);
            Assert.assertEquals("johnnyboy", acc.getAccountName());
            Assert.assertEquals("john@email.com", acc.getEmail());
            
            
            ChatServer.ClientHandler handler = server.getListener().getHandlers()[0];
            Assert.assertTrue("Account should be authenticated after account creation", handler.isAuthenticated());
            client.logout();
            Assert.assertFalse("Account should be unauthenticated after logout", handler.isAuthenticated());
            client.login("johnnyboy", "hunter2");
            Assert.assertTrue("Account should be authenticated after login", handler.isAuthenticated());
            
        }
        catch(Exception e)
        {
            Assert.fail("Exception occurred");
        }
    }

}
