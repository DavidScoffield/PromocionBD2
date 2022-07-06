package ar.edu.unlp.info.bd2.promocionbd2.beans;

import com.opencsv.bean.CsvBindByName;;

public class AccidentBean extends CsvBean {

  @CsvBindByName(column = "ID")
  private String id;

  @CsvBindByName(column = "Source")
  private String source;

  @CsvBindByName(column = "TMC")
  private String tmc;

  @CsvBindByName(column = "Severity")
  private String severity;

  @CsvBindByName(column = "Start_Time")
  private String start_time;

  @CsvBindByName(column = "End_Time")
  private String end_time;

  @CsvBindByName(column = "Start_Lat")
  private String start_lat;

  @CsvBindByName(column = "Start_Lng")
  private String start_lng;

  @CsvBindByName(column = "End_Lat")
  private String end_lat;

  @CsvBindByName(column = "End_Lng")
  private String end_lng;

  @CsvBindByName(column = "Distance")
  private String distance;

  @CsvBindByName(column = "Description")
  private String description;

  @CsvBindByName(column = "Number")
  private String number;

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
  private String weather_timestamp;

  @CsvBindByName(column = "Temperature")
  private String temperature;

  @CsvBindByName(column = "Wind_Chill")
  private String wind_chill;

  @CsvBindByName(column = "Humidity")
  private String humidity;

  @CsvBindByName(column = "Pressure")
  private String pressure;

  @CsvBindByName(column = "Visibility")
  private String visibility;

  @CsvBindByName(column = "Wind_Direction")
  private String wind_direction;

  @CsvBindByName(column = "Wind_Speed")
  private String wind_speed;

  @CsvBindByName(column = "Precipitation")
  private String precipitation;

  @CsvBindByName(column = "Weather_Condition")
  private String weather_condition;

  @CsvBindByName(column = "Amenity")
  private String amenity;

  @CsvBindByName(column = "Bump")
  private String bump;

  @CsvBindByName(column = "Crossing")
  private String crossing;

  @CsvBindByName(column = "Give_Way")
  private String give_Way;

  @CsvBindByName(column = "Junction")
  private String junction;

  @CsvBindByName(column = "No_Exit")
  private String no_exit;

  @CsvBindByName(column = "Railway")
  private String railway;

  @CsvBindByName(column = "Roundabout")
  private String roundabout;

  @CsvBindByName(column = "Station")
  private String station;

  @CsvBindByName(column = "Stop")
  private String stop;

  @CsvBindByName(column = "Traffic_Calming")
  private String traffic_calming;

  @CsvBindByName(column = "Traffic_Signal")
  private String traffic_signal;

  @CsvBindByName(column = "Turning_Loop")
  private String turning_loop;

  @CsvBindByName(column = "Sunrise_Sunset")
  private String sunrise_sunset;

  @CsvBindByName(column = "Civil_Twilight")
  private String civil_twilight;

  @CsvBindByName(column = "Nautical_Twilight")
  private String nautical_twilight;

  @CsvBindByName(column = "Astronomical_Twilight")
  private String astronomical_twilight;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }

  public String getTmc() {
    return tmc;
  }

  public void setTmc(String tmc) {
    this.tmc = tmc;
  }

  public String getSeverity() {
    return severity;
  }

  public void setSeverity(String severity) {
    this.severity = severity;
  }

  public String getStart_time() {
    return start_time;
  }

  public void setStart_time(String start_time) {
    this.start_time = start_time;
  }

  public String getEnd_time() {
    return end_time;
  }

  public void setEnd_time(String end_time) {
    this.end_time = end_time;
  }

  public String getStart_lat() {
    return start_lat;
  }

  public void setStart_lat(String start_lat) {
    this.start_lat = start_lat;
  }

  public String getStart_lng() {
    return start_lng;
  }

  public void setStart_lng(String start_lng) {
    this.start_lng = start_lng;
  }

  public String getEnd_lat() {
    return end_lat;
  }

  public void setEnd_lat(String end_lat) {
    this.end_lat = end_lat;
  }

  public String getEnd_lng() {
    return end_lng;
  }

  public void setEnd_lng(String end_lng) {
    this.end_lng = end_lng;
  }

  public String getDistance() {
    return distance;
  }

  public void setDistance(String distance) {
    this.distance = distance;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public String getSide() {
    return side;
  }

  public void setSide(String side) {
    this.side = side;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getCounty() {
    return county;
  }

  public void setCounty(String county) {
    this.county = county;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getZipcode() {
    return zipcode;
  }

  public void setZipcode(String zipcode) {
    this.zipcode = zipcode;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getTimezone() {
    return timezone;
  }

  public void setTimezone(String timezone) {
    this.timezone = timezone;
  }

  public String getAirport_code() {
    return airport_code;
  }

  public void setAirport_code(String airport_code) {
    this.airport_code = airport_code;
  }

  public String getWeather_timestamp() {
    return weather_timestamp;
  }

  public void setWeather_timestamp(String weather_timestamp) {
    this.weather_timestamp = weather_timestamp;
  }

  public String getTemperature() {
    return temperature;
  }

  public void setTemperature(String temperature) {
    this.temperature = temperature;
  }

  public String getWind_chill() {
    return wind_chill;
  }

  public void setWind_chill(String wind_chill) {
    this.wind_chill = wind_chill;
  }

  public String getHumidity() {
    return humidity;
  }

  public void setHumidity(String humidity) {
    this.humidity = humidity;
  }

  public String getPressure() {
    return pressure;
  }

  public void setPressure(String pressure) {
    this.pressure = pressure;
  }

  public String getVisibility() {
    return visibility;
  }

  public void setVisibility(String visibility) {
    this.visibility = visibility;
  }

  public String getWind_direction() {
    return wind_direction;
  }

  public void setWind_direction(String wind_direction) {
    this.wind_direction = wind_direction;
  }

  public String getWind_speed() {
    return wind_speed;
  }

  public void setWind_speed(String wind_speed) {
    this.wind_speed = wind_speed;
  }

  public String getPrecipitation() {
    return precipitation;
  }

  public void setPrecipitation(String precipitation) {
    this.precipitation = precipitation;
  }

  public String getWeather_condition() {
    return weather_condition;
  }

  public void setWeather_condition(String weather_condition) {
    this.weather_condition = weather_condition;
  }

  public String getAmenity() {
    return amenity;
  }

  public void setAmenity(String amenity) {
    this.amenity = amenity;
  }

  public String getBump() {
    return bump;
  }

  public void setBump(String bump) {
    this.bump = bump;
  }

  public String getCrossing() {
    return crossing;
  }

  public void setCrossing(String crossing) {
    this.crossing = crossing;
  }

  public String getGive_Way() {
    return give_Way;
  }

  public void setGive_Way(String give_Way) {
    this.give_Way = give_Way;
  }

  public String getJunction() {
    return junction;
  }

  public void setJunction(String junction) {
    this.junction = junction;
  }

  public String getNo_exit() {
    return no_exit;
  }

  public void setNo_exit(String no_exit) {
    this.no_exit = no_exit;
  }

  public String getRailway() {
    return railway;
  }

  public void setRailway(String railway) {
    this.railway = railway;
  }

  public String getRoundabout() {
    return roundabout;
  }

  public void setRoundabout(String roundabout) {
    this.roundabout = roundabout;
  }

  public String getStation() {
    return station;
  }

  public void setStation(String station) {
    this.station = station;
  }

  public String getStop() {
    return stop;
  }

  public void setStop(String stop) {
    this.stop = stop;
  }

  public String getTraffic_calming() {
    return traffic_calming;
  }

  public void setTraffic_calming(String traffic_calming) {
    this.traffic_calming = traffic_calming;
  }

  public String getTraffic_signal() {
    return traffic_signal;
  }

  public void setTraffic_signal(String traffic_signal) {
    this.traffic_signal = traffic_signal;
  }

  public String getTurning_loop() {
    return turning_loop;
  }

  public void setTurning_loop(String turning_loop) {
    this.turning_loop = turning_loop;
  }

  public String getSunrise_sunset() {
    return sunrise_sunset;
  }

  public void setSunrise_sunset(String sunrise_sunset) {
    this.sunrise_sunset = sunrise_sunset;
  }

  public String getCivil_twilight() {
    return civil_twilight;
  }

  public void setCivil_twilight(String civil_twilight) {
    this.civil_twilight = civil_twilight;
  }

  public String getNautical_twilight() {
    return nautical_twilight;
  }

  public void setNautical_twilight(String nautical_twilight) {
    this.nautical_twilight = nautical_twilight;
  }

  public String getAstronomical_twilight() {
    return astronomical_twilight;
  }

  public void setAstronomical_twilight(String astronomical_twilight) {
    this.astronomical_twilight = astronomical_twilight;
  }
}
