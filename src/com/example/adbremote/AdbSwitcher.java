package com.example.adbremote;

import android.os.SystemClock;

import com.common.utils.CmdUtils;

public class AdbSwitcher {
	
	
	static public void switchToUsb() {
		CmdUtils.rootCmd("stop adbd");
		SystemClock.sleep(2000);
		CmdUtils.rootCmd("setprop service.adb.tcp.port \"\"");
		CmdUtils.rootCmd("start adbd");
		
	}

	public static void switchToTcp() {
		
		CmdUtils.rootCmd("stop adbd");
		SystemClock.sleep(2000);
		CmdUtils.rootCmd("setprop service.adb.tcp.port 5555");
		CmdUtils.rootCmd("start adbd");
	}

}
