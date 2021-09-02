import java.net.*;  // for Socket, ServerSocket, and InetAddress
import java.io.*;   // for IOException and Input/OutputStream

public class myFirstUDPServer {

   private static final int ECHOMAX = 255;  // Maximum size of echo datagram

   public static void main(String[] args) throws IOException {
      if (args.length != 1)  // Test for correct argument list
         throw new IllegalArgumentException("Parameter(s): <Port>");
   
      int servPort = Integer.parseInt(args[0]);
      
      DatagramSocket socket = new DatagramSocket(servPort);
      DatagramPacket packet = new DatagramPacket(new byte[ECHOMAX], ECHOMAX);
      
      System.out.println("\nAwaiting Client Connection..."
         + "\nPress Ctrl + C to abort.\n");
      byte[] byteBuffer = new byte[ECHOMAX];  // Receive buffer

      for (;;) {  // Run forever, receiving and echoing datagrams
         socket.receive(packet);     // Receive packet from client
         System.out.println("Handling Client: "
            + packet.getAddress().getHostAddress() 
            + "\nOn port: " + packet.getPort());
            
         //InputStreamReader in = new InputStreamReader();
         //BufferedReader bf = new BufferedReader(packet.getData());
         String msg = new String(packet.getData());
         msg = msg.trim();
         System.out.println("\nRecieved message: " + msg
            + "\nFrom Client at: " + packet.getAddress().getHostAddress()
            + "\nOn port: " + packet.getPort());
            
         StringBuilder sendmsg = new StringBuilder();
         sendmsg.append(msg);
         sendmsg.reverse();
         msg = sendmsg.toString();
         System.out.println("\nReversed message: " + msg);
         
         byteBuffer = msg.getBytes();
         DatagramPacket reverse = new DatagramPacket(byteBuffer, byteBuffer.length, packet.getAddress(), packet.getPort());
         socket.send(reverse);
         packet.setLength(ECHOMAX); // Reset length to avoid shrinking buffer
      }
      /* NOT REACHED */
   }
}