#include <iostream>
#include <vector>
#include <string>
#include <io.h>
#include <fstream>

using namespace std;

class VersionControl
{
public:
	
	string getOldFilePath() const;
	void setOldFilePath(string& setName);
	string getNewFilePath() const;
	void setNewFilePath(string& setName);
	vector<string>& getOldFileList();
	void setOldFileList(vector<string>& setName);
	vector<string> getNewFileList();
	void setNewFileList(vector<string>& setName);
	vector<string> getDeleteFileList() const;
	void setDeleteFileList(vector<string>& setName);
	vector<string> getAddFileList() const;
	void setAddFileList(vector<string>& setName);
	vector<string> getModifiedFileList() const;
	void setModifiedFileList(vector<string>& setName);

	void printVectorValueList(vector<string> VectorValueList, const string option);
	bool compareWeatherItIsSameOrNot(const std::string& p1, const std::string& p2);
	vector<string> compareVectorList(vector<string> oldVector, vector<string> newVector, vector<string>& modifiedFileVector, bool InsertModified);
	void getFilesInDirectory(vector<string>& _oldFileList, const string _path, const std::string _filter);

private:

	static const int FILE_FOLDER_ATTRIBUTE = 16;

	string oldFilePath; 
	string newFilePath;
	vector<string> oldFileList ,newFileList;
	vector<string> deleteFileList, addFileList, modifiedFileList;
};
