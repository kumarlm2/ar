package com.example.thirdarapp;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.math.Quaternion;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.Renderable;
import com.google.ar.sceneform.rendering.Texture;
import com.google.ar.sceneform.rendering.ViewRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.BaseArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import java.util.Queue;

public class website_renderer extends AppCompatActivity {

    ArFragment arFragment;
    ViewRenderable view;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        arFragment.setOnTapArPlaneListener(new BaseArFragment.OnTapArPlaneListener() {
            @Override
            public void onTapPlane(HitResult hitResult, Plane plane, MotionEvent motionEvent) {

                    Anchor anchor = hitResult.createAnchor();
                    placeobject(arFragment,anchor);
                    Log.d("aa", "onTapPlane: entered");

            }
        });
    }

    public void placeobject(ArFragment arFragment,Anchor anchor){

        ViewRenderable.builder()
                .setView(arFragment.getContext(),R.layout.websiteview)
                .setHorizontalAlignment(ViewRenderable.HorizontalAlignment.CENTER)
                .build()
                .thenAccept(viewRenderable ->{
                    view = viewRenderable;
                    view.setShadowCaster(false);
                    view.setShadowReceiver(false);
                    WebView webView = view.getView().findViewById(R.id.webview) ;
                    webView.setWebViewClient(new WebViewClient());
                    webView.getSettings().setJavaScriptEnabled(true);
                    webView.loadUrl("https://www.geeksforgeeks.org");
              addControlsToScene(arFragment, anchor, view);
              Log.d("aa", "onTapPlane: entered view");

        }



                );



    }

    public void placeobject1(ArFragment arFragment,Anchor anchor){

        ViewRenderable.builder()
                .setView(arFragment.getContext(),R.layout.video)
                .setHorizontalAlignment(ViewRenderable.HorizontalAlignment.CENTER)
                .build()
                .thenAccept(viewRenderable ->{
                            view = viewRenderable;
                            view.setShadowCaster(false);
                            view.setShadowReceiver(false);
                            VideoView videoView = view.getView().findViewById(R.id.video);
                            Uri uri=Uri.parse("https://youtu.be/ge8iG7JecR4");
                            videoView.setVideoURI(uri);
                            videoView.start();


                            addControlsToScene(arFragment, anchor, view);
                            Log.d("aa", "onTapPlane: entered view");

                        }



                );



    }
    public  void addControlsToScene(ArFragment arFragment, Anchor anchor, Renderable renderable)
    {
        AnchorNode anchorNode =new  AnchorNode(anchor);
        TransformableNode node = new TransformableNode(arFragment.getTransformationSystem());
        node.setLocalRotation(Quaternion.axisAngle(new Vector3(-1f,0,0),90f));
        node.setRenderable(renderable);
        node.setParent(anchorNode);
        arFragment.getArSceneView().getScene().addChild(anchorNode);

    }
}
