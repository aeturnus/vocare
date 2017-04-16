package io.bnguyen.vocare.server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import io.bnguyen.vocare.data.Account;

public class ChatServer
{
    private ServerSocket serverSocket;
    public ChatServer()
    {
        try
        {
            serverSocket = new ServerSocket(8000);
        }
        catch (IOException ioe)
        {
        }
    }
    
    public void execute()
    {
        ServerSocketListener listener = new ServerSocketListener();
        Thread listenerThread = new Thread(listener);
        listenerThread.start();
        while(listener.isRunning())
        {
        }
    }
    
    public static void main(String[] args)
    {
        ChatServer server = new ChatServer();
        server.execute();
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
        //private BufferedReader fromClient;
        private DataInputStream fromClient;
        private BufferedReader  fromClientB;
        private DataOutputStream toClient;
        private ServerSocketListener listener;
        private Account account;
        public ClientHandler(Socket socket, ServerSocketListener listener)
        {
            this.socket = socket;
            this.listener = listener;
            try
            {
                //this.fromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                this.fromClient = new DataInputStream(socket.getInputStream());
                this.fromClientB = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                this.toClient = new DataOutputStream(socket.getOutputStream());
            }
            catch(IOException ioe)
            {
                ioe.printStackTrace();
            }
        }
        
        public void run()
        {
            while(!socket.isInputShutdown())
            {
                try
                {
                    //String clientData = fromClient.readLine();
                    String clientData = fromClientB.readLine();
                    System.out.println(clientData);
                }
                catch(IOException ioe)
                {
                    ioe.printStackTrace();
                }
            }
            listener.notifyClosed(this);
        }
    }
}
