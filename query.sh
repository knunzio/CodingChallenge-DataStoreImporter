#!/bin/bash

display_help() {
    echo "Usage: $0 -s <select> -o <order> -f <filter>" >&2
    exit 1
}

if [ $# -eq 0 ]
  then
      display_help
fi

SELECT="UNDEFINED"
ORDER="UNDEFINED"
FILTER="UNDEFINED"
while test $# -gt 0
do
    case "$1" in
        -s) shift
            SELECT=$1
            ;;
        -o) shift
            ORDER=$1
            ;;
        -f) shift
            FILTER=$1
            ;;
        -*) display_help
            ;;
        *) display_help
            ;;
    esac
    shift
done

java -classpath ".:./lib/gson-2.5.jar:./out/artifacts/DataStoreImporter_jar/DataStoreImporter.jar" com.rentrak.app.DataStoreQueryTool $SELECT $ORDER $FILTER
