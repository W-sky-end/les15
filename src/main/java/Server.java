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
            System.out.println("Server is started...");

            try (Socket clientSocket = serverSocket.accept();
                 BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                System.out.println("Client is online: " + clientSocket.getInetAddress());

                String clientMessage = in.readLine();
                System.out.println("Client: " + clientMessage);

                if (containsRussianLetters(clientMessage)) {
                    out.println("Что такое паляница?");
                    String answer = in.readLine();

                    if (answer != null && answer.trim().equalsIgnoreCase(PASSWORD)) {
                        String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                        out.println("Correct! Data time is: " + dateTime);
                        out.println("Goodbye!");
                        System.out.println("Answer is correct.");
                    } else {
                        out.println("Nope!!!Fuck off Russian pig!.");
                        System.out.println("Server is off");
                    }
                } else {
                    out.println("Hello and welcome!");
                    System.out.println("Server is online.");
                }

            } catch (IOException e) {
                System.err.println("Error with client " + e.getMessage());
            }
        } catch (IOException e) {
            System.err.println("Error .Server is offline " + e.getMessage());
        }
    }

    private static boolean containsRussianLetters(String text) {
        return text.matches(".*[ёыэъ].*");
    }
}