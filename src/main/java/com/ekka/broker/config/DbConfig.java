package com.ekka.broker.config;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DbConfig {

  String host;
  int port;
  String database;
  String user;
  String password;

  @Override
  public String toString() {
    return "DbConfig{" +
      "host='" + host + '\'' +
      ", port=" + port +
      ", database='" + database + '\'' +
      ", user='" + user + '\'' +
      ", password='****'" +
      '}';
    //password replaced with stars, so that it doesn't end up in the LOG file
  }
}
