package com.imagehandle.utils;

import android.graphics.Bitmap;

import android.graphics.BitmapFactory;
import android.util.Log;

/**
 *  @version 1.0, 2013-5-4
 *  @author wangqiang
**/
public class DecodeBitmap {
	public static Bitmap decodeSampledBitmapFromFile(String path,int dw,int dh)
	{
		BitmapFactory.Options bmpFactoryOptions=new BitmapFactory.Options();
		 bmpFactoryOptions.inJustDecodeBounds=true;
		    Bitmap mBitmap = BitmapFactory.decodeFile(path,bmpFactoryOptions);
		    int hratio=(int) Math.ceil((float)bmpFactoryOptions.outHeight / (float) dh);
		    int wratio=(int) Math.ceil((float)bmpFactoryOptions.outWidth / (float) dw);
		    Log.i("info", "hratio and wratio: "+hratio+" "+wratio);
		    if(hratio>1&&wratio>1)
		    {
		    	if(hratio>wratio)
		    	{
		    		bmpFactoryOptions.inSampleSize=hratio;
		    	}
		    	else
		    	{
		    		bmpFactoryOptions.inSampleSize=wratio;
		    	}
		    }
		    
		    bmpFactoryOptions.inJustDecodeBounds=false;
		    mBitmap = BitmapFactory.decodeFile(path,bmpFactoryOptions);
		
		return mBitmap;
		
	}
   
}
