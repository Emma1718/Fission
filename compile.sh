#!/bin/bash

function find_main()
{
	local dir=$1
	local mainsrc=`find "$dir" -iname '*.java' -print0 |xargs -0 egrep -l 'public[[:space:]]+class[^{}()]*extends[^{}()]Player'`
	if [ ! -n "$mainsrc" ]
	then
		mainsrc=`find "$dir" -iname '*.java' |head -n 1`
	fi
	if [ ! -n "$mainsrc" ]
	then
		echo "Can not find main file for $name"
		exit
	fi
	echo $mainsrc
}

function smart_cp()
{
	local file=$1
	local dir=$2
	local package=`package $file|sed 's/\./\//g'`
	dir="$dir/src/main/java/$package"
	mkdir -p "$dir"
	cp -v "$file" "$dir"
}

function package()
{
	grep '^package' $1|sed 's/^package[[:space:]]//g'|sed 's/;//g'
}

function mainClass()
{
	local file=$1
	local package=`package $file`
	local class=`basename $file .java`
	echo "$package.$class"
}

INPUT=$1
OUTPUT=$2
groupId="studenci"

if [ ! -d "$INPUT" ] || [ ! -n "$OUTPUT" ]
then
	echo "Usage: $0 dir-with-subdirs-with-sources outdir"
	exit
fi

if [ -e "$OUTPUT" ]
then
	echo "$OUTPUT exists, remove it prior to running this script"
	exit
fi

mkdir -p "$OUTPUT"

for dir in "$INPUT"/*
do
	if [ -d "$dir" ]
	then
		player=`mktemp -d /tmp/player.XXXXXX`
		name=`basename $dir`
		mainsrc=`find_main $dir`
		echo "$name -> $mainsrc"
		mainClass=`mainClass $mainsrc`
		echo "    -> $mainClass"
		find "$dir" -iname '*.java' | while read file
		do
			smart_cp $file $player
		done
		pom="$player/pom.xml"
		cat pom-pattern.xml|sed "s/\\\${groupId}/$groupId/g"|sed "s/\\\${mainClass}/$mainClass/g" |sed "s/\\\${artifactId}/$name/g" >"$pom"
		mvn -f "$pom" package  || exit 1
		cp "$player/target/"*.jar "$OUTPUT/$name.jar" # || echo "Can not copy $name/$player"; exit 1
		rm -rf "$player"
	fi
done
