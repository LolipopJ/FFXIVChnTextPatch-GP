$env:JAVA_HOME = "C:\Program Files\Java\jdk-11.0.2"
$env:PATH = "$env:JAVA_HOME\bin;C:\gradle\gradle-6.8.3\bin;$env:PATH"

Write-Host "[Environment Temporarily Activated]" -ForegroundColor Green

java -version
gradle -v
