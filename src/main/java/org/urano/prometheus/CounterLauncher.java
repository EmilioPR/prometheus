package org.urano.prometheus;

import io.prometheus.client.Counter;
import io.prometheus.client.exporter.HTTPServer;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class CounterLauncher {

    private static final Counter requests = Counter.build()
        .name("my_requests_counter")
        .help("Total number of requests for counter object.")
        .register();

    public static void main(String[] args) throws IOException, InterruptedException {
        try(HTTPServer server = new HTTPServer(8080)) {
            Random random = new Random();

            long counter = 0;
            while (counter < Long.MAX_VALUE) {
                requests.inc(random.nextInt(10));
                TimeUnit.SECONDS.sleep(1);
                counter++;
            }
        }
    }

}