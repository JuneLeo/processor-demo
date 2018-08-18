package com.example.songpengfei.myapplication;

import android.app.Application;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.cleanmaster.mguard.R;
import com.example.songpengfei.myapplication.abtest.ABTest;
import com.example.songpengfei.myapplication.abtest.ui.ABTestActivity;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareOpenGraphAction;
import com.facebook.share.model.ShareOpenGraphContent;
import com.facebook.share.model.ShareOpenGraphObject;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.widget.ShareDialog;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;


public class MainActivity extends AppCompatActivity {
    private CallbackManager callbackManager;
    private ShareDialog shareDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ABTest.getDefault().init(getApplicationContext());

        FacebookSdk.sdkInitialize(this.getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {

                Toast.makeText(MainActivity.this, "success" + result.getPostId(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
                Toast.makeText(MainActivity.this, "cancel", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(MainActivity.this, "error" + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(MainActivity.this, "success" + loginResult.getAccessToken().getPermissions().toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancel() {
                Toast.makeText(MainActivity.this, "cancel" , Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(MainActivity.this, "error" , Toast.LENGTH_LONG).show();
            }
        });


        findViewById(R.id.share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.timg);
                SharePhoto sharePhoto = new SharePhoto.Builder().setBitmap(bitmap)
                        .setImageUrl(Uri.parse("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1534316177360&di=45014f0ed0ef38414b5f793e18006a72&imgtype=0&src=http%3A%2F%2Fg.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F5bafa40f4bfbfbedc5597ab474f0f736aec31ffc.jpg"))
                        .setUserGenerated(false).build();
                ShareOpenGraphObject object = new ShareOpenGraphObject.Builder()

                        .putString("og:url", "https://play.google.com/store/apps/details?id=com.cleanmaster.mguard&referrer=utm_source%3D204197")
//                        .putString("og:image","https://pic.pimg.tw/jimmylu1974/1532663596-553229263.png?v=1532663604")
//                        .putInt("og:image:width",600)
//                        .putInt("og:image:height",600)
                        .putBoolean("image:user_generated", true)
                        .putPhoto("og:image", null)
//                        .putInt("og:image:width",600)
//                        .putInt("og:image:height",600)
                        .putString("og:type", "website")
                        .putString("og:title", "A Game of Thrones")
                        .putString("og:description", "In the frozen wastes to the north of Winterfell, sinister and supernatural forces are mustering.")

                        .build();
                ShareOpenGraphAction action = new ShareOpenGraphAction.Builder()
                        .setActionType("apps.saves")
                        .putObject("website", object)
//                        .putPhoto("image",sharePhoto)
                        .build();
                // Create the content
                ShareOpenGraphContent content = new ShareOpenGraphContent.Builder()
                        .setPreviewPropertyName("website")
                        .setAction(action)
                        .build();


                if (ShareDialog.canShow(ShareOpenGraphContent.class)) {
                    shareDialog.show(content);
                } else {
                    Toast.makeText(MainActivity.this, "未安装", Toast.LENGTH_SHORT).show();
                }



            }
        });


        findViewById(R.id.allows).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    LoginManager.getInstance().logInWithPublishPermissions(
                            MainActivity.this,
                            Arrays.asList("publish_actions"));


            }
        });


        findViewById(R.id.hash).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ABTestActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
