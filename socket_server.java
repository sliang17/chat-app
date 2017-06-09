package com.company;
import java.io.*;
import java.net.*;

/**
 * Created by Dan on 6/5/2017.
 */
public class socket_server {
    public static void main(String[] args) throws Exception
    {
        socket_server server = new socket_server();
        server.run();
    }

    public void run() throws Exception
    {
        ServerSocket ser_sock = new ServerSocket(444);
        Socket sock = ser_sock.accept();
        InputStreamReader IR = new InputStreamReader(sock.getInputStream());
        BufferedReader BR = new BufferedReader(IR);

        String message = BR.readLine();
        System.out.println(message);

        if(message != null)
        {
            PrintStream PS = new PrintStream(sock.getOutputStream());
            PS.println("message received");
        }
    }
}
