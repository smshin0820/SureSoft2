#include "VersionControl.h"

using namespace std;

int main()
{
	class VersionControl vc;
	string oldFilePath, newFilePath;

	cout << "�� ������ ���� ��θ� �Է��ϼ���" << endl;
	cin >> oldFilePath;
	vc.setOldFilePath(oldFilePath);
	cout << "�� ������ ���� ��θ� �Է��ϼ���" << endl;
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