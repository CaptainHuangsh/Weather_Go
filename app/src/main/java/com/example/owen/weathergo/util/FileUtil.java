package com.example.owen.weathergo.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Toast;

import com.example.owen.weathergo.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by owen on 2017/5/5
 */

public class FileUtil {

    private final int TYPE_SHARE = 0;
    private final int TYPE_FEEDBACK = 1;

    public static FileUtil getInstance() {
        return FileHolder.sInstance;
    }

    public static class FileHolder {
        private static FileUtil sInstance = new FileUtil();
    }


    /**
     * 第一次安装或未获取新权限时调用此方法申请获取权限（6.0以上）
     * http://blog.csdn.net/loongggdroid/article/details/35572269
     * 动态获取权限，Android 6.0 新特性，一些保护权限，除了要在AndroidManifest中声明权限，还要使用如下代码动态获取
     *
     * @param activity
     */
    public void getPermission(Activity activity) {
        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            //验证是否许可权限
            for (String str : permissions) {
                if (activity.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    activity.requestPermissions(permissions, REQUEST_CODE_CONTACT);

                }
            }
        }
    }


    /**
     * 实现分享功能
     *
     * @param context
     * @param activityTitle
     * @param msgTitle
     * @param msgText
     * @param imgPath
     * @param type
     */
    public void shareMsg(Context context, String activityTitle, String msgTitle, String msgText,
                         String imgPath, int type) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        if (type == 0) {
            if (imgPath == null || imgPath.equals("")) {
                intent.setType("text/plain");//纯文本
            } else {
                File f = new File(imgPath);
                if (f != null && f.exists() && f.isFile()) {
                    intent.setType("image/png");
                    Uri u = Uri.fromFile(f);
                    intent.putExtra(Intent.EXTRA_STREAM, u);
                }

            }
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } else if (type == 1) {
            intent.setType("message/rfc822");//邮件
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{context.getString(R.string.email)});
//            intent2.putExtra(Intent.EXTRA_SUBJECT, "Lucid Dream Alarm Feedback");
//            intent2.putExtra(Intent.EXTRA_TEXT, getAppInfo(context).toString());
            try {
                context.startActivity(Intent.createChooser(intent, context.getString(R.string.send_email)));
            } catch (android.content.ActivityNotFoundException e) {
                Toast.makeText(context, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
            }
        }
        intent.putExtra(Intent.EXTRA_SUBJECT, msgTitle);
        intent.putExtra(Intent.EXTRA_TEXT, msgText);
        context.startActivity(Intent.createChooser(intent, activityTitle));
    }


    /**
     * 获取设备信息
     *
     * @param context
     * @return
     */
    @NonNull
    public StringBuilder getAppInfo(Context context) {
        String mVersionName = null;
        try {

            PackageInfo packageInfo = context.getPackageManager().
                    getPackageInfo(context.getPackageName(), 0);
            mVersionName = packageInfo.versionName;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        StringBuilder builder = new StringBuilder();
        builder.append("\n\n\n");
        builder.append(context.getString(R.string.tech_info));
        builder.append("\n\n");
        builder.append("Lucid Dream Alarm " + mVersionName);
        builder.append("\n");
        builder.append(Build.MODEL);
        return builder;
    }


    /**
     * 保存截图
     *
     * @param bitmap
     * @param path
     * @return
     */
    public String saveMyBitmap(Bitmap bitmap, String path) {
        File file1 = new File(path);
        File file = new File(path + String.valueOf(System.currentTimeMillis()) + ".png");
        if (!file1.exists()) {
            file1.mkdir();
        }
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "";
        }
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, fileOutputStream);
        try {
            fileOutputStream.flush();
            fileOutputStream.close();
//            ToastUtil.showShort(""+file+"保存成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file + "";
    }

    /**
     * 截图
     *
     * @param view
     * @return
     */
    public Bitmap convertViewBitmap(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(),
                view.getHeight(), Bitmap.Config.RGB_565);
        //利用Bitmap生成画布
        Canvas canvas = new Canvas(bitmap);
        //把view中的内容绘制在画布上
        view.draw(canvas);
        return bitmap;
    }

}
