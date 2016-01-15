#!/bin/bash

display_help() {
    echo "Usage: $0 <input file path and name>" >&2
    exit 1
}

if [ $# -eq 0 ]
  then
      display_help
fi

java -classpath ".:./lib/gson-2.5.jar:./out/artifacts/DataStoreImporter_jar/DataStoreImporter.jar" com.rentrak.app.DataStoreImporter $1
