package com.example.cy.myapplication.activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.cy.myapplication.R;
import com.example.cy.myapplication.util.ToastUtil;

public class BluetoothTestActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_BLUETOOTH_ON = 1;

    BluetoothAdapter bluetoothAdapter;//蓝牙适配器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_test);

        if ((bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()) != null) {
            if (!bluetoothAdapter.isEnabled()) {
                Intent in = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                in.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 200);
                startActivityForResult(in, REQUEST_CODE_BLUETOOTH_ON);
            } else {
                ToastUtil.showText("蓝牙模块已经开启");
            }

        } else {
            ToastUtil.showText("当前设备无蓝牙模块");
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_BLUETOOTH_ON && resultCode == RESULT_OK) {
            ToastUtil.showText("蓝牙打开成功");
        }
    }
}
