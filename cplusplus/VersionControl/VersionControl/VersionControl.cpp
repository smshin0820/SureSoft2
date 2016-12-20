#include "VersionControl.h"

string VersionControl::getOldFilePath() const 
{
	return oldFilePath;
}

void VersionControl::setOldFilePath(string& setName)
{
	oldFilePath = setName;
}

string VersionControl::getNewFilePath() const 
{
	return newFilePath;
}

void VersionControl::setNewFilePath(string& setName)
{
	newFilePath = setName;
}

vector<string>& VersionControl::getOldFileList()
{
	return oldFileList;
}

void VersionControl::setOldFileList(vector<string>& setName)
{
	oldFileList = setName;
}

vector<string> VersionControl::getNewFileList() 
{
	return newFileList;
}
	
void VersionControl::setNewFileList(vector<string>& setName)
{
	newFileList = setName;
}

vector<string> VersionControl::getDeleteFileList() const
{
	return deleteFileList;
}
	
void VersionControl::setDeleteFileList(vector<string>& setName)
{
	deleteFileList = setName;
}
	
vector<string> VersionControl::getAddFileList() const
{
	return addFileList;
}
	
void VersionControl::setAddFileList(vector<string>& setName)
{
	addFileList = setName;
}

vector<string> VersionControl::getModifiedFileList() const
{
	return modifiedFileList;
}

void VersionControl::setModifiedFileList(vector<string>& setName)
{
	modifiedFileList = setName;
}

void VersionControl::printVectorValueList(vector<string> VectorValueList, const string option)
{
	for (std::vector<string>::iterator Iterator = VectorValueList.begin() ; Iterator != VectorValueList.end(); ++Iterator){
		cout << "[" << option << "]" << *Iterator << endl;
	}
	cout << "=============================================================================" << endl;
}

bool VersionControl::compareWeatherItIsSameOrNot(const std::string& p1, const std::string& p2)
{
	std::ifstream f1(p1, std::ifstream::binary|std::ifstream::ate);
  std::ifstream f2(p2, std::ifstream::binary|std::ifstream::ate);

  if (f1.fail() || f2.fail()) {
    return false; //file problem
  }

  if (f1.tellg() != f2.tellg()) {
    return false; //size mismatch
  }

  //seek back to beginning and use std::equal to compare contents
  f1.seekg(0, std::ifstream::beg);
  f2.seekg(0, std::ifstream::beg);
  return std::equal(std::istreambuf_iterator<char>(f1.rdbuf()), \
                    std::istreambuf_iterator<char>(), \
                    std::istreambuf_iterator<char>(f2.rdbuf()));
}

vector<string> VersionControl::compareVectorList(vector<string> oldVector, vector<string> newVector, vector<string>& modifiedFileVector, bool InsertModified = false)
{
	vector<string> returnVector;

	for (vector<string>::iterator oldIterator = oldVector.begin() ; oldIterator != oldVector.end(); ++oldIterator){
		bool findSameFile = false;

		for (std::vector<string>::iterator newIterator = newVector.begin() ; newIterator != newVector.end(); ++newIterator){

			if((*oldIterator).substr((*oldIterator).rfind("\\") + 1, (*oldIterator).length() - (*oldIterator).rfind("\\") + 1) ==
				(*newIterator).substr((*newIterator).rfind("\\") + 1, (*newIterator).length() - (*newIterator).rfind("\\") + 1))
			{
				if(compareWeatherItIsSameOrNot((*oldIterator),(*newIterator)) == false && InsertModified == true) modifiedFileVector.push_back((*newIterator));
				findSameFile = true;
				break;
			}
		}
		if(findSameFile == false) 	returnVector.push_back(*oldIterator);	
	}

	return returnVector;
}

void VersionControl::getFilesInDirectory(vector<string>& _oldFileList, const string _path, const std::string _filter)
{
	string searching = _path + _filter;
	_finddata_t fd;
	long handle = _findfirst(searching.c_str(), &fd);

	if (handle == -1)    return ;

	int result = 0;
	do 
	{
		if(fd.attrib == FILE_FOLDER_ATTRIBUTE && (strcmp(fd.name, ".") == 0 || strcmp(fd.name, "..") == 0))
		{
			result = _findnext(handle, &fd);
			continue;
		}
		if(fd.attrib == FILE_FOLDER_ATTRIBUTE)
		{
			getFilesInDirectory(_oldFileList, _path + "\\" +fd.name, _filter);
			result = _findnext(handle, &fd);
			continue ;
		}

		_oldFileList.push_back(_path + "\\" + fd.name);
		result = _findnext(handle, &fd);
	} while (result != -1);

	_findclose(handle);
}