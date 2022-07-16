package ar.edu.unlp.info.bd2.promocionbd2.beans;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class AccidentBean extends CsvBean {

  @CsvBindByName(column = "ID")
  private String id;

  @CsvBindByName(column = "Source")
  private String source;

  @CsvBindByName(column = "TMC")
  private String tmc;

  @CsvBindByName(column = "Severity")
  private int severity;

  @CsvBindByName(column = "Start_Time")
  private Timestamp start_time;

  @CsvBindByName(column = "End_Time")
  private Timestamp end_time;

  @CsvBindByName(column = "Start_Lat")
  private double start_lat;

  @CsvBindByName(column = "Start_Lng")
  private double start_lng;

  @CsvBindByName(column = "End_Lat")
  private double end_lat;

  @CsvBindByName(column = "End_Lng")
  private double end_lng;

  @CsvBindByName(column = "Distance")
  private double distance;

  @CsvBindByName(column = "Description")
  private String description;

  @CsvBindByName(column = "Number")
  private double number;

  @CsvBindByName(column = "Street")
  private String street;

  @CsvBindByName(column = "Side")
  private String side;

  @CsvBindByName(column = "City")
  private String city;

  @CsvBindByName(column = "County")
  private String county;

  @CsvBindByName(column = "State")
  private String state;

  @CsvBindByName(column = "Zipcode")
  private String zipcode;

  @CsvBindByName(column = "Country")
  private String country;

  @CsvBindByName(column = "Timezone")
  private String timezone;

  @CsvBindByName(column = "Airport_Code")
  private String airport_code;

  @CsvBindByName(column = "Weather_Timestamp")
  private Timestamp weather_timestamp;

  @CsvBindByName(column = "Temperature")
  private double temperature;

  @CsvBindByName(column = "Wind_Chill")
  private double wind_chill;

  @CsvBindByName(column = "Humidity")
  private double humidity;

  @CsvBindByName(column = "Pressure")
  private double pressure;

  @CsvBindByName(column = "Visibility")
  private double visibility;

  @CsvBindByName(column = "Wind_Direction")
  private String wind_direction;

  @CsvBindByName(column = "Wind_Speed")
  private double wind_speed;

  @CsvBindByName(column = "Precipitation")
  private double precipitation;

  @CsvBindByName(column = "Weather_Condition")
  private String weather_condition;

  @CsvBindByName(column = "Amenity")
  private boolean amenity;

  @CsvBindByName(column = "Bump")
  private boolean bump;

  @CsvBindByName(column = "Crossing")
  private boolean crossing;

  @CsvBindByName(column = "Give_Way")
  private boolean give_Way;

  @CsvBindByName(column = "Junction")
  private boolean junction;

  @CsvBindByName(column = "No_Exit")
  private boolean no_exit;

  @CsvBindByName(column = "Railway")
  private boolean railway;

  @CsvBindByName(column = "Roundabout")
  private boolean roundabout;

  @CsvBindByName(column = "Station")
  private boolean station;

  @CsvBindByName(column = "Stop")
  private boolean stop;

  @CsvBindByName(column = "Traffic_Calming")
  private boolean traffic_calming;

  @CsvBindByName(column = "Traffic_Signal")
  private boolean traffic_signal;

  @CsvBindByName(column = "Turning_Loop")
  private boolean turning_loop;

  @CsvBindByName(column = "Sunrise_Sunset")
  private String sunrise_sunset;

  @CsvBindByName(column = "Civil_Twilight")
  private String civil_twilight;

  @CsvBindByName(column = "Nautical_Twilight")
  private String nautical_twilight;

  @CsvBindByName(column = "Astronomical_Twilight")
  private String astronomical_twilight;
}
