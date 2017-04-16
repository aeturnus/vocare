package io.bnguyen.vocare.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient
{

    public ChatClient()
    {
    }
    
    public static void main(String[] args)
    {
        try
        {
            Socket socket = new Socket("localhost",8000);
            PrintWriter toServer = new PrintWriter(socket.getOutputStream(),true);
            boolean running = true;
            while(running)
            {
                toServer.println("Hello world!");
                Thread.sleep(1000);
            }
            socket.close();
        }
        catch(IOException ioe)
        {
        }
        catch(Exception e)
        {
        }
    }
}
