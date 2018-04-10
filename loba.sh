#!/bin/bash
git config --global user.email "deepali.arvind@metamagic.in"
git config --global user.name "deepali-arvind"

apt-get -y install zip unzip

COMMIT_TIMESTAMP=`date +'%Y-%m-%d %H:%M:%S %Z'`

sed -i  "s|localhost:8081|config.desire3d.com:5030|g" auth-service/WEB-INF/classes/bootstrap.yml
sed -i  "s|active: dev_env|active: qa_env|g" auth-service/WEB-INF/classes/bootstrap.yml

cd auth-client-service/ && mvn clean install -Dmaven.test.skip=true

git clone https://deepali-arvind:magic%2312@github.com/meta-magic/auth-loba-docker.git 
pwd
ls -la

cp -rf target/auth-client-service-0.0.1-SNAPSHOT.war auth-loba/loba/

cd auth-loba/loba/  && rm -rf auth-client && unzip -qq  auth-client-service-0.0.1-SNAPSHOT.war -d auth-client && rm -rf auth-client-service-0.0.1-SNAPSHOT.war


cd ../ && git add auth && git commit -m "Automated commit on ${COMMIT_TIMESTAMP}" && git push
cd\
