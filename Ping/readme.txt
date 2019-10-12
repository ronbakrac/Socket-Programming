PingClient takes a server IP and a port in which you want to send packets through. PingClient will send 10 packets (UDP protocol) to the server and calculate the roundtrip time of each packet. If no reply is recieved after 1 second then PingClient sends the next packet.

PingServer (code provided for PingServer, I created PingClient) takes in a server port to listen to and will respond to packets it recieves by sending the packet back to complete the ping command. PingServer simulates packet loss (about 50% with default settings, can change in program) and transmission delay. 

Compile with 
javac PingClient 
javac PingServer

You can run both locally or on seperate machines. 