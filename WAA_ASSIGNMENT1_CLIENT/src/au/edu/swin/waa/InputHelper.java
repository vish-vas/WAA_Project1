package au.edu.swin.waa;

import java.util.Scanner;

/**
 *
 * @author Vov
 */
public class InputHelper 
{
    private static Scanner in = new Scanner(System.in);
    
    /**
     * Method getValidInt used to get a validated integer input from the user
     *
     * @param a validation limit
     * @return returns validated integer
     */
    public static int getValidInt(int a, String msg)
    {
        Integer i = 0;
        
        while(true)
        {
            try
            {
                System.out.print(msg+ " ");
                i = Integer.parseInt(in.nextLine());
                if(i>0&&i<=a)
                {
                    break;
                }
                else
                {
                    System.out.println("Please select a number from the shown list.");
                }
            }
            catch(Exception e)
            {
                System.out.println("Not a valid Integer");
            }
        }
        return i;  
    }
    
     /**
     * Method getString used to get a string from user
     *
     * @param a
     * @param b
     * @param msg custom message to identify the kind of input required
     * @return returns string data
     */
    public static String getNumbers(int arr [], String msg)
    {
        int a = arr[0];
        int b = arr[1];
        String text="";
        while(true)
        {
            System.out.print(msg+ " ");
            String temp = in.next();
            if(temp.length()>=a && temp.length()<=b && temp.matches("[0-9]+"))
            {
                text=temp;
                break;
            }
            else
                System.out.println("The Field has a requirement of minimum "+a+" and maximum "+b+" Numbers");
        }  
        return text;
    }
    
    /**
     * Method checkString used to check is a string is alphabetic only or not.
     *
     * @param a
     * @param b
     * @param msg
     * @return returns true or false based upon result of validation check.
     */
    public static String getString(int arr[],String msg)
    {
        int a = arr[0];
        int b = arr[1];
        String text="";
        while(true)
        {
            System.out.print(msg+ " ");
            String temp = in.next();
            if(temp.length()>=a && temp.length()<=b && temp.matches("[a-zA-Z]+"))
            {
                text=temp;
                break;
            }
            else
                System.out.println("The Field has a requirement of minimum "+a+" and maximum "+b+" Characters");
        }  
        return text;
    }
    
    /**
     *
     * @param a
     * @param b
     * @param msg
     * @return
     */
    public static String getMixedInputString(int arr[], String msg)
    {
        int a = arr[0];
        int b = arr[1];
        String text="";
        while(true)
        {
            System.out.print(msg+ " ");
            String temp = in.nextLine();
            if(temp.length()>=a && temp.length()<=b)
            {
                text = temp;
                break;
            }
            else
                System.out.println("The Field has a requirement of minimum "+a+" and maximum "+b+" characters and/or numbers.");
        }  
        return text;
    }
    
    public static boolean yesOrNoInput(String msg)
    {
        while(true)
        {
            System.out.print(msg+" ");
            String temp = in.next();
            if(temp.equalsIgnoreCase("y"))
                return true;
            if(temp.equalsIgnoreCase("n"))
                return false;
        }
    }
    
    public static void waitForKeypress()
    {
        System.out.println("--Press Enter key to continue.--");
        in.nextLine();
    }
    
    
    
    public static String getInputString(String msg)
    {
        
        
        
            System.out.print(msg+ " ");
            String text = in.nextLine();
            
            
        return text;
    }
    
    public static void print(String msg)
    {
    	System.out.println(msg);
    }
}