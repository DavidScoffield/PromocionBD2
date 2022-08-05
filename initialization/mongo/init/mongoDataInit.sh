#!/bin/bash

# tail -n +2 $1 | mongoimport --host localhost --db $2 --collection $3 --type csv --headerline --fieldTypeString --fieldsType < $1

echo "------------ |X| START Formating CSV |X| ------------"

tail -n +2 /resources/csv/$NAME_CSV_FILE.csv >/tmp/csv.csv

echo "------------ |X| FINISH Formating CSV |X| ------------"

echo "------------ |X| Importing data into MongoDB |X| ------------"
echo "Import started at: $(date +%T)"

mongoimport --username=$MONGO_INITDB_ROOT_USERNAME --password=$MONGO_INITDB_ROOT_PASSWORD --port=27017 --authenticationDatabase "admin" --db $MONGO_INITDB_DATABASE --collection accident --type=csv --drop --parseGrace=autoCast --columnsHaveTypes --fieldFile=/resources/init/fieldsNamesCsv.txt --file=/tmp/csv.csv

echo "Import finished at: $(date +%T)"
echo "------------ |X| Successfully importation |X| ------------"

mongo --quiet --username=$MONGO_INITDB_ROOT_USERNAME --password=$MONGO_INITDB_ROOT_PASSWORD </resources/init/createIndexes.js

echo "------------ |X| SUCCESSFULLY INITIALIZACION MONGO DATABASE |X| ------------"
