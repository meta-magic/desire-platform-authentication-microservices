#!/bin/bash
#
# Copyright (c) METAMAGIC GLOBAL INC, USA
#
# This script will create the database setup
#

FILE_PATH=$(pwd)
POSTGRES=/usr/lib/postgresql/9.6/bin
DBNAME=authdb
DBUSER=postgres

echo Database setup started.

echo User account creation process started.
$POSTGRES/psql -f $FILE_PATH/script/create-user.sql
echo User account creation process completed successfully.

echo Tablespace creation process started.
$POSTGRES/psql -f $FILE_PATH/script/create-tablespace.sql
echo Tablespace creation process completed successfully.

echo Database $DBNAME creation process started.
$POSTGRES/psql -f $FILE_PATH/script/create-database.sql
echo Database $DBNAME creation process completed successfully.


echo Creating master tables.
$POSTGRES/psql -d $DBNAME -U $DBUSER -f $FILE_PATH/tables/masters/LOGINFORMFACTOR.sql
$POSTGRES/psql -d $DBNAME -U $DBUSER -f $FILE_PATH/tables/masters/LOGINTYPE.sql
echo Master tables created successfully.

echo Creating transactional tables.
$POSTGRES/psql -d $DBNAME -U $DBUSER -f $FILE_PATH/tables/transactions/APPSESSION.sql
$POSTGRES/psql -d $DBNAME -U $DBUSER -f $FILE_PATH/tables/transactions/USERSCHEMA.sql
$POSTGRES/psql -d $DBNAME -U $DBUSER -f $FILE_PATH/tables/transactions/PASSWORDSCHEMA.sql
$POSTGRES/psql -d $DBNAME -U $DBUSER -f $FILE_PATH/tables/transactions/LOGINFAILURE.sql
$POSTGRES/psql -d $DBNAME -U $DBUSER -f $FILE_PATH/tables/transactions/AUTHSCHEMA.sql
$POSTGRES/psql -d $DBNAME -U $DBUSER -f $FILE_PATH/tables/transactions/APPSTATE.sql
$POSTGRES/psql -d $DBNAME -U $DBUSER -f $FILE_PATH/tables/transactions/LOGINHISTORY.sql
$POSTGRES/psql -d $DBNAME -U $DBUSER -f $FILE_PATH/tables/transactions/PASSWORDHISTORY.sql
echo Transactional tables created successfully.

echo Database setup completed.
echo Note: Please add data to all the tables by running load.sh
