package edu.escuelaing.arep;

@RestController
public class HelloService {
    @GetMapping("/hello")
    public static String hello(){
        return "Hello Wold!";
    }

    @GetMapping("/pi")
    public static String pi(){
        return String.valueOf(Math.PI);
    }

    @GetMapping("/random")
    public static String random() {
        double randomValue = Math.random();
        return String.format("Random value: %f", randomValue);
    }

    @GetMapping("/current-time")
    public static String currentTime() {
        return String.format("Current time is: %s", java.time.LocalTime.now().toString());
    }
  
}
