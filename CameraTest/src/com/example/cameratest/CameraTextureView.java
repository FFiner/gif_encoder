package com.example.cameratest;

import java.io.IOException;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.util.Log;
import android.view.TextureView;

public class CameraTextureView extends TextureView implements TextureView.SurfaceTextureListener
{
    private Context mContext;
    private SurfaceTexture mSurface;
    private Camera mCamera;
    
//    public CameraTextureView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        // TODO Auto-generated constructor stub
//        mContext = context;
//        this.setSurfaceTextureListener(this);
//    }
//    
    public CameraTextureView(Context context, Camera camera)
    {
        super(context);
        mContext = context;
        mCamera = camera;
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        // TODO Auto-generated method stub
        Log.d("CameraTest", "onSurfaceTextureAvailable");
        mSurface = surface;
        Camera.Size previewSize = mCamera.getParameters().getPreviewSize();
//        myTexture.setLayoutParams(new FrameLayout.LayoutParams(
//        previewSize.width, previewSize.height, Gravity.CENTER));
        try {
           mCamera.setPreviewTexture(surface);
          } catch (IOException t) {
          }
        mCamera.startPreview();
//        myTexture.setAlpha(1.0f);
//        myTexture.setRotation(90.0f);
        
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        // TODO Auto-generated method stub
        return false;
        
//        CameraInterface.getInstance().doStopCamera();
//        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        // TODO Auto-generated method stub
        
    }
    
    public SurfaceTexture _getSurfaceTexture(){
        return mSurface;
    }
}