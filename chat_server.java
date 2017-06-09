package com.company;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 * Created by Dan on 6/5/2017.
 */
public class chat_server {
    public static ArrayList<Socket> ConnectionArray = new ArrayList<Socket>();
    public static ArrayList<String> CurrentUsers = new ArrayList<String>();
    public static HashMap<String,Socket> Map = new HashMap<>();

    public static void main(String[] args) throws IOException
    {
        try
        {
            final int port = 444;
            ServerSocket server = new ServerSocket(port);
            System.out.println("waiting for clients");

            while(true)
            {
                Socket sock = server.accept();
                ConnectionArray.add(sock);

                System.out.println("client connect from "+ sock.getLocalAddress().getHostName());

                addUserName(sock);

                chat_server_return chat = new chat_server_return(sock);
                Thread x = new Thread(chat);
                x.start();
            }
        }
        catch (Exception x)
        {
            System.out.print(x);
        }
    }

    public static void addUserName(Socket x)  throws IOException
    {
        Scanner input = new Scanner(x.getInputStream());
        String UserName = input.nextLine();
        CurrentUsers.add(UserName);

        for(int i = 1; i <= chat_server.ConnectionArray.size(); i++)
        {
                Socket temp_sock = (Socket) chat_server.ConnectionArray.get(i-1);
                PrintWriter out = new PrintWriter(temp_sock.getOutputStream());
                //chat_server.Map.put(UserName,x);
                out.println("#?!"+CurrentUsers);
                out.flush();
        }

        //adding clients and sockets hash map
        chat_server.Map.put(UserName,x);
    }
}
