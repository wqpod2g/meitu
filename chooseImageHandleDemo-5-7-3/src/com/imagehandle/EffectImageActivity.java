package com.imagehandle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import com.imagehandle.utils.DecodeBitmap;

public class EffectImageActivity extends Activity{
	
	private ImageView mGetImage = null;
	private Bitmap mBitmap = null;
	private Bitmap mDstBmp = null;//临时保存
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);				
		Log.i("wq222222222222222","+++++++++++++" );
		// 全屏显示
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
	
		setContentView(R.layout.image_effect);
		TextView TV_Title = (TextView) findViewById(R.id.TV_Title);
	    TV_Title.setText("特效处理");
		mGetImage = (ImageView) findViewById(R.id.choose_image);
		
		Intent intent = getIntent();
		String path = intent.getStringExtra("path");
		Log.i("info", "HandleImageActivity-->path:" + path);
		
		Display currentDisplay=getWindowManager().getDefaultDisplay();
		int dw=currentDisplay.getWidth();
		int dh=currentDisplay.getHeight();
		mBitmap=DecodeBitmap.decodeSampledBitmapFromFile(path, dw, dh);
		mGetImage.setImageBitmap(mBitmap);


	}
	
	
public void onClick(View view) throws IOException{
		
		int viewId = view.getId();
		Log.i("info", "onCLick-->viewId:" + viewId);
		
		switch(viewId)
		{
		case R.id.old_remeber_button:
		mDstBmp = oldRemeber(mBitmap);//怀旧效果
		mGetImage.setImageBitmap(mDstBmp);
		    break;
		
		case R.id.sharpen_button:
	        mDstBmp = sharpenImageAmeliorate(mBitmap);//图片锐化
		    mGetImage.setImageBitmap(mDstBmp);
		    break;
		
		case R.id.blur_button:
			mDstBmp = blurImage(mBitmap);//模糊效果
		    mGetImage.setImageBitmap(mDstBmp);
			break;
			
		case R.id.overlay_button:
			mDstBmp = overlayAmeliorate(mBitmap);//图片效果叠加
			mGetImage.setImageBitmap(mDstBmp);
			break;
			
		case R.id.emboss_button:
			mDstBmp = embossAmeliorate(mBitmap);//浮雕效果
			mGetImage.setImageBitmap(mDstBmp);
			break;
			
		case R.id.film_button:
			mDstBmp = filmAmeliorate(mBitmap);//底片效果
			mGetImage.setImageBitmap(mDstBmp);
			break;
			
		case R.id.sunshine_button:
			mDstBmp = sunshineAmeliorate(mBitmap);//光照效果
			mGetImage.setImageBitmap(mDstBmp);
			break;
			
		case R.id.neon_button:
			mDstBmp = neon(mBitmap);//霓虹效果
			mGetImage.setImageBitmap(mDstBmp);
			break;
		case R.id.back:
			back();
			break;
			
		case R.id.save:
			save();
			break;
			
			
			
		default:
			break;
		}
		
		
}

private void save() throws IOException
{
	Log.i("infoccccccccccccccccccccccccccccccc", "onCLick-->viewId:");
	File f = new File(getExternalCacheDir(),"temp.jpg");
	f.createNewFile();
	FileOutputStream fOut = null;
	
	try {
		fOut = new FileOutputStream(f);        
	} catch (FileNotFoundException e) {        
		e.printStackTrace();
	}
	mDstBmp.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
	try {
		fOut.flush();
	} catch (IOException e) {
		e.printStackTrace();
	}
	try {
		fOut.close();
	} catch (IOException e) {
		e.printStackTrace();
	}
	
	
	Log.i("infoooooooooo","wqwqwwqwqwqwqw");
	Intent intent = new Intent(this, HandleImageActivity.class);
	String path=getExternalCacheDir()+"/temp.jpg";
	intent.putExtra("path",path);
	Log.i("inwwwwwwwwwwwwwwwwwwwwwwww",path);
	//System.out.println(path);
	startActivity(intent);
	
	
}



private void back()//返回上层
{
	finish();
}






private Bitmap neon(Bitmap bmp)//霓虹效果
{
	int width = bmp.getWidth();
	int height = bmp.getHeight();
	Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
	
	int pixR = 0;
	int pixG = 0;
	int pixB = 0;
	
	int pixColor = 0;
	int pixColor1 = 0;
	int pixColor2 = 0;
	
	int pixR1 = 0;
	int pixG1 = 0;
	int pixB1 = 0;
	int pixR2 = 0;
	int pixG2 = 0;
	int pixB2 = 0;
	
	int newR = 0;
	int newG = 0;
	int newB = 0;
	
	int delta = 2;
	for (int i = 1, length = width - 1; i < length; i++)
	{
		for (int k = 1, len = height - 1; k < len; k++)
		{
			pixColor = bmp.getPixel(i, k);
			pixColor1 = bmp.getPixel(i+1, k);
			pixColor2 = bmp.getPixel(i, k+1);
			
			pixR = Color.red(pixColor);
			pixG = Color.green(pixColor);
			pixB = Color.blue(pixColor);
			
			pixR1 = Color.red(pixColor1);
			pixG1 = Color.green(pixColor1);
			pixB1 = Color.blue(pixColor1);
			
			pixR2 = Color.red(pixColor2);
			pixG2 = Color.green(pixColor2);
			pixB2 = Color.blue(pixColor2);
			
			newR = (int) (Math.sqrt(Math.pow(pixR - pixR1, 2) + Math.pow(pixR - pixR2, 2)) * delta);
			newG = (int) (Math.sqrt(Math.pow(pixG - pixG1, 2) + Math.pow(pixG - pixG2, 2)) * delta);
			newB = (int) (Math.sqrt(Math.pow(pixB - pixB1, 2) + Math.pow(pixB - pixB2, 2)) * delta);
			
			newR = Math.min(255, Math.max(0, newR));
			newG = Math.min(255, Math.max(0, newG));
			newB = Math.min(255, Math.max(0, newB));
			
			bitmap.setPixel(i, k, Color.argb(255, newR, newG, newB));
		}
	}
	
	return bitmap;
}






private Bitmap sunshineAmeliorate(Bitmap bmp)//光照效果
{
	final int width = bmp.getWidth();
	final int height = bmp.getHeight();
	Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
	
	int pixR = 0;
	int pixG = 0;
	int pixB = 0;
	
	int pixColor = 0;
	
	int newR = 0;
	int newG = 0;
	int newB = 0;
	
	int centerX = width / 2;
	int centerY = height / 2;
	int radius = Math.min(centerX, centerY);
	
	final float strength = 150F; // 光照强度 100~150
	int[] pixels = new int[width * height];
	bmp.getPixels(pixels, 0, width, 0, 0, width, height);
	int pos = 0;
	for (int i = 1, length = height - 1; i < length; i++)
	{
		for (int k = 1, len = width - 1; k < len; k++)
		{
			pos = i * width + k;
			pixColor = pixels[pos];
			
			pixR = Color.red(pixColor);
			pixG = Color.green(pixColor);
			pixB = Color.blue(pixColor);
			
			newR = pixR;
			newG = pixG;
			newB = pixB;
			
			// 计算当前点到光照中心的距离，平面座标系中求两点之间的距离
			int distance = (int) (Math.pow((centerY - i), 2) + Math.pow(centerX - k, 2));
			if (distance < radius * radius)
			{
				// 按照距离大小计算增加的光照值
				int result = (int) (strength * (1.0 - Math.sqrt(distance) / radius));
				newR = pixR + result;
				newG = pixG + result;
				newB = pixB + result;
			}
			
			newR = Math.min(255, Math.max(0, newR));
			newG = Math.min(255, Math.max(0, newG));
			newB = Math.min(255, Math.max(0, newB));
			
			pixels[pos] = Color.argb(255, newR, newG, newB);
		}
	}
	
	bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
	return bitmap;
}



private Bitmap filmAmeliorate(Bitmap bmp)//底片效果
{
	// RGBA的最大值
	final int MAX_VALUE = 255;
	int width = bmp.getWidth();
	int height = bmp.getHeight();
	Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
	
	int pixR = 0;
	int pixG = 0;
	int pixB = 0;
	
	int pixColor = 0;
	
	int newR = 0;
	int newG = 0;
	int newB = 0;
	
	int[] pixels = new int[width * height];
	bmp.getPixels(pixels, 0, width, 0, 0, width, height);
	int pos = 0;
	for (int i = 1, length = height - 1; i < length; i++)
	{
		for (int k = 1, len = width - 1; k < len; k++)
		{
			pos = i * width + k;
			pixColor = pixels[pos];
			
			pixR = Color.red(pixColor);
			pixG = Color.green(pixColor);
			pixB = Color.blue(pixColor);
			
			newR = MAX_VALUE - pixR;
			newG = MAX_VALUE - pixG;
			newB = MAX_VALUE - pixB;
			
			newR = Math.min(MAX_VALUE, Math.max(0, newR));
			newG = Math.min(MAX_VALUE, Math.max(0, newG));
			newB = Math.min(MAX_VALUE, Math.max(0, newB));
			
			pixels[pos] = Color.argb(MAX_VALUE, newR, newG, newB);
		}
	}
	
	bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
	return bitmap;
}


private Bitmap embossAmeliorate(Bitmap bmp)//浮雕效果
{
	
	int width = bmp.getWidth();
	int height = bmp.getHeight();
	Log.i("info", "embossAmeliorate-->width:" + width + "-->height:" + height);
	Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);		
	int pixR = 0;
	int pixG = 0;
	int pixB = 0;		
	int pixColor = 0;		
	int newR = 0;
	int newG = 0;
	int newB = 0;		
	int[] pixels = new int[width * height];
	
	bmp.getPixels(pixels, 0, width, 0, 0, width, height);
	int pos = 0;
	for (int i = 1, length = height - 1; i < length; i++)
	{
		for (int k = 1, len = width - 1; k < len; k++)
		{
			pos = i * width + k;
			pixColor = pixels[pos];
			
			pixR = Color.red(pixColor);
			pixG = Color.green(pixColor);
			pixB = Color.blue(pixColor);
			
			pixColor = pixels[pos + 1];
			newR = Color.red(pixColor) - pixR + 127;
			newG = Color.green(pixColor) - pixG + 127;
			newB = Color.blue(pixColor) - pixB + 127;
			
			newR = Math.min(255, Math.max(0, newR));
			newG = Math.min(255, Math.max(0, newG));
			newB = Math.min(255, Math.max(0, newB));
			
			pixels[pos] = Color.argb(255, newR, newG, newB);
		}
	}
	
	bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
	return bitmap;
}






private Bitmap overlayAmeliorate(Bitmap bmp)//叠加效果
{
	long start = System.currentTimeMillis();
	int width = bmp.getWidth();
	int height = bmp.getHeight();
	Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
	
	// 对边框图片进行缩放
	Bitmap overlay = BitmapFactory.decodeResource(this.getResources(), R.drawable.rainbow_overlay);
	int w = overlay.getWidth();
	int h = overlay.getHeight();
	float scaleX = width * 1F / w;
	float scaleY = height * 1F / h;
	Matrix matrix = new Matrix();
	matrix.postScale(scaleX, scaleY);
	
	Bitmap overlayCopy = Bitmap.createBitmap(overlay, 0, 0, w, h, matrix, true);
	
	int pixColor = 0;
	int layColor = 0;
	
	int pixR = 0;
	int pixG = 0;
	int pixB = 0;
	int pixA = 0;
	
	int newR = 0;
	int newG = 0;
	int newB = 0;
	int newA = 0;
	
	int layR = 0;
	int layG = 0;
	int layB = 0;
	int layA = 0;
	
	final float alpha = 0.5F;
	
	int[] srcPixels = new int[width * height];
	int[] layPixels = new int[width * height];
	bmp.getPixels(srcPixels, 0, width, 0, 0, width, height);
	overlayCopy.getPixels(layPixels, 0, width, 0, 0, width, height);
	
	int pos = 0;
	for (int i = 0; i < height; i++)
	{
		for (int k = 0; k < width; k++)
		{
			pos = i * width + k;
			pixColor = srcPixels[pos];
			layColor = layPixels[pos];
			
			pixR = Color.red(pixColor);
			pixG = Color.green(pixColor);
			pixB = Color.blue(pixColor);
			pixA = Color.alpha(pixColor);
			
			layR = Color.red(layColor);
			layG = Color.green(layColor);
			layB = Color.blue(layColor);
			layA = Color.alpha(layColor);
			
			newR = (int) (pixR * alpha + layR * (1 - alpha));
			newG = (int) (pixG * alpha + layG * (1 - alpha));
			newB = (int) (pixB * alpha + layB * (1 - alpha));
			layA = (int) (pixA * alpha + layA * (1 - alpha));
			
			newR = Math.min(255, Math.max(0, newR));
			newG = Math.min(255, Math.max(0, newG));
			newB = Math.min(255, Math.max(0, newB));
			newA = Math.min(255, Math.max(0, layA));
			
			srcPixels[pos] = Color.argb(newA, newR, newG, newB);
		}
	}
	
	bitmap.setPixels(srcPixels, 0, width, 0, 0, width, height);
	long end = System.currentTimeMillis();
	Log.d("may", "overlayAmeliorate used time="+(end - start));
	return bitmap;
}



private Bitmap blurImage(Bitmap bmp)//模糊效果
{
	int width = bmp.getWidth();
	int height = bmp.getHeight();
	Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
	
	int pixColor = 0;
	
	int newR = 0;
	int newG = 0;
	int newB = 0;
	
	int newColor = 0;
	
	int[][] colors = new int[9][3];
	for (int i = 1, length = width - 1; i < length; i++)
	{
		for (int k = 1, len = height - 1; k < len; k++)
		{
			for (int m = 0; m < 9; m++)
			{
				int s = 0;
				int p = 0;
				switch(m)
				{
				case 0:
					s = i - 1;
					p = k - 1;
					break;
				case 1:
					s = i;
					p = k - 1;
					break;
				case 2:
					s = i + 1;
					p = k - 1;
					break;
				case 3:
					s = i + 1;
					p = k;
					break;
				case 4:
					s = i + 1;
					p = k + 1;
					break;
				case 5:
					s = i;
					p = k + 1;
					break;
				case 6:
					s = i - 1;
					p = k + 1;
					break;
				case 7:
					s = i - 1;
					p = k;
					break;
				case 8:
					s = i;
					p = k;
				}
				pixColor = bmp.getPixel(s, p);
				colors[m][0] = Color.red(pixColor);
				colors[m][1] = Color.green(pixColor);
				colors[m][2] = Color.blue(pixColor);
			}
			
			for (int m = 0; m < 9; m++)
			{
				newR += colors[m][0];
				newG += colors[m][1];
				newB += colors[m][2];
			}
			
			newR = (int) (newR / 9F);
			newG = (int) (newG / 9F);
			newB = (int) (newB / 9F);
			
			newR = Math.min(255, Math.max(0, newR));
			newG = Math.min(255, Math.max(0, newG));
			newB = Math.min(255, Math.max(0, newB));
			
			newColor = Color.argb(255, newR, newG, newB);
			bitmap.setPixel(i, k, newColor);
			
			newR = 0;
			newG = 0;
			newB = 0;
		}
	}
	
	return bitmap;
}





private Bitmap sharpenImageAmeliorate(Bitmap bmp)//锐化效果
{
	long start = System.currentTimeMillis();
	// 拉普拉斯矩阵
	int[] laplacian = new int[] { -1, -1, -1, -1, 9, -1, -1, -1, -1 };
	
	int width = bmp.getWidth();
	int height = bmp.getHeight();
	Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
	
	int pixR = 0;
	int pixG = 0;
	int pixB = 0;
	
	int pixColor = 0;
	
	int newR = 0;
	int newG = 0;
	int newB = 0;
	
	int idx = 0;
	float alpha = 0.3F;
	int[] pixels = new int[width * height];
	bmp.getPixels(pixels, 0, width, 0, 0, width, height);
	for (int i = 1, length = height - 1; i < length; i++)
	{
		for (int k = 1, len = width - 1; k < len; k++)
		{
			idx = 0;
			for (int m = -1; m <= 1; m++)
			{
				for (int n = -1; n <= 1; n++)
				{
					pixColor = pixels[(i + n) * width + k + m];
					pixR = Color.red(pixColor);
					pixG = Color.green(pixColor);
					pixB = Color.blue(pixColor);
					
					newR = newR + (int) (pixR * laplacian[idx] * alpha);
					newG = newG + (int) (pixG * laplacian[idx] * alpha);
					newB = newB + (int) (pixB * laplacian[idx] * alpha);
					idx++;
				}
			}
			
			newR = Math.min(255, Math.max(0, newR));
			newG = Math.min(255, Math.max(0, newG));
			newB = Math.min(255, Math.max(0, newB));
			
			pixels[i * width + k] = Color.argb(255, newR, newG, newB);
			newR = 0;
			newG = 0;
			newB = 0;
		}
	}
	
	bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
	long end = System.currentTimeMillis();
	Log.d("may", "used time="+(end - start));
	return bitmap;
}




private Bitmap oldRemeber(Bitmap bmp)//怀旧效果
{
	// 速度测试
	long start = System.currentTimeMillis();
	int width = bmp.getWidth();
	int height = bmp.getHeight();
	Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
	int pixColor = 0;
	int pixR = 0;
	int pixG = 0;
	int pixB = 0;
	int newR = 0;
	int newG = 0;
	int newB = 0;
	int[] pixels = new int[width * height];
	bmp.getPixels(pixels, 0, width, 0, 0, width, height);
	for (int i = 0; i < height; i++)
	{
		for (int k = 0; k < width; k++)
		{
			pixColor = pixels[width * i + k];
			pixR = Color.red(pixColor);
			pixG = Color.green(pixColor);
			pixB = Color.blue(pixColor);
			newR = (int) (0.393 * pixR + 0.769 * pixG + 0.189 * pixB);
			newG = (int) (0.349 * pixR + 0.686 * pixG + 0.168 * pixB);
			newB = (int) (0.272 * pixR + 0.534 * pixG + 0.131 * pixB);
			int newColor = Color.argb(255, newR > 255 ? 255 : newR, newG > 255 ? 255 : newG, newB > 255 ? 255 : newB);
			pixels[width * i + k] = newColor;
		}
	}
	
	bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
	long end = System.currentTimeMillis();
	Log.d("may", "used time="+(end - start));
	return bitmap;
}
	
	
}
