package com.example.demo;

import io.vertx.core.json.JsonObject;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * EventGreeterController
 */
@RestController
public class EventGreeterController {

    private final SimpleDateFormat SDF = new SimpleDateFormat("HH:mm:ss");

    private static final String RESPONSE_STRING_FORMAT = "Event greeter => '%s' : %d \n";

    private static final String HOSTNAME =
            parseContainerIdFromHostname(System.getenv().getOrDefault("HOSTNAME", "unknown"));

    static String parseContainerIdFromHostname(String hostname) {
        return hostname.replaceAll("greeter-v\\d+-", "");
    }


    /**
     * Counter to help us see the lifecycle
     */
    private int count = 0;

    @PostMapping("/")
    public @ResponseBody  String greet(@RequestBody String cloudEventJson) {
        count++;
        String greeterHost = String.format(RESPONSE_STRING_FORMAT, HOSTNAME, count);
        JsonObject response = new JsonObject(cloudEventJson)
                .put("host",greeterHost.replace("\n",""))
                .put("time",SDF.format(new Date()));
        return response.encode();
    }

    @GetMapping("/healthz")
    public String health() {
        return "OK";
    }
}
