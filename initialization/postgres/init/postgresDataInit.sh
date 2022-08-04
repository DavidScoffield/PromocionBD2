#!/bin/bash

# psql -d database --user=root -c "\COPY accidents_test FROM '/resources/tmp/US_Accidents_Dec19_short-10.csv' DELIMITER ',' CSV HEADER"
echo "------------ |X| Importing data into MongoDB |X| ------------"

psql -d $POSTGRES_DB --user=$POSTGRES_USER --set=csvpath="/resources/csv/$NAME_CSV_FILE.csv" -f /resources/init/commands.sql

echo "------------ |X| SUCCESSFULLY INITIALIZACION POSTGRES DATABASE |X| ------------"
