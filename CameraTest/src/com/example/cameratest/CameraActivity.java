package com.example.cameratest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;


public class CameraActivity extends Activity implements TextureView.SurfaceTextureListener, View.OnClickListener, AutoFocusCallback{

    private TextureView myTexture;
    private Camera mCamera;
    private SurfaceTexture mSurface;
    
    public Bitmap[] bmps = null;
    private int  mCap = 0;
    private GifUtil mGifUtil;
    
    private int num = 20;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        
//        myTexture = new TextureView(this);
        myTexture = (TextureView)findViewById(R.id.preview_content);
        myTexture.setSurfaceTextureListener(this);
        //setContentView(myTexture);
        
        Button btn_gif = (Button)findViewById(R.id.gif_make);
        btn_gif.setOnClickListener(this);
        Button btn_bmp = (Button)findViewById(R.id.bmps_cap);
        btn_bmp.setOnClickListener(this);
        
        mGifUtil = new GifUtil();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.camera, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        // TODO Auto-generated method stub
        
        mCamera = Camera.open();
        Camera.Size previewSize = mCamera.getParameters().getPreviewSize();
        myTexture.setLayoutParams(new FrameLayout.LayoutParams(
            previewSize.width, previewSize.height, Gravity.CENTER));
        mSurface = surface;
        try {
           mCamera.setPreviewTexture(surface);
          } catch (IOException t) {
          }
        mCamera.startPreview();
        myTexture.setAlpha(1.0f);
        myTexture.setRotation(90.0f);
        
        mCamera.autoFocus(this);
        
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        // TODO Auto-generated method stub
        mCamera.stopPreview();
        mCamera.release();
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        // TODO Auto-generated method stub
        if(mCap > 0)
        {
            Log.d("CameraTest-gifflen", "onSurfaceTextureUpdated " + mCap);
            bmps[num - mCap] = myTexture.getBitmap(400, 400);
            mCap --;
            
            if(mCap == 0)
            {
                Toast.makeText(this, "DONE !!!", Toast.LENGTH_LONG).show();
            }
        }
        
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                mSurface.updateTexImage();
                mCamera.autoFocus(this);
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch(v.getId())
        {
            case R.id.bmps_cap:
                
                bmps = new Bitmap[num];
                mCap = num;
                break;
            case R.id.gif_make:
                String path = Environment.getExternalStorageDirectory() + "/Test/" + "test.gif";    
                Log.d("CameraTest-gifflen", path);
                if(bmps == null)
                {
                    Toast.makeText(this, "GET BMPs FIRST!!!", Toast.LENGTH_LONG).show();
                    break;
                }
                mGifUtil.Encode(path, bmps, 12);
                
                //save bmp file to compair the pixels
//                for(int i = 0; i < num; i ++)
//                {
//                    String bmppath = Environment.getExternalStorageDirectory() + "/Test/" /*+ "test.gif"*/;
//                    File f = new File(bmppath, "testbmp" + i + ".bmp");
//                    if (f.exists()) {
//                     f.delete();
//                    }
//                    try {
//                     FileOutputStream out = new FileOutputStream(f);
//                     bmps[i].compress(Bitmap.CompressFormat.PNG, 100, out);
//                     out.flush();
//                     out.close();
//                    } catch (FileNotFoundException e) {
//                     // TODO Auto-generated catch block
//                     e.printStackTrace();
//                    } catch (IOException e) {
//                     // TODO Auto-generated catch block
//                     e.printStackTrace();
//                    }
//                    
//                }
                break;
                default:
                    break;
        }
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        
        bmps = null;
    }

    @Override
    public void onAutoFocus(boolean success, Camera camera) {
        // TODO Auto-generated method stub
        
    }
}
