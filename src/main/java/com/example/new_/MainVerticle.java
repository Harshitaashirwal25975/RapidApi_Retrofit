package com.example.new_;
import io.ebean.Database;
import io.vertx.core.json.JsonObject;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
//import io.vertx.core.impl.logging.Logger;
//import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.core.json.Json;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.core.json.DecodeException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class MainVerticle extends AbstractVerticle {
  private static final Logger logger = LoggerFactory.getLogger(MainVerticle.class);



  private ApiHolder apiHolder;
//  private Database database;

  private void createUser(RoutingContext routingContext, Database database) {
    logger.info("Handling createUser request");

    // Log request headers and body for debugging
    logger.info("Request headers: {}", routingContext.request().headers());
    logger.info("Request body as string: {}", routingContext.getBodyAsString());

    try {
      JsonObject json = routingContext.getBodyAsJson();
      if (json == null) {
        throw new IllegalArgumentException("Request body is empty or not in JSON format");
      }

      logger.info("Request body as JSON: {}", json);

      String name = json.getString("name");
      String email = json.getString("email");
      String password = json.getString("password");

      if (name == null || email == null || password == null) {
        throw new IllegalArgumentException("Missing required fields: name and/or email");
      }
      String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

      User user = new User();
      user.setName(name);
      user.setEmail(email);
      user.setPassword(hashedPassword);


      database.save(user);

      routingContext.response()
        .setStatusCode(201)
        .end("User created successfully");
    } catch (DecodeException e) {
      // Handle case where the request body is not valid JSON
      logger.error("Invalid JSON: {}", e.getMessage());
      routingContext.response()
        .setStatusCode(400)
        .end("Invalid JSON format: " + e.getMessage());
    } catch (IllegalArgumentException e) {
      // Handle case where JSON is invalid or missing required fields
      logger.error("Invalid input: {}", e.getMessage());
      routingContext.response()
        .setStatusCode(400)
        .end("Invalid input: " + e.getMessage());
    } catch (Exception e) {
      // Handle other exceptions such as database errors
      logger.error("Internal server error: {}", e.getMessage(), e);
      routingContext.response()
        .setStatusCode(500)
        .end("Internal server error: " + e.getMessage());
    }
  }

  private void loginUser(RoutingContext routingContext, Database database) {
    logger.info("Handling updateUser request");

    // Log request headers and body for debugging
    logger.info("Request headers: {}", routingContext.request().headers());
    logger.info("Request body as string: {}", routingContext.getBodyAsString());

    try {
      JsonObject json = routingContext.getBodyAsJson();
      if (json == null) {
        throw new IllegalArgumentException("Request body is empty or not in JSON format");
      }

      logger.info("Request body as JSON: {}", json);

      String password = json.getString("name");
      String email = json.getString("email");

      if (password == null || email == null) {
        throw new IllegalArgumentException("Missing required fields: password, email");
      }

      User user = database.find(User.class, email);
      if (user == null) {
        routingContext.response()
          .setStatusCode(404)
          .end("User not found");
        return;
      }

      if (user.getPassword() == password) {
        routingContext.response()
          .end("User logged in successfully");
      }
    }
    catch (Exception e) {
      logger.error("Internal server error: {}", e.getMessage(), e);
      routingContext.response()
        .setStatusCode(500)
        .end("Internal server error: " + e.getMessage());
    }
  }



  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    Database database = DBConfig.setup();
    Retrofit retrofit = RetrofitClient.getClient();
    apiHolder = retrofit.create(ApiHolder.class);
    AuthHandler authHandler = new AuthHandler();
    Router router = Router.router(vertx);
    router.route().handler(BodyHandler.create());
    router.get("/get/:city").handler(routingContext -> handleGetByCity(routingContext, authHandler));

    router.post("/create").handler(context -> createUser(context, database));


    router.post("/login").handler(context -> {
      System.out.println("hello");
      authHandler.handleLogin(context);
    });

    vertx.createHttpServer().requestHandler(router).listen(8888).onComplete(http -> {
      if (http.succeeded()) {
        startPromise.complete();
        System.out.println("HTTP server started on port 8888");
        try {
          InetAddress localHost = InetAddress.getLocalHost();
          System.out.println("Server IP Address: " + localHost.getHostAddress());
        } catch (UnknownHostException e) {
          System.err.println("Unable to get local host address: " + e.getMessage());
        }
      } else {
        startPromise.fail(http.cause());
      }
    });
  }

  private void handleGetByCity(RoutingContext routingContext,AuthHandler authHandler) {
    String city = routingContext.pathParam("city");
    Call<WeatherResponse> call = apiHolder.getByCity(city);
    String token = routingContext.request().getHeader("Authorization");
    logger.info(token);
//    System.out.println(token);
//    System.out.println("token");
    if (!authHandler.validateToken(routingContext)) {
      return;
    }

    String clientIp = routingContext.request().remoteAddress().host();
    String ip = routingContext.request().getHeader("X-Forwarded-For");
    ææ
    System.out.println("clientIp: " + clientIp);
    call.enqueue(new Callback<WeatherResponse>() {
      @Override
      public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
        if (response.isSuccessful()) {
          WeatherResponse weatherResponse = response.body();
          if (weatherResponse != null) {
            String json = Json.encode(weatherResponse);
            routingContext.response()
              .putHeader("content-type", "application/json")
              .end(json);

          } else {
            routingContext.response().setStatusCode(404).end(Json.encode(new JsonObject().put("message", "City not found")));
          }
        } else {
          try {
            String errorBody = response.errorBody().string();
            JsonObject errorJson = new JsonObject(errorBody);
            System.err.println("Error body: " + errorBody);
            routingContext.response().setStatusCode(response.code()).end(Json.encode(errorJson));
          } catch (Exception e) {
            routingContext.response().setStatusCode(500).end(Json.encode(new JsonObject().put("message", "Failed to fetch weather data")));
          }
        }
      }

      @Override
      public void onFailure(Call<WeatherResponse> call, Throwable t) {
        routingContext.response().setStatusCode(500).end(Json.encode(new JsonObject().put("message", "Error: " + t.getMessage())));
      }
    });
  }
}
