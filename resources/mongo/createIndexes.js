use database
db.accident.updateMany({}, [
  { $addFields: {
      location: {
          "type": "Point",
          "coordinates": ["$Start_Lng","$Start_Lat"]
        }}
  },
])
db.accident.createIndex({ location: '2dsphere' })
