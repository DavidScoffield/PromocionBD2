#!/bin/bash

# psql -d database --user=root -c "\COPY accidents_test FROM '/resources/tmp/US_Accidents_Dec19_short-10.csv' DELIMITER ',' CSV HEADER"

psql -d database --user=root -f /resources/postgres/commands.sql
