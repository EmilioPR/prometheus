package org.urano.prometheus.scraping;

import io.prometheus.client.Gauge;
import io.prometheus.client.exporter.HTTPServer;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class GaugeLauncher {

	private static final Gauge gauge = Gauge.build().name("my_requests_gauge")
			.help("Total number of requests for gauge object.").register();

	public static void main(String[] args) throws IOException, InterruptedException {
		try (final HTTPServer server = new HTTPServer(8080)) {
			final Random random = new Random();
			long counter = 0;
			long total = 0;
			while (counter < 10000) {
				final int increment = random.nextInt(72) + 1 - 36;
				gauge.set(increment);
				total += increment;
				TimeUnit.MILLISECONDS.sleep(500);
				counter++;
			}
			System.out.println("Total requests: " + total);
			TimeUnit.MILLISECONDS.sleep(16000);
			System.out.println("Proceso finalizado");
		}
	}
}
