import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final int PORT = 8088; // задаем номер порта для подключения сервера

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT); // сервер-сокет подключаем к порту
        while (true) {
            try (Socket clientSocket = serverSocket.accept(); // ждем подключения
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(
                         new InputStreamReader(clientSocket.getInputStream()))) {
                while (true) {
                    String request = in.readLine(); // читаем сообщение от клиента
                    if (!"end".equals(request)) {
                        Integer count = Integer.parseInt(request);
                        String answer = Long.toString(fibonacci(count));
                        out.println(answer); // направляем очередное сообщение клиенту
                    } else {
                        // направляем последнее сообщение клиенту
                        out.println("Спасибо, что воспользовались нашим сервисом! До свидания!");
                        break;
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace(System.out);
            }
        }
    }

    public static long fibonacci(long count) {
        long prev = 0;
        long next = 1;
        long result = 0;
        for (int i = 0; i < count; i++) {
            result = prev + next;
            prev = next;
            next = result;
        }
        return result;
    }

}

