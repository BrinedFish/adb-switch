package com.example.adbremote;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.common.utils.NetworkUtils;
import com.common.utils.SPUtils;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		final EditText edtIniText = (EditText) findViewById(R.id.edt_initscript);
		
		((Button) (findViewById(R.id.btn_tcp))).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				AdbSwitcher.switchToTcp();
			}

		});

		((Button) (findViewById(R.id.btn_usb))).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				AdbSwitcher.switchToUsb();
			}
		});
		((Button) (findViewById(R.id.btn_save))).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				SPUtils.put(MainActivity.this, "ip", edtIniText.getText().toString());

			}
		});
		
		final CheckBox cbx = (CheckBox)findViewById(R.id.cbx_autotcp);
		cbx.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SPUtils.put(MainActivity.this, "isTcp", cbx.isChecked());
				
				
			}
		});
		Boolean autotcp = (Boolean) SPUtils.get(this, "isTcp", false);
		String script = (String) SPUtils.get(this, "ip", "busybox ifconfig eth0 192.168.2.3 up");

		((TextView) (findViewById(R.id.ips))).setText(NetworkUtils.getLocalHostIp());
		edtIniText.setText(script);
		cbx.setChecked(autotcp);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
