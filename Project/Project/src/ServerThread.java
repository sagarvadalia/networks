import java.net.*;
import java.io.*;

public class ServerThread extends Thread {
    private Socket socket = null;
    public ServerThread(Socket socket) {
        super("KKMultiServerThread");
        this.socket = socket;
    }
    public void run() {
        String directory="E:\\contact.html";
        //File file = new File(directory);
        try  {
            //get the clients socket input
            DataInputStream is= new DataInputStream(socket.getInputStream());
            //connect to client's socket output
            OutputStream os= socket.getOutputStream();
            //get client socket's data
            DataOutputStream dos=new DataOutputStream(os);
            String userinput= is.readUTF();
            //We pass in the request to a protocol
            httpProtocol http=new httpProtocol(userinput);
            //System.out.println("Client message:\n" + userinput);
            if(http.validate()==200) {
                dos.writeUTF("HTTP/1.0 200 OK \r\n");
                File file = new File(directory);
                FileInputStream fis= new FileInputStream(file);
                //Read file contents into contents array
                byte contents[]= new byte[5000];// Random size as we dont know the size of each file
                //long fileLength= file.length();
                //long current=0;
                BufferedInputStream bis= new BufferedInputStream(fis);
                fis.read(contents,0,contents.length);
                os.write(contents,0,contents.length);
                bis.close();
                fis.close();
                dos.flush();
            }
            else if(http.validate()==404){
                dos.writeUTF("HTTP/1.0 404 Not Found");
                dos.flush();
            }
            else if(http.validate()==400){
                dos.writeUTF("HTTP/1.0 400 Bad Request");
                dos.flush();
            }
           /* //
            File file = new File(directory);
            FileInputStream fis= new FileInputStream(file);
            BufferedInputStream bis= new BufferedInputStream(fis);
            //Get the sockets output
            OutputStream os= socket.getOutputStream();

            //Read file contents into contents array
            byte contents[]= new byte[5000];// Random size as we dont know the size of each file
            long fileLength= file.length();
            long current=0;
            fis.read(contents,0,contents.length);
            os.write(contents,0,contents.length);
            System.out.println("File sent successfully");*/

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}