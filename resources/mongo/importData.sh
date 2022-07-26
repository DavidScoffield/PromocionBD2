#!/bin/bash

mongoimport --username=root --password=password --port=27017 --authenticationDatabase "admin" --db database --collection accident --type=csv --drop --parseGrace=autoCast --columnsHaveTypes --fieldFile=/resources/mongo/fieldsNamesCsv.txt --file=/resources/tmp/US_Accidents_Dec19-copia.csv

mongosh --username=root --password=password </resources/mongo/createIndexes.js
