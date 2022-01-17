#!/usr/bin/env bash

REPOSITORY=/home/ec2-user/green-challenge
cd $REPOSITORY

APP_NAME=Green-Challenge
JAR_NAME=$(ls $REPOSITORY/target/ | grep 'SNAPSHOT.jar' | head -n 1)
JAR_PATH=$REPOSITORY/target/$JAR_NAME

CURRENT_PID=$(pgrep -f $APP_NAME)

if [ -z $CURRENT_PID ]
then
  echo "> 종료할것 없음."
else
  echo "> kill -9 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi

echo "> $JAR_PATH 배포"
nohup java -jar $JAR_PATH >> /home/ec2-user/deploy.log 2>/home/ec2-user/deploy_err.log &
