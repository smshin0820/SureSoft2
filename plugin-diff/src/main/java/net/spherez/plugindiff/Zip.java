package net.spherez.plugindiff;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.io.File;

public class Zip {
	private PluginInfo plugin_orign;
	private PluginInfo plugin;
	private String logFile = "";

	public Zip(PluginInfo plugin_orign, PluginInfo plugin, String logFile) {
		this.plugin_orign = plugin_orign;
		this.plugin = plugin;
		this.logFile = logFile;
	}

	public String getLogFile() {
		return logFile;
	}

	public void archive() {

		ArrayList<String> fileList = new ArrayList<>();

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String date = sdf.format(cal.getTime());

		String directory = plugin_orign.getRootPathInput();
		int index = directory.lastIndexOf("\\");
		String programname = directory.substring(index, directory.length());

		File dir = new File(System.getProperty("user.dir") + "\\diffLogs\\" + programname);
		if (!dir.exists()) {
			dir.mkdirs();
		}

		String zipFileName = System.getProperty("user.dir") + "\\diffLogs\\" + programname + "\\patch" + date + ".zip";

		FileReader fr = null;
		BufferedReader br = null;

		String read = null;

		fileList.add(logFile);
		try {
			fr = new FileReader(logFile);
			br = new BufferedReader(fr);


			while ((read = br.readLine()) != null) {
				if (read.substring(1, 2).equals("*") || read.substring(1, 2).endsWith("+")) {
					fileList.add(plugin.getRootPathInput() + read.substring(3));
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
			System.out.println("zipFileName : " + zipFileName);
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));

			boolean firstFlag = true;
			for (String file : fileList) {
				System.out.println("before : " + file);
				FileInputStream in = new FileInputStream(file);
				if(firstFlag){
					firstFlag = false;
					Path p = Paths.get(file);
					file = p.getFileName().toString();
				}else{
					file = file.substring(plugin.getRootPathInput().length());
				}
				System.out.println("after : " + file);

				ZipEntry ze = new ZipEntry(file);
				out.putNextEntry(ze);

				int len = 0;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				out.closeEntry();
				in.close();
			} // for end
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	} // archive end


}
