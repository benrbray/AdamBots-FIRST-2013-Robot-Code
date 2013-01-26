# Clear out the old files: (files which will be served)
rm -rf ./javadoc/* 

# Replace them with new files and commit them:
cp -pr ~/adambots-temp-javadoc/* ./javadoc
rm -rf ~/adambots-temp-javadoc/*

git status

git add -A
git commit -a -m "generated javadoc"

#Remove the generated doc
#rm -rf pcdoc/*

git push origin gh-pages || exit $?

# Switch back to the old branch
git checkout $OLD_BRANCH || exit $?