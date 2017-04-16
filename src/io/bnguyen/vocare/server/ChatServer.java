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
import io.bnguyen.vocare.data.InvalidEmailException;
import io.bnguyen.vocare.data.InvalidNameException;
import io.bnguyen.vocare.data.Password;
import io.bnguyen.vocare.data.User;
import io.bnguyen.vocare.server.db.Database;

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
    
    // remove this in production
    public Database getDB()
    {
        return db;
    }
    
    public ServerSocketListener getListener()
    {
        return listener;
    }
    
    public void run()
    {
        listener = new ServerSocketListener();
        Thread listenerThread = new Thread(listener);
        listenerThread.start();
    }
    
    public void shutdown()
    {
        listener.shutdown();
        try
        {
            serverSocket.close();
        }
        catch(IOException ioe)
        {
            ioe.printStackTrace();
        }
    }
    
    public static void main(String[] args)
    {
        ChatServer server = new ChatServer();
        server.run();
    }
    
    public class ServerSocketListener implements Runnable
    {
        private ArrayList<ClientHandler> clientHandlers;
        private ArrayList<Thread> clientHandlersThreads;
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
                /*
                for(Thread t : clientHandlersThreads)
                {
                    t.interrupt();
                }
                */
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
        
        public ClientHandler[] getHandlers()
        {
            ClientHandler[] handlers = new ClientHandler[clientHandlers.size()];
            return clientHandlers.toArray(handlers);
        }
    }
    
    public class ClientHandler implements Runnable
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
        
        public boolean isAuthenticated()
        {
            return (account != null);
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
            case VocareAPI.CREATE_USER:
                handleCreateUser();
                break;
            }
            write(VocareAPI.END);
        }
        
        private void handleCreateUser() throws IOException
        {
            String userName = read();
            String firstName = read();
            String lastName = read();
            String phone = read();
            String email = read();
            try
            {
                User newUser = db.createUser(account, userName, firstName, lastName, phone, email);
                if(account.getUsers().length == 1)
                {
                    user = newUser;
                }
            }
            catch(InvalidNameException ine)
            {
                write(VocareAPI.CREATE_USER_USERNAME_TAKEN);
            }
        }
        
        private void handleLoginAccount() throws IOException
        {
            String accountName = read();
            String password = read();
            Account acc = db.getAccounts().findByAccountName(accountName);
            if(acc == null)
            {
                write(VocareAPI.FAIL);
            }
            else
            {
                if( Password.checkPassword(password, acc.getHashword()) )
                {
                    account = acc;
                    // if only one user, auto select
                    if(acc.getUsers().length == 1)
                    {
                        user = acc.getUsers()[0];
                    }
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
            catch(InvalidNameException iane)
            {
                write(VocareAPI.CREATE_ACCOUNT_ACCOUNTNAME_TAKEN);
            }
        }
    }
}
