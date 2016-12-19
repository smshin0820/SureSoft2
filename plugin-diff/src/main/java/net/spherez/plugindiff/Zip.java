package net.spherez.plugindiff;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Zip {
	private PluginInfo plugin;
	private String logFile = "";

	public Zip(PluginInfo plugin, String filePath) {
		this.plugin = plugin;
		this.logFile = filePath;
	}

	public String getFilePath() {
		return logFile;
	}

	public void setFilePath(String filePath) {
		this.logFile = filePath;
	}

	public void archive() {
		// +, *인 경우만 ArrayList에 담기
		// ArrayList에 담겨있는 애들만 압축하기
		// 압축이 필요한 파일만 fileList에 넣기
		ArrayList<String> fileList = new ArrayList<>();
		String zipFileName = plugin.getRootPathInput() + "\\patch.zip";

		FileReader fr = null;
		BufferedReader br = null;

		String read = null;

		try {
			fr = new FileReader(logFile);
			br = new BufferedReader(fr);
			System.out.println("root path :  " + plugin.getRootPathInput());
			System.out.println("alzip fileName : " + zipFileName);
			while ((read = br.readLine()) != null) {
				if (read.substring(1, 2).equals("*") || read.substring(1, 2).endsWith("+")) {
					fileList.add(plugin.getRootPathInput() + read.substring(4));
				}
			}

			if (fr != null) {
				fr.close();
			}
			if (br != null) {
				br.close();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		byte[] buf = new byte[4096];

		try {
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));

			for (String file : fileList) {
				System.out.println(file);
				FileInputStream in = new FileInputStream(file);
				Path p = Paths.get(file);
				String fileName = p.getFileName().toString();

				ZipEntry ze = new ZipEntry(fileName);
				out.putNextEntry(ze);

				int len = 0;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}

				out.closeEntry();
				in.close();
			}
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	} // archive end

}
