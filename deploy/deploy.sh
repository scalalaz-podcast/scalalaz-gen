#!/bin/sh
cd $TRAVIS_BUILD_DIR/target
rsync -r site/* travis@scalalaz.ru:/home/travis/site_target
