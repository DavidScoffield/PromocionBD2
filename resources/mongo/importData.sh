#!/bin/bash

# tail -n +2 $1 | mongoimport --host localhost --db $2 --collection $3 --type csv --headerline --fieldTypeString --fieldsType < $1
# tail -n +2 /resources/tmp/US_Accidents_Dec19.csv > /resources/tmp/US_Accidents_Dec19-without_header.csv

# With field types
mongoimport --username=root --password=password --port=27017 --authenticationDatabase "admin" --db database --collection accident --type=csv --drop --parseGrace=autoCast --columnsHaveTypes --fieldFile=/resources/mongo/fieldsNamesCsv.txt --file=/resources/tmp/US_Accidents_Dec19-copia.csv

# Without field types
# mongoimport --username=root --password=password --port=27017 --authenticationDatabase "admin" --db database --collection accident --type=csv --drop --headerline --file=/resources/tmp/US_Accidents_Dec19.csv

mongosh --username=root --password=password </resources/mongo/createIndexes.js
