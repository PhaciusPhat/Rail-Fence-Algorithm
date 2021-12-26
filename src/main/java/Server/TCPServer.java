/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server;
import java.io.*;
import java.net.*;
import java.util.*;
/**
 *
 * @author yusuk
 */
public class TCPServer {
    public static void main(String[] args)  throws IOException  {
        ServerSocket server = new ServerSocket(8888);
        System.out.println("Server đang đợi kết nối");
        while (true) { 
        Socket sktrunggian = server.accept();
        System.out.println("Client kết nối");
        DataInputStream din = new DataInputStream(sktrunggian.getInputStream());
        DataOutputStream dout = new DataOutputStream(sktrunggian.getOutputStream());
        String str = din.readUTF();
        String[] string = str.split("=-key-=");
        int key = Integer.parseInt(string[1]);
        String rs1 = decryptRF(string[0], key);
        rs1 = rs1 + findSecondWord(rs1);
        dout.writeUTF(rs1);
        sktrunggian.close();
        }
        
    }
    
    
    public static String decryptRF(String str, int key){
//        String str = "dnhaw-eedtees al-f  tl-";
        String temp = str.replaceAll("=-=", "");
//        System.out.println(temp.length());
//        return;
        String[] rs = str.split("=-=");
        char[][] arr = new char[key][temp.length()];
        for (int i = 0; i < key; i++) {
            char[] chars = rs[i].toCharArray();
            arr[i][i] = chars[0];
            int pos = 0;
            int column = i;
            if(i==0 || i==(key-1)){
                while(true){
                    column += 2*key-2;
                    ++pos;
                    if(column >= temp.length()){
                        break;
                    }
                    arr[i][column] = chars[pos];
                }
            }
            else{
                while(true){
                    column += 2*(key-i)-2;
                    ++pos;
                    if(column >= temp.length()){
                        break;
                    }
                    arr[i][column] = chars[pos];
                    column = column + 2*i;
                    ++pos;
                    if(column >= temp.length()){
                        break;
                    }
                    arr[i][column] = chars[pos];
                }
            }
        }
//        for (int i = 0; i < key; i++) {
//            for (int j = 0; j < temp.length(); j++) {
//                if(arr[i][j] != 0){
//                    System.out.print(arr[i][j]);
//                } else{
//                    System.out.print(".");
//                }
//            }
//            System.out.println();
//        }
        for (int i = 1; i < key; i++) {
            for (int j = i; j < temp.length(); j++) {
                if(arr[i][j] != 0){
                    arr[0][j] = arr[i][j];
                }
            }
        }
        String result = "";
        for (int i = 0; i < 1; i++) {
            for (int j = i; j < temp.length(); j++) {
               result += arr[i][j];
            }
        }
        return result;
    }
    
    public static String findSecondWord(String str){
        char[] arr = str.toCharArray();
        ArrayList<Character> chars = new ArrayList<Character>();
        ArrayList<Integer> numbers = new ArrayList<Integer>();
        for (int i = 0; i < arr.length-1; i++) {
            int number = 0;
            if(arr[i] != '='){
                chars.add(arr[i]);
                ++number;
                for (int j = i+1; j < arr.length; j++) {
                    if(arr[j] == arr[i]){
                        arr[j] = '=';
                        ++number;
                    }
                }
            }
            if(number!=0){
                numbers.add(number);
            }
        }
//        for (int i = 0; i < numbers.size(); i++) {
//            System.out.println(chars.get(i) + ": " + numbers.get(i));
//        }
        //lon nhat
        int pos1 = 0;
        int max1 = numbers.get(0);
        //lon nhi`
        int pos2 = 1;
        int max2 = numbers.get(1);
        if(max1 < max2){
            int t;
            t = max1;
            max1 = max2;
            max2 = t;
            pos2 = 0;
            pos1 = 1;
        }
        for (int i = 2; i < numbers.size(); i++) {
            if(numbers.get(i) > max1){
                max2 = max1;
                max1 = numbers.get(i);
                pos2 = pos1;
                pos1 = i;
            }
            if(numbers.get(i) > max2 && numbers.get(i) < max1){
                max2 = numbers.get(i);
                pos2 = i;
            }
        }
        String result = "";
        for (int i = 0; i < numbers.size(); i++) {
            if(numbers.get(i) == numbers.get(pos2)){
                result += String.valueOf(chars.get(i) + " ");
            }
        }
        result = "=findSecondWord=" + result + "=findSecondWord=" + max2;
        return result;
    }
    
}
    