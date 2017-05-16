package com.example.owen.weathergo.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;

import java.io.File;

/**
 * Created by owen on 2017/5/5.
 */

public class FileUtil {

    public static void getPermission(Activity activity) {
/**
 * 动态获取权限，Android 6.0 新特性，一些保护权限，除了要在AndroidManifest中声明权限，还要使用如下代码动态获取
 */
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

    //实现分享功能
    public static void shareMsg(Context context,String activityTitle, String msgTitle, String msgText,
                                String imgPath) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        if (imgPath == null || imgPath.equals("")) {
            intent.setType("text/plain");//纯文本
        } else {
            File f = new File(imgPath);
            if (f!=null&&f.exists()&&f.isFile()){
                intent.setType("image/png");
                Uri u = Uri.fromFile(f);
                intent.putExtra(Intent.EXTRA_STREAM,u);
            }

        }
        intent.putExtra(Intent.EXTRA_SUBJECT,msgTitle);
        intent.putExtra(Intent.EXTRA_TEXT,msgText);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(Intent.createChooser(intent,activityTitle));

    }

}
