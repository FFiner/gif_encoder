package com.example.cameratest;

import android.graphics.Bitmap;
import android.util.Log;

public class GifUtil
{
    static 
    {
        System.loadLibrary("gifflen");
    }
    
    public native int Init(String gifName, int w, int h, int numColors, int quality, int frameDelay);

    public native void Close();

    public native int AddFrame(int[] pixels);
    
    public void Encode(String fileName,Bitmap[] bitmaps,int delay)
    {
        if(bitmaps==null||bitmaps.length==0)
        {
            throw new NullPointerException("Bitmaps should have content!!!");

        }
        int width=bitmaps[0].getWidth();
        int height=bitmaps[0].getHeight();
        
        Log.d("CameraTest-gifflen", "width = " + width + "height = " + height);

        int rl = Init(fileName,width,height,256,100,delay);
        if(rl != 0)
        {
            Log.d("CameraTest-gifflen", "GifUtil init failed " + rl);
            return;
        }

        for(Bitmap bp:bitmaps)
        {
            int pixels[]=new int[width*height]; 

            bp.getPixels(pixels, 0, width, 0, 0, width, height);
            int ad = AddFrame(pixels);
            Log.d("CameraTest-gifflen", "AddFrame ad = " + ad);
        }

        Close();

    }
}