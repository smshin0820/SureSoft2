package net.spherez.plugindiff;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by KYOUNGIN on 2016. 12. 18..
 */
public class PluginInfo {

	private final Logger logger = LoggerFactory.getLogger(PluginInfo.class);
	private final String rootPathInput;
	private final String rootPathReal;
	private Map<String, String> map = new HashMap<>();

	public Map<String, String> getMap() {
		return map;
	}

	public String getRootPathReal() {
		return rootPathReal;
	}

	public String getRootPathInput() {
		return rootPathInput;
	}

	public PluginInfo(String path) throws IOException {
		this.rootPathInput = path;
		this.rootPathReal = new File(path).getAbsolutePath();
		this.map = getFileList(rootPathReal, path);
	}

	private static Map<String, String> getFileList(String rootPath, String path) throws IOException {

		Map<String, String> map = new HashMap<>();

		File[] files = new File(path).listFiles();

		if (files != null) {
			for (File file : files) {
				if (file.isFile()) {
					FileInputStream fis = new FileInputStream(file.getCanonicalFile());

					map.put(file.getCanonicalPath().replace(rootPath, ""), DigestUtils.md5Hex(fis));

					fis.close();
				} else if (file.isDirectory()) {
					map.putAll(getFileList(rootPath, file.getCanonicalPath()));
				}
			}
		}

		return map;
	}

	// lhd : version2
	public void diff(PluginInfo lhd) throws IOException {

		// copy hashmap
		Map<String, String> lhdMap = new HashMap<>();
		lhdMap.putAll(lhd.getMap());

		for (String key : map.keySet()) {
			if (lhdMap.containsKey(key)) {
				if (map.get(key).equals(lhdMap.get(key))) {
					// equal
					logger.debug("EQUAL: {}", key);
				} else {
					// modified
					logger.debug("MODIFIED: {}", key);
					System.out.println(rootPathInput + key + " (*)");
					printContent(rootPathInput + key);
				}

				lhdMap.remove(key);
			} else {
				// removed
				logger.debug("REMOVED: {}", key);
				System.out.println(rootPathInput + key + " (-)");
			}
		}

		for (String key : lhdMap.keySet()) {
			// added
			logger.debug("ADDED: {}", key);
			System.out.println(rootPathInput + key + " (+)");
		}
	}

	public void printContent(String fileName) throws IOException {
		FileReader fr = null;
		BufferedReader br = null;

		String str = "";
		String line = null;

		try {
			fr = new FileReader(fileName);
			br = new BufferedReader(fr);

			while ((line = br.readLine()) != null) {
				// 파일 내용 출력
				// System.out.println(str); 
				str += line;
			}

			if (fr != null) {
				fr.close();
			}
			if (br != null) {
				br.close();
			}
			
			char writeTxt[] = new char[str.length()];
			FileWriter fw = new FileWriter("");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	} // printContent end

}
