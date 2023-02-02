package com.ekka.broker;

import com.ekka.broker.assets.AssetsRestApi;
import com.ekka.broker.quotes.QuotesRestApi;
import com.ekka.broker.watchlist.WatchListRestApi;
import io.vertx.core.*;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainVerticle extends AbstractVerticle {

  private static final Logger LOG = LoggerFactory.getLogger(MainVerticle.class);
  public static final int PORT = 8888;

  public static void main(String[] args) {
    var vertx = Vertx.vertx();
    vertx.exceptionHandler(error -> {
      LOG.error("Unhandled:", error);
    });
    vertx.deployVerticle(new MainVerticle())
        .onFailure(ar -> LOG.error("Failed to deploy: ",ar))
        .onSuccess(id ->
          LOG.info("\"Deployed {} with id {}", MainVerticle.class.getSimpleName(),id)
        );
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    vertx.deployVerticle(RestApiVerticle.class.getName(),
      new DeploymentOptions().setInstances(processors()))
      .onFailure(startPromise::fail)
      .onSuccess(id -> {
        LOG.info("Deployed {} with id {}",RestApiVerticle.class.getSimpleName(),id);
        startPromise.complete();
      });
  }

  private static int processors() {
    return Runtime.getRuntime().availableProcessors();
  }


}
