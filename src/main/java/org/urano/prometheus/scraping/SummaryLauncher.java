package org.urano.prometheus.scraping;

import io.prometheus.client.Summary;
import io.prometheus.client.exporter.HTTPServer;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class SummaryLauncher {

	private static final Summary summary = Summary.build().name("my_requests_summary")
			.help("Total number of requests for summary object.").quantile(0.5, 0.05).quantile(0.9, 0.01)
			.quantile(0.99, 0.001).register();

	public static void main(String[] args) throws IOException, InterruptedException {
		try (final HTTPServer server = new HTTPServer(8080)) {
			final Random random = new Random();
			long counter = 0;
			while (counter < 10000) {
				try (final Summary.Timer timer = summary.startTimer()) {
					simulateWork(random);
					timer.observeDuration();
				}
				counter++;
			}
			TimeUnit.MILLISECONDS.sleep(16000);
			System.out.println("Proceso finalizado");
		}
	}

	private static void simulateWork(Random random) throws InterruptedException {
		TimeUnit.MILLISECONDS.sleep(random.nextLong(900) + 100);
	}

}
