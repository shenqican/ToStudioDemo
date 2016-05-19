package com.xiaoshen.tostudiodemo;

import android.app.Activity;


import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.xiaoshen.zxing_test.Zxing_Activity;

public class MainActivity extends Activity {
	private static final String TAG = "MainActivity";
	private Button  btn;
	private NetworkReceiver receiver;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btn=(Button)findViewById(R.id.btn);

		IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
		Log.e(TAG, "onCreate: 1" );
		receiver = new NetworkReceiver();
		Log.e(TAG, "onCreate:2" );
		this.registerReceiver(receiver, filter);
		Log.e(TAG, "onCreate: 3" );
		btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				NetUtil.showNetState(MainActivity.this);
			}
		});
	}
  public void zxing(View view){
	  startActivity(new Intent(this, Zxing_Activity.class));
  }



}
