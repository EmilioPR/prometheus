package org.urano.prometheus.scraping;

import io.prometheus.client.Counter;
import io.prometheus.client.exporter.HTTPServer;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class CounterLauncher {

	private static final Counter requests = Counter.build().name("my_requests_prb_counter")
			.help("Total number of requests for counter object.").register();

	public static void main(String[] args) throws IOException, InterruptedException {
		try (final HTTPServer server = new HTTPServer(8080)) {
			final Random random = new Random();
			long counter = 0;
			long total = 0;
			while (counter < 10000) {
				final long increment = random.nextLong(10) + 1;
				requests.inc(increment);
				total += increment;
				TimeUnit.MILLISECONDS.sleep(250);
				counter++;
			}
			System.out.println("Total requests: " + total);
			TimeUnit.MILLISECONDS.sleep(16000);
			System.out.println("Proceso finalizado");
		}
	}

}