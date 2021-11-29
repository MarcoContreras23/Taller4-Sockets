/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;


public class SocketController {
    
    private Thread theThread = null;
    private Socket theSocket = null;
    private PrintWriter theOut = null;
    private BufferedReader theIn = null;
    private String nameClient = null;

    private void initStream(Socket newSocket)
            throws IOException {

        OutputStream outStream = null;
        InputStream inStream = null;

        outStream = newSocket.getOutputStream();
        inStream = newSocket.getInputStream();

        theOut = new PrintWriter(outStream, true);
        theIn = new BufferedReader(new InputStreamReader(inStream, 
                StandardCharsets.UTF_8));
    }

    
    public SocketController(String newHostname, int newPort) 
            throws IOException {
        
        theSocket = new Socket(newHostname, newPort);
        initStream(theSocket);
    }
    
    public boolean isActive(){
        return !this.theSocket.isClosed();
    }
   
    public Thread start(Runnable runnableBlock){
        
        theThread = new Thread(runnableBlock);
        theThread.start();
        
        return theThread;
    }
    
    public String readText(){
        
        try {
            return theIn.readLine();
        }
        catch (IOException ex) {
            return null;
        }
    }
    
    public void writeText(String newText){
        if(newText.toUpperCase().trim().startsWith("REGISTER")){
            this.nameClient = newText.substring(9).trim().toLowerCase();
        }
        theOut.println(newText);
    }
    
     public void close() 
            throws IOException{
        
        theSocket.close();
    }
     
    public Socket getSocket(){
        
        return theSocket;
    }

    public Thread getThread() {
        
        return theThread;
    }

    public String getNameClient() {
        return nameClient;
    }

    public void setNameClient(String nameClient) {
        this.nameClient = nameClient;
    }
    
    
}
