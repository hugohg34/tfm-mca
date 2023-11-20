#!/bin/bash

# Lista de URLs
URLS=("http://localhost:8080/establishments/add" "http://localhost:8080/rooms" "http://localhost:8080/rooms/add" "http://localhost:8080/", "http://localhost:8080/establishments")
#URLS=("http://localhost:8081/establishments")

# N peticiones concurrentes
CONCURRENT_REQUESTS=50

# Duración total en segundos
DURATION=60

#  Intervalo
INTERVAL=1

# params
if [ -n "$1" ]; then
  DURATION=$1
fi

if [ -n "$2" ]; then
  CONCURRENT_REQUESTS=$2
fi
if [ -n "$3" ]; then
  INTERVAL=$3
fi


# Función para enviar una petición
send_request() {
  while true; do
    # Seleccionar una URL aleatoria de la lista
    RANDOM_INDEX=$((RANDOM % ${#URLS[@]}))
    RANDOM_URL="${URLS[$RANDOM_INDEX]}"

    # Realizar una solicitud HTTP usando curl en segundo plano (-s)
    (curl -s -o /dev/null -w "%{http_code}\t" "$RANDOM_URL" &)

    # Esperar el tiempo de intervalo antes de realizar la siguiente solicitud
    sleep $INTERVAL
  done
}

# Iniciar las peticiones concurrentes en segundo plano
for i in $(seq 1 $CONCURRENT_REQUESTS); do
  send_request &
done

# Esperar la duración especificada y luego terminar el script
sleep $DURATION

# Matar todos los procesos hijos (peticiones en segundo plano)
kill 0
