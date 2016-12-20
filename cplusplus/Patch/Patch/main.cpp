#include <iostream>
#include <fstream>
#include <string>
#include <Windows.h>
#include "Unzipper.h"
#include <atlstr.h>  
  

using namespace std;

int main() {
	CUnzipper uzip;
	string zipPath;
	string targetPath;
	string filePath;
	ifstream myReadFile;

	cout << "압축 파일 경로를 입력해주세요." << endl;
	cin >> zipPath;
	cout << "업데이트할 타겟 폴더를 입력해주세요." << endl;
	cin >> targetPath;

	bool br = uzip.OpenZip(zipPath.c_str());
	if(br){
		br = uzip.UnzipTo(targetPath.c_str());
	}
	else{
		cout << "알집을 찾을 수 없습니다. 종료" << endl;
		exit(0);
	}
	cout << targetPath + "\\diff.txt" << endl;
	myReadFile.open( targetPath + "\\diff.txt");
	
	if (myReadFile.is_open()) {
		while (!myReadFile.eof()) {
			myReadFile >> filePath;

			if(strchr(filePath.c_str(),'-')){			//파일 삭제	
				string deleteFileName = targetPath;
				deleteFileName.append((filePath.erase(0,3)));;
				std::remove(deleteFileName.c_str());
			}
		}
	}
	myReadFile.close();

	return 0;
}

