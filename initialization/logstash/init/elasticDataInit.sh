#!/bin/bash

echo "------------ |X| Creating geo_point |X| ------------"

finished=false

until $finished; do
  curl -XPUT "$ELASTICSEARCH_HOSTS/accident" --silent -H 'Content-Type: application/json' -d'
  {
    "mappings": {
      "properties": {
        "location": { "type":"geo_point" },
        "start_time": {
          "type": "date",
          "format": "yyyy-MM-dd HH:mm:ss"
        }
      }
    }
  }
  '
  if [ $? -eq 0 ]; then
    finished=true
    echo "------------ |X| Successful creation geo_point |X| ------------"
  else
    echo "------------ |X| Waiting for elasticsearch |X| ------------"
    sleep 3
  fi
done

echo "------------ |X| RUN docker-entrypoint.sh |X| ------------"

bash "/usr/local/bin/docker-entrypoint"
