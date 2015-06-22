package com.example.adbremote;


import com.common.utils.AssetCopyer;
import com.common.utils.CmdUtils;
import com.common.utils.SPUtils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;

public class BootComplite extends BroadcastReceiver {

	@Override
	public void onReceive(final Context context, Intent intent) {
		// TODO Auto-generated method stub
		String action = "android.intent.action.BOOT_COMPLETED";
		if (intent.getAction().equals(action)) {
			
			new Thread(){
				
				public void run() {
					Log.e("", "BOOT TCP SERVER");
					AssetCopyer ac= new AssetCopyer();
					ac.copyAssets(context);
					CmdUtils.rootCmd("cp -rf "+context.getApplicationContext().getFilesDir().getAbsolutePath()+"/*.so /system/lib/");
					CmdUtils.rootCmd("cp -rf "+context.getApplicationContext().getFilesDir().getAbsolutePath()+"/*.ko /system/lib/");
					SystemClock.sleep(2000);
					
					CmdUtils.rootCmd("insmod /system/lib/usbnet.ko");
					CmdUtils.rootCmd("insmod /system/lib/asix.ko");
					
					SystemClock.sleep(2000);
					String ip = (String) SPUtils.get(context, "ip", "busybox ifconfig eth0 192.168.2.3 up");
					CmdUtils.rootCmd(ip);
					SystemClock.sleep(2000);
					
					Boolean isTcp = (Boolean) SPUtils.get(context, "isTcp", false);
					
					if (isTcp) {
						AdbSwitcher.switchToTcp();
					}
					else {
						AdbSwitcher.switchToUsb();
					}
//					SystemClock.sleep(2000);
//					CmdUtils.rootCmd("busybox killall com.test.app");
					
				}
				
				
			}.start();
			
		
			
		}

	}
	

}
