package com.example.aidl;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edtFirstNumber,edtSecondNumber;
    Button btnMultiply;
    TextView txtMultiplyResult;

    MultiplyInterface  myInterface;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtFirstNumber= (EditText) findViewById(R.id.edtFirstNumber);
        edtSecondNumber= (EditText) findViewById(R.id.edtSecondNumber);
        btnMultiply = (Button) findViewById(R.id.btnMultiply);
        txtMultiplyResult = (TextView) findViewById(R.id.txtMultiplyResult);

        btnMultiply.setOnClickListener(MainActivity.this);

        Intent multiplyService= new Intent(MainActivity.this, MultiplicationService.class);
        bindService(multiplyService, myServiceConnection, Context.BIND_AUTO_CREATE);

    }
    ServiceConnection myServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            myInterface =MultiplyInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    public void onClick(View v) {
        int firstnumber = Integer.parseInt(edtFirstNumber.getText().toString());
        int secondnumber = Integer.parseInt(edtSecondNumber.getText().toString());

        try {
            int result = myInterface.multiplyTwoValuesTogether(firstnumber, secondnumber);
            txtMultiplyResult.setText(result + "");

        } catch (RemoteException e){
            e.printStackTrace();

        }



    }
}
