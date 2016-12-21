#plugin-diff 프로젝트
###plug-diff 프로젝트는 이전버전 v1폴더와 새 버전 v2폴더가 있을시 v1폴더를 새버전으로 변경을 위해 추가, 삭제, 수정할 파일들의 정보를 제공합니다.
###[+],[-],[*] 기호와 루트디렉토리를 제외한 파일경로를 출력함과 동시에 diffLogs/diff.txt가 제공됩니다.

###[+][directory_path] 해당 파일 추가
###[-][directory_path] 해당 파일 삭제
###[*][directory_path] 해당 파일 수정 을 의미
-----------------------------------------------------------------------------------------------------------
### ex) diff.txt
####[+]\drive.py
####[-]\setting\dirveSetting.py
####[*]\setting\userSetting.py

-------------------------------------------------------------------------------------------------------------

# How to bulid? 

### $cd [run rootfile] 
### $mvn clean compile assembly:single 
### $java -jar target\plugin-diff-1.0-SNAPSHOT-jar-with-dependencies.jar [v1 file_root 절대경로] [v2 file_root 절대경로]
-----------------------------------------------------------------------------------------------------------------------
## batch File manual
### $cd [batch File Dir]
### $patch.bat [v1 file_root 절대경로] [v2 file_root 절대경로]

