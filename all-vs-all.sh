#!/bin/sh

DIR=$1

if [ ! -d "$DIR" ]
then
	echo "Usage: $0 dir-with-jars" 1>&2
	exit 1
fi

CONFIG="10 5000"

echo "player1;player2;result;exception;Configuration: $CONFIG"

for p1 in "$DIR"/*.jar
do
	for p2 in "$DIR"/*.jar
	do
		if [ ! $p1 -ef $p2 ]
		then
			echo "$p1 vs $p2" 1>&2
			./runcli.sh "$p1" "$p2" $CONFIG
		fi
	done
done
