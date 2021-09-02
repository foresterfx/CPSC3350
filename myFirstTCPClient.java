import java.net.*;  // for Socket
import java.io.*;   // for IOException and Input/OutputStream
import java.util.Scanner; // User Input Scanner
import java.util.Date; // Local Machine Time for Round Trip tracking

public class myFirstTCPClient {

   public static void main(String[] args) throws IOException {
   
      if ((args.length < 1) || (args.length > 2))  // Test for correct # of args
         throw new IllegalArgumentException("Parameter(s): <Server> [<Port>]");
   
      String server = args[0];
      // Server Name or IP address
   
      int servPort = (args.length == 2) ? Integer.parseInt(args[1]) : 7;
   
      Scanner scan = new Scanner(System.in);
      
      System.out.println("\nPress Ctrl + C to abort.");
      while(true) {
         // Create socket that is connected to server on specified port
         Socket socket = new Socket(server, servPort);
         System.out.print("\nInput Message: ");	
         String msg = scan.nextLine();
      	// Convert input String to bytes using the default character encoding
      
         PrintWriter pr = new PrintWriter(socket.getOutputStream());
         long startTime = System.currentTimeMillis(); 
         System.out.println("\nConnected to Server: " + server 
            + "\nOn Port: " + servPort + "\n\nSending echo string...\n");
      
         pr.println(msg);  // Send the string to the server
         pr.flush();
      	
         InputStreamReader in = new InputStreamReader(socket.getInputStream());
         BufferedReader br = new BufferedReader(in);
         String rcvMsg = br.readLine();
         long endTime = System.currentTimeMillis();
         long roundTripTime = endTime - startTime;
      
         System.out.println("Received: " + rcvMsg);
         System.out.println("Round Trip Time: " + String.valueOf(roundTripTime) + "ms");
         socket.close();  // Close the socket and its streams
      }
   }
}