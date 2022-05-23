import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientOne {
    public static final String HOST = "netology.homework"; // задаем локальный хост
    private static final int PORT = 8088; // задаем номер порта для подключения к серверу

    public static void main(String[] args) throws IOException {
        Socket clientSocket = new Socket(HOST, PORT); // запрашиваем у сервера доступ на соединение
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(
                     new OutputStreamWriter(clientSocket.getOutputStream()), true);
             Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.print("Вас приветствует сервис вычисления N-го члена ряда Фибоначчи!\n" +
                        " - Введите число, для которого хотите произвести вычисления;\n" +
                        " - Или введите 'end', если хотите выйти.\n" +
                        "Ваш ввод:\n");
                String msg = scanner.nextLine();
                if (!"end".equals(msg)) {
                    try {
                        int clientMessage = Integer.parseInt(msg);
                        out.println(clientMessage); // отправляем сообщение на сервер
                        String serverMessage = in.readLine(); // читаем очередное сообщение с сервера
                        System.out.println("Результат вычисления сервиса: " + serverMessage);
                    } catch (NumberFormatException e) {
                        System.out.println("Ошибка: введенное Вами значение не является числом!");
                    }
                } else {
                    out.println(msg); // отправляем сообщение на сервер
                    String serverFinalMessage = in.readLine(); // читаем последнее сообщение с сервера
                    System.out.println(serverFinalMessage);
                    break;
                }
            }
        } catch (IOException err) {
            System.out.println(err.getMessage());
        }
    }

}