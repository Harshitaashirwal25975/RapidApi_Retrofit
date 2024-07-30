package com.example.new_;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.ebean.DB;

import java.util.Date;

public class AuthHandler {

  private static final Logger logger = LoggerFactory.getLogger(AuthHandler.class);
  private static final String SECRET_KEY = "YWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYQ=="; // Replace with your actual secret key

  public void handleLogin(RoutingContext routingContext) {
    try {
      JsonObject json = routingContext.getBodyAsJson();
      if (json == null) {
        throw new IllegalArgumentException("Request body is empty or not in JSON format");
      }

      String email = json.getString("email");
      String password = json.getString("password");

      if (email == null || password == null) {
        throw new IllegalArgumentException("Missing required fields: email and/or password");
      }

      boolean isAuthenticated = authenticateUser(email, password);

      if (isAuthenticated) {
        String token = generateToken(email);
        JsonObject response = new JsonObject().put("token", token);
        routingContext.response()
          .setStatusCode(200)
          .putHeader("Content-Type", "application/json")
          .end(response.encode());
      } else {
        routingContext.response()
          .setStatusCode(401)
          .end("Invalid email or password");
      }
    } catch (Exception e) {
      logger.error("Login error: {}", e.getMessage(), e);
      routingContext.response()
        .setStatusCode(500)
        .end("Internal server error:" + e.getMessage());
    }
  }

  public boolean validateToken(RoutingContext routingContext) {
    try {
      System.out.println("valid token");

      String token = routingContext.request().getHeader("Authorization");
      if (token == null || !token.startsWith("Bearer ")) {
        routingContext.response().setStatusCode(401).end("Unauthorized: No token provided");
        System.out.println("invalid token");
        return false;
      }

      token = token.substring(7); // Remove "Bearer " prefix

      Claims claims = Jwts.parser()
        .setSigningKey(SECRET_KEY)
        .parseClaimsJws(token)
        .getBody();

      routingContext.put("userEmail", claims.getSubject());
      return true;
    } catch (Exception e) {
      routingContext.response().setStatusCode(401).end("Unauthorized: Invalid token");
      return false;
    }
  }

  private boolean authenticateUser(String email, String password) {
    try {
      User user = DB.find(User.class).where().eq("email", email).findOne();
      return user != null && BCrypt.checkpw(password, user.getPassword());
    } catch (Exception e) {
      // Log the exception
      logger.error("Error during authentication: {}", e.getMessage(), e);
      return false;
    }
  }

  private String generateToken(String email) {
    long now = System.currentTimeMillis();
    long expiryTime = now + 3600_000; // Token valid for 1 hour

    return Jwts.builder()
      .setSubject(email)
      .setIssuedAt(new Date(now))
      .setExpiration(new Date(expiryTime))
      .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
      .compact();
  }
}
