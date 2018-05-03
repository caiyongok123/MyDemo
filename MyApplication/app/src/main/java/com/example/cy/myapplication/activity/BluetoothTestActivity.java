package com.example.cy.myapplication.activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.cy.myapplication.R;
import com.example.cy.myapplication.util.ToastUtil;
import com.example.cy.myapplication.widget.view.SimpleDialog;
import com.example.cy.myapplication.widget.view.TitleBar;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Arrays;
import java.util.Set;
import java.util.UUID;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.bluetooth.BluetoothClass.Device.AUDIO_VIDEO_CAMCORDER;
import static android.bluetooth.BluetoothClass.Device.AUDIO_VIDEO_CAR_AUDIO;
import static android.bluetooth.BluetoothClass.Device.AUDIO_VIDEO_HANDSFREE;
import static android.bluetooth.BluetoothClass.Device.AUDIO_VIDEO_HEADPHONES;
import static android.bluetooth.BluetoothClass.Device.AUDIO_VIDEO_HIFI_AUDIO;
import static android.bluetooth.BluetoothClass.Device.AUDIO_VIDEO_LOUDSPEAKER;
import static android.bluetooth.BluetoothClass.Device.AUDIO_VIDEO_MICROPHONE;
import static android.bluetooth.BluetoothClass.Device.AUDIO_VIDEO_PORTABLE_AUDIO;
import static android.bluetooth.BluetoothClass.Device.AUDIO_VIDEO_SET_TOP_BOX;
import static android.bluetooth.BluetoothClass.Device.AUDIO_VIDEO_UNCATEGORIZED;
import static android.bluetooth.BluetoothClass.Device.AUDIO_VIDEO_VCR;
import static android.bluetooth.BluetoothClass.Device.AUDIO_VIDEO_VIDEO_CAMERA;
import static android.bluetooth.BluetoothClass.Device.AUDIO_VIDEO_VIDEO_CONFERENCING;
import static android.bluetooth.BluetoothClass.Device.AUDIO_VIDEO_VIDEO_DISPLAY_AND_LOUDSPEAKER;
import static android.bluetooth.BluetoothClass.Device.AUDIO_VIDEO_VIDEO_GAMING_TOY;
import static android.bluetooth.BluetoothClass.Device.AUDIO_VIDEO_VIDEO_MONITOR;
import static android.bluetooth.BluetoothClass.Device.AUDIO_VIDEO_WEARABLE_HEADSET;
import static android.bluetooth.BluetoothClass.Device.COMPUTER_DESKTOP;
import static android.bluetooth.BluetoothClass.Device.COMPUTER_HANDHELD_PC_PDA;
import static android.bluetooth.BluetoothClass.Device.COMPUTER_LAPTOP;
import static android.bluetooth.BluetoothClass.Device.COMPUTER_PALM_SIZE_PC_PDA;
import static android.bluetooth.BluetoothClass.Device.COMPUTER_SERVER;
import static android.bluetooth.BluetoothClass.Device.COMPUTER_UNCATEGORIZED;
import static android.bluetooth.BluetoothClass.Device.COMPUTER_WEARABLE;
import static android.bluetooth.BluetoothClass.Device.HEALTH_BLOOD_PRESSURE;
import static android.bluetooth.BluetoothClass.Device.HEALTH_DATA_DISPLAY;
import static android.bluetooth.BluetoothClass.Device.HEALTH_GLUCOSE;
import static android.bluetooth.BluetoothClass.Device.HEALTH_PULSE_OXIMETER;
import static android.bluetooth.BluetoothClass.Device.HEALTH_PULSE_RATE;
import static android.bluetooth.BluetoothClass.Device.HEALTH_THERMOMETER;
import static android.bluetooth.BluetoothClass.Device.HEALTH_UNCATEGORIZED;
import static android.bluetooth.BluetoothClass.Device.HEALTH_WEIGHING;
import static android.bluetooth.BluetoothClass.Device.PHONE_CELLULAR;
import static android.bluetooth.BluetoothClass.Device.PHONE_CORDLESS;
import static android.bluetooth.BluetoothClass.Device.PHONE_ISDN;
import static android.bluetooth.BluetoothClass.Device.PHONE_MODEM_OR_GATEWAY;
import static android.bluetooth.BluetoothClass.Device.PHONE_SMART;
import static android.bluetooth.BluetoothClass.Device.PHONE_UNCATEGORIZED;
import static android.bluetooth.BluetoothClass.Device.TOY_CONTROLLER;
import static android.bluetooth.BluetoothClass.Device.TOY_DOLL_ACTION_FIGURE;
import static android.bluetooth.BluetoothClass.Device.TOY_GAME;
import static android.bluetooth.BluetoothClass.Device.TOY_ROBOT;
import static android.bluetooth.BluetoothClass.Device.TOY_UNCATEGORIZED;
import static android.bluetooth.BluetoothClass.Device.TOY_VEHICLE;
import static android.bluetooth.BluetoothClass.Device.WEARABLE_GLASSES;
import static android.bluetooth.BluetoothClass.Device.WEARABLE_HELMET;
import static android.bluetooth.BluetoothClass.Device.WEARABLE_JACKET;
import static android.bluetooth.BluetoothClass.Device.WEARABLE_PAGER;
import static android.bluetooth.BluetoothClass.Device.WEARABLE_UNCATEGORIZED;
import static android.bluetooth.BluetoothClass.Device.WEARABLE_WRIST_WATCH;
import static android.bluetooth.BluetoothDevice.DEVICE_TYPE_UNKNOWN;
import static android.view.View.VISIBLE;
import static android.widget.LinearLayout.HORIZONTAL;


/**
 * 蓝牙开发测试
 * <p>
 * 蓝牙权限
 * <uses-permission android:name="android.permission.BLUETOOTH"/>
 * <uses-permission android:name="android.permission.BLUETOOTH_ADMIN"/>
 * <p>
 * <p>
 * Android各种蓝牙设备的UUID:  https://blog.csdn.net/wletv/article/details/8957612
 */
public class BluetoothTestActivity extends AppCompatActivity {


    //蓝牙串口服务
    public static final UUID SERIAL_PORT_SERVICE_CLASS_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");


    private static final int REQUEST_CODE_BLUETOOTH_ON = 1;

    BluetoothAdapter bluetoothAdapter;//蓝牙适配器


    BluetoothReceiver bluetoothReceiver = new BluetoothReceiver();


    @Bind(R.id.titleBar)
    TitleBar titleBar;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    BaseQuickAdapter<BluetoothDevice, BaseViewHolder> adapter;


    BluetoothServerSocket serverSocket;
    Thread connectThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_test);
        ButterKnife.bind(this);


        initView();


        //注册蓝牙搜索用的广播接收器
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        //当搜索结束后调用onReceive
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        this.registerReceiver(bluetoothReceiver, filter);

        //开启蓝牙
        try {
            if (openBluetooth()) {
                getDevices();
            }
        } catch (Exception e) {//当前设备无蓝牙模块
            ToastUtil.showText(e.getMessage());
            e.printStackTrace();
        }


    }

    private void initView() {
        titleBar.setOnTitleBarClickListener(new TitleBar.OnTitleBarClickListener() {
            @Override
            public void onBackClick() {
                onBackPressed();
            }

            @Override
            public void onRightClick() {

            }
        });
        recyclerView.setAdapter(adapter = new BaseQuickAdapter<BluetoothDevice, BaseViewHolder>(R.layout.item_bluetooth) {
            @Override
            protected void convert(BaseViewHolder baseViewHolder, final BluetoothDevice device) {
                if (device.getBondState() == BluetoothDevice.BOND_BONDED) {//已经配对的
                    if (baseViewHolder.getAdapterPosition() == 0) {
                        baseViewHolder.setText(R.id.tv_title, "已配对");
                        baseViewHolder.getView(R.id.tv_title).setVisibility(View.VISIBLE);
                    } else {
                        baseViewHolder.getView(R.id.tv_title).setVisibility(View.GONE);
                    }
                } else {
                    if (baseViewHolder.getAdapterPosition() == 0 || getItem(baseViewHolder.getAdapterPosition() - 1).getBondState() == BluetoothDevice.BOND_BONDED) {
                        baseViewHolder.setText(R.id.tv_title, "未配对");
                        baseViewHolder.getView(R.id.tv_title).setVisibility(View.VISIBLE);
                    } else {
                        baseViewHolder.getView(R.id.tv_title).setVisibility(View.GONE);
                    }
                }

                String type = "";
                switch (device.getType()) {
                    case DEVICE_TYPE_UNKNOWN:
                        type = "未知类型";
                        break;
                    case 1:
                        type = "CLASSIC";
                        break;
                    case 2:
                        type = "BLE only";
                        break;
                    case 3:
                        type = "CLASSIC and BLE";
                        break;
                    default:
                        type = device.getType() + "";
                        break;
                }

                try {
                    baseViewHolder.setText(R.id.tv_bluetooth,
                            "名称：" + device.getName() + "\n" +
                                    "mac地址：" + device.getAddress() + "\n" +
                                    "蓝牙类型：" + type + "(" + device.getType() + ")" + "\n" +
                                    "设备类型：" + getBluetoothClassString(device) + "(" + device.getBluetoothClass().getDeviceClass() + ")" + "\n" +
                                    "uuid：" + Arrays.toString(device.getUuids()));
                } catch (Exception e) {
                }

                baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new SimpleDialog(BluetoothTestActivity.this)
                                .setMessageText("连接设备：" + device.getName() + "\n" + device.getAddress())
                                .setConfirmButtonText("确定")
                                .setOnConfirmListener(new SimpleDialog.OnConfirmListener() {
                                    @Override
                                    public void onConfirm(DialogInterface dialog) {
                                        dialog.dismiss();
                                        if (connectThread != null) {
                                            connectThread.interrupt();
                                            connectThread = null;
                                            serverSocket = null;
                                        }
                                        connectDevice(device, SERIAL_PORT_SERVICE_CLASS_UUID);
                                    }
                                }).show();
                    }
                });

            }
        });
        recyclerView.addItemDecoration(new DividerItemDecoration(this, VISIBLE));

    }


    /**
     * 连接某个蓝牙设备
     *
     * @param device
     */
    private void connectDevice(final BluetoothDevice device, final UUID uuid) {
        connectThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    serverSocket = bluetoothAdapter.listenUsingRfcommWithServiceRecord(device.getName() + "", uuid);
                    Log.e("蓝牙连接：", device.getAddress() + "设备连接成功"+(serverSocket == null));
                } catch (IOException e) {
                    Log.e("蓝牙连接：", device.getAddress() + "设备连接失败");
                    e.printStackTrace();
                }
            }
        });
        connectThread.start();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_BLUETOOTH_ON) {//resultCode 这里没有卵用了，国内的手机，不同手机返回居然不一样，成功有的是200，还有的是120
            if (bluetoothAdapter.isEnabled()) {
                ToastUtil.showText("蓝牙打开成功");
                getDevices();


            } else {
                ToastUtil.showText("蓝牙打开失败");
            }


        }
    }


    /**
     * 获取所有的设备（先获取已配对的，再搜索未配对的）
     */
    public void getDevices() {
        adapter.setNewData(null);
        getBondedDevices();
        bluetoothAdapter.startDiscovery();
    }


    /**
     * 打开蓝牙
     * <p>
     * 如果蓝牙没有开启，就启动,并指定秒数内处于可发现状态（这个在国内的api很多都有改动）
     *
     * @return 蓝牙设备是否本来就是开启的
     * @throws Exception //当前设备无蓝牙模块
     */
    public boolean openBluetooth() throws Exception {
        if ((bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()) != null) {
            if (bluetoothAdapter.isEnabled()) {
                return true;
            } else {
                Intent in = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                in.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 200);
                startActivityForResult(in, REQUEST_CODE_BLUETOOTH_ON);
                return false;
            }
        } else {
            throw new Exception("当前设备无蓝牙模块");
        }
    }

    /**
     * 查找已经配对的蓝牙设备
     */
    public void getBondedDevices() {
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                adapter.addData(device);
            }
        }
    }


    @Override
    protected void onDestroy() {
        this.unregisterReceiver(bluetoothReceiver);
        super.onDestroy();
    }


    class BluetoothReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // 已经配对的则跳过
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    adapter.addData(device);
                } else {
                    Log.e("已经配对的：", device.getName() + "  ======  " + device.getAddress());
                }
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {  //搜索结束
                Log.e("搜索蓝牙结束", "搜索蓝牙结束");
            }

        }
    }


    /**
     * 获取蓝牙设备具体分类
     *
     * @param device
     * @return
     */
    public static String getBluetoothClassString(BluetoothDevice device) {
        String string;
        switch (device.getBluetoothClass().getDeviceClass()) {
            case AUDIO_VIDEO_CAMCORDER://   1076
            case AUDIO_VIDEO_CAR_AUDIO://   1056
            case AUDIO_VIDEO_HANDSFREE://  1032
                string = "音视频摄像机";
                break;
            case AUDIO_VIDEO_HEADPHONES://  1048
            case AUDIO_VIDEO_HIFI_AUDIO://  1064
                string = "头戴式受话器";
                break;
            case AUDIO_VIDEO_LOUDSPEAKER:// 1044
                string = "扬声器";
                break;
            case AUDIO_VIDEO_MICROPHONE://  1040
                string = "麦克风";
                break;
            case AUDIO_VIDEO_PORTABLE_AUDIO://  1052
            case AUDIO_VIDEO_SET_TOP_BOX:// 1060
            case AUDIO_VIDEO_UNCATEGORIZED://   1024
                string = "手提..";
                break;
            case AUDIO_VIDEO_VCR://     1068
            case AUDIO_VIDEO_VIDEO_CAMERA://    1072
            case AUDIO_VIDEO_VIDEO_CONFERENCING://  1088
            case AUDIO_VIDEO_VIDEO_DISPLAY_AND_LOUDSPEAKER://   1084
            case AUDIO_VIDEO_VIDEO_GAMING_TOY://    1096
                string = "VCR";
                break;
            case AUDIO_VIDEO_VIDEO_MONITOR://   1080
                string = "监视器";
                break;
            case AUDIO_VIDEO_WEARABLE_HEADSET://    1028
                string = "可穿戴耳机";
                break;
            case COMPUTER_DESKTOP://    260
                string = "电脑桌面";
                break;
            case COMPUTER_HANDHELD_PC_PDA://    272
                string = "掌上电脑PAD";
                break;
            case COMPUTER_LAPTOP://     268
                string = "便携式电脑(笔记本)";
                break;
            case COMPUTER_PALM_SIZE_PC_PDA://   276
            case COMPUTER_SERVER://     264
            case COMPUTER_UNCATEGORIZED://  256
                string = "PDA";
                break;
            case COMPUTER_WEARABLE://       280
                string = "可穿戴电脑";
                break;
            case HEALTH_BLOOD_PRESSURE://   2308
                string = "健康设备,血压器";
                break;
            case HEALTH_DATA_DISPLAY://     2332
                string = "健康设备,数据展示";
                break;
            case HEALTH_GLUCOSE://          2320
                string = "葡萄糖";
                break;
            case HEALTH_PULSE_OXIMETER://   2324
                string = "脉搏仪";
                break;
            case HEALTH_PULSE_RATE://       2328
                string = "脉搏率";
                break;
            case HEALTH_THERMOMETER://      2312
            case HEALTH_UNCATEGORIZED://    2304
            case HEALTH_WEIGHING://         2316
                string = "温度计";
                break;
            case PHONE_CELLULAR://          516
                string = "蜂窝电话";
                break;
            case PHONE_CORDLESS://          520
                string = "无线电话";
                break;
            case PHONE_ISDN://              532
            case PHONE_MODEM_OR_GATEWAY://  528
                string = "ISDN电话";
                break;
            case PHONE_SMART://             524
                string = "智能手机";
                break;
            case PHONE_UNCATEGORIZED://     512
            case TOY_CONTROLLER://          2064
            case TOY_DOLL_ACTION_FIGURE://  2060
            case TOY_GAME://                2068
            case TOY_ROBOT://               2052
            case TOY_UNCATEGORIZED://       2048
            case TOY_VEHICLE://             2056
            case WEARABLE_GLASSES://        1812
            case WEARABLE_HELMET://         1808
            case WEARABLE_JACKET://         1804
            case WEARABLE_PAGER://          1800
            case WEARABLE_UNCATEGORIZED://  1792
            case WEARABLE_WRIST_WATCH://    1796
            default:
                string = "未指定,未分类";
                break;
        }
        return string;
    }
}
