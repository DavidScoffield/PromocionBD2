#!/bin/bash

# tail -n +2 $1 | mongoimport --host localhost --db $2 --collection $3 --type csv --headerline --fieldTypeString --fieldsType < $1
# tail -n +2 /resources/tmp/US_Accidents_Dec19.csv > /resources/tmp/US_Accidents_Dec19-without_header.csv

echo "------------ |X| Importing data into MongoDB |X| ------------"

mongoimport --username=$MONGO_INITDB_ROOT_USERNAME --password=$MONGO_INITDB_ROOT_PASSWORD --port=27017 --authenticationDatabase "admin" --db $MONGO_INITDB_DATABASE --collection accident --type=csv --drop --parseGrace=autoCast --columnsHaveTypes --fieldFile=/resources/init/fieldsNamesCsv.txt --file=/resources/csv/US_Accidents_Dec19-copia.csv

echo "------------ |X| Successfully importation |X| ------------"

mongo --quiet --username=$MONGO_INITDB_ROOT_USERNAME --password=$MONGO_INITDB_ROOT_PASSWORD </resources/init/createIndexes.js

echo "------------ |X| SUCCESSFULLY INITIALIZACION MONGO DATABASE |X| ------------"
