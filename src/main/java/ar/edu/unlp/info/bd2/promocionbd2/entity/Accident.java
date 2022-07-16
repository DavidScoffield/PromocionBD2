package ar.edu.unlp.info.bd2.promocionbd2.entity;

import ar.edu.unlp.info.bd2.promocionbd2.beans.CsvBean;
import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Document(collection = "accident")
@Table(name = "accident")
public class Accident extends CsvBean {

    @Id
    @Column(name = "id")
    @Field(name = "ID")
    @CsvBindByName(column = "ID")
    private String id;

    @Column(name = "source")
    @Field(name = "Source")
    @CsvBindByName(column = "Source")
    private String source;

    @Column(name = "tmc")
    @Field(name = "TMC")
    @CsvBindByName(column = "TMC")
    private String tmc;

    @Column(name = "severity")
    @Field(name = "Severity")
    @CsvBindByName(column = "Severity")
    private Integer severity;

    @Column(name = "start_time")
    @Field(name = "Start_Time")
    @CsvBindByName(column = "Start_Time")
    private Date startTime;

    @Column(name = "end_time")
    @Field(name = "End_Time")
    @CsvBindByName(column = "End_Time")
    private Date endTime;

    @Column(name = "start_lat")
    @Field(name = "Start_Lat")
    @CsvBindByName(column = "Start_Lat")
    private Double startLat;

    @Column(name = "start_lng")
    @Field(name = "Start_Lng")
    @CsvBindByName(column = "Start_Lng")
    private Double startLng;

    @Column(name = "end_lat")
    @Field(name = "End_Lat")
    @CsvBindByName(column = "End_Lat")
    private Double endLat;

    @Column(name = "end_lng")
    @Field(name = "End_Lng")
    @CsvBindByName(column = "End_Lng")
    private Double endLng;

    @Column(name = "distance")
    @Field(name = "Distance")
    @CsvBindByName(column = "Distance")
    private Double distance;

    @Column(name = "description")
    @Field(name = "Description")
    @CsvBindByName(column = "Description")
    private String description;

    @Column(name = "number")
    @Field(name = "Number")
    @CsvBindByName(column = "Number")
    private Double number;

    @Column(name = "street")
    @Field(name = "Street")
    @CsvBindByName(column = "Street")
    private String street;

    @Column(name = "side")
    @Field(name = "Side")
    @CsvBindByName(column = "Side")
    private String side;

    @Column(name = "city")
    @Field(name = "City")
    @CsvBindByName(column = "City")
    private String city;

    @Column(name = "county")
    @Field(name = "County")
    @CsvBindByName(column = "County")
    private String county;

    @Column(name = "state")
    @Field(name = "State")
    @CsvBindByName(column = "State")
    private String state;

    @Column(name = "zipcode")
    @Field(name = "Zipcode")
    @CsvBindByName(column = "Zipcode")
    private String zipcode;

    @Column(name = "country")
    @Field(name = "Country")
    @CsvBindByName(column = "Country")
    private String country;

    @Column(name = "timezone")
    @CsvBindByName(column = "Timezone")
    private String timezone;

    @Column(name = "airport_code")
    @Field(name = "Airport_Code")
    @CsvBindByName(column = "Airport_Code")
    private String airportCode;

    @Column(name = "weather_timestamp")
    @Field(name = "Weather_Timestamp")
    @CsvBindByName(column = "Weather_Timestamp")
    private Date weatherTimestamp;

    @Column(name = "temperature")
    @Field(name = "Temperature")
    @CsvBindByName(column = "Temperature")
    private Double temperature;

    @Column(name = "wind_chill")
    @Field(name = "Wind_Chill")
    @CsvBindByName(column = "Wind_Chill")
    private Double windChill;

    @Column(name = "humidity")
    @Field(name = "Humidity")
    @CsvBindByName(column = "Humidity")
    private Double humidity;

    @Column(name = "pressure")
    @Field(name = "Pressure")
    @CsvBindByName(column = "Pressure")
    private Double pressure;

    @Column(name = "visibility")
    @Field(name = "Visibility")
    @CsvBindByName(column = "Visibility")
    private Double visibility;

    @Column(name = "wind_direction")
    @Field(name = "Wind_Direction")
    @CsvBindByName(column = "Wind_Direction")
    private String windDirection;

    @Column(name = "wind_speed")
    @Field(name = "Wind_Speed")
    @CsvBindByName(column = "Wind_Speed")
    private Double windSpeed;

    @Column(name = "precipitation")
    @Field(name = "Precipitation")
    @CsvBindByName(column = "Precipitation")
    private Double precipitation;

    @Column(name = "weather_condition")
    @Field(name = "Weather_Condition")
    @CsvBindByName(column = "Weather_Condition")
    private String weatherCondition;

    @Column(name = "amenity")
    @Field(name = "Amenity")
    @CsvBindByName(column = "Amenity")
    private Boolean amenity;

    @Column(name = "bump")
    @Field(name = "Bump")
    @CsvBindByName(column = "Bump")
    private Boolean bump;

    @Column(name = "crossing")
    @Field(name = "Crossing")
    @CsvBindByName(column = "Crossing")
    private Boolean crossing;

    @Column(name = "give_way")
    @Field(name = "Give_Way")
    @CsvBindByName(column = "Give_Way")
    private Boolean giveWay;

    @Column(name = "junction")
    @Field(name = "Junction")
    @CsvBindByName(column = "Junction")
    private Boolean junction;

    @Column(name = "no_exit")
    @Field(name = "No_Exit")
    @CsvBindByName(column = "No_Exit")
    private Boolean noExit;

    @Column(name = "railway")
    @Field(name = "Railway")
    @CsvBindByName(column = "Railway")
    private Boolean railway;

    @Column(name = "roundabout")
    @Field(name = "Roundabout")
    @CsvBindByName(column = "Roundabout")
    private Boolean roundabout;

    @Column(name = "station")
    @Field(name = "Station")
    @CsvBindByName(column = "Station")
    private Boolean station;

    @Column(name = "stop")
    @Field(name = "Stop")
    @CsvBindByName(column = "Stop")
    private Boolean stop;

    @Column(name = "traffic_calming")
    @Field(name = "Traffic_Calming")
    @CsvBindByName(column = "Traffic_Calming")
    private Boolean trafficCalming;

    @Column(name = "traffic_signal")
    @Field(name = "Traffic_Signal")
    @CsvBindByName(column = "Traffic_Signal")
    private Boolean trafficSignal;

    @Column(name = "turning_loop")
    @Field(name = "Turning_Loop")
    @CsvBindByName(column = "Turning_Loop")
    private Boolean turningLoop;

    @Column(name = "sunrise_sunset")
    @Field(name = "Sunrise_Sunset")
    @CsvBindByName(column = "Sunrise_Sunset")
    private String sunriseSunset;

    @Column(name = "civil_twilight")
    @Field(name = "Civil_Twilight")
    @CsvBindByName(column = "Civil_Twilight")
    private String civilTwilight;

    @Column(name = "nautical_twilight")
    @Field(name = "Nautical_Twilight")
    @CsvBindByName(column = "Nautical_Twilight")
    private String nauticalTwilight;

    @Column(name = "astronomical_twilight")
    @Field(name = "Astronomical_Twilight")
    @CsvBindByName(column = "Astronomical_Twilight")
    private String astronomicalTwilight;

    private @GeoSpatialIndexed Point location;
}
