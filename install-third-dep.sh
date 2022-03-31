#!/bin/bash
for i in $(ls libs/);
  do mvn install:install-file -Dfile=libs/$i -DgroupId=com.topcoder.thirdpart -DartifactId=$(basename $i .jar) -Dversion=1.0-SNAPSHOT -Dpackaging=jar;
done
