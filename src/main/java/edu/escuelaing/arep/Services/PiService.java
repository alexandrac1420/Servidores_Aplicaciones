package edu.escuelaing.arep.Services;

import edu.escuelaing.arep.Annotations.GetMapping;
import edu.escuelaing.arep.Annotations.RequestMapping;
import edu.escuelaing.arep.Annotations.RequestParam;
import edu.escuelaing.arep.Annotations.RestController;

@RestController
@RequestMapping("/app")
public class PiService {
    
    @GetMapping("/pi")
    public static String pi(@RequestParam(value = "decimals", defaultValue = "2") int decimals) {
        if (decimals < 0) decimals = 0;
        String format = "%." + decimals + "f";
        return String.format(format, Math.PI);
    }
}