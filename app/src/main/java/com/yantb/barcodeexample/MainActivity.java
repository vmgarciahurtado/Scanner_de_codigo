package com.yantb.barcodeexample;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {

    private Button btnScan;
    private TextView txtResultScan, txtFormatScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnScan = (Button) this.findViewById(R.id.btn_scan);
        txtResultScan = (TextView) this.findViewById(R.id.txt_content_scan);
        txtFormatScan = (TextView) this.findViewById(R.id.txt_format_scan);

        final Activity activity = this;

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("Escanear Producto");
                integrator.setCameraId(0);
                integrator.setOrientationLocked(false);
                integrator.setBeepEnabled(true);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (result != null) {
            if (result.getContents() == null) {
                Log.d("MainActivity", "Escaneo Cancelado");
                Toast.makeText(this, "Escaneao Cancelado", Toast.LENGTH_SHORT).show();
            } else {
                Log.d("MainActivity", "Escaneo Realizado");
                Toast.makeText(this, result.getContents(), Toast.LENGTH_SHORT).show();
                txtResultScan.setText(result.getContents());
                txtFormatScan.setText(result.getFormatName());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
            Log.d("MainActivity", "Escaneao Cancelado");
            Toast.makeText(this, "Escaneao Cancelado", Toast.LENGTH_SHORT).show();
        }

    }
}