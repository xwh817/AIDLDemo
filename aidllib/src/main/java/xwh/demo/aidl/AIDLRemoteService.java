package xwh.demo.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;


/**
 * Created by xwh on 2019/4/29.
 */
public class AIDLRemoteService extends Service {
	/**
	 * 当客户端绑定到该服务的时候，返回实现的iBinder
	 * @param intent
	 * @return
	 */
	@Override
	public IBinder onBind(Intent intent) {
		Log.d("AIDLDemo", "RemoteService onBind");
		return mIBinder;
	}

	// 实现服务端接口
	private IBinder mIBinder = new IMyAidl.Stub() {
		private Person temp;
		@Override
		public int add(int num1, int num2) {
			Log.d("AIDLDemo", "RemoteService 收到客户端调用：" + num1 +" + " + num2);
			return num1 + num2;
		}

		@Override
		public void addPerson(Person p) throws RemoteException {
			temp = p;
			Log.d("AIDLDemo", "RemoteService 收到客户端调用：" + p.toString());
		}

		@Override
		public Person getPerson() throws RemoteException {
			temp.setName("changed by service");
			return temp;
		}
	};

}
