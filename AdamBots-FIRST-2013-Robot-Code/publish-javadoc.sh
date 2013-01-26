#!/bin/sh
# Push javadoc files to a website hosted by GitHub.
# Before executing this script, generate the javadoc files into 
# AdamBots-FIRST-2013-Robot-Code/javadoc

# verbose output
set -v

# commit changes
git commit -a
git push

# find out the current branch so we know where to switch back
OLD_BRANCH=`git branch --no-color | sed -e '/^[^*]/d' -e 's/* \(.*\)/\1/'`

echo $OLD_BRANCH

git checkout gh-pages || exit $?

# Clear out the old files: (files which will be served)
rm -rf ./javadoc/* 

# Copy files to this branch
git checkout $OLD_BRANCH ./javadoc

# Add and Commit Javadoc
git add -A
git commit -a -m "generated javadoc"

git push origin gh-pages || exit $?

# Switch back to the old branch
git checkout $OLD_BRANCH || exit $?