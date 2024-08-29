package edu.escuelaing.arep;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class SpringEci {
    public static void main(String[] args) throws ClassNotFoundException, MalformedURLException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Class c = Class.forName(args[0]);
        Map<String, Method> services = new HashMap<>();
        //Cargar componentes
        if(c.isAnnotationPresent(RestController.class)){
            Method[] methods = c.getDeclaredMethods();
            for (Method m: methods){
                if(m.isAnnotationPresent(GetMapping.class)){
                    String key = m.getAnnotation(GetMapping.class).value();
                    services.put(key, m);
                }
            }

        }

        //Ejecutar componentes
        //codigo quemado para ejemplo con hello
        URL serviceUrl = new URL("http://localhost:8080/App/hello");
        String path = serviceUrl.getPath();
        System.out.println("Path: "+path);

        String serviceName = path.substring(4);
        System.out.println("Service name: "+serviceName);

        Method methodService = services.get(serviceName);
        System.out.println("Respuesta servicio: " +  methodService.invoke(null, null));

        System.out.println("-----------------------------------------------------------------------");

        //Ejecutar componentes
        //codigo quemado para ejemplo con random
        URL serviceUrlRandom = new URL("http://localhost:8080/App/random");
        String pathRandom = serviceUrlRandom.getPath();
        System.out.println("Path: "+pathRandom);

        String serviceNameRandom = pathRandom.substring(4);
        System.out.println("Service name: "+serviceNameRandom);

        Method methodServiceRandom = services.get(serviceNameRandom);
        System.out.println("Respuesta servicio: " +  methodServiceRandom.invoke(null, null));

        System.out.println("-----------------------------------------------------------------------");


        //Ejecutar componentes
        //codigo quemado para ejemplo con current-time
        URL serviceUrlCurrentTime = new URL("http://localhost:8080/App/current-time");
        String pathCurrentTime = serviceUrlCurrentTime.getPath();
        System.out.println("Path: "+pathCurrentTime);

        String serviceNameCurrentTime = pathCurrentTime.substring(4);
        System.out.println("Service name: "+serviceNameCurrentTime);

        Method methodServiceCurrentTime = services.get(serviceNameCurrentTime);
        System.out.println("Respuesta servicio: " +  methodServiceCurrentTime.invoke(null, null));

        System.out.println("-----------------------------------------------------------------------");


        //Ejecutar componentes
        //codigo quemado para ejemplo con pi
        URL serviceUrlPi = new URL("http://localhost:8080/App/pi");
        String pathPi = serviceUrlPi.getPath();
        System.out.println("Path: "+pathPi);

        String serviceNamePi = pathPi.substring(4);
        System.out.println("Service name: "+serviceNamePi);

        Method methodServicePi = services.get(serviceNamePi);
        System.out.println("Respuesta servicio: " +  methodServicePi.invoke(null, null));
       
    }
    
}
