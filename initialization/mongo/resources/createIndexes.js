const databaseName = _getEnv('MONGO_INITDB_DATABASE')

db = db.getSiblingDB(databaseName)

print('------------ |X| Creating location field |X| ------------')
db.accident.updateMany({}, [
  {
    $addFields: {
      location: {
        type: 'Point',
        coordinates: ['$Start_Lng', '$Start_Lat'],
      },
    },
  },
])
print('------------ |X| FINISH creation location field |X| ------------')

print('------------ |X| Creating 2dsphere index over location field |X| ------------')
db.accident.createIndex({ location: '2dsphere' })
print('------------ |X| FINISH creation 2dsphere index over location field |X| ------------')
