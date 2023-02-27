package ar.edu.unlp.info.bd2.promocionbd2.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.elasticsearch.common.geo.GeoPoint;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.GeoPointField;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Document(collection = "accident")
@Table(name = "accident")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "accident")
public class Accident {

    @Column(name = "id")
    @Field(name = "ID")
    @Id
    private String id;

    @Column(name = "source")
    @Field(name = "Source")
    @org.springframework.data.elasticsearch.annotations.Field(name = "source")
    private String source;

    @Column(name = "tmc")
    @Field(name = "TMC")
    private String tmc;

    @Column(name = "severity")
    @Field(name = "Severity")
    private Integer severity;

    @Column(name = "start_time")
    @Field(name = "Start_Time")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Date, pattern = "yyyy-MM-dd HH:mm:ss", name = "start_time")
    private Date startTime;

    @Column(name = "end_time")
    @Field(name = "End_Time")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Date, pattern = "yyyy-MM-dd HH:mm:ss", name = "end_time")
    private Date endTime;

    @Column(name = "start_lat")
    @Field(name = "Start_Lat")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Double, name = "start_lat")
    private Double startLat;

    @Column(name = "start_lng")
    @Field(name = "Start_Lng")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Double, name = "start_lng")
    private Double startLng;

    @Column(name = "end_lat")
    @Field(name = "End_Lat")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Double, name = "end_lat")
    private Double endLat;

    @Column(name = "end_lng")
    @Field(name = "End_Lng")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Double, name = "end_lng")
    private Double endLng;

    @Column(name = "distance")
    @Field(name = "Distance")
    @org.springframework.data.elasticsearch.annotations.Field(name = "distance_mi")
    private Double distance;

    @Column(name = "description")
    @Field(name = "Description")
    private String description;

    @Column(name = "number")
    @Field(name = "Number")
    private Double number;

    @Column(name = "street")
    @Field(name = "Street")
    private String street;

    @Column(name = "side")
    @Field(name = "Side")
    private String side;

    @Column(name = "city")
    @Field(name = "City")
    private String city;

    @Column(name = "county")
    @Field(name = "County")
    private String county;

    @Column(name = "state")
    @Field(name = "State")
    private String state;

    @Column(name = "zipcode")
    @Field(name = "Zipcode")
    private String zipcode;

    @Column(name = "country")
    @Field(name = "Country")
    private String country;

    @Column(name = "timezone")
    @Field(name = "timezone")
    private String timezone;

    @Column(name = "airport_code")
    @Field(name = "Airport_Code")
    @org.springframework.data.elasticsearch.annotations.Field(name = "airport_code")
    private String airportCode;

    @Column(name = "weather_timestamp")
    @Field(name = "Weather_Timestamp")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Date, pattern = "yyyy-MM-dd HH:mm:ss", name = "weather_timestamp")
    private Date weatherTimestamp;

    @Column(name = "temperature")
    @Field(name = "Temperature")
    @org.springframework.data.elasticsearch.annotations.Field(name = "temperature_f")
    private Double temperature;

    @Column(name = "wind_chill")
    @Field(name = "Wind_Chill")
    @org.springframework.data.elasticsearch.annotations.Field(name = "wind_chill_f")
    private Double windChill;

    @Column(name = "humidity")
    @Field(name = "Humidity")
    private Double humidity;

    @Column(name = "pressure")
    @Field(name = "Pressure")
    @org.springframework.data.elasticsearch.annotations.Field(name = "pressure_in")
    private Double pressure;

    @Column(name = "visibility")
    @Field(name = "Visibility")
    @org.springframework.data.elasticsearch.annotations.Field(name = "visibility_mi")
    private Double visibility;

    @Column(name = "wind_direction")
    @Field(name = "Wind_Direction")
    @org.springframework.data.elasticsearch.annotations.Field(name = "wind_direction")
    private String windDirection;

    @Column(name = "wind_speed")
    @Field(name = "Wind_Speed")
    @org.springframework.data.elasticsearch.annotations.Field(name = "wind_speed_mph")
    private Double windSpeed;

    @Column(name = "precipitation")
    @Field(name = "Precipitation")
    @org.springframework.data.elasticsearch.annotations.Field(name = "precipitation_in")
    private Double precipitation;

    @Column(name = "weather_condition")
    @Field(name = "Weather_Condition")
    @org.springframework.data.elasticsearch.annotations.Field(name = "weather_condition")
    private String weatherCondition;

    @Column(name = "amenity")
    @Field(name = "Amenity")
    private Boolean amenity;

    @Column(name = "bump")
    @Field(name = "Bump")
    private Boolean bump;

    @Column(name = "crossing")
    @Field(name = "Crossing")
    private Boolean crossing;

    @Column(name = "give_way")
    @Field(name = "Give_Way")
    @org.springframework.data.elasticsearch.annotations.Field(name = "give_way")
    private Boolean giveWay;

    @Column(name = "junction")
    @Field(name = "Junction")
    private Boolean junction;

    @Column(name = "no_exit")
    @Field(name = "No_Exit")
    @org.springframework.data.elasticsearch.annotations.Field(name = "no_exit")
    private Boolean noExit;

    @Column(name = "railway")
    @Field(name = "Railway")
    private Boolean railway;

    @Column(name = "roundabout")
    @Field(name = "Roundabout")
    private Boolean roundabout;

    @Column(name = "station")
    @Field(name = "Station")
    private Boolean station;

    @Column(name = "stop")
    @Field(name = "Stop")
    private Boolean stop;

    @Column(name = "traffic_calming")
    @Field(name = "Traffic_Calming")
    @org.springframework.data.elasticsearch.annotations.Field(name = "traffic_calming")
    private Boolean trafficCalming;

    @Column(name = "traffic_signal")
    @Field(name = "Traffic_Signal")
    @org.springframework.data.elasticsearch.annotations.Field(name = "traffic_signal")
    private Boolean trafficSignal;

    @Column(name = "turning_loop")
    @Field(name = "Turning_Loop")
    @org.springframework.data.elasticsearch.annotations.Field(name = "turning_loop")
    private Boolean turningLoop;

    @Column(name = "sunrise_sunset")
    @Field(name = "Sunrise_Sunset")
    @org.springframework.data.elasticsearch.annotations.Field(name = "sunrise_sunset")
    private String sunriseSunset;

    @Column(name = "civil_twilight")
    @Field(name = "Civil_Twilight")
    @org.springframework.data.elasticsearch.annotations.Field(name = "civil_twilight")
    private String civilTwilight;

    @Column(name = "nautical_twilight")
    @Field(name = "Nautical_Twilight")
    @org.springframework.data.elasticsearch.annotations.Field(name = "nautical_twilight")
    private String nauticalTwilight;

    @Column(name = "astronomical_twilight")
    @Field(name = "Astronomical_Twilight")
    @org.springframework.data.elasticsearch.annotations.Field(name = "astronomical_twilight")
    private String astronomicalTwilight;

    @JsonIgnore
    @GeoPointField
    @Transient
    private @GeoSpatialIndexed Point location;

    public Point location() {
        return this.location;
    }

    public GeoPoint geoPoint() {
        return new GeoPoint(this.startLat, this.startLng);
    }
}
