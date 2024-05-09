import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.Vector;

public class Client {
    private static final int PORT = 2206;
    private static final String HOST = "localhost";
    private static Socket client;

    private static final Vector<String> attributes = new Vector<>();
    private static final Vector<String> labels = new Vector<>();
    private static final Vector<Vector<String>> data = new Vector<>();

    public static void main(String[] args) {
        try {
            client = new Socket(HOST, PORT);
            System.out.println("Cliente conectado al servidor en el puerto " + client.getPort());
            recibirDatosDelServidor();
        } catch (IOException e) {
            throw new RuntimeException("Error al conectar con el servidor", e);
        }
    }

    private static void recibirDatosDelServidor() {
        try (ObjectInputStream inputStream = new ObjectInputStream(client.getInputStream());
             Scanner scanner = new Scanner(System.in)) {
            while (true) {
                // Recibir datos del servidor
                Object receivedData = inputStream.readObject();
                if (receivedData instanceof Vector<?>) {
                    Vector<?> receivedVector = (Vector<?>) receivedData;
                    if (!receivedVector.isEmpty()) {
                        attributes.clear();
                        attributes.addAll((Vector<String>) receivedVector.get(0));
                        labels.clear();
                        labels.addAll((Vector<String>) receivedVector.get(1));
                        data.clear();
                        data.addAll((Vector<Vector<String>>) receivedVector.get(2));
                        procesarDatos();
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Error al recibir datos del servidor", e);
        }
    }

    private static void procesarDatos() {
        // Aquí se puede implementar el algoritmo de árbol de decisión
        // Utilizando los datos recibidos: attributes, labels y data
        // Por simplicidad, solo imprimiré los datos aquí
        System.out.println("Datos recibidos:");
        System.out.println("Atributos: " + attributes);
        System.out.println("Etiquetas: " + labels);
        System.out.println("Datos:");
        for (Vector<String> row : data) {
            System.out.println(row);
        }
        System.out.println("Procesamiento de árbol de decisión...");
        // Aquí se implementaría el algoritmo de árbol de decisión
        // Y se enviarían los resultados de vuelta al servidor si es necesario
    }
}
