ARG=$1
if [[ $ARG == "down" ]]; then
  docker-compose -f ./docker-compose.yml down
else
  docker-compose -f ./docker-compose.yml up -d --renew-anon-volumes --remove-orphans
  sleep 10s
  java -jar ./target/marvel.jar 
fi