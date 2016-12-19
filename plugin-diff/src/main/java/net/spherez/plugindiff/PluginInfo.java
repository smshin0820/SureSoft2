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

public class PluginInfo {

	private final Logger logger = LoggerFactory.getLogger(PluginInfo.class);
	private final String rootPathInput;
	private final String rootPathReal;
	private Map<String, String> map = new HashMap<>();

	public Map<String, String> getMap() {
		return map;
	}

	public String getRootPathInput() {
		return rootPathInput;
	}

	public String getRootPathReal() {
		return rootPathReal;
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
				} else if(file.isDirectory()) {
					map.putAll(getFileList(rootPath, file.getCanonicalPath()));
				}
			}
		}

		return map;
	}

	public void diff(PluginInfo lhd) {

		// copy hashmap
		Map<String, String> lhdMap = new HashMap<>();
		lhdMap.putAll(lhd.getMap());

		for(String key : map.keySet()) {
			if(lhdMap.containsKey(key))  {
				if(map.get(key).equals(lhdMap.get(key))) {
					// equal
					//logger.debug("EQUAL: {}", key);
				} else {
					// modified
					logger.debug("[*] {}", key);
					//	System.out.println(rootPathInput + key + " (*)");
				}

				lhdMap.remove(key);
			} else {
				// removed
				logger.debug("[-] {}", key);
				//System.out.println(rootPathInput + key + " (-)");
			}
		}

		for(String key : lhdMap.keySet()) {
			// added
			logger.debug("[+] {}", key);
			//System.out.println(rootPathInput + key + " (+)");
		}
	}

}
