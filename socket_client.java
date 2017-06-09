package com.company;
import jdk.internal.util.xml.impl.Input;

import java.io.*;
import java.net.*;

/**
 * Created by Dan on 6/5/2017.
 */
public class socket_client {
    public static void main(String[] args) throws Exception
    {
        socket_client client = new socket_client();
        client.run();
    }

    public void run() throws Exception
    {
        Socket sock = new Socket("localhost",444);
        PrintStream PS = new PrintStream(sock.getOutputStream());
        PS.println("hello");

        InputStreamReader IR = new InputStreamReader(sock.getInputStream());
        BufferedReader BR = new BufferedReader(IR);

        String message = BR.readLine();
        System.out.println(message);
    }
}
