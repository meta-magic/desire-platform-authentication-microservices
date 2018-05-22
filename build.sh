#!/bin/bash
git config --global user.email "deepali.arvind@metamagic.in"
git config --global user.name "deepali-arvind"

apt-get -y install zip unzip

COMMIT_TIMESTAMP=`date +'%Y-%m-%d %H:%M:%S %Z'`



cd auth-service/ && mvn clean install -Dmaven.test.skip=true

git clone https://deepali-arvind:magic%2312@github.com/meta-magic/auth-service-docker.git 
pwd
ls -la

cp -rf target/auth-service-0.0.1-SNAPSHOT.war auth-service-docker/auth/

cd auth-service-docker/auth/ && rm -rf auth-service && unzip -qq auth-service-0.0.1-SNAPSHOT.war -d auth-service && rm -rf auth-service-0.0.1-SNAPSHOT.war
sed -i  "s|localhost:8081|config.desire3d.com:5030|g" auth-service/WEB-INF/classes/bootstrap.yml
sed -i  "s|active: dev_env|active: qa_env|g" auth-service/WEB-INF/classes/bootstrap.yml
sed -i "s|jdbc:postgresql:|jdbc:postgresql://db.desire3d.tech:8432/|g" auth-service/WEB-INF/classes/META-INF/persistence.xml
sed -i "s|<property name="javax.jdo.option.ConnectionPassword" value="postgres" />|<property name="javax.jdo.option.ConnectionPassword" value="Meta\#2k.18" />|g" auth-service/WEB-INF/classes/META-INF/persistence.xml

cd ../ && git add auth && git commit -m "Automated commit on ${COMMIT_TIMESTAMP}" && git push
cd\
