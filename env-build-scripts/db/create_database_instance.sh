#!/bin/bash
set -e

docker run --name working-hour-mgmt -p 33306:3306 -e MYSQL_ROOT_PASSWORD=admin -d mysql:8
