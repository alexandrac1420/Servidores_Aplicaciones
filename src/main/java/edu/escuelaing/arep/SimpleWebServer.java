package edu.escuelaing.arep;

import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

import edu.escuelaing.arep.Annotations.GetMapping;
import edu.escuelaing.arep.Annotations.RequestParam;
import edu.escuelaing.arep.Annotations.RestController;

public class SimpleWebServer {
    private static final int PORT = 8080;
    public static final String WEB_ROOT = "src/main/java/edu/escuelaing/arep/resources/";
    public static Map<String, Method> services = new HashMap<>();
    private static boolean running = true;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        loadServices();
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Ready to receive on port " + PORT + "...");
        while (running) {
            Socket clientSocket = serverSocket.accept();
            threadPool.submit(new ClientHandler(clientSocket));
        }
        serverSocket.close();
        threadPool.shutdown();
    }

    private static void loadServices() throws ClassNotFoundException, IOException {
    File servicesDir = new File("src/main/java/edu/escuelaing/arep/Services");
    if (servicesDir.exists() && servicesDir.isDirectory()) {
        for (File file : servicesDir.listFiles()) {
            if (file.getName().endsWith(".java")) {
                String className = "edu.escuelaing.arep.Services." + file.getName().replace(".java", "");
                Class<?> serviceClass = Class.forName(className);
                
                if (serviceClass.isAnnotationPresent(RestController.class)) {
                    Method[] methods = serviceClass.getDeclaredMethods();
                    for (Method method : methods) {
                        if (method.isAnnotationPresent(GetMapping.class)) {
                            String path = method.getAnnotation(GetMapping.class).value();
                            services.put(path, method);
                        }
                    }
                }
            }
        }
    }
}

    public static void stop() {
        running = false;
    }
}

class ClientHandler implements Runnable {
    private Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedOutputStream dataOut = new BufferedOutputStream(clientSocket.getOutputStream())) {

            String requestLine = in.readLine();
            if (requestLine == null)
                return;
            String[] tokens = requestLine.split(" ");
            String method = tokens[0];
            String fileRequested = tokens[1];

            printRequestLine(requestLine, in);

            if (fileRequested.startsWith("/app")) {
                handleAppRequest(method, fileRequested, out);
            } else {
                if (method.equals("GET")) {
                    handleGetRequest(fileRequested, out, dataOut);
                } else if (method.equals("POST")) {
                    handlePostRequest(fileRequested, out, dataOut);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close(); 
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void printRequestLine(String requestLine, BufferedReader in) {
        System.out.println("Request line: " + requestLine);
        String inputLine;
        try {
            while ((inputLine = in.readLine()) != null && !inputLine.isEmpty()) {
                System.out.println("Header: " + inputLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    private void handleGetRequest(String fileRequested, PrintWriter out, BufferedOutputStream dataOut)
            throws IOException {
        File file = new File(SimpleWebServer.WEB_ROOT, fileRequested);
        int fileLength = (int) file.length();
        String content = getContentType(fileRequested);

        if (file.exists()) {
            byte[] fileData = readFileData(file, fileLength);
            out.println("HTTP/1.1 200 OK");
            out.println("Content-type: " + content);
            out.println("Content-length: " + fileLength);
            out.println();
            out.flush();
            dataOut.write(fileData, 0, fileLength);
            dataOut.flush();
        } else {
            out.println("HTTP/1.1 404 Not Found");
            out.println("Content-type: text/html");
            out.println();
            out.flush();
            out.println("<html><body><h1>File Not Found</h1></body></html>");
            out.flush();
        }
    }

    private void handlePostRequest(String fileRequested, PrintWriter out, BufferedOutputStream dataOut)
            throws IOException {
        StringBuilder payload = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null && !line.isEmpty()) {
                payload.append(line);
            }
        }

        out.println("HTTP/1.1 200 OK");
        out.println("Content-type: text/html");
        out.println();
        out.println("<html><body><h1>POST data received:</h1>");
        out.println("<p>" + payload.toString() + "</p>");
        out.println("</body></html>");
        out.flush();
    }

    private void handleAppRequest(String method, String fileRequested, PrintWriter out) {
        out.println("HTTP/1.1 200 OK");
        out.println("Content-type: text/html");
        out.println();
    
        String[] pathAndQuery = fileRequested.substring(fileRequested.indexOf("/app/") + 4).split("\\?");
        String path = pathAndQuery[0];
        String query = pathAndQuery.length > 1 ? pathAndQuery[1] : "";
    
        Method serviceMethod = SimpleWebServer.services.get(path);
        if (serviceMethod != null) {
            try {
                Map<String, String> queryParams = parseQueryParams(query);
                Object[] parameters = new Object[serviceMethod.getParameterCount()];
                Class<?>[] parameterTypes = serviceMethod.getParameterTypes();
                Annotation[][] annotations = serviceMethod.getParameterAnnotations();
                
                for (int i = 0; i < annotations.length; i++) {
                    for (Annotation annotation : annotations[i]) {
                        if (annotation instanceof RequestParam) {
                            RequestParam requestParam = (RequestParam) annotation;
                            String paramName = requestParam.value();
                            String paramValue = queryParams.get(paramName);
    
                            if (paramValue == null || paramValue.isEmpty()) {
                                parameters[i] = convertToType(parameterTypes[i], requestParam.defaultValue());
                            } else {
                                parameters[i] = convertToType(parameterTypes[i], paramValue);
                            }
                        } else {
                            parameters[i] = null; 
                        }
                    }
                }
    
                String response = (String) serviceMethod.invoke(null, parameters);
                out.println(response);
            } catch (Exception e) {
                e.printStackTrace();
                out.println("Error executing service method: " + e.getMessage());
            }
        } else {
            out.println("<html><body><h1>Service Not Found</h1></body></html>");
        }
    
        out.flush();
    }
    

    private Map<String, String> parseQueryParams(String query) {
        Map<String, String> queryParams = new HashMap<>();
        if (query != null && !query.isEmpty()) {
            String[] pairs = query.split("&");
            for (String pair : pairs) {
                String[] keyValue = pair.split("=");
                if (keyValue.length == 2) {
                    queryParams.put(keyValue[0], keyValue[1]);
                }
            }
        }
        return queryParams;
    }
    
    private Object convertToType(Class<?> type, String value) {
        if (type == int.class || type == Integer.class) {
            return Integer.parseInt(value);
        } else if (type == double.class || type == Double.class) {
            return Double.parseDouble(value);
        } else if (type == float.class || type == Float.class) {
            return Float.parseFloat(value);
        } else if (type == long.class || type == Long.class) {
            return Long.parseLong(value);
        } else if (type == boolean.class || type == Boolean.class) {
            return Boolean.parseBoolean(value);
        } else {
            return value; 
        }
    }
    

    private String getContentType(String fileRequested) {
        if (fileRequested.endsWith(".html"))
            return "text/html";
        else if (fileRequested.endsWith(".css"))
            return "text/css";
        else if (fileRequested.endsWith(".js"))
            return "application/javascript";
        else if (fileRequested.endsWith(".png"))
            return "image/png";
        else if (fileRequested.endsWith(".jpg"))
            return "image/jpeg";
        return "text/plain";
    }

    private byte[] readFileData(File file, int fileLength) throws IOException {
        FileInputStream fileIn = null;
        byte[] fileData = new byte[fileLength];
        try {
            fileIn = new FileInputStream(file);
            fileIn.read(fileData);
        } finally {
            if (fileIn != null)
                fileIn.close();
        }
        return fileData;
    }
}
