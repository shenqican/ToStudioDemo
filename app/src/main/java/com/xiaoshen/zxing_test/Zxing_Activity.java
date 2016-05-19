package com.xiaoshen.zxing_test;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.xiaoshen.tostudiodemo.R;
import com.xys.libzxing.zxing.activity.CaptureActivity;
import com.xys.libzxing.zxing.encoding.EncodingUtils;

public class Zxing_Activity extends Activity {
    private static final String TAG = "Zxing_Activity";
    private ImageView iv_show;
    private TextView tv_show;
    private EditText et_write;
    private CheckBox cb_logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zxing);
        init();
    }
    public void init(){
        cb_logo=(CheckBox) findViewById(R.id.cb_logo);
        iv_show=(ImageView) findViewById(R.id.iv_show);
        tv_show=(TextView) findViewById(R.id.tv_show);
        et_write=(EditText) findViewById(R.id.et_write);
    }
    public void scan(View view){
       startActivityForResult(new Intent(this, CaptureActivity.class),0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            Bundle bundle=data.getExtras();
            String result=bundle.getString("result");
            tv_show.setText(result);

        }
    }

    public  void make(View view){
        String input=et_write.getText().toString();
        Log.e(TAG, "make: "+input );
        if("".equals(input)){
            Toast.makeText(this,"输入不能为空",Toast.LENGTH_LONG).show();
        }else{
            Bitmap bitmap= EncodingUtils.createQRCode(input,300,300,cb_logo.isChecked()?
                    BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher):null);
            iv_show.setImageBitmap(bitmap);
        }
    }
}
