package com.imagehandle.utils;

import android.graphics.Bitmap;
import android.graphics.Matrix;

/**
 * 
 * @author wangqiang
 * @function ͼƬ�����һЩ����
 *
 */

public class MyBitMap {
	
	private Bitmap bmp;
	
	//���췽��
	public MyBitMap(Bitmap map){
		bmp=map;
	}
	

	
	//90������ͼƬ
	public Bitmap rotateLeft(){
		Bitmap bitMap = rotate(bmp, 270);
		return bitMap;
		}
	
	//90������ͼƬ
	public Bitmap rotateRight(){
		Bitmap bitMap = rotate(bmp, 90);
		return bitMap;
		}
	
	
	//ͼƬ��ת
	public Bitmap rotate(Bitmap bmp, float degree)
	{
		Matrix matrix = new Matrix();
		matrix.postRotate(degree);
		int width = bmp.getWidth();
		int height = bmp.getHeight();
		Bitmap bm = Bitmap.createBitmap(bmp, 0, 0, width, height, matrix, true);
		return bm;
	}
	
	//ˮƽ��ת
	public Bitmap ReverseHorizontal()
	{
		Bitmap bitMap;
		bitMap = reversion(bmp, 0);
		return bitMap;
	}
	
	//��ֱ��ת
	public Bitmap ReverseVertical()
	{
		Bitmap bitMap;
		bitMap = reversion(bmp, 1);
		return bitMap;
	}
	
	
	//ͼƬ��ת
	public Bitmap reversion(Bitmap bmp, int flag)
	{
		float[] floats = null;
		
		switch (flag){
		case 0: // ˮƽ��ת
			floats = new float[] { -1f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 1f };
			break;
		case 1: // ��ֱ��ת
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
