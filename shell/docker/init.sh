#!/bin/bash

# Function to wait for MySQL to be healthy
wait_for_mysql() {
  local max_attempts=30
  local attempt=0

  echo "Waiting for MySQL to be healthy..."

  while [ $attempt -lt $max_attempts ]; do
    if mysql -h mysql -u root -p${mysql_password} -e "SELECT 1;" > /dev/null 2>&1; then
      echo "MySQL is healthy."
      return 0
    fi
    echo "Attempt $((attempt+1)) failed. Retrying in 10 seconds..."
    sleep 10
    attempt=$((attempt+1))
  done

  echo "MySQL failed to become healthy."
  exit 1
}

# Function to wait for Redis to be healthy
wait_for_redis() {
  local max_attempts=30
  local attempt=0

  echo "Waiting for Redis to be healthy..."

  while [ $attempt -lt $max_attempts ]; do
    if redis-cli -h redis -a ${redis_password} PING | grep -q "PONG"; then
      echo "Redis is healthy."
      return 0
    fi
    echo "Attempt $((attempt+1)) failed. Retrying in 10 seconds..."
    sleep 10
    attempt=$((attempt+1))
  done

  echo "Redis failed to become healthy."
  exit 1
}

# Function to wait for Kafka to be healthy
wait_for_kafka() {
  local max_attempts=30
  local attempt=0

  echo "Waiting for Kafka to be healthy..."

  while [ $attempt -lt $max_attempts ]; do
    if curl -s http://kafka:8083/connectors | grep -q "200"; then
      echo "Kafka is healthy."
      return 0
    fi
    echo "Attempt $((attempt+1)) failed. Retrying in 10 seconds..."
    sleep 10
    attempt=$((attempt+1))
  done

  echo "Kafka failed to become healthy."
  exit 1
}

# Function to wait for Elasticsearch to be healthy
wait_for_elasticsearch() {
  local max_attempts=30
  local attempt=0

  echo "Waiting for Elasticsearch to be healthy..."

  while [ $attempt -lt $max_attempts ]; do
    if curl -s -u elastic:${elasticsearch_password} "http://elasticsearch:9200" | grep -q "200"; then
      echo "Elasticsearch is healthy."
      return 0
    fi
    echo "Attempt $((attempt+1)) failed. Retrying in 10 seconds..."
    sleep 10
    attempt=$((attempt+1))
  done

  echo "Elasticsearch failed to become healthy."
  exit 1
}

# Wait for MySQL
wait_for_mysql

# Initialize MySQL
echo "Initializing MySQL..."
mysql -h mysql -u root -p${mysql_password} -e "CREATE DATABASE IF NOT EXISTS mydb; USE mydb; CREATE TABLE IF NOT EXISTS users (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255));"
echo "MySQL initialization complete."

# Wait for Redis
wait_for_redis

# Initialize Redis
echo "Initializing Redis..."
redis-cli -h redis -a ${redis_password} SET mykey "Hello Redis"
echo "Redis initialization complete."

# Wait for Kafka
wait_for_kafka

# Initialize Kafka
echo "Initializing Kafka..."
# Kafka initialization can be more complex, here we just create a topic
kafka-topics.sh --create --topic my_topic --bootstrap-server kafka:9092 --replication-factor 1 --partitions 1
echo "Kafka initialization complete."

# Wait for Elasticsearch
wait_for_elasticsearch

# Initialize Elasticsearch
echo "Initializing Elasticsearch..."
curl -u elastic:${elasticsearch_password} -X PUT "http://elasticsearch:9200/my_index" -H 'Content-Type: application/json' -d'
{
  "mappings": {
    "properties": {
      "field1": { "type": "text" },
      "field2": { "type": "keyword" }
    }
  }
}
'
echo "Elasticsearch initialization complete."

echo "All services initialized."
