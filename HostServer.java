package sss;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

// https://medium.com/martinomburajr/java-create-your-own-hello-world-server-2ca33b6957e

public class HostServer implements Runnable {
    private boolean running = false;

    public void start() {
        running = true;
    }

    public void run() {
        while (running) {
            //Try connect to the server on an unused port eg 9991. A successful connection will return a socket
            try (ServerSocket serverSocket = new ServerSocket(9991)) {
                Socket connectionSocket = serverSocket.accept();

                //Create Input&Outputstreams for the connection
                InputStream inputToServer = connectionSocket.getInputStream();
                //OutputStream outputFromServer = connectionSocket.getOutputStream();

                Scanner scanner = new Scanner(inputToServer, "UTF-8");
                //PrintWriter serverPrintOut = new PrintWriter(new OutputStreamWriter(outputFromServer, "UTF-8"), true);

                //serverPrintOut.println("Hello World! Enter Peace to exit.");

                //Have the server take input from the client and echo it back
                //This should be placed in a loop that listens for a terminator text e.g. bye

                while (running && scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    //serverPrintOut.println("Echo from <Your Name Here> Server: " + line);
                    TwitchChat.outsideDEBUGMessage(line);

                    if (line.toLowerCase().trim().equals("peace")) {
                        running = false;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() { running = false; }

}
