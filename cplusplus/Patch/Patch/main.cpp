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

	cout << "���� ���� ��θ� �Է����ּ���." << endl;
	cin >> zipPath;
	cout << "������Ʈ�� Ÿ�� ������ �Է����ּ���." << endl;
	cin >> targetPath;

	bool br = uzip.OpenZip(zipPath.c_str());
	if(br){
		br = uzip.UnzipTo(targetPath.c_str());
	}
	else{
		cout << "������ ã�� �� �����ϴ�. ����" << endl;
		exit(0);
	}
	cout << targetPath + "\\diff.txt" << endl;
	myReadFile.open( targetPath + "\\diff.txt");
	
	if (myReadFile.is_open()) {
		while (!myReadFile.eof()) {
			myReadFile >> filePath;

			if(strchr(filePath.c_str(),'-')){			//���� ����	
				string deleteFileName = targetPath;
				deleteFileName.append((filePath.erase(0,3)));;
				std::remove(deleteFileName.c_str());
			}
		}
	}
	myReadFile.close();

	return 0;
}

