package org.urano.prometheus;

import io.prometheus.client.Gauge;
import io.prometheus.client.exporter.HTTPServer;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class GaugeLauncher {

    private static final Gauge gauge = Gauge.build()
        .name("my_requests_gauge")
        .help("Total number of requests for gauge object.")
        .register();

    public static void main(String[] args) throws IOException, InterruptedException {
        try(HTTPServer _ = new HTTPServer(8080)) {
            Random random = new Random();

            long counter = 0;
            while (counter < Long.MAX_VALUE) {
                gauge.set(random.nextInt(72));
                TimeUnit.SECONDS.sleep(1);
                counter++;
            }
        }
    }
}
