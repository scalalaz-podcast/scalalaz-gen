#!/bin/sh
cd $TRAVIS_BUILD_DIR/target
tar cvf site_$TRAVIS_BUILD_NUMBER.tar site
scp site.tar travis@scalalaz.ru
