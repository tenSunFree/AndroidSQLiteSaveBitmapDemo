package com.example.administrator.androidsqlitesavebitmapdemo;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.androidsqlitesavebitmapdemo.DBHelper.DBHelper;
import com.example.administrator.androidsqlitesavebitmapdemo.Utils.Utils;

public class SelectActivity extends AppCompatActivity {

    private static final int SELECT_PHOTO = 7777;
    private ImageView imageView, returnImageView;
    private Button btn_choose, btn_save;
    private DBHelper dbHelper;
    private TextView appNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        dbHelper = new DBHelper(this);

        /** 讓字體自動縮到到合適的比例 */
        appNameTextView = findViewById(R.id.appNameTextView);
        TextViewCompat.setAutoSizeTextTypeWithDefaults(appNameTextView, TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(appNameTextView, 5, 100, 1, TypedValue.COMPLEX_UNIT_SP);

        imageView = findViewById(R.id.image_view);

        btn_choose = findViewById(R.id.select_image);
        btn_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, SELECT_PHOTO);
            }
        });

        btn_save = findViewById(R.id.save_image);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /** convert src of image view to Bitmap */
                final Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

                /**create dialog to enter name to save */
                AlertDialog.Builder builder = new AlertDialog.Builder(SelectActivity.this);
                builder.setTitle("Enter name");
                final EditText editText = new EditText(SelectActivity.this);
                builder.setView(editText);

                /**set button */
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (!TextUtils.isEmpty(editText.getText().toString())) {
                            dbHelper.addBitmap(editText.getText().toString(), Utils.getBytes(bitmap));
                            Toast.makeText(SelectActivity.this, "Save success!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(SelectActivity.this, "Name can't be empty", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.show();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_PHOTO && resultCode == RESULT_OK && data != null) {
            Uri pickedImage = data.getData();
            imageView.setImageURI(pickedImage);
            btn_save.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 修改導航欄 返回上一頁按鈕的功能
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
