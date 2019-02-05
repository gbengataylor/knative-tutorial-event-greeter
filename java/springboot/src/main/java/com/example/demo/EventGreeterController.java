package com.example.demo;

import io.vertx.core.json.JsonObject;
import org.springframework.web.bind.annotation.*;

/**
 * EventGreeterController
 */
@RestController
public class EventGreeterController {

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
                .put("host",greeterHost.replace("\n",""));
        return response.encode();
    }

    @GetMapping("/healthz")
    public String health() {
        return "OK";
    }
}
