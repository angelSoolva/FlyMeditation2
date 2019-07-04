package com.soolva.flymeditation;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.le.BluetoothLeScanner;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static android.content.ContentValues.TAG;
import static android.support.design.widget.Snackbar.make;

public class BLE_set extends AppCompatActivity {

    private static final int REQUEST_ENABLE_BT = 1;
    private BluetoothAdapter bluetoothAdapter;

    private ListView mDevicesListViewBLE;


    private boolean mScanning;
    private Handler handler;

    private BluetoothLeScanner bleScanner;
    private BluetoothGatt bleGatt;

    // Stops scanning after 10 seconds.
    private static final long SCAN_PERIOD = 10000;

    DataRetrieve mDataRetrieve;
    DataInterpreter mDataInterpreter;
    SoundPlayer mSP;

    Button btnStartBLEScan;
    Button btnStopBLEScan;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ble_set);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDevicesListViewBLE = findViewById(R.id.Devices);
        btnStartBLEScan = findViewById(R.id.button_start_scan);
        btnStopBLEScan = findViewById(R.id.button_stop_scan);
        // Calling Application class (see application tag in AndroidManifest.xml)
        final Global_Data_Class globalVariable = (Global_Data_Class) getApplicationContext();
        mSP = new SoundPlayer(this);

        mDataInterpreter =new DataInterpreter(globalVariable, mSP);
        mDataRetrieve = new DataRetrieve(globalVariable, mDataInterpreter );

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        btnStartBLEScan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                BLE();
            }
        });
        btnStopBLEScan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                scanLeDevice(false);
            }
        });



    }


    private void BLE() {
        // Initializes Bluetooth adapter.
        final BluetoothManager bluetoothManager =
                (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothAdapter = bluetoothManager.getAdapter();
        //Представя физическия блутут модул. с него може да го управляваме

        // Ensures Bluetooth is available on the device and it is enabled. If not,
// displays a dialog requesting user permission to enable Bluetooth.
        if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
        // Трябва да е пуснат и GPS-a
        try {
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

                Log.i("About GPS", "GPS is Enabled in your devide");
            } else {
                //showAlert
                // Generate a message based on the position
                String message = "tURN ON gps";

                // Use the message to create a Snackbar
                View view = findViewById(android.R.id.content);

                Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                        .show(); // Show the Snackbar
            }
        } finally {
        }
        if (bluetoothAdapter != null || bluetoothAdapter.isEnabled())
            scanLeDevice(true); else {
            String message = "Bluethooth not active";
            View view = findViewById(android.R.id.content);
            Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                    .show(); // Show the Snackbar
        }

        /* make list of devices for selection */


    }

    private void scanLeDevice(final boolean enable) {
        if (enable) {
            // Stops scanning after a pre-defined scan period.
/*            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mScanning = false;
                    bluetoothAdapter.stopLeScan(leScanCallback);
                }
            }, SCAN_PERIOD);
                           */
            mScanning = true;
            bluetoothAdapter.startLeScan(leScanCallback);
        } else {
            mScanning = false;
            bluetoothAdapter.stopLeScan(leScanCallback);
        }
    }

    // private LeDeviceListAdapter leDeviceListAdapter;
    // Device scan callback.
    ArrayList<BluetoothDevice> scannedDevices = new ArrayList<BluetoothDevice>();
    ArrayList<String> addressDevicesList = new ArrayList<String>();
    private BluetoothAdapter.LeScanCallback leScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
            //  Log.d(TAG, "Device discovered");
            if (device.getName() != null && !addressDevicesList.contains(device.getAddress())) {
                Log.d(TAG, device.getName());
                Log.d(TAG, device.getAddress());
                scannedDevices.add(device);
                addressDevicesList.add(device.getAddress());
                createList();
            }

        }
    };
    /*new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(final BluetoothDevice device, int rssi,
                             byte[] scanRecord) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    leDeviceListAdapter.addDevice(device);
                    leDeviceListAdapter.notifyDataSetChanged();
        */
    BluetoothDevice[] scannedDevicesArr = new BluetoothDevice[scannedDevices.size()];

    public void createList() {
        //   Creates list of devices


        scannedDevicesArr = scannedDevices.toArray(scannedDevicesArr);


        ArrayAdapter<BluetoothDevice> devicesAdapter = new ArrayAdapter<BluetoothDevice>(this, 0, scannedDevicesArr) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                BluetoothDevice currentDevice = scannedDevicesArr[position];
                // Inflate only once
                if (convertView == null) {
                    convertView = getLayoutInflater()
                            .inflate(R.layout.custom_ble_device, null, false);
                }
                TextView bleAddress =
                        (TextView) convertView.findViewById(R.id.BLE_address);
                TextView bleName =
                        (TextView) convertView.findViewById(R.id.BLE_name);

                bleAddress.setText(currentDevice.getAddress());
                bleName.setText(currentDevice.getName());

                return convertView;

            }
        };

        mDevicesListViewBLE.setAdapter(devicesAdapter);

        mDevicesListViewBLE.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int position, long rowId) {
                  BluetoothDevice device;
                // Generate a message based on the position
                String message = "You clicked on " + scannedDevicesArr[position].getName() + "Adr." + scannedDevicesArr[position].getAddress();

                // Use the message to create a Snackbar
                Snackbar.make(adapterView, message, Snackbar.LENGTH_LONG)
                        .show(); // Show the Snackbar

                //Connect to selected device
                scanLeDevice(false);
                device =scannedDevicesArr[position];
                Arrays.fill(scannedDevicesArr, null);
                connectBLE(device);

            }

        });
    }


    private void connectBLE(BluetoothDevice device1) {
        bleGatt = device1.connectGatt(this, false, gattCallback);
    }

    private final BluetoothGattCallback gattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            Log.i("onConnectionStateChange", "Status: " + status);
            switch (newState) {
                case BluetoothProfile.STATE_CONNECTED:
                    Log.i("gattCallback", "STATE_CONNECTED");
                    gatt.discoverServices();
                    break;
                case BluetoothProfile.STATE_DISCONNECTED:
                    Log.e("gattCallback", "STATE_DISCONNECTED");
                    break;
                default:
                    Log.e("gattCallback", "STATE_OTHER");
            }

        }


        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            List<BluetoothGattService> services = gatt.getServices();
            for (int i = 0; i < services.size(); i++) {

                Log.i("Discover Service", "NO:" + i + 1 + ":" + services.get(i).toString());

                List<BluetoothGattCharacteristic> characteristics = services.get(i).getCharacteristics();

                for (int o = 0; o < characteristics.size(); o++) {
                    Log.i("Discovered Characteristic", "NO:" + o + ":" + characteristics.get(o).toString());
                    //Read characteristic asinchronous
                    //  gatt.readCharacteristic(characteristics.get(o)); //Ne znam kakvo dava

                    //subscribvane za listener


                    // 0x2902 org.bluetooth.descriptor.gatt.client_characteristic_configuration.xml
                    if (i == 2) {
                        gatt.setCharacteristicNotification(characteristics.get(o), true);
                        UUID uuid = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
                        BluetoothGattDescriptor descriptor = characteristics.get(o).getDescriptor(uuid);

                        descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                        Log.i("Descriptor Value", descriptor.getValue().toString());

                        gatt.writeDescriptor(descriptor);
                    }


                    List<BluetoothGattDescriptor> descriptors = characteristics.get(o).getDescriptors();


                    for (int p = 0; p < descriptors.size(); p++) {
                        Log.i("Discovered Descriptor", "NO:" + p + ":" + descriptors.get(p).toString());
                    }

                }
            }
            Log.i("onServicesDiscovered666", services.toString());

            //    gatt.readCharacteristic(services.get(0).getCharacteristics().get
            //           (0));
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt,
                                         BluetoothGattCharacteristic
                                                 characteristic, int status) {
           // mCharacteristic++;
            //Log.i("onCharacteristicRead", mCharacteristic + ": " + characteristic.toString());
            Log.i("onCharacteristicRead", ": " + characteristic.toString());
            //  gatt.setCharacteristicNotification(characteristic,true);

            // gatt.disconnect();
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, final BluetoothGattCharacteristic characteristic) {
            //read the characteristic data

            String data = characteristic.getStringValue(0);

           // Log.i("onCharacteristicRead", mCharacteristic + ": " + data);
            Log.i("onCharacteristicRead",  ": " + data);

           //mDataRead = data;
           mDataRetrieve.stringProcess(data);
           // Message message = Message.obtain();
           // message.arg1 = 1;
            //message.arg2=secondsRST;
           // mMainActivityHandler.sendMessage(message);
        }
    };
}






