#include "VersionControl.h"

using namespace std;

int main()
{
	class VersionControl vc;
	string oldFilePath, newFilePath;

	cout << "구 버전의 폴더 경로를 입력하세요" << endl;
	cin >> oldFilePath;
	vc.setOldFilePath(oldFilePath);
	cout << "신 버전의 폴더 경로를 입력하세요" << endl;
	cin >> newFilePath;
	vc.setNewFilePath(newFilePath);

	vc.getFilesInDirectory(vc.getOldFileList(), vc.getOldFilePath(), "\\*.*");
	vc.getFilesInDirectory(vc.getNewFileList(), vc.getNewFilePath(), "\\*.*");

	vc.setDeleteFileList(vc.compareVectorList(vc.getOldFileList(), vc.getNewFileList(), vc.getModifiedFileList(), true));
	vc.setAddFileList(vc.compareVectorList(vc.getNewFileList(),vc.getOldFileList(),vc.getModifiedFileList(), false)); 

	vc.printVectorValueList(vc.getAddFileList(), "+");
	vc.printVectorValueList(vc.getDeleteFileList(), "-");
	vc.printVectorValueList(vc.getModifiedFileList(), "*");

	return 1;
}