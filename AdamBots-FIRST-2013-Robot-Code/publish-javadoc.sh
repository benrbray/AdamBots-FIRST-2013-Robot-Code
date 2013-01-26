#!/bin/sh
# Push javadoc files to a website hosted by GitHub.
# Before executing this script, generate the javadoc files into 
# AdamBots-FIRST-2013-Robot-Code/javadoc

# commit changes
git commit -a
git push

# find out the current branch so we know where to switch back
OLD_BRANCH=`git branch --no-color | sed -e '/^[^*]/d' -e 's/* \(.*\)/\1/'`

# Copy Javadoc to Temporary Directory out of
mkdir ~/adambots-temp-javadoc
cp -pr javadoc/* ~/adambots-temp-javadoc

git checkout gh-pages || exit $?

# Clear out the old files: (files which will be served)
rm -rf ./javadoc/* 

# Replace them with new files and commit them:
cp -pr ~/adambots-temp-javadoc/* ./javadoc
rm -rf ~/adambots-temp-javadoc/*
git add javadoc
git commit -a -m "generated javadoc"

#Remove the generated doc
#rm -rf pcdoc/*

git push origin gh-pages || exit $?

# Switch back to the old branch
git checkout $OLD_BRANCH || exit $?