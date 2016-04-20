package com.orionletizi;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;

public class App {
  public static void main(String[] args) throws Exception {
    String address = "172.30.0.140";
    int port = 80;
    if (args.length >= 2) {
      address = args[0];
      port = Integer.parseInt(args[1]);
    }

    info("Setting up server: address: " + address + ", port: " + port);
    HttpServer server = HttpServer.create(new InetSocketAddress(InetAddress.getByName(address), port), 0);
    server.createContext("/test", new MyHandler());
    server.setExecutor(null); // creates a default executor
    server.start();
  }

  private static void info(final Object o) {
    System.out.println(App.class + ": " + o);
  }

  static class MyHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange t) throws IOException {
      String response = "This is the response\n";
      t.sendResponseHeaders(200, response.length());
      OutputStream os = t.getResponseBody();
      os.write(response.getBytes());
      os.close();
    }
  }
}
