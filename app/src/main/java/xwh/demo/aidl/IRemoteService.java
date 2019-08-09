package xwh.demo.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;


/**
 * Created by xwh on 2019/4/29.
 */
public class IRemoteService extends Service {
	/**
	 * 当客户端绑定到该服务的时候，返回实现的iBinder
	 * @param intent
	 * @return
	 */
	@Override
	public IBinder onBind(Intent intent) {
		Log.d("AIDLDemo", "IRemoteService onBind");
		return mIBinder;
	}

	// 实现服务端接口
	private IBinder mIBinder = new IMyAidl.Stub() {
		@Override
		public int add(int num1, int num2) {
			Log.d("AIDLDemo", "IRemoteService 收到客户端调用：" + num1 +" + " + num2);
			return num1 + num2;
		}

		@Override
		public void addPerson(Person p) throws RemoteException {
			Log.d("AIDLDemo", "IRemoteService 收到客户端调用：" + p.toString());
		}
	};

}
