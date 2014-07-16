package com.imagehandle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import com.imagehandle.R;
import com.imagehandle.utils.DecodeBitmap;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class AddframeImageActivity extends Activity{
	
	private ImageView mGetImage = null;
	private Bitmap mBitmap = null;
	private Bitmap savemap = null;//ÁÙÊ±±£´æ

	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);				
		Log.i("wq222222222222222","+++++++++++++" );
		// È«ÆÁÏÔÊ¾
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.image_addframe);
		TextView TV_Title = (TextView) findViewById(R.id.TV_Title);
	    TV_Title.setText("±ß¿ò");
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
		
		switch(viewId){
		
		case R.id.frame_button_one:
			addFrameByID(R.id.frame_button_one);
			break;
			
		case R.id.frame_button_two:
			addFrameByID(R.id.frame_button_two);
			break;
			
		case R.id.frame_button_three:
			addFrameByID(R.id.frame_button_three);
			break;
			
		case R.id.frame_button_fore:
			addFrameByID(R.id.frame_button_fore);
			break;
			
		case R.id.frame_button_five:
			addFrameByID(R.id.frame_button_five);
			break;
			
		case R.id.frame_button_six:
			addFrameByID(R.id.frame_button_six);
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
	File f = new File(getExternalCacheDir(),"temp.jpg");
	f.createNewFile();
	FileOutputStream fOut = null;
	
	try {
		fOut = new FileOutputStream(f);        
	} catch (FileNotFoundException e) {        
		e.printStackTrace();
	}
	savemap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
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

private void back()//·µ»ØÉÏ²ã
{
	finish();
}

private Bitmap addFrame(Bitmap bmp, int id)//Ìí¼Ó±ß¿ò
{  
	int width = bmp.getWidth();  
	int height = bmp.getHeight();  
	  
	// »ñÈ¡±ß¿òÍ¼Æ¬   
	Bitmap framePicture = null; //= BitmapFactory.decodeResource(this.getResources(), R.drawable.black);
	switch(id){
		case R.id.frame_button_one:
			framePicture = BitmapFactory.decodeResource(this.getResources(), R.drawable.frame_big1);
			break;
		case R.id.frame_button_two:
			framePicture = BitmapFactory.decodeResource(this.getResources(), R.drawable.frame_2);
			break;
		case R.id.frame_button_three:
			framePicture = BitmapFactory.decodeResource(this.getResources(), R.drawable.frame_three);
			break;
		case R.id.frame_button_fore:
			framePicture = BitmapFactory.decodeResource(this.getResources(), R.drawable.frame_four);
			break;
		case R.id.frame_button_five:
			framePicture = BitmapFactory.decodeResource(this.getResources(), R.drawable.frame_five);
			break;
			
		case R.id.frame_button_six:
			framePicture = BitmapFactory.decodeResource(this.getResources(), R.drawable.frame_six);
			break;
		default:
			break;
	}
	 Log.i("info", "onCLick-->viewId:111--------------------");
	 int framewidth= framePicture.getWidth();
	 int frameheight=framePicture.getHeight();
	 float scalewidth=((float) width)/framewidth;
	 float scaleheight=((float) height)/frameheight;
	 Matrix matrix = new Matrix();
	 Log.i("info", "onCLick-->viewId:222--------------------"+scalewidth+"  "+scaleheight);
	 matrix.postScale(scalewidth,scaleheight);
	 Log.i("info", "onCLick-->viewId:333--------------------");
	 Bitmap newframePicture= Bitmap.createBitmap(framePicture, 0, 0, framewidth,frameheight, matrix, true);
	 Log.i("info", "onCLick-->viewId:--------------------");
	 
	 Bitmap newb = Bitmap.createBitmap(width, height, Config.ARGB_8888);
	 Canvas canvas = new Canvas(newb);
	 canvas.drawBitmap(bmp, 0, 0, null);
	 canvas.drawBitmap(newframePicture,0,0,null);
	
	 canvas.save(Canvas.ALL_SAVE_FLAG);  
	
	return newb;  
}  


private void addFrameByID(int id)
{
	savemap = addFrame(mBitmap, id);
	mGetImage.setImageBitmap(savemap);
}


		
}
