package com.example.new_;



import java.util.List;

public class WeatherResponse {
  private Location location;
  private Forecast forecast;


  public Location getLocation() {
    return location;
  }

  public void setLocation(Location location) {
    this.location = location;
  }

  public Forecast getForecast() {
    return forecast;
  }

  public void setForecast(Forecast forecast) {
    this.forecast = forecast;
  }

  public static class Location {
    private String code;
    private String name;
    private String timezone;
    private Coordinates coordinates;


    public String getCode() {
      return code;
    }

    public void setCode(String code) {
      this.code = code;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getTimezone() {
      return timezone;
    }

    public void setTimezone(String timezone) {
      this.timezone = timezone;
    }

    public Coordinates getCoordinates() {
      return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
      this.coordinates = coordinates;
    }

    public static class Coordinates {
      private double latitude;
      private double longitude;


      public double getLatitude() {
        return latitude;
      }

      public void setLatitude(double latitude) {
        this.latitude = latitude;
      }

      public double getLongitude() {
        return longitude;
      }

      public void setLongitude(double longitude) {
        this.longitude = longitude;
      }
    }
  }

  public static class Forecast<Item> {
    private List<Item> items;


    public List<Item> getItems() {
      return items;
    }

    public void setItems(List<Item> items) {
      this.items = items;
    }

  }

}

