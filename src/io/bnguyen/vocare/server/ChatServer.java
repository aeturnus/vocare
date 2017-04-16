package io.bnguyen.vocare.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import io.bnguyen.vocare.VocareAPI;
import io.bnguyen.vocare.data.Account;
import io.bnguyen.vocare.data.InvalidAccountNameException;
import io.bnguyen.vocare.data.InvalidEmailException;
import io.bnguyen.vocare.data.Password;
import io.bnguyen.vocare.data.User;

public class ChatServer implements Runnable
{
    private ServerSocket serverSocket;
    private Database db;
    private ServerSocketListener listener;
    public ChatServer()
    {
        try
        {
            serverSocket = new ServerSocket(8000);
            db = new Database();
        }
        catch (IOException ioe)
        {
        }
    }
    
    public void run()
    {
        listener = new ServerSocketListener();
        Thread listenerThread = new Thread(listener);
        listenerThread.start();
        while(listener.isRunning())
        {
        }
    }
    
    public void shutdown()
    {
        listener.shutdown();
    }
    
    public static void main(String[] args)
    {
        ChatServer server = new ChatServer();
        server.run();
    }
    
    private class ServerSocketListener implements Runnable
    {
        ArrayList<ClientHandler> clientHandlers;
        ArrayList<Thread> clientHandlersThreads;
        private boolean active;
        public ServerSocketListener()
        {
            clientHandlers        = new ArrayList<ClientHandler>();
            clientHandlersThreads = new ArrayList<Thread>();
            active = true;
        }
        
        public void run()
        {
            try
            {
                while(active)
                {
                    if(clientHandlers.size() < 50)
                    {
                        Socket socket = serverSocket.accept();
                        ClientHandler handler = new ClientHandler(socket,this);
                        Thread thread = new Thread(handler);
                        synchronized(clientHandlers)
                        {
                            clientHandlers.add(handler);
                        }
                        clientHandlersThreads.add(thread);
                        thread.start();
                    }
                }
            }
            catch(IOException ioe)
            {
                return;
            }
            
        }
        
        public void notifyClosed(ClientHandler handler)
        {
            synchronized(clientHandlers)
            {
                clientHandlers.remove(handler);
            }
        }
        
        int getClientNum()
        {
            return clientHandlers.size();
        }
        boolean isRunning()
        {
            return active;
        }
        void shutdown()
        {
            active = false;
        }
    }
    
    private class ClientHandler implements Runnable
    {
        
        private Socket socket;
        private BufferedReader  fromClient;
        private PrintWriter toClient;
        private ServerSocketListener listener;
        
        private Account account;
        private User user;
        public ClientHandler(Socket socket, ServerSocketListener listener)
        {
            this.socket = socket;
            this.listener = listener;
            try
            {
                this.fromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                this.toClient = new PrintWriter(socket.getOutputStream(),true);
            }
            catch(IOException ioe)
            {
                ioe.printStackTrace();
            }
        }
        
        private String read() throws IOException
        {
            return fromClient.readLine();
        }
        
        private void write(String content)
        {
            toClient.println(content);
        }
        
        public void run()
        {
            while(!socket.isInputShutdown())
            {
                try
                {
                    String request = read();
                    handleRequest(request);
                }
                catch(IOException ioe)
                {
                    ioe.printStackTrace();
                }
            }
            listener.notifyClosed(this);
        }
        
        private void handleRequest(String request) throws IOException
        {
            switch(request)
            {
            case VocareAPI.CREATE_ACCOUNT:
                handleCreateAccount();
                break;
            case VocareAPI.LOGOUT_ACCOUNT:
                account = null;
                user = null;
                break;
            case VocareAPI.LOGIN_ACCOUNT:
                handleLoginAccount();
                break;
            }
            write(VocareAPI.END);
        }
        
        private void handleLoginAccount() throws IOException
        {
            String accountName = read();
            String password = read();
            Account acc = db.getAccount(accountName, true);
            if(acc == null)
            {
                write(VocareAPI.FAIL);
            }
            else
            {
                if( Password.checkPassword(password, acc.getHashword()) )
                {
                    account = acc;
                    write(VocareAPI.SUCCESS);
                }
                else
                {
                    write(VocareAPI.FAIL);
                }
            }
        }

        private void handleCreateAccount() throws IOException
        {
            String accountName = read();
            String email = read();
            String password = read();
            try
            {
                account = db.createAccount(accountName, email, password);
            }
            catch(InvalidEmailException iee)
            {
                write(VocareAPI.CREATE_ACCOUNT_INVALIDEMAIL);
            }
            catch(InvalidAccountNameException iane)
            {
                write(VocareAPI.CREATE_ACCOUNT_ACCOUNTNAME_TAKEN);
            }
        }
    }
}
