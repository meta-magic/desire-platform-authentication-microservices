#!/bin/bash
#
# Copyright (c) METAMAGIC GLOBAL INC, USA
#
# This script will restore the database setup

FILE_PATH=$(pwd)

echo "Database restoration process started"

sh ./drop.sh
sh ./create.sh
sh ./load.sh

echo "Database restoration process completed successfully"