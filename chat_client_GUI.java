package com.company;

import jdk.nashorn.internal.scripts.JO;
import sun.rmi.runtime.Log;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.PrintWriter;
import java.net.Socket;
//import java.util.LinkedList;
import java.util.Scanner;

/**
 * Created by Dan on 6/6/2017.
 */
public class chat_client_GUI {
    private static chat_client chatClient;
    public static String UserName = "Anonymous";
    //public static LinkedList a_list;
    private static list a_list = new list();
    //public static Scanner input;

    private static JFrame MainWindow = new JFrame();
    private static JFrame PrivateWindow = new JFrame();
    private static JButton B_connect = new JButton();
    private static JButton B_disconnect = new JButton();
    private static JButton B_send = new JButton();
    private static JButton B_history = new JButton();
    private static JLabel L_message = new JLabel("message: ");
    public static JTextField TF_message = new JTextField(20);
    private static JLabel L_conversation = new JLabel();
    public static JTextArea TA_conversation = new JTextArea();
    private static JScrollPane SP_conversation = new JScrollPane();
    private static JLabel L_online = new JLabel();
    public static JList JL_online = new JList();
    private static JScrollPane SP_online = new JScrollPane();
    private static JLabel L_loginAS = new JLabel();
    private static JLabel L_loginBox =new JLabel();

    private static JFrame LoginWindow =  new JFrame();
    private static JTextField TF_usernameBox = new JTextField(20);
    private static JTextField TF_passwordsBox = new JTextField(20);
    private static JButton B_enter = new JButton("Enter");
    private static JButton B_register = new JButton("Register");
    private static JLabel L_enterUsername = new JLabel("Enter username: ");
    private static JLabel L_enterPasswords = new JLabel("Enter passwords: ");
    private static JPanel P_login = new JPanel();



    public static void main(String[] args)
    {
        BuildMainWindow();
        Initialize();
    }


    private static void connect()
    {
        try
        {
            final int port = 444;
            final String host = "localhost";
            Socket sock = new Socket(host,port);
            System.out.println("You connected to: "+host);

            chatClient = new chat_client(sock);

            PrintWriter out = new PrintWriter(sock.getOutputStream());
            out.println(UserName);
            out.flush();

            Thread x = new Thread(chatClient);
            x.start();
        }
        catch(Exception x)
        {
            System.out.print(x);
            JOptionPane.showMessageDialog(null,"Server no responding");
            System.exit(0);
        }
    }


    private static void Initialize()
    {
        try {
            a_list.read_data();
        }
        catch (Exception x) {
            System.out.println(x);
        }
        B_send.setEnabled(false);
        B_disconnect.setEnabled(false);
        B_connect.setEnabled(true);
    }

    //------------------------------------------------------------------------------------------------------

    private static void BuildMainWindow()
    {
        MainWindow.setTitle(UserName+"'s chat box");
        MainWindow.setSize(450,500);
        MainWindow.setLocation(220,180);
        MainWindow.setResizable(false);
        ConfigureMainWindow();
        MainWindow_action();
        MainWindow.setVisible(true);

    }

    private static void BuildLoginWindow()
    {
        LoginWindow.setTitle("Login box");
        LoginWindow.setSize(400,150);
        LoginWindow.setLocation(250,200);
        LoginWindow.setResizable(false);
        P_login = new JPanel();
        P_login.add(L_enterUsername);
        ///////////////////////////////////////////////////////////////
        P_login.add(TF_usernameBox);
        //////////////////////////////////////////////////////////////password
        P_login.add(L_enterPasswords);
        P_login.add(TF_passwordsBox);
        //////////////////////////////////////////////////////////////
        P_login.add(B_enter);
        P_login.add(B_register);
        LoginWindow.add(P_login);

        Login_action();
        LoginWindow.setVisible(true);
    }

    private static void BuildPrivateWindow(String name)
    {
        PrivateWindow.setTitle("private chat box");
        PrivateWindow.setSize(450,500);
        PrivateWindow.setLocation(22,180);
        PrivateWindow.setResizable(false);
        //PrivateWindow.add(B_disconnect);
        //PrivateWindow.add(TA_conversation);
        ConfigurePrivate();
        PrivateWindow_action(name);
        PrivateWindow.setVisible(true);
        //MainWindow.setVisible(false);
    }

    //------------------------------------------------------------------------------------------------------------

    //Configurations

    private static void ConfigureMainWindow()
    {
        MainWindow.setBackground(new java.awt.Color(255,255,255));
        MainWindow.setSize(500,320);
        MainWindow.getContentPane().setLayout(null);

        B_send.setBackground(new java.awt.Color(0,0,255));
        B_send.setForeground(new java.awt.Color(255,255,255));
        B_send.setText("send");
        MainWindow.getContentPane().add(B_send);
        B_send.setBounds(250, 40, 81, 25);

        B_disconnect.setBackground(new java.awt.Color(0,0,255));
        B_disconnect.setBackground(new java.awt.Color(0,0,255));
        B_disconnect.setForeground(new java.awt.Color(255,255,255));
        B_disconnect.setText("disconnect");
        MainWindow.getContentPane().add(B_disconnect);
        B_disconnect.setBounds(10, 40, 110, 25);

        B_connect.setBackground(new java.awt.Color(0,0,255));
        B_connect.setForeground(new java.awt.Color(255,255,255));
        B_connect.setText("connect");
        MainWindow.getContentPane().add(B_connect);
        B_connect.setBounds(130, 40, 110, 25);

        /*B_history.setBackground(new java.awt.Color(0,0,255));
        B_history.setForeground(new java.awt.Color(255,255,255));
        B_history.setText("history");
        MainWindow.getContentPane().add(B_history);
        B_connect.setBounds(0,40,110, 25);*/

        L_message.setText("Message: ");
        MainWindow.getContentPane().add(L_message);
        L_message.setBounds(10,10,60,20);

        TF_message.setForeground(new java.awt.Color(0,0,255));
        TF_message.requestFocus();
        MainWindow.getContentPane().add(TF_message);
        TF_message.setBounds(70,4,260,30);

        L_conversation.setHorizontalAlignment(SwingConstants.CENTER);
        L_conversation.setText("Conversation");
        MainWindow.getContentPane().add(L_conversation);
        L_conversation.setBounds(100,70,40,16);

        TA_conversation.setColumns(20);
        TA_conversation.setFont(new java.awt.Font("Tahoma", Font.PLAIN,12));
        TA_conversation.setForeground(new java.awt.Color(0,0,255));
        TA_conversation.setLineWrap(true);
        TA_conversation.setRows(5);
        TA_conversation.setEditable(false);

        SP_conversation.setHorizontalScrollBarPolicy(
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        SP_conversation.setVerticalScrollBarPolicy(
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        SP_conversation.setViewportView(TA_conversation);
        MainWindow.getContentPane().add(SP_conversation);
                //ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        SP_conversation.setViewportView(TA_conversation);
        MainWindow.getContentPane().add(SP_conversation);
        SP_conversation.setBounds(10,90,330,180);

        L_online.setHorizontalAlignment(SwingConstants.CENTER);
        L_online.setText("Currently online");
        L_online.setToolTipText("");
        MainWindow.getContentPane().add(L_online);
        L_online.setBounds(350,70,130,16);

        JL_online.setForeground(new java.awt.Color(0,0,255));

        SP_online.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        SP_online.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        SP_online.setViewportView(JL_online);
        MainWindow.getContentPane().add(SP_online);
        SP_online.setBounds(350,90,130,180);

        L_loginAS.setFont(new java.awt.Font("Tahoma",Font.PLAIN,12));
        L_loginAS.setText("currently logged in as");
        MainWindow.getContentPane().add(L_loginAS);
        L_loginAS.setBounds(348,0,140,15);

        L_loginBox.setHorizontalAlignment(SwingConstants.CENTER);
        L_loginBox.setFont(new java.awt.Font("Tahoma",Font.BOLD,12));
        L_loginBox.setForeground(new java.awt.Color(255,0,0));
        L_loginBox.setBorder(BorderFactory.createLineBorder(new java.awt.Color(0,0,0)));
        MainWindow.getContentPane().add(L_loginBox);
        L_loginBox.setBounds(340,17,150,20);
    }

    //clone from ConfigureMainWindow
    private static void ConfigurePrivate()
    {
        PrivateWindow.setBackground(new java.awt.Color(255,255,255));
        PrivateWindow.setSize(500,320);
        PrivateWindow.getContentPane().setLayout(null);

        B_send.setBackground(new java.awt.Color(0,0,255));
        B_send.setForeground(new java.awt.Color(255,255,255));
        B_send.setText("send");
        PrivateWindow.getContentPane().add(B_send);
        B_send.setBounds(250, 40, 81, 25);

        B_disconnect.setBackground(new java.awt.Color(0,0,255));
        B_disconnect.setBackground(new java.awt.Color(0,0,255));
        B_disconnect.setForeground(new java.awt.Color(255,255,255));
        B_disconnect.setText("disconnect");
        MainWindow.getContentPane().add(B_disconnect);
        B_disconnect.setBounds(10, 40, 110, 25);

      /*  B_connect.setBackground(new java.awt.Color(0,0,255));
        B_connect.setForeground(new java.awt.Color(255,255,255));
        B_connect.setText("connect");
        MainWindow.getContentPane().add(B_connect);
        B_connect.setBounds(130, 40, 110, 25);

        B_history.setBackground(new java.awt.Color(0,0,255));
        B_history.setForeground(new java.awt.Color(255,255,255));
        B_history.setText("history");
        MainWindow.getContentPane().add(B_history);
        B_connect.setBounds(0,40,110, 25);*/

        L_message.setText("Message: ");
        PrivateWindow.getContentPane().add(L_message);
        L_message.setBounds(10,10,60,20);

        TF_message.setForeground(new java.awt.Color(0,0,255));
        TF_message.requestFocus();
        PrivateWindow.getContentPane().add(TF_message);
        TF_message.setBounds(70,4,260,30);

        L_conversation.setHorizontalAlignment(SwingConstants.CENTER);
        L_conversation.setText("Conversation");
        PrivateWindow.getContentPane().add(L_conversation);
        L_conversation.setBounds(100,70,40,16);

        TA_conversation.setColumns(20);
        TA_conversation.setFont(new java.awt.Font("Tahoma",Font.PLAIN,12));
        TA_conversation.setForeground(new java.awt.Color(0,0,255));
        TA_conversation.setLineWrap(true);
        TA_conversation.setRows(5);
        TA_conversation.setEditable(false);

        SP_conversation.setHorizontalScrollBarPolicy(
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        SP_conversation.setVerticalScrollBarPolicy(
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        SP_conversation.setViewportView(TA_conversation);
        PrivateWindow.getContentPane().add(SP_conversation);
        //ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        SP_conversation.setViewportView(TA_conversation);
        PrivateWindow.getContentPane().add(SP_conversation);
        SP_conversation.setBounds(10,90,330,180);

        L_online.setHorizontalAlignment(SwingConstants.CENTER);
        L_online.setText("Currently online");
        L_online.setToolTipText("");
        PrivateWindow.getContentPane().add(L_online);
        L_online.setBounds(350,70,130,16);

        JL_online.setForeground(new java.awt.Color(0,0,255));

        SP_online.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        SP_online.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        SP_online.setViewportView(JL_online);
        PrivateWindow.getContentPane().add(SP_online);
        SP_online.setBounds(350,90,130,180);

        L_loginAS.setFont(new java.awt.Font("Tahoma",Font.PLAIN,12));
        L_loginAS.setText("currently logged in as");
        PrivateWindow.getContentPane().add(L_loginAS);
        L_loginAS.setBounds(348,0,140,15);

        L_loginBox.setHorizontalAlignment(SwingConstants.CENTER);
        L_loginBox.setFont(new java.awt.Font("Tahoma",Font.PLAIN,12));
        L_loginBox.setForeground(new java.awt.Color(255,0,0));
        L_loginBox.setBorder(BorderFactory.createLineBorder(new java.awt.Color(0,0,0)));
        PrivateWindow.getContentPane().add(L_loginBox);
        L_loginBox.setBounds(340,17,150,20);
    }

    //-------------------------------------------------------------------------------------------------------------

    //window buttons
    private static void MainWindow_action() {
        B_send.addActionListener(
                new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt)
                    {
                        action_b_send("P");
                    }
                }
        );

        B_disconnect.addActionListener(
                new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        action_b_disconnect();
                    }
                }
        );

        B_connect.addActionListener(
                new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        BuildLoginWindow();
                    }
                }
        );

        B_history.addActionListener(
                new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        action_b_history();
                    }
                }
        );

        //double click
        JL_online.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // super.mouseClicked(e);
                if(e.getClickCount() == 2)
                {
                    String name = (String)JL_online.getSelectedValue();
                    BuildPrivateWindow(name);
                }
            }
        });
    }

    private static void Login_action()
    {
        B_enter.addActionListener(
                new java.awt.event.ActionListener()
                {
                   // @Override
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        action_b_enter();
                    }
                }
        );
        B_register.addActionListener(
                new java.awt.event.ActionListener()
                {
                    public void actionPerformed(java.awt.event.ActionEvent evt)
                    {
                        action_b_register();
                    }
                }
        );
    }

    //private window action
    private static void PrivateWindow_action(String name)
    {
        B_send.addActionListener(
                new java.awt.event.ActionListener()
                {
                    public void actionPerformed(java.awt.event.ActionEvent evt)
                    {
                        action_b_send(name);
                    }
                }
        );

        B_disconnect.addActionListener(
                new java.awt.event.ActionListener()
                {
                    public void actionPerformed(java.awt.event.ActionEvent evt)
                    {
                        action_b_disconnect();
                    }
                }
        );
    }

    //----------------------------------------------------------------------------------------------------------------

   //enter button

    private static void action_b_enter()
    {
        if(!TF_usernameBox.getText().equals("") && !TF_passwordsBox.getText().equals(""))
        {
            UserName = TF_usernameBox.getText().trim();
            /////////////////////////////////////////////////////////password
            String pass = TF_passwordsBox.getText().trim();
            a_list.display();
            if(a_list.check(UserName,pass)) {
                ///////////////////////////////////////////////////////////
                L_loginBox.setText(UserName);
                chat_server.CurrentUsers.add(UserName);
                MainWindow.setTitle(UserName + "'s chat box");

                LoginWindow.setVisible(false);
                B_send.setEnabled(true);
                B_disconnect.setEnabled(true);
                B_connect.setEnabled(false);
                connect();
            }
            else
            {
                JOptionPane.showMessageDialog(null,"incorrect passwords");
                /*String name =  input.nextLine();
                String passwords = input.nextLine();
                a_list.insert(name, passwords);*/
            }
        }
        else
        {
            JOptionPane.showMessageDialog(null,"please enter name and passwords");
        }
    }

    //register button
    private static void action_b_register()
    {
        if(!TF_usernameBox.getText().equals("") && !TF_passwordsBox.getText().equals(""))
        {
            UserName = TF_usernameBox.getText().trim();
            String pass = TF_passwordsBox.getText().trim();
            a_list.insert(UserName,pass);
            JOptionPane.showMessageDialog(null,"Succeed!");
            chat_server.CurrentUsers.add(UserName);
            MainWindow.setTitle(UserName + "'s chat box");
            LoginWindow.setVisible(false);
            B_send.setEnabled(true);
            B_disconnect.setEnabled(true);
            B_connect.setEnabled(false);
            connect();
        }
        else
        {
            JOptionPane.showMessageDialog(null,"enter valid username and passwords");
        }
    }


    //send button
    private static void action_b_send(String name)
    {
        if(!TF_message.getText().equals(""))
        {
                chatClient.send(name,TF_message.getText()); //TF_message.getText()
                TF_message.requestFocus();
        }
    }

    //disconnect button
    private static void action_b_disconnect()
    {
        try
        {
            chatClient.disconnect();
        }
        catch (Exception y)
        {
            y.printStackTrace();
        }
    }

    //history button
    private static void action_b_history()
    {

    }
}
