package edu.escuelaing.arep.Services;

import edu.escuelaing.arep.Annotations.GetMapping;
import edu.escuelaing.arep.Annotations.RequestMapping;
import edu.escuelaing.arep.Annotations.RestController;

@RestController
@RequestMapping("/app")
public class CurrentTimeService {

    @GetMapping("/current-time")
    public static String currentTime() {
        return String.format("Current time is: %s", java.time.LocalTime.now().toString());
    }
}