package com.ekka.broker;

import com.ekka.broker.config.ConfigLoader;
import io.vertx.core.Vertx;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractRestApiTest {

  private static final Logger LOG = LoggerFactory.getLogger(AbstractRestApiTest.class);

  public static final int TEST_SERVER_PORT = 9000;
  @BeforeEach
  void deploy_verticle(Vertx vertx, VertxTestContext context) {
    System.setProperty(ConfigLoader.SERVER_PORT,String.valueOf(TEST_SERVER_PORT));
    System.setProperty(ConfigLoader.DB_HOST, "localhost");
    System.setProperty(ConfigLoader.DB_PORT, "5432");
    System.setProperty(ConfigLoader.DB_DATABASE, "vertx-stock-broker");
    System.setProperty(ConfigLoader.DB_USER, "postgres");
    System.setProperty(ConfigLoader.DB_PASSWORD, "secret");
    LOG.warn("!!!Tests are using the local database!!!");
    vertx.deployVerticle(new MainVerticle(), context.succeeding(id -> context.completeNow()));
  }

}