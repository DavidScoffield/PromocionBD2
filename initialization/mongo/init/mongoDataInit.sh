#!/bin/bash

# tail -n +2 $1 | mongoimport --host localhost --db $2 --collection $3 --type csv --headerline --fieldTypeString --fieldsType < $1
# tail -n +2 /resources/tmp/US_Accidents_Dec19.csv > /resources/tmp/US_Accidents_Dec19-without_header.csv

echo "------------ |X| Importing data into MongoDB |X| ------------"

mongoimport --username=root --password=password --port=27017 --authenticationDatabase "admin" --db database --collection accident --type=csv --drop --parseGrace=autoCast --columnsHaveTypes --fieldFile=/resources/init/fieldsNamesCsv.txt --file=/resources/csv/US_Accidents_Dec19-copia.csv

echo "------------ |X| Successfully importation |X| ------------"

mongo --quiet --username=root --password=password </resources/init/createIndexes.js

echo "------------ |X| SUCCESSFULLY INITIALIZACION OF DATABASE |X| ------------"
