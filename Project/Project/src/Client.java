import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class Client {
    public static void main(String [] args) throws IOException {
        //Prompt the user to enter an address
        System.out.println("Enter the file address (ex. folder/text.txt)");
        //Get the input from the keyboard in the form file/text.txt for directory
        BufferedReader std= new BufferedReader(new InputStreamReader(System.in));
        //A string where the directories will be checked
        String userInput;
        while((userInput=std.readLine()) !=null){
            //Create the request
            String request= "GET "+userInput+ " HTTP/1.0\r\nHost: www.someschool.edu\r\nConnection: close User-agent: Personal/4.0\r\nAccept: text/html, image/gif,image/jpeg\r\nAccept-language:fr\r\n(extra carriage return + line feed)";
            //initiate a socket
            Socket socketClient= new Socket("localhost",5000);
            //connect client's socket input to server socket output
            OutputStream os= socketClient.getOutputStream();
            //prepared data to be output or sent to the client
            DataOutputStream out=new DataOutputStream(os);
            //connect server socket output to client's socket input
            DataInputStream in=new DataInputStream(socketClient.getInputStream());
            //write the data and send it
            out.writeUTF(request);
            out.flush();
            String data= in.readUTF();
            //Parse the response and determine whether the file exists or not
            System.out.println(data);
            String [] message =data.split(" ");
            //System.out.println(message[1]);
            if(message[1].compareTo("200")!=0) {
                byte contents[] = new byte[5000];
                //Initialize the Fileoutputstream to the outputs files full path
                FileOutputStream fos = new FileOutputStream("E:\\contactReceived.txt");//name the file and create it
                BufferedOutputStream bos = new BufferedOutputStream(fos); //
                InputStream is = socketClient.getInputStream(); //will catch the stream sent by the server
                //No of the bytes read in one read() call
                int bytesRead = 0;
                is.read(contents, 0, contents.length);
                fos.write(contents, 0, contents.length);
                fos.close();
                bos.close();
            }
            socketClient.close();
            //*System.out.println("Client: "+in.read)*//*
        }
    }
        /*byte contents[]= new byte[5000];

        //Initialize the Fileoutputstream to the outputs files full path
        FileOutputStream fos=new FileOutputStream("E:\\contactReceived.html");//name the file and create it
        BufferedOutputStream bos= new BufferedOutputStream(fos); //
        InputStream is= socketClient.getInputStream(); //will catch the stream sent by the server

        //No of the bytes read in one read() call
        int bytesRead=0;
        is.read(contents,0,contents.length);
        fos.write(contents,0,contents.length);
        //bos.flush();*/

}
