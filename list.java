package com.company;

import java.io.*;
//import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Dan on 6/6/2017.
 */
class Node
{
    protected Node next;
    private String name;
    private String passwords;

    public Node(String name,String pass)
    {
        this.name = name;
        this.passwords = pass;
        this.next = null;
    }

    public void setNext(Node n)
    {
        this.next = n;
    }

    public String getName()
    {
        return name;
    }

    public String getPasswords()
    {
        return passwords;
    }
}

public class list {
    private Node head;
    //protected static Scanner input = null;



    public list()
    {
        this.head = null;
    }

    public int read_data() throws IOException
    {
        File infile = new File("C:\\Users\\Dan\\IdeaProjects\\chat app\\src\\com\\company\\data.txt");
        Scanner sc = new Scanner(infile);
        while (sc.hasNextLine()) {
            String string = sc.nextLine();
            String[] parts = string.split(";");
            String name = parts[0];
            String passwords = parts[1];
            //System.out.print(name);
            //System.out.print(passwords);
            //test();
            insert(name,passwords);
            System.out.println("account load in successfully");
        }
        sc.close();
        //display(head);

        return 1;
    }

    public void test()
    {
        System.out.println("test");
    }

    public void display()
    {
        display(head);
    }

    private void display(Node head)
    {
        //System.out.print("display");
        if(head == null)
            return;
        System.out.println("name:  "+head.getName()+" passwords: "+head.getPasswords());
        display(head.next);
    }

    /*public void insert(String name, String passwords)
    {
        System.out.println(name+passwords);
        insert(head,name,passwords);
    }*/

    public void insert(String n, String p)
    {
        //
        if(head == null)
        {
            //System.out.println(n);
            head = new Node(n,p);
            //System.out.println(head.getName());
            //head.setNext(null);
        }
        else
        {
            Node current = head;
            //System.out.println(n+p);
            while(current.next != null)
            {
                current = current.next;
            }
            Node temp = new Node(n,p);
            current.next = temp;
            temp.next = null;
           /*Node temp = new Node(n,p);
           temp.setNext(head);
           head = temp;*/
        }
    }

    public boolean check(String name,String passwords)
    {
        return check(head,name,passwords);
    }

    private boolean check(Node head, String name, String passwords)
    {
        if(head == null)
            return false;
        if(head.getName().equals(name))
        {
            if(head.getPasswords().equals(passwords))
            {
                return true;
            }
        }
        return check(head.next,name,passwords);
    }

}
