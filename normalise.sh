#!/bin/bash

cd DataBase-VoNZ-word

if [ ! -e done.txt ];  then
for input in $(ls -1) ; do
	ffmpeg -y -i "$input" -filter:a loudnorm "$input" -nostats -loglevel 0
done
echo normalisation has been applied > done.txt
fi
