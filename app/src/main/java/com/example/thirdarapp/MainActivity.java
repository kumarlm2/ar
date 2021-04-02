package com.example.thirdarapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;

import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.ar.core.Anchor;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.math.Quaternion;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.Color;
import com.google.ar.sceneform.rendering.ExternalTexture;
import com.google.ar.sceneform.rendering.FixedHeightViewSizer;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.ViewRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import static android.media.MediaCodec.MetricsConstants.HEIGHT;

public class MainActivity extends AppCompatActivity {

    private ModelRenderable videoRenderable;
    ViewRenderable getTestViewRenderable2;
    private float HEIGHT = 0.95f;
    WebView wb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArFragment arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);

        ExternalTexture texture = new ExternalTexture();



        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.video1);

        mediaPlayer.setSurface(texture.getSurface());
        mediaPlayer.setLooping(true);

//        ViewRenderable.builder()
//                .setView(arFragment.getContext(),R.layout.image)
//                .build()
//                .thenAccept(viewRenderable -> {
//                             ImageView img=(ImageView)viewRenderable.getView();
//                             img.setImageResource(R.drawable.imag);
//
//
//
//
//                }
//                )
//                .exceptionally(
//                        throwable -> {
//                            Toast toast = Toast.makeText(this, "Unable to load andy renderable", Toast.LENGTH_LONG);
//                            toast.setGravity(Gravity.CENTER, 0, 0);
//                            toast.show();
//                            return null;
//                        });;





        ModelRenderable.builder()
                .setSource(this, R.raw.video_screen)
                .build()
                .thenAccept(modelRenderable -> {
                    videoRenderable = modelRenderable;
                    videoRenderable.getMaterial().setExternalTexture("videoTexture",texture);
                    videoRenderable.getMaterial().setFloat4("keyColor", new Color(0.01843f, 1.0f,0.098f));
                    videoRenderable.setShadowCaster(false);
                    videoRenderable.setShadowReceiver(false);

                });




        arFragment.setOnTapArPlaneListener((hitResult, plane, motionEvent) -> {
            Anchor anchor = hitResult.createAnchor();
            AnchorNode anchorNode = new AnchorNode(anchor);
            Log.d("aa" ,"onFrameAvailable: "+plane.getCenterPose());
            TransformableNode node = new TransformableNode(arFragment.getTransformationSystem());
            node.setLocalRotation(Quaternion.axisAngle(new Vector3(-1f,0,0),90f));
            if(!mediaPlayer.isPlaying()){
                mediaPlayer.start();

                texture.getSurfaceTexture().setOnFrameAvailableListener(surfaceTexture -> {
                    node.setRenderable(videoRenderable);
                    texture.getSurfaceTexture().setOnFrameAvailableListener(null);
                });
            }else{

                node.setLocalRotation(Quaternion.axisAngle(new Vector3(-1f,0,0),90f));
                anchorNode.setRenderable(videoRenderable);
            }
            float width = mediaPlayer.getVideoWidth();
            float height = mediaPlayer.getVideoHeight();
          //  anchorNode.setWorldRotation(Quaternion.axisAngle(new Vector3(-1f,0,0),90f));
           node.setLocalPosition(new Vector3(0f, 0f,0f));

           node.setLocalScale(new Vector3(HEIGHT * (width/height), HEIGHT, 90f));
            node.setParent(anchorNode);
            arFragment.getArSceneView().getScene().addChild(anchorNode);
            Log.d("asss", "onCreate: "+arFragment.isAdded());
        });


    }

}
