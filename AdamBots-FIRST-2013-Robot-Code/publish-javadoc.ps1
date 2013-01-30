. (Resolve-Path "$env:LOCALAPPDATA\GitHub\shell.ps1")
cd ..
cd ..
if(Test-Path publish-javadoc.sh){
	sh .\publish-javadoc.sh
}