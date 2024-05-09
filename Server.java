import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    private static final int PORT = 2206;
    private static ServerSocket serverSocket;
    private static List<Socket> nodeSockets = new ArrayList<>();
    private static List<String> data = new ArrayList<>();

    public static void main(String[] args) {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("El servidor está en ejecución en el puerto " + PORT);
        } catch (IOException e) {
            throw new RuntimeException("Error al iniciar el servidor.", e);
        }

        // Aceptar conexiones de los nodos
        aceptarConexionesDeNodos();
        
        // Enviar tareas a los nodos y recibir resultados
        enviarTareasANodosYRecibirResultados();
    }

    private static void aceptarConexionesDeNodos() {
        new Thread(() -> {
            while (true) {
                try {
                    Socket nodeSocket = serverSocket.accept();
                    System.out.println("Nodo conectado: " + nodeSocket.getInetAddress().getHostAddress());
                    nodeSockets.add(nodeSocket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private static void enviarTareasANodosYRecibirResultados() {
        // Preparar los datos (atributos y etiquetas) para entrenar el árbol de decisión
        prepararDatos();

        // Dividir los datos en partes y enviar cada parte a un nodo
// Dividir los datos en partes y enviar cada parte a un nodo
int tamañoDatos = data.size();
int numNodos = nodeSockets.size();

// Verificar si hay al menos un nodo antes de dividir
if (numNodos > 0) {
    int tamañoLote = tamañoDatos / numNodos;
    int restante = tamañoDatos % numNodos;
    int índiceInicio = 0;
    for (Socket nodeSocket : nodeSockets) {
        int índiceFin = índiceInicio + tamañoLote;
        if (restante > 0) {
            índiceFin++;
            restante--;
        }
        List<String> parteDatos = data.subList(índiceInicio, índiceFin);
        enviarTareaANodo(nodeSocket, parteDatos);
        índiceInicio = índiceFin;
    }
} else {
    System.out.println("No hay nodos conectados para enviar tareas.");
}


        // Recibir resultados de los nodos
        List<String> resultados = new ArrayList<>();
        for (Socket nodeSocket : nodeSockets) {
            String resultado = recibirResultadoDeNodo(nodeSocket);
            resultados.add(resultado);
        }

        // Combinar resultados y procesarlos
        procesarResultados(resultados);
    }

    private static void prepararDatos() {

        // Por simplicidad, generaremos algunos datos aleatorios aquí
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            String puntoDatos = random.nextInt(2) + "," + random.nextInt(2) + "," + random.nextInt(2) + "," + (random.nextInt(2) + 1);
            data.add(puntoDatos);
        }
    }

    private static void enviarTareaANodo(Socket nodeSocket, List<String> parteDatos) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(nodeSocket.getOutputStream());
            outputStream.writeObject(parteDatos);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String recibirResultadoDeNodo(Socket nodeSocket) {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(nodeSocket.getInputStream());
            return (String) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void procesarResultados(List<String> resultados) {
        // Combinar y procesar los resultados de todos los nodos
        // Esto podría implicar agregar, analizar o procesar aún más los resultados
        // Por simplicidad, solo imprimiremos los resultados aquí
        for (String resultado : resultados) {
            System.out.println("Resultado del nodo: " + resultado);
        }
    }
}
