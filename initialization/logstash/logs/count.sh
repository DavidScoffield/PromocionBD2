#!/bin/bash

echo ""
echo "------------ |X| Press Ctrl+C to stop the script |X| ------------"

while true; do
  RESPONSE=$(curl -X GET "localhost:9200/accident/_count" --silent)

  # if [ -z "$RESPONSE" ]; then
  #   echo "No response from the server"
  # fi

  # Check if jq command is available
  if ! command -v jq &>/dev/null; then
    COUNT=$(echo $RESPONSE | sed -n 's/.*"count":\([0-9]*\).*/\1/p')
  else
    COUNT=$(echo $RESPONSE | jq '.count')
  fi

  echo "Number of records stored up to the hour ($(date +%T)) -> |"$COUNT"|"
  sleep 1
done
