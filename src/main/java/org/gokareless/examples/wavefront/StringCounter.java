package org.gokareless.examples.wavefront;

import com.codahale.metrics.Counter;
import com.codahale.metrics.MetricRegistry;
import com.wavefront.integrations.metrics.WavefrontReporter;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class StringCounter {

  public static void main(String[] args) {
    MetricRegistry registry = new MetricRegistry();
    Counter evictions = registry.counter("str-count");
    String hostname = "localhost";
    int port = 2878;
    WavefrontReporter reporter = WavefrontReporter.forRegistry(registry).
        withSource("my.company.com").
        withPointTag("dc", "us-west-2").
        withPointTag("service", "query").
        build(hostname, port);
    reporter.start(5, TimeUnit.SECONDS);
    Scanner scanner = new Scanner(System.in);
    while (scanner.hasNext()) {
      System.out.println(scanner.next());
      evictions.inc();
      reporter.report();
    }
    scanner.close();
  }
}
