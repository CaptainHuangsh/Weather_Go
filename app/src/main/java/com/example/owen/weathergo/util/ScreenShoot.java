package com.example.owen.weathergo.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by owen on 2017/5/5.
 */

public class ScreenShoot {
    public static Bitmap convertViewBitmap(View view) {
        Log.d("huangshaohua", "screen");
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(),
                view.getHeight(), Bitmap.Config.RGB_565);
        Log.d("huangshaohua", "screen2");
        //利用Bitmap生成画布
        Canvas canvas = new Canvas(bitmap);
        //把view中的内容绘制在画布上
        view.draw(canvas);
        Log.d("huangshaohua", "screen3");
        return bitmap;
    }

    public static void saveMyBitmap(Bitmap bitmap, String path) {
        Log.d("huangshaohuasave", "" + path);
        File file1 = new File(path);
        File file = new File(path + String.valueOf(System.currentTimeMillis()) + ".png");
        if (!file1.exists()) {
            Log.d("huangshaohuamkdir2","");
                file1.mkdir();
            Log.d("huangshaohuamkdir","");
        }
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, fileOutputStream);
        try {
            fileOutputStream.flush();
            fileOutputStream.close();
            ToastUtil.showShort(""+file+"保存成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
