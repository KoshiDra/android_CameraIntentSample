package com.example.cametaintantsample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    // ストレージに保存された画像のURI
    private Uri imageUri;

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

            // 画像を表示するImageViewの取得
            ImageView imageView = findViewById(R.id.ivCamera);

            // 画像URIをImageViewに設定
            imageView.setImageURI(imageUri);


            /*
            * 撮影された画像をそのままImageViewに表示する場合のソース
            */
//            // 撮影された画像のビットマップデータ取得
//            // ※getParcelableExtra = Intentから文字列や数値以外のデータ取得をするメソッド
//            Bitmap bitmap = data.getParcelableExtra("data");
//
//            // 画像を表示するImageViewの取得
//            ImageView imageView = findViewById(R.id.ivCamera);
//
//            // 撮影された画像を設定
//            imageView.setImageBitmap(bitmap);
        }
    }

    /**
     * 画面中央のカメラ画像がタップされた際の処理
     * @param view
     */
    public void onCameraImageClick(View view) {

        // 日時データを「yyyyMMddHHmmss」に成形するフォーマッタの生成
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        // 現在の日時取得
        Date now = new Date(System.currentTimeMillis());
        // 取得した日時データを「yyyyMMddHHmmss」形式の文字列に変換
        String nowStr = df.format(now);
        // ストレージに保存するファイル名の生成
        String fileName = String.format("CameraIntentSamplePhoto%s.jpg", nowStr);


        // ContentValuesオブジェクト（データ型を指定せずに格納できるオブジェクト）の生成
        ContentValues contentValues = new ContentValues();

        // 画像ファイルのファイル名を設定
        contentValues.put(MediaStore.Images.Media.TITLE, fileName);
        // 画像ファイルの種類を設定
        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");


        // ContentResolverオブジェクト（Uriを生成するためのオブジェクト）の生成
        ContentResolver resolver = getContentResolver();

        // ContentResolverを使用してURIオブジェクトの生成
        imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);

        // Intent生成
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Extra情報としてimageUriを設定
        // KEY:MediaStore.EXTRA_OUTPUTを指定することで撮影した画像データを第二引数のURI（imageUri）が表すストレージに保存する。
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

        // アクティビティ起動
        // ※撮影後に起動元アクティビティに処理を戻すためstartActivityではなくstartActivityForResultを使用
        startActivityForResult(intent, R.integer.camera);


        /*
         * 撮影された画像をそのままImageViewに表示する場合のソース
         */
//        // Intent生成
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//
//        // アクティビティ起動
//        // ※撮影後に起動元アクティビティに処理を戻すためstartActivityではなくstartActivityForResultを使用
//        startActivityForResult(intent, R.integer.camera);
    }
}