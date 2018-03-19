#!/bin/bash
#
# Copyright (c) METAMAGIC GLOBAL INC, USA
#
# This script will load the data in associated database

FILE_PATH=$(pwd)
POSTGRES=/usr/lib/postgresql/9.6/bin
DBNAME=authdb
DBUSER=postgres

echo Load database process started.

echo Loading master data.
$POSTGRES/psql -d $DBNAME -U $DBUSER -c "\COPY \"LOGINTYPE\" from '$FILE_PATH/data/masters/LOGINTYPE.csv' with delimiter '#' CSV HEADER"
$POSTGRES/psql -d $DBNAME -U $DBUSER -c "\COPY \"LOGINFORMFACTOR\" from '$FILE_PATH/data/masters/LOGINFORMFACTOR.csv' with delimiter '#' CSV HEADER"
echo Loading master data successfully done.

echo Loading default trasactions data.
$POSTGRES/psql -d $DBNAME -U $DBUSER -c "\COPY \"USERSCHEMA\" from '$FILE_PATH/data/transactions/USERSCHEMA.csv' with delimiter '#' CSV HEADER"
$POSTGRES/psql -d $DBNAME -U $DBUSER -c "\COPY \"AUTHSCHEMA\" from '$FILE_PATH/data/transactions/AUTHSCHEMA.csv' with delimiter '#' CSV HEADER"
$POSTGRES/psql -d $DBNAME -U $DBUSER -c "\COPY \"PASSWORDSCHEMA\" from '$FILE_PATH/data/transactions/PASSWORDSCHEMA.csv' with delimiter '#' CSV HEADER"
echo Loading trasactions data successfully done.

echo Load database process successfully completed.