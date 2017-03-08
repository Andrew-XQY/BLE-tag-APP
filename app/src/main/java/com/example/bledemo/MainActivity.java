package com.example.bledemo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothAdapter.LeScanCallback;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity {

	TextView tv_status;

	List<BluetoothDevice> devicelist = new ArrayList<BluetoothDevice>();

	MusicPlayer player;

	BluetoothAdapter bluetoothAdapter;

	BluetoothDevice bluetoothDevice;

	BluetoothGatt bluetoothGatt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		player = new MusicPlayer(this);
		tv_status = (TextView) findViewById(R.id.tv_status);

		BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
		bluetoothAdapter = bluetoothManager.getAdapter();

	}

	public void scanAction(View v) {
		devicelist.clear();

		bluetoothAdapter.startLeScan(mScanCallback);

		showDeviceListDialog();
	}

	public void ringAction(View v) {

	}

	public void stopRingAction(View v) {

	}

	private LeScanCallback mScanCallback = new LeScanCallback() {

		@Override
		public void onLeScan(final BluetoothDevice bluetoothDevice, int rssi,
				byte[] values) {

			Log.e("", " name:" + bluetoothDevice.getName() + "  mac:"
					+ bluetoothDevice.getAddress());

			runOnUiThread(new Runnable() {

				@Override
				public void run() {

					if (!devicelist.contains(bluetoothDevice)) {
						devicelist.add(bluetoothDevice);
						adapter.notifyDataSetChanged();
					}
				}
			});

		}
	};

	Dialog dialog;

	private void showDeviceListDialog() {
		Button btn_dialgo_cancle = null;
		LayoutInflater factory = LayoutInflater.from(this);
		View view = factory.inflate(R.layout.dialog_scan_device, null);
		dialog = new Dialog(this, R.style.MyDialog);
		// ContentView
		dialog.setContentView(view);
		dialog.setCancelable(false);
		dialog.show();
		btn_dialgo_cancle = (Button) view
				.findViewById(R.id.btn_dialog_scan_cancle);
		ListView listView = (ListView) view.findViewById(R.id.listview_device);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				dialog.dismiss();

				
				

			}
		});
		btn_dialgo_cancle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();

			}
		});
	}

	
	private BaseAdapter adapter = new BaseAdapter() {
		@Override
		public int getCount() {

			return devicelist.size();
		}

		@Override
		public long getItemId(int arg0) {

			return 0;
		}

		@Override
		public Object getItem(int arg0) {

			return null;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder;
			if (convertView == null) {
				convertView = LayoutInflater.from(parent.getContext()).inflate(
						R.layout.list_item_scan, null);
				viewHolder = new ViewHolder();
				viewHolder.tv_device = (TextView) convertView
						.findViewById(R.id.tv_device);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			viewHolder.tv_device.setText(devicelist.get(position).getName()
					+ "  " + devicelist.get(position).getAddress());
			return convertView;
		}

		class ViewHolder {
			TextView tv_device;
		}
	};

}
