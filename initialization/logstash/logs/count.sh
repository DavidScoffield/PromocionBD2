#!/bin/bash

echo ""
echo "------------ |X| Press Ctrl+C to stop the script |X| ------------"

while true; do
  IP=$(curl -X GET "localhost:9200/accidents/_count" --silent | jq '.count')
  echo "Number of records stored up to the hour ($(date +%T)) -> |"$IP"|"
  sleep 1
done
