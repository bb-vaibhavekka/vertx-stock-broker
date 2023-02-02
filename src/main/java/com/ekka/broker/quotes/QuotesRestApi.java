package com.ekka.broker.quotes;

import com.ekka.broker.assets.Asset;
import com.ekka.broker.assets.AssetsRestApi;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.util.internal.ThreadLocalRandom;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class QuotesRestApi {
  private static final Logger LOG = LoggerFactory.getLogger(QuotesRestApi.class);

  public static void attach(Router parent){
    final Map<String,Quote> cachedQuotes = new HashMap<>();
    AssetsRestApi.ASSETS.forEach(symbol ->
      cachedQuotes.put(symbol,initRandomQuote(symbol))
    );
    parent.get("/quotes/:asset").handler(new GetQuoteHandler(cachedQuotes));
  }

  private static Quote initRandomQuote(final String assetParam) {
    return Quote.builder()
      .asset(new Asset(assetParam))
      .ask(randomValue())
      .bid(randomValue())
      .lastPrice(randomValue())
      .volume(randomValue())
      .build();
  }

  private static BigDecimal randomValue() {
    return BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(1, 100));
  }

}
