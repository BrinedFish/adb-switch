package com.common.utils;

import java.io.DataOutputStream;
import java.io.IOException;

public class CmdUtils {

	

	public static int getPid(Process process) {
		int pid = 0;
		String pname = process.getClass().getName();
		if (pname.equals("java.lang.ProcessManager$ProcessImpl")) {
			
			try {
				java.lang.reflect.Field f = process.getClass()
						.getDeclaredField("pid");
				f.setAccessible(true);
				pid = f.getInt(process);
			} catch (Throwable e) {
			}
		}
		return pid;
	}

	public static Process rootCmd(String cmd) {
		Process p = null;
		try {
			p = Runtime.getRuntime().exec("/system/xbin/su");

			DataOutputStream ou = new DataOutputStream(p.getOutputStream());

			try {
				String str1 = String.valueOf(cmd);
				String str2 = str1 + "\n";
				ou.writeBytes(str2);
				ou.flush();

			} catch (Exception e) {

				e.printStackTrace();
			}

		} catch (IOException e) {

			e.printStackTrace();
		}

		return p;
	}
	public static void rootCmdWait(String cmd) {
		Process p = null;
		try {
			p = Runtime.getRuntime().exec("/system/xbin/su");

			DataOutputStream ou = new DataOutputStream(p.getOutputStream());

			try {
				String str1 = String.valueOf(cmd);
				String str2 = str1 + "\n";
				ou.writeBytes(str2);
				ou.flush();
				ou.writeBytes("exit\n");
				ou.flush();
				p.waitFor();
			    int nRet = p.exitValue();

			} catch (Exception e) {

				e.printStackTrace();
			}

		} catch (IOException e) {

			e.printStackTrace();
		}

		
	}

	
    
	public static Process cmd(String cmd) {
		Process p = null;
		try {
			p = Runtime.getRuntime().exec(cmd);

		} catch (IOException e) {

			e.printStackTrace();
		}

		return p;
	}
}
