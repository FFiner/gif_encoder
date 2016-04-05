package com.example.cameratest;

import java.io.IOException;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;

/** A basic Camera preview class */
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback{
    private SurfaceHolder mHolder;
    private Camera mCamera;
    Parameters mParameters;
 
    public CameraPreview(Context context, Camera camera) {
        super(context);
        mCamera = camera;
 
        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        mHolder = getHolder();
        mHolder.addCallback(this);
        // deprecated setting, but required on Android versions prior to 3.0
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }
 
    public void surfaceCreated(SurfaceHolder holder) {
        // The Surface has been created, now tell the camera where to draw the preview.
        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
            mParameters = mCamera.getParameters();
        } catch (IOException e) {
            //Log.d(TAG, "Error setting camera preview: " + e.getMessage());
        }
        
        Log.d("CameraTest", "surfaceCreated");
    }
 
    public void surfaceDestroyed(SurfaceHolder holder) {
        // empty. Take care of releasing the Camera preview in your activity.
        Log.d("CameraTest", "surfaceDestroyed");
    }
 
    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        // If your preview can change or rotate, take care of those events here.
        // Make sure to stop the preview before resizing or reformatting it.
        
        Log.d("CameraTest", "surfaceChanged");
 
        if (mHolder.getSurface() == null){
          // preview surface does not exist
          return;
        }
 
        // stop preview before making changes
        try {
            mCamera.stopPreview();
        } catch (Exception e){
          // ignore: tried to stop a non-existent preview
        }
 
        // make any resize, rotate or reformatting changes here
 
        // start preview with new settings
        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();
 
        } catch (Exception e){
            //Log.d(TAG, "Error starting camera preview: " + e.getMessage());
        }
    }
//
//    @Override
//    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
//        // TODO Auto-generated method stub
//        Log.d("CameraTest", "onSurfaceTextureAvailable");
//    }
//
//    @Override
//    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
//        // TODO Auto-generated method stub
//        Log.d("CameraTest", "onSurfaceTextureSizeChanged");
//        
//    }
//
//    @Override
//    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
//        // TODO Auto-generated method stub
//        Log.d("CameraTest", "onSurfaceTextureDestroyed");
//        return false;
//    }
//
//    @Override
//    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
//        // TODO Auto-generated method stub
//        Log.d("CameraTest", "onSurfaceTextureUpdated");
//    }
//    
//    
}