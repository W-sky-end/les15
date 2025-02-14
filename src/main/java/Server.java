import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Server {
    private static final int PORT = 8081;
    private static final String PASSWORD = "хлiб";

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Сервер запущен, ожидание подключения...");

            try (Socket clientSocket = serverSocket.accept();
                 BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                System.out.println("Клиент подключился: " + clientSocket.getInetAddress());

                String clientMessage = in.readLine();
                System.out.println("Клиент: " + clientMessage);

                if (containsRussianLetters(clientMessage)) {
                    out.println("Что такое паляница?");
                    String answer = in.readLine();

                    if (answer != null && answer.trim().equalsIgnoreCase(PASSWORD)) {
                        String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                        out.println("Правильно! Текущая дата и время: " + dateTime);
                        out.println("До свидания!");
                        System.out.println("Клиент ответил правильно, соединение завершено.");
                    } else {
                        out.println("Неверный ответ! Соединение закрыто.");
                        System.out.println("Клиент дал неверный ответ, соединение закрыто.");
                    }
                } else {
                    out.println("Привет! Ты можешь использовать наш сервер.");
                    System.out.println("Клиент прошел проверку.");
                }

            } catch (IOException e) {
                System.err.println("Ошибка при взаимодействии с клиентом: " + e.getMessage());
            }
        } catch (IOException e) {
            System.err.println("Не удалось запустить сервер: " + e.getMessage());
        }
    }

    private static boolean containsRussianLetters(String text) {
        return text.matches(".*[ёыэъ].*");
    }
}