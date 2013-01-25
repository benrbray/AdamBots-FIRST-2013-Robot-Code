TEMP_JAVADOC="temp-javadoc"
GHPAGES_JAVADOC="javadoc"
set -v
git commit -a
git push
rm -rf ../$TEMP_JAVADOC
cp -r ./AdamBots-FIRST-2013-Robot-Code/doc ../$TEMP_JAVADOC
git checkout gh-pages
git rm -rf ./$GHPAGES_JAVADOC
cp -r ../$TEMP_JAVADOC ./$GHPAGES_JAVADOC
git add $GHPAGES_JAVADOC
git commit -a -m "new javadoc published."
git push
git checkout master