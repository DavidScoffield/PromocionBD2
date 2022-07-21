# PromocionBD2

## ##

## Instrucciones 
1. import csv to database with Mongo Compass 
	- Set all timestamp-like fields with Date type 
	
  
2. Run the following query to merge longitud and latitud fields into a GeoJSON field

```
db.accident.updateMany({}, [
    { $addFields: { 
        location: {
            "type": "Point",
            "coordinates": ["$Start_Lng","$Start_Lat"]
          }}
    },
])

```

(in my case it took 19s)
	
	
	
3. Run the following command to create a 2dsphere index

```
db.accident.createIndex({location: "2dsphere"})
```
