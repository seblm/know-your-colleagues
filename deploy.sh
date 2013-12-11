#!/bin/sh

mvn clean package
bees app:deploy --appid seblm/know-your-colleagues --endPoint eu -R java_version=1.8 --type tomcat7 target/*.war
