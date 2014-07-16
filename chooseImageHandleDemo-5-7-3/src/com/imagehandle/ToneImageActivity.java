package com.imagehandle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import com.imagehandle.utils.DecodeBitmap;

public class ToneImageActivity extends Activity{
	
	private ImageView mGetImage = null;
	private SeekBar mSaturationBar = null;
	private SeekBar mBrightnessBar = null;
	private SeekBar mContrastBar = null;
	private Bitmap mBitmap = null;
	private Bitmap mDstBmp = null;//��ʱ����
	private Bitmap tempBmp = null;
	private ColorMatrix mAllColorMatrix;
	private ColorMatrix mSaturationMatrix;
	private ColorMatrix mBrightnessMatrix;
	private ColorMatrix mContrastMatrix;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);				
		Log.i("wq222222222222222","+++++++++++++" );
		// ȫ����ʾ
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		
		setContentView(R.layout.image_tone);
		
		TextView TV_Title = (TextView) findViewById(R.id.TV_Title);
	    TV_Title.setText("ͼ���ɫ");
		mGetImage = (ImageView) findViewById(R.id.choose_image);
		mSaturationBar = (SeekBar) findViewById(R.id.saturation_bar);
		mBrightnessBar = (SeekBar) findViewById(R.id.brightness_bar);
		mContrastBar = (SeekBar) findViewById(R.id.contrast_bar);
		
		Intent intent = getIntent();
		String path = intent.getStringExtra("path");
		Log.i("info", "HandleImageActivity-->path:" + path);
		
	
		
		Display currentDisplay=getWindowManager().getDefaultDisplay();
		int dw=currentDisplay.getWidth();
		int dh=currentDisplay.getHeight();
		mBitmap=DecodeBitmap.decodeSampledBitmapFromFile(path, dw, dh);
		mGetImage.setImageBitmap(mBitmap);
		
	
		mDstBmp= mBitmap;
		tempBmp=mBitmap;
		toneHandle();
		
}
	
	public void onClick(View view) throws IOException
	{
			
			int viewId = view.getId();
			Log.i("info", "onCLick-->viewId:" + viewId);
			
			switch(viewId)
			{
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
		File f = new File(getExternalCacheDir(),"temp.jpg");
		f.createNewFile();
		FileOutputStream fOut = null;
		
		try {
			fOut = new FileOutputStream(f);        
		} catch (FileNotFoundException e) {        
			e.printStackTrace();
		}
		tempBmp.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
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
	
	
	
	
	
	
	private void back()//�����ϲ�
	{
		finish();
	}
	
	
	
	
	
	
	
	
	
	
	
	private void toneHandle()
	{
		if (null == mAllColorMatrix){
			mAllColorMatrix = new ColorMatrix();
		}
		
		if(null == mSaturationMatrix){
			mSaturationMatrix = new ColorMatrix();
		}
		
		if(null == mBrightnessMatrix){
			mBrightnessMatrix = new ColorMatrix();
		}
		
		if(null == mContrastMatrix){
			mContrastMatrix = new ColorMatrix();
		}
		
		mSaturationBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

			// ����һ����ͬ�ߴ�Ŀɱ��λͼ��,���ڻ��Ƶ�ɫ���ͼƬ
			Bitmap saturationBmp = Bitmap.createBitmap(mDstBmp.getWidth(), 
					mDstBmp.getHeight(), Config.ARGB_8888);
			// �õ����ʶ���
			Canvas canvas = new Canvas(saturationBmp); 
			// �½�paint
			Paint paint = new Paint(); 
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				// ���ÿ����,Ҳ���Ǳ�Ե��ƽ������
				paint.setAntiAlias(true); 								
				// ��ΪĬ��ֵ
				mSaturationMatrix.reset();
				//���ñ��Ͷ�
				mSaturationMatrix.setSaturation((float) (progress / 100.0));					
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub				
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub	
				
				mAllColorMatrix.reset();
				// Ч������
				mAllColorMatrix.postConcat(mSaturationMatrix);
				mAllColorMatrix.postConcat(mBrightnessMatrix); 
				mAllColorMatrix.postConcat(mContrastMatrix); 
				
				paint.setColorFilter(new ColorMatrixColorFilter(mAllColorMatrix));
				canvas.drawBitmap(mBitmap, 0, 0, paint);
				mGetImage.setImageBitmap(saturationBmp);
				tempBmp=saturationBmp;
			}			
		});
		
		mBrightnessBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

			// ����һ����ͬ�ߴ�Ŀɱ��λͼ��,���ڻ��Ƶ�ɫ���ͼƬ
			Bitmap brightnessBmp = Bitmap.createBitmap(mDstBmp.getWidth(), 
					mDstBmp.getHeight(), Config.ARGB_8888);
			// �õ����ʶ���
			Canvas canvas = new Canvas(brightnessBmp); 
			// �½�paint
			Paint paint = new Paint(); 
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub		
				int brightNess = progress - 127;				
				// ���ÿ����,Ҳ���Ǳ�Ե��ƽ������
				paint.setAntiAlias(true); 			
				//���ó�Ĭ��ֵ
				mBrightnessMatrix.reset();
				mBrightnessMatrix.set(new float[]{
						1, 0, 0, 0, brightNess,
						0, 1, 0, 0, brightNess,
						0, 0, 1, 0, brightNess,
						0, 0, 0, 1, 0
				});						
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub	
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub	
				
				mAllColorMatrix.reset();
				// Ч������
				mAllColorMatrix.postConcat(mSaturationMatrix);
				mAllColorMatrix.postConcat(mBrightnessMatrix); 
				mAllColorMatrix.postConcat(mContrastMatrix); 
				
				paint.setColorFilter(new ColorMatrixColorFilter(mAllColorMatrix));
				canvas.drawBitmap(mBitmap, 0, 0, paint);
				mGetImage.setImageBitmap(brightnessBmp);
				tempBmp=brightnessBmp;
			}			
		});
		
		mContrastBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			
			// ����һ����ͬ�ߴ�Ŀɱ��λͼ��,���ڻ��Ƶ�ɫ���ͼƬ
			Bitmap contrastBmp = Bitmap.createBitmap(mDstBmp.getWidth(), 
					mDstBmp.getHeight(), Config.ARGB_8888);
			// �õ����ʶ���
			Canvas canvas = new Canvas(contrastBmp); 
			// �½�paint
			Paint paint = new Paint(); 
						
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub	
				float contrast = (float)((progress + 64) / 128.0);
				
				// ���ÿ����,Ҳ���Ǳ�Ե��ƽ������
				paint.setAntiAlias(true); 
				//���ó�Ĭ��ֵ
				mContrastMatrix.reset();
				mContrastMatrix.set(new float[]{
						contrast, 0, 0, 0, 0,
						0, contrast, 0, 0, 0,
						0, 0, contrast, 0, 0,
						0, 0, 0, 1, 0
				});							
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub				
			}
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
				mAllColorMatrix.reset();
				// Ч������
				mAllColorMatrix.postConcat(mSaturationMatrix);
				mAllColorMatrix.postConcat(mBrightnessMatrix); 
				mAllColorMatrix.postConcat(mContrastMatrix); 
				paint.setColorFilter(new ColorMatrixColorFilter(mAllColorMatrix));
				canvas.drawBitmap(mBitmap, 0, 0, paint);
				mGetImage.setImageBitmap(contrastBmp);
				tempBmp=contrastBmp;
			}
		});
	}
	
	
	
	
	

}
