package pooja.borkar.qrapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

public class MainActivity extends AppCompatActivity {
    TextView textViewBarcodeResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewBarcodeResult = findViewById(R.id.barcode_Content);
        isCameraPermissionGiven();
    }

    private boolean isCameraPermissionGiven() {

        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "PERMISSION GRANTED", Toast.LENGTH_SHORT).show();
                return true;
            } else {
                Toast.makeText(this, "PERMISSION RE GRANT", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
                return false;
            }
        } else {
            Toast.makeText(this, "Android OS is not Supported...", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    public void scanBarcode(View view) {
        startActivityForResult(new Intent(this, ScanBarCode.class), 0);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Requested " + permissions[0] + "\n Result \n" + grantResults[0], Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode==0)
        {
            if (resultCode== CommonStatusCodes.SUCCESS)
            {
                if(data!=null)
                {
                    Barcode barcode=data.getParcelableExtra("barcode");
                    assert barcode != null;
                    textViewBarcodeResult.setText(barcode.displayValue);

                }
                else
                {
                    textViewBarcodeResult.setText("No Bar Code Found");
                }
            }
        }
        else
        {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}