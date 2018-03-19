#!/bin/bash
#
# Copyright (c) METAMAGIC GLOBAL INC, USA
#
# This script will drop the database setup

FILE_PATH=$(pwd)
POSTGRES=/usr/lib/postgresql/9.6/bin
DBNAME=authdb

echo drop database setup started.
echo Dropping $DBNAME database.
$POSTGRES/psql -f $FILE_PATH/script/drop-database.sql
echo drop database setup completed.