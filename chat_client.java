package com.company;
import java.net.*;
import java.io.*;
import java.util.Scanner;
import javax.swing.JOptionPane;
/**
 * Created by Dan on 6/6/2017.
 */
public class chat_client implements Runnable{
    Socket sock;
    Scanner input;
    Scanner send = new Scanner(System.in);
    PrintWriter out;

    public chat_client(Socket x)
    {
        this.sock = x;
    }

    public void run()
    {
        try
        {
            try
            {
                input = new Scanner(sock.getInputStream());
                out = new PrintWriter(sock.getOutputStream());
                out.flush();
                CheckStream();
            }
            finally {
                sock.close();
            }
        }
        catch (Exception x)
        {
            System.out.print(x);
        }
    }

    public void disconnect() throws Exception
    {
        out.println(chat_client_GUI.UserName+" has disconnected");
        out.flush();
        sock.close();
        JOptionPane.showMessageDialog(null,"you disconnect");
        System.exit(0);
    }

    public void CheckStream()
    {
        while(true)
        {
            receive();
        }
    }

    public void receive()
    {
        if(input.hasNext())
        {
            String message = input.nextLine();

            if(message.contains("#?!"))
            {
                String temp1 = message.substring(3);
                temp1 = temp1.replace("[","");
                temp1 = temp1.replace("]","");

                String[] CurrentUsers = temp1.split(", ");

                chat_client_GUI.JL_online.setListData(CurrentUsers);
            }
            else
            {
                chat_client_GUI.TA_conversation.append(message+"\n");
                //System.out.println(message);
            }
        }
    }

    public void send(String name,String x)
    {
        if(!name.equals("P")) {
            out.println(name + "TO" + chat_client_GUI.UserName + ":" + x);
        }
        else
            out.println(name+chat_client_GUI.UserName+":"+x);
        out.flush();
        chat_client_GUI.TF_message.setText("");
    }


}
