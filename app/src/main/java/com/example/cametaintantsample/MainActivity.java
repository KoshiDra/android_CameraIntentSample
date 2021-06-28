package com.example.cametaintantsample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * startActivityForResultで起動した処理から戻った際に実行される処理
     * @param requestCode startActivityForResultの第二引数
     * @param resultCode startActivityForResultで起動した処理の終了ステータス
     * @param data startActivityForResultで起動した処理の戻り値
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        // カメラアプリとの連携及び撮影成功の場合
        if (requestCode == R.integer.camera && resultCode == RESULT_OK) {

            // 撮影された画像のビットマップデータ取得
            // ※getParcelableExtra = Intentから文字列や数値以外のデータ取得をするメソッド
            Bitmap bitmap = data.getParcelableExtra("data");

            // 画像を表示するImageViewの取得
            ImageView imageView = findViewById(R.id.ivCamera);

            // 撮影された画像を設定
            imageView.setImageBitmap(bitmap);
        }
    }

    /**
     * 画面中央のカメラ画像がタップされた際の処理
     * @param view
     */
    public void onCameraImageClick(View view) {

        // Intent生成
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // アクティビティ起動
        // ※撮影後に起動元アクティビティに処理を戻すためstartActivityではなくstartActivityForResultを使用
        startActivityForResult(intent, R.integer.camera);
    }
}