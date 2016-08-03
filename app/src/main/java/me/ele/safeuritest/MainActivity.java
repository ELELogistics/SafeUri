package me.ele.safeuritest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;

import me.ele.safeuri.SafeUriUtils;

public class MainActivity extends AppCompatActivity {

    Button button;
    ImageView imageView;
    File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button);
        imageView = (ImageView) findViewById(R.id.iv);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                file = SafeUriUtils.getImageCacheFileForWriting(
                        MainActivity.this, intent, "output", "me.ele.safeuritest.fileprovider", "1.jpg");
                startActivityForResult(intent, 100);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            Bitmap b = BitmapFactory.decodeFile(file.getCanonicalPath());
            imageView.setImageBitmap(b);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
