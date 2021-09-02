import java.net.*;  // for Socket, ServerSocket, and InetAddress
import java.io.*;   // for IOException and Input/OutputStream

public class myFirstTCPServer {

   private static final int BUFSIZE = 32;   // Size of receive buffer

   public static void main(String[] args) throws IOException {
   
      if (args.length != 1)  // Test for correct # of args
         throw new IllegalArgumentException("Parameter(s): <Port>");
   
      int servPort = Integer.parseInt(args[0]);
   
      // Create a server socket to accept client connection requests
      ServerSocket servSock = new ServerSocket(servPort);
   
      int msgSize;   // Size of received message
      byte[] byteBuffer = new byte[BUFSIZE];  // Receive buffer
      System.out.println("\nAwaiting Client Connection..."
         + "\nPress Ctrl + C to abort.");
      for (;;) { // Run forever, accepting and servicing connections
         Socket clntSock = servSock.accept();     // Get client connection
      
         System.out.println("\nHandling Client: " +
            clntSock.getInetAddress().getHostAddress() 
            + "\nOn Port: " + clntSock.getPort());
      
         InputStreamReader in = new InputStreamReader(clntSock.getInputStream());
         BufferedReader bf = new BufferedReader(in);
         String msg = bf.readLine();
         System.out.println("\nRecieved message: " + msg
            + "\nFrom Client at: " + clntSock.getInetAddress().getHostAddress()
            + "\nOn port: " + clntSock.getPort());
         StringBuilder sendmsg = new StringBuilder();
         sendmsg.append(msg);
         sendmsg.reverse();
         msg = sendmsg.toString();
         System.out.println("\nReversed message: " + msg);
         
         PrintWriter pr = new PrintWriter(clntSock.getOutputStream());
         pr.println(msg);
         pr.flush();
         clntSock.close();  // Close the socket.  We are done with this client!
      }
      /* NOT REACHED */
   }
}