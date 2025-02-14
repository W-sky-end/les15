import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 8081;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Подключено к серверу!");


            System.out.print("Введите приветствие: ");
            String greeting = scanner.nextLine();
            out.println(greeting);


            String serverResponse = in.readLine();
            System.out.println("Сервер: " + serverResponse);

            if (serverResponse.equals("Что такое паляница?")) {
                System.out.print("Введите ответ: ");
                String answer = scanner.nextLine();
                out.println(answer);

                System.out.println("Сервер: " + in.readLine());
                System.out.println("Сервер: " + in.readLine());
            }

        } catch (IOException e) {
            System.err.println("Ошибка подключения к серверу: " + e.getMessage());
        }
    }
}