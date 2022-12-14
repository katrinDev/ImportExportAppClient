package org.project.clientMain;

import com.google.gson.Gson;
import org.project.TCP.Response;
import org.project.entities.User;

import java.io.*;
import java.net.Socket;

public class Client {
    private static Socket socket;
    private static final int PORT = 3334;
    public static ObjectOutputStream out;
    private static ObjectInputStream in;

    private static User user;

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

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        Client.user = user;
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
