package io.bnguyen.vocare.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import io.bnguyen.vocare.VocareAPI;
import io.bnguyen.vocare.data.InvalidAccountNameException;
import io.bnguyen.vocare.data.InvalidEmailException;

public class ChatClient implements Runnable
{
    private Socket socket;
    private PrintWriter toServer;
    private BufferedReader fromServer;
    private boolean running;
    public ChatClient()
    {
        running = false;
    }
    
    private String read() throws IOException
    {
        return fromServer.readLine();
    }
    
    private void write(String content) throws IOException
    {
        toServer.println(content);
    }
    
    public void connect() throws IOException
    {
        socket = new Socket("localhost",8000);
        toServer = new PrintWriter(socket.getOutputStream(),true);
        fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }
    
    public void shutdown()
    {
        running = false;
    }
    
    public void run()
    {
        running = true;
        while(running)
        {
        }
    }
    
    private void waitForEnd() throws IOException
    {
        while(!(read().equals(VocareAPI.END))){}
    }
    
    public void createAccount(String accountName, String email, String password)
            throws InvalidEmailException, InvalidAccountNameException, IOException
    {
        write(VocareAPI.CREATE_ACCOUNT);
        write(accountName);
        write(email);
        write(password);
        // wait for response
        boolean badEmail = false;
        boolean badAccountName = false;
        String response;
        do
        {
            response = read();
            switch(response)
            {
            case VocareAPI.CREATE_ACCOUNT_INVALIDEMAIL:
                badEmail = true;
                break;
            case VocareAPI.CREATE_ACCOUNT_ACCOUNTNAME_TAKEN:
                badAccountName = true;
                break;
            }
        } while (!response.equals(VocareAPI.END));
        if(badEmail)
            throw new InvalidEmailException(email);
        if(badAccountName)
            throw new InvalidAccountNameException(accountName);
    }
    
    /**
     * Logs out of current account
     * @throws IOException
     */
    public void logout()
        throws IOException
    {
        write(VocareAPI.LOGOUT_ACCOUNT);
        waitForEnd();
    }
    
    /**
     * Logins for a given account
     * @param accountName
     * @param password
     * @return true if success, false if unsuccessful
     */
    public boolean login(String accountName, String password)
        throws IOException
    {
        boolean result = true;
        write(VocareAPI.LOGIN_ACCOUNT);
        write(accountName);
        write(password);
        String response = read();
        if(response.equals(VocareAPI.FAIL))
        {
            result = false;
        }
        waitForEnd();
        return result;
    }
    
    public static void main(String[] args)
    {
        ChatClient client = new ChatClient();
        while(true)
        {
            try
            {
                client.connect();
                try
                {
                    client.createAccount("johnnyboy", "john@email.com", "hunter2");
                }catch(Exception e)
                {
                    System.err.println("Could not create account");
                }
                client.run();
            }
            catch(IOException ioe)
            {
            }
        }
    }
}
