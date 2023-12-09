#!/bin/sh


if [ -f "/tmp/first-run-complete.temp" ]; then
    echo "Debezium already configured"
    exit 0
fi

# Default values for environment variables used by Debezium
: ${DEBEZIUM_HOST:="debezium"}
: ${DEBEZIUM_PORT:="8083"}
: ${CONNECTOR_NAME:="debezium-connector"}
: ${CONNECTOR_CLASS:="io.debezium.connector.postgresql.PostgresConnector"}
: ${DB_HOSTNAME:="postgres"}
: ${DB_PORT:="5432"}
: ${DB_USER:="postgres"}
: ${DB_PASSWORD:="postgres"}
: ${DB_NAME:="postgresdb"}
: ${DB_SERVER_ID:="1001"}
: ${TOPIC_PREFIX:="dbserver1"}

: ${PLUGIN_NAME:="pgoutput"}

post_connector_conf() {
    curl -X POST -H "Accept:application/json" -H "Content-Type:application/json" ${DEBEZIUM_HOST}:${DEBEZIUM_PORT}/connectors/ -d "
    {
     \"name\": \"$CONNECTOR_NAME\",
     \"config\": {
       \"connector.class\": \"$CONNECTOR_CLASS\",
       \"database.hostname\": \"$DB_HOSTNAME\",
       \"database.port\": \"$DB_PORT\",
       \"database.user\": \"$DB_USER\",
       \"database.password\": \"$DB_PASSWORD\",
       \"database.dbname\" : \"$DB_NAME\",
       \"database.server.id\": \"$DB_SERVER_ID\",
       \"topic.prefix\": \"$TOPIC_PREFIX\",
       \"plugin.name\": \"$PLUGIN_NAME\"
     }
    }"
}

check_debezium() {
    response=$(curl -s -o /dev/null -w "%{http_code}" ${DEBEZIUM_HOST}:${DEBEZIUM_PORT})
    if [ "$response" = "200" ]; then
        return 0
    else
        return 1
    fi
}

max_attempts=30
attempt=1
while [ $attempt -le $max_attempts ]; do
  echo "Check Debezium (attempt $attempt of $max_attempts)"
  if check_debezium; then
    echo "Debezium is ready. POST request..."
    if post_connector_conf; then
      echo "Config sent to Debezium"
      touch /tmp/first-run-complete.temp
      break
    fi
  else
    echo "Debezium is not ready yet."
  fi
  attempt=$(( attempt + 1 ))
  sleep 10
done

if [ $attempt -gt $max_attempts ]; then
  echo "Impossible to connect to Debezium after several attempts"
    exit 1
fi
