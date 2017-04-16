package io.bnguyen.vocare;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import io.bnguyen.vocare.client.ChatClient;
import io.bnguyen.vocare.data.Account;
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
    public void testBasic()
    {
        try
        {
            server.run();
            Thread.sleep(100);
            client.connect();
            try
            {
                client.createAccount("johnnyboy", "john@email.com", "hunter2");
            }catch(Exception e)
            {
                Assert.fail("Failed to create an account: ");
            }
            Thread.sleep(100);
            Account acc = server.getDB().getAccounts().findByAccountName("johnnyboy");
            
            Assert.assertNotEquals("Account was not created serverside",null, acc);
            Assert.assertEquals("johnnyboy", acc.getAccountName());
            Assert.assertEquals("john@email.com", acc.getEmail());
        }
        catch(Exception e)
        {
            Assert.fail("Exception occurred");
        }
    }

}
