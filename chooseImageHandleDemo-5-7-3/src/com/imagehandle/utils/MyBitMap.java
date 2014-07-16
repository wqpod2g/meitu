package com.imagehandle.utils;

import android.graphics.Bitmap;
import android.graphics.Matrix;

/**
 * 
 * @author wangqiang
 * @function 图片处理的一些方法
 *
 */

public class MyBitMap {
	
	private Bitmap bmp;
	
	//构造方法
	public MyBitMap(Bitmap map){
		bmp=map;
	}
	

	
	//90度左旋图片
	public Bitmap rotateLeft(){
		Bitmap bitMap = rotate(bmp, 270);
		return bitMap;
		}
	
	//90度右旋图片
	public Bitmap rotateRight(){
		Bitmap bitMap = rotate(bmp, 90);
		return bitMap;
		}
	
	
	//图片旋转
	public Bitmap rotate(Bitmap bmp, float degree)
	{
		Matrix matrix = new Matrix();
		matrix.postRotate(degree);
		int width = bmp.getWidth();
		int height = bmp.getHeight();
		Bitmap bm = Bitmap.createBitmap(bmp, 0, 0, width, height, matrix, true);
		return bm;
	}
	
	//水平翻转
	public Bitmap ReverseHorizontal()
	{
		Bitmap bitMap;
		bitMap = reversion(bmp, 0);
		return bitMap;
	}
	
	//垂直翻转
	public Bitmap ReverseVertical()
	{
		Bitmap bitMap;
		bitMap = reversion(bmp, 1);
		return bitMap;
	}
	
	
	//图片反转
	public Bitmap reversion(Bitmap bmp, int flag)
	{
		float[] floats = null;
		
		switch (flag){
		case 0: // 水平反转
			floats = new float[] { -1f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 1f };
			break;
		case 1: // 垂直反转
			floats = new float[] { 1f, 0f, 0f, 0f, -1f, 0f, 0f, 0f, 1f };
			break;
		}
		
		if (floats != null){
			Matrix matrix = new Matrix();
			matrix.setValues(floats);
			Bitmap bm = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
			
			return bm;
		}
		
		return null;
	}


}
