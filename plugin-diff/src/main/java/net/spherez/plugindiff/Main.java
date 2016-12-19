package net.spherez.plugindiff;

import java.io.IOException;

/**
 * Created by KYOUNGIN on 2016. 12. 18..
 */
public class Main {

	public static void main(String[] args) throws IOException {

		if(args.length < 2) {
			System.out.println("Usage: JAR_EXE FILE1 FILE2");
			System.exit(1);
		}

		PluginInfo pd1 = new PluginInfo(args[0]);
		PluginInfo pd2 = new PluginInfo(args[1]);

		pd1.diff(pd2);

		/*String diffTextPath = System.getProperty("user.dir")+"\\diffLogs\\diff.txt";*/
		String diffTextPath = System.getProperty("user.dir")+"\\diffLogs\\diff.txt";
		
		System.out.println("Main diffTextPath : " + diffTextPath);
		Zip zipTest = new Zip(pd2, diffTextPath);
		zipTest.archive();
	}
}
