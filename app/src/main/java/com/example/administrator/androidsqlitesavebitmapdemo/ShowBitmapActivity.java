package com.example.administrator.androidsqlitesavebitmapdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.androidsqlitesavebitmapdemo.DBHelper.DBHelper;
import com.example.administrator.androidsqlitesavebitmapdemo.Utils.Utils;

public class ShowBitmapActivity extends AppCompatActivity {

    private EditText edt_name;
    private Button btn_select;
    private ImageView imageView, returnImageView;
    private DBHelper dbHelper;
    private TextView appNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_bitmap);

        dbHelper = new DBHelper(this);

        /** 讓字體自動縮到到合適的比例 */
        appNameTextView = findViewById(R.id.appNameTextView);
        TextViewCompat.setAutoSizeTextTypeWithDefaults(appNameTextView, TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(appNameTextView, 5, 100, 1, TypedValue.COMPLEX_UNIT_SP);

        edt_name = findViewById(R.id.edt_name);
        imageView = findViewById(R.id.img_view);

        btn_select = findViewById(R.id.btn_select);
        btn_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                byte[] data = dbHelper.getBitmapByName(edt_name.getText().toString());
                if (data != null) {
                    InputMethodManager imm =
                            (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);                    // 强制隐藏键盘
                    Bitmap bitmap = Utils.getImage(data);
                    imageView.setImageBitmap(bitmap);
                } else {
                    Toast.makeText(ShowBitmapActivity.this,
                            edt_name.getText().toString() + " not found", Toast.LENGTH_SHORT).show();
                }
            }
        });

        returnImageView = findViewById(R.id.returnImageView);
        returnImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }
}
