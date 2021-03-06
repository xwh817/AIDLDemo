package xwh.demo.aidlclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import xwh.demo.aidl.IMyAidl;
import xwh.demo.aidl.Person;

public class MainActivity extends AppCompatActivity {

	private TextView mTextNum1, mTextNum2, mTextResult;

	private IMyAidl mAidl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mTextNum1 = this.findViewById(R.id.num1);
		mTextNum2 = this.findViewById(R.id.num2);
		mTextResult = this.findViewById(R.id.result);

		init();
		bindService();

	}

	private void init() {
		this.findViewById(R.id.bt_add).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {

					if (mAidl == null) {
						bindService();
						throw new Exception("服务未连接，请重试！");
					}

					int num1 = Integer.parseInt(mTextNum1.getText().toString());
					int num2 = Integer.parseInt(mTextNum2.getText().toString());
					int result = mAidl.add(num1, num2);

					mTextResult.setText(num1 + " + " + num2 + " = " + result);
				} catch (Exception e) {
					e.printStackTrace();
					mTextResult.setText(e.getMessage());
				}
			}
		});

		this.findViewById(R.id.bt_add_person).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {

					if (mAidl == null) {
						bindService();
						throw new Exception("服务未连接，请重试！");
					}

					Person p = new Person("xwh", 100);
					mAidl.addPerson(p);

					Person remoteP = mAidl.getPerson();

					mTextResult.setText("addPerson: " + p + "\nremotePerson: " + remoteP);
				} catch (Exception e) {
					e.printStackTrace();
					mTextResult.setText(e.getMessage());
				}
			}
		});

		this.findViewById(R.id.bt_async).setOnClickListener(v -> new Thread(() -> {
			try {
				for (int i = 0; i < 10; i++) {
					int result = mAidl.add(i, i);

					String re = i + " + " + i + " = " + result + "\n";

					mTextResult.post(()->{
						mTextResult.append(re);
					});

					Thread.sleep(1000);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start());
	}

	private void bindService() {
		ServiceConnection serviceConnection = new ServiceConnection() {
			@Override
			public void onServiceConnected(ComponentName name, IBinder service) {
				mAidl = IMyAidl.Stub.asInterface(service);
				Log.d("AIDLDemo", "onServiceConnected: " + name.getClassName());
			}

			@Override
			public void onServiceDisconnected(ComponentName name) {
				Log.d("AIDLDemo", "onServiceDisconnected: " + name.getClassName());
				mAidl = null;
			}
		};

		Intent intent = new Intent();
		intent.setComponent(new ComponentName("xwh.demo.aidl", "xwh.demo.aidl.AIDLRemoteService"));
		bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
	}
}
