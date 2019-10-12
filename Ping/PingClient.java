import java.io.*;
import java.net.*;
import java.util.*;

/*
 * Server to process ping requests over UDP.
 */
public class PingClient {


   public static void main(String[] args) throws Exception {
      // Get command line argument.
      if ((args.length < 2) || (args.length > 3)){ // Test for correct # of args 
         System.out.println("Required arguments: port");
         return;
      }


      // Create a datagram socket for receiving and sending UDP packets
      // through the port specified on the command line.
      DatagramSocket socket = new DatagramSocket();
      int servPort = Integer.parseInt(args[1]);
      InetAddress address = InetAddress.getByName(args[0]);
      socket.setSoTimeout(1000);

      float maxTime=0;
      float minTime= Float.MAX_VALUE;
      float totalTime=0;
      int packetsRecieved=0;

      // Processing loop.
      for(int i=0; i<10; i++) 
      {
        long timeBefore = System.currentTimeMillis();
        String msg = "PING " + i + " " + timeBefore + "\n";
        byte[] bytemsg = msg.getBytes();
        DatagramPacket packet = new DatagramPacket(bytemsg, bytemsg.length, address, servPort);
        socket.send(packet);

        DatagramPacket response = new DatagramPacket(new byte[1024], 1024);

        try {
            socket.receive(response);
        } catch (SocketTimeoutException e) {
            continue;
        }

        long timeAfter = System.currentTimeMillis();
        int packetnumber = splitData(response);

        long newTime = System.currentTimeMillis() - timeBefore;

        if(newTime < minTime)
            minTime = newTime;
 
        if(newTime > maxTime)
            maxTime = newTime;

        totalTime+=newTime;
        packetsRecieved++;

        System.out.println("Pakcet #" + packetnumber + " RTT: " + newTime + "ms");
       }

        System.out.println("Max time: " + maxTime + "ms");
        System.out.println("Min time: " + minTime + "ms");
        System.out.println("Average time: " + totalTime / packetsRecieved + "ms");

      }
   
      

   private static int splitData(DatagramPacket request) throws Exception {
        byte[] buf = request.getData();

        ByteArrayInputStream bais = new ByteArrayInputStream(buf);
        InputStreamReader isr = new InputStreamReader(bais);
        BufferedReader br = new BufferedReader(isr);

        String line = br.readLine();
        String lineString = new String(line);
        String[] split = lineString.split(" ");
        
        return Integer.parseInt(split[1]);
    }
   
}