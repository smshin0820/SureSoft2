# How to bulid? 

$cd [run rootfile] \n
$mvn clean compile assembly:single \n
$java -jar target\plugin-diff-1.0-SNAPSHOT-jar-with-dependencies.jar [v1 file_root 절대경로] [v2 file_root 절대경로]
