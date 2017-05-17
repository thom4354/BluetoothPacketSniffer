package com.example.peter.bluetoothpacketsniffer;

import android.os.Bundle;
//import android.widget.Button;
//import android.os.Environment;
//import android.os.FileObserver;
import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
//import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
//import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.content.Intent;
import android.bluetooth.BluetoothAdapter;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Context;
//import android.os.FileObserver;
//import android.support.v4.app.ActivityCompat;
//import android.Manifest;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
//import com.example.peter.bluetoothpacketsniffer.MyFileObserver;
//import android.util.Log;
import android.widget.ListView;
import android.os.Handler;
import android.os.Message;
import java.io.File;
import java.lang.Thread;



/*public class MyFileObserver extends FileObserver{
    {
        public String absolutePath;
    public MyFileObserver(String path) {
        super(path, FileObserver.ALL_EVENTS);
        absolutePath = path;
    }
        @Override
        public void onEvent(int event, String path) {
        if (path == null) {
            return;
        }
        //a new file or subdirectory was created under the monitored directory
        if ((FileObserver.CREATE & event)!=0) {
            FileAccessLogStatic.accessLogMsg += absolutePath + "/" + path + " is createdn";
        }
        //a file or directory was opened
        if ((FileObserver.OPEN & event)!=0) {
            FileAccessLogStatic.accessLogMsg += path + " is openedn";
        }
        //data was read from a file
        if ((FileObserver.ACCESS & event)!=0) {
            FileAccessLogStatic.accessLogMsg += absolutePath + "/" + path + " is accessed/readn";
        }
        //data was written to a file
        if ((FileObserver.MODIFY & event)!=0) {
            FileAccessLogStatic.accessLogMsg += absolutePath + "/" + path + " is modifiedn";
        }
        //someone has a file or directory open read-only, and closed it
        if ((FileObserver.CLOSE_NOWRITE & event)!=0) {
            FileAccessLogStatic.accessLogMsg += path + " is closedn";
        }
        //someone has a file or directory open for writing, and closed it
        if ((FileObserver.CLOSE_WRITE & event)!=0) {
            FileAccessLogStatic.accessLogMsg += absolutePath + "/" + path + " is written and closedn";
        }
        //a file was deleted from the monitored directory
        if ((FileObserver.DELETE & event)!=0) {
            //for testing copy file
// FileUtils.copyFile(absolutePath + "/" + path);
            FileAccessLogStatic.accessLogMsg += absolutePath + "/" + path + " is deletedn";
        }
        //the monitored file or directory was deleted, monitoring effectively stops
        if ((FileObserver.DELETE_SELF & event)!=0) {
            FileAccessLogStatic.accessLogMsg += absolutePath + "/" + " is deletedn";
        }
        //a file or subdirectory was moved from the monitored directory
        if ((FileObserver.MOVED_FROM & event)!=0) {
            FileAccessLogStatic.accessLogMsg += absolutePath + "/" + path + " is moved to somewhere " + "n";
        }
        //a file or subdirectory was moved to the monitored directory
        if ((FileObserver.MOVED_TO & event)!=0) {
            FileAccessLogStatic.accessLogMsg += "File is moved to " + absolutePath + "/" + path + "n";
        }
        //the monitored file or directory was moved; monitoring continues
        if ((FileObserver.MOVE_SELF & event)!=0) {
            FileAccessLogStatic.accessLogMsg += path + " is movedn";
        }
        //Metadata (permissions, owner, timestamp) was changed explicitly
        if ((FileObserver.ATTRIB & event)!=0) {
            FileAccessLogStatic.accessLogMsg += absolutePath + "/" + path + " is changed (permissions, owner, timestamp)n";
        }
    }


        @Override
        public void onEvent(int event, String file) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(event);
        builder.setMessage(file);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();

        alertDialog.show();
    }
    };
}*/



/*class MyFileObserver extends FileObserver {

    Context c = MainActivity.class;

    MyFileObserver(String path, Context c) {
        super(path, FileObserver.ALL_EVENTS);
    }

    @Override
    public void onEvent(int event, String file) {
        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle(event);
        builder.setMessage(file);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();

        alertDialog.show();

    }
}*/

public class MainActivity extends AppCompatActivity {

    private final static int REQUEST_ENABLE_BT = 1;
    final BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    final Context context = this;

    static Handler h = new Handler(){
        @Override
        public void handleMessage(Message msg){

        }
    };


    // Used to load the 'native-lib' library on application startup.
    /*static {
        System.loadLibrary("native-lib");
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if(mBluetoothAdapter == null){
            System.exit(1);
        }

        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

        Runnable runnable = new Runnable(){
            public void run(){

            }
        };

        final Thread t = new Thread(runnable);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //TODO: Check permissions logic
                //ActivityCompat.requestPermissions(MainActivity.this,
                //        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

                //File file = new File();
                //FileObserver observer = new FileObserver(/*android.os.Environment.getExternalStorageDirectory().getPath() + */ "/cache"){ @Override public void onEvent(int event, String path) { Log.i("ABC", String.format("%d", event)); } };


                if (!mBluetoothAdapter.isEnabled()) {
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                }

                File file = new File(android.os.Environment.getExternalStorageDirectory().getPath() + "/btsnoop_hci.log");
                if(!file.exists()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Error");
                    builder.setMessage("Unable to find btsnoop_hci.log. Please ensure that \"Enable Bluetooth HCI snoop log\" is enabled in developer options.");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog alertDialog = builder.create();

                    alertDialog.show();
                }

                else{
                    /*AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Message");
                    builder.setMessage(Long.toString(file.length()));
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog alertDialog = builder.create();

                    alertDialog.show();*/



                    if(t.getState() == Thread.State.NEW) t.start();

                }


                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        //.setAction("Action", null).show();
            }
        });


        // Example of a call to a native method
        //TextView tv = (TextView) findViewById(R.id.sample_text);
        //tv.setText(stringFromJNI());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id == R.id.action_settings) {
            return true;
        }

        if(id == R.id.pause_button) {
            //start = false;
        }

        if(id == R.id.action_info){
            /*Button mInfo_button = (Button) findViewById(R.id.action_info);
            mInfo_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {*/

                    String macAddress = android.provider.Settings.Secure.getString(context.getContentResolver(), "bluetooth_address");

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Bluetooth adapter information");
                    builder.setMessage("MAC address: "+macAddress);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog alertDialog = builder.create();

                    alertDialog.show();
                //}
            //});
        }

        return super.onOptionsItemSelected(item);
    }



    /*
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    //public native String stringFromJNI();

}
