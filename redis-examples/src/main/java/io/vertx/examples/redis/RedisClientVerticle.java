package io.vertx.examples.redis;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import io.vertx.groovy.core.Vertx;
import io.vertx.redis.RedisClient;

/**
 * A verticle setting and reading a value in Redis.
 */
public class RedisClientVerticle extends AbstractVerticle {

  @Override
  public void start() throws Exception {
    // If a config file is set, read the host and port.
    String host = (String) Vertx.currentContext().config().get("host");
    if (host == null) {
      host = "127.0.0.1";
    }

    // Create the redis client
    final RedisClient client = RedisClient.create(vertx,
        new JsonObject()
            .put("host", host));

    client.set("key", "value", r -> {
      if (r.succeeded()) {
        System.out.println("key stored");
        client.get("key", s -> {
          System.out.println("Retrieved value: " + s.result());
        });
      } else {
        System.out.println("Connection or Operation Failed " + r.cause());
      }
    });
  }
}
