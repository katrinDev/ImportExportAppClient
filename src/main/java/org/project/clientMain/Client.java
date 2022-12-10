package org.project.clientMain;

import com.google.gson.Gson;
import org.project.TCP.Response;

import java.io.*;
import java.net.Socket;

public class Client {
    private static Socket socket;
    private static final int PORT = 3334;
    private static ObjectOutputStream out;
    private static ObjectInputStream in;

    public static void createInstance(){
        try{
            socket = new Socket("localhost", PORT);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            System.out.println("Соединение с сервером установлено");
        } catch(IOException e){
            System.out.println("Ошибка при создании клиента!");
        }
    }

    public static <T> void sendMessage(T data) throws IOException {
        out.writeObject(new Gson().toJson(data));
        out.flush();
    }

    public static Response getMessage() throws IOException, ClassNotFoundException {
        String message = String.valueOf(in.readObject());
        Response response = new Gson().fromJson(message, Response.class);
        return response;
    }

    public static void closeEverything() {
        try{
            if(in != null){
                in.close();
            }
            if(out != null){
                out.close();
            }
            if(socket != null){
                socket.close();
            }
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
