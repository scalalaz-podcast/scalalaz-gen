#!/bin/sh
echo "start script"
cd $TRAVIS_BUILD_DIR/target
tar cvf site_$TRAVIS_BUILD_NUMBER.tar site
scp site_$TRAVIS_BUILD_NUMBER.tar travis@scalalaz.ru:
