package com.example.new_;

import io.ebean.DB;
import io.ebean.Database;
import io.ebean.DatabaseFactory;
import io.ebean.config.ServerConfig;
import io.ebean.datasource.DataSourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBConfig {

  private static final Logger logger = LoggerFactory.getLogger(DBConfig.class);

  public static Database setup() {
    ServerConfig config = new ServerConfig();
    config.setName("db");

    DataSourceConfig dsConfig = new DataSourceConfig();
    dsConfig.setUsername("root");
    dsConfig.setPassword("horshita00");
    dsConfig.setUrl("jdbc:mysql://localhost:3306/crudDBVertex");
    dsConfig.setDriver("com.mysql.cj.jdbc.Driver");

    config.setDataSourceConfig(dsConfig);
    config.setDdlGenerate(true);
    config.setDdlRun(true);
    config.setDefaultServer(true);

    Database db = DatabaseFactory.create(config);

    return db;
  }
}
