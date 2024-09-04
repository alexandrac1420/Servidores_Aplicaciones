package edu.escuelaing.arep.Services;

import edu.escuelaing.arep.Annotations.GetMapping;
import edu.escuelaing.arep.Annotations.RequestMapping;
import edu.escuelaing.arep.Annotations.RequestParam;
import edu.escuelaing.arep.Annotations.RestController;

@RestController
@RequestMapping("/app")
public class RandomService {
    @GetMapping("/random")
    public static String random(@RequestParam(value = "min", defaultValue = "0") double min,
                                @RequestParam(value = "max", defaultValue = "1") double max) {
        if (min > max) {
            double temp = min;
            min = max;
            max = temp;
        }
        double randomValue = min + (Math.random() * (max - min));
        return String.format("Random value between %.2f and %.2f: %f", min, max, randomValue);
    }
}