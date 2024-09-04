package edu.escuelaing.arep.Services;

import edu.escuelaing.arep.Annotations.GetMapping;
import edu.escuelaing.arep.Annotations.RequestMapping;
import edu.escuelaing.arep.Annotations.RequestParam;
import edu.escuelaing.arep.Annotations.RestController;

@RestController
@RequestMapping("/app")
public class HelloService {

    @GetMapping("/hello")
    public static String hello(@RequestParam(value = "name", defaultValue = "World")String name){
        return "Hello " + name;
    }
}

  

