package com.company;
import java.io.*;
import java.net.*;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Dan on 6/5/2017.
 */
public class chat_server_return implements Runnable{
        Socket sock;
        private Scanner input;
        private PrintWriter out;
        String message = "";

        public chat_server_return(Socket x)
        {
            this.sock = x;
        }

        public void checkConnection()throws IOException
        {
            if(!sock.isConnected())
            {
                for(int i = 1; i <= chat_server.ConnectionArray.size(); i++)
                {
                    if(chat_server.ConnectionArray.get(i) == sock)
                    {
                        chat_server.ConnectionArray.remove(i);
                    }
                }

                for(int i = 1; i <= chat_server.ConnectionArray.size(); i++)
                {
                    Socket temp_sock = (Socket) chat_server.ConnectionArray.get(i-1);
                    PrintWriter temp_out = new PrintWriter(temp_sock.getOutputStream());
                    temp_out.println(temp_sock.getLocalAddress().getHostName()+" disconnected");
                    System.out.println(temp_sock.getLocalAddress().getHostName()+" disconnected");
                }
            }
        }

        public void run()
        {
            try{
                try{
                    input = new Scanner(sock.getInputStream());
                    out = new PrintWriter(sock.getOutputStream());

                    while(true)
                    {
                        checkConnection();

                        if(!input.hasNext())
                        {
                            return;
                        }

                        message = input.nextLine();
                        System.out.println("Client said: "+message);


                        //private message
                        if(message.contains("TO"))
                        {
                            String name = message.substring(0,message.indexOf("TO"));
                            Socket temp_sock = (Socket) chat_server.Map.get(name);
                            //Socket temp_sock =(Socket) chat_server.ConnectionArray.get(k);
                            PrintWriter temp_out = new PrintWriter(temp_sock.getOutputStream());
                            temp_out.println(message);
                            temp_out.flush();
                            System.out.println("send to: "+temp_sock.getLocalAddress().getHostName());
                        }

                        if(message.charAt(0)=='P'){
                        //public message
                            for (int i = 1; i <= chat_server.ConnectionArray.size(); i++) {
                                Socket temp_sock = (Socket) chat_server.ConnectionArray.get(i - 1);
                                PrintWriter temp_out = new PrintWriter(temp_sock.getOutputStream());
                                temp_out.println(message);
                                temp_out.flush();
                                System.out.println("send to: " + temp_sock.getLocalAddress().getHostName());
                            }
                        }
                    }
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
}
