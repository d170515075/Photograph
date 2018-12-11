package cn.xiaofz.myapplication;

import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.io.IOException;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.os.Environment;
import java.io.FileOutputStream;
import java.io.File;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.os.Environment;

public class MainActivity extends AppCompatActivity {

    private Button mButton;
    private ImageView mImageView;//用于显示照片
    private File mPhotoFile;
    private String mPhotoPath;
    public final static int CAMERA_RESULT = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton = (Button) findViewById(R.id.button);
        mButton.setOnClickListener(new ButtonOnClickListener());
        mImageView = (ImageView) findViewById(R.id.imageView);
    }

    private class ButtonOnClickListener implements View.OnClickListener {
        public void onClick(View v) {
            try {
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");//开始拍照
                mPhotoPath = getSDPath()+"/"+ getPhotoFileName();//设置图片文件路径，getSDPath()和getPhotoFileName()具体实现在下面

                mPhotoFile = new File(mPhotoPath);
                if (!mPhotoFile.exists()) {
                    mPhotoFile.createNewFile();//创建新文件
                }
                intent.putExtra(MediaStore.EXTRA_OUTPUT,//Intent有了图片的信息
                        Uri.fromFile(mPhotoFile));
                startActivityForResult(intent, CAMERA_RESULT);//跳转界面传回拍照所得数据
            } catch (Exception e) {
            }
        }
    }
    public String getSDPath(){
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(android.os.Environment.MEDIA_MOUNTED);   //判断sd卡是否存在
        if   (sdCardExist)
        {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        }
        return sdDir.toString();

    }


    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "'IMG'_yyyyMMdd_HHmmss");
        return dateFormat.format(date)  +".jpg";
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_RESULT) {
            Bitmap bitmap = BitmapFactory.decodeFile(mPhotoPath, null);
            mImageView.setImageBitmap(bitmap);
        }
    }
}