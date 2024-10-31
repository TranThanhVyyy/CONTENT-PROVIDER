package com.example.contentprovider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int SMS_PERMISSION_CODE = 1;
    private static final String TAG = "MainActivity";

    private RecyclerView smsRecyclerView;
    private SMSAdapter smsAdapter;
    private List<SMS> smsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        smsRecyclerView = findViewById(R.id.sms_recycler_view);
        smsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        smsList = new ArrayList<>();
        smsAdapter = new SMSAdapter(smsList);
        smsRecyclerView.setAdapter(smsAdapter);

        // Kiểm tra quyền SMS
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_SMS}, SMS_PERMISSION_CODE);
        } else {
            readSmsMessages();
        }
    }

    private void readSmsMessages() {
        Uri smsUri = Uri.parse("content://sms/inbox");
        Cursor cursor = getContentResolver().query(smsUri, null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range")
                String address = cursor.getString(cursor.getColumnIndex("address"));
                @SuppressLint("Range")
                String body = cursor.getString(cursor.getColumnIndex("body"));
                smsList.add(new SMS(address, body));
            }
            cursor.close();
            smsAdapter.notifyDataSetChanged();
        } else {
            Log.d(TAG, "No SMS messages found or cursor is null.");
            Toast.makeText(this, "No SMS messages found.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == SMS_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                readSmsMessages();
            } else {
                Toast.makeText(this, "Permission denied to read SMS", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
