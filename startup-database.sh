#!/usr/bin/env bash
docker-compose up -d mongodb.bytebank.api
sleep 5
echo 'Creating MongoDB Indexes...'
docker exec mongodb.bytebank.api sh -c "mongo mongodb://localhost:27017/bytebank --eval 'db.account.createIndex({\"document\":1}, { unique: true });'"
echo 'MongoDB Indexes created...'