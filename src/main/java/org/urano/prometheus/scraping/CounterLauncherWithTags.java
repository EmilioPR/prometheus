package org.urano.prometheus.scraping;

import io.prometheus.client.Counter;
import io.prometheus.client.exporter.HTTPServer;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class CounterLauncherWithTags {

	private static final List<String> STORE_ID_LIST = List.of("STORE_01", "STORE_02");

	private static final List<String> PRODUCT_ID_LIST = List.of("PRODUCT_01", "PRODUCT_02");

	private static final Counter requests = Counter.build().name("my_requests_counter_with_store_product_tag")
			.help("Total number of requests for counter object with tags.").labelNames("store", "product").register();

	public static void main(String[] args) throws IOException, InterruptedException {
		try (final HTTPServer server = new HTTPServer(8081)) {
			final Random random = new Random();

			long counter = 0;
			while (counter < 2000) {
				final int store = random.nextInt(2);
				final int product = random.nextInt(2);
				final double quantity = random.nextLong(5);
				requests.labels(STORE_ID_LIST.get(store), PRODUCT_ID_LIST.get(product)).inc(quantity);
				TimeUnit.MILLISECONDS.sleep(100);
				counter++;
			}
			TimeUnit.MILLISECONDS.sleep(16000);
			System.out.println("Proceso finalizado");
		}
	}

}