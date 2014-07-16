package com.imagehandle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import com.imagehandle.utils.DecodeBitmap;
import com.imagehandle.utils.MyBitMap;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class EditImageActivity extends Activity{

	private ImageView mGetImage = null;
	private File mFile;
	private static final int CROP_BIG_PICTURE =4;
	private float scaleWidth=1;
	private float scaleHeight=1;
	private Bitmap mBitmap = null;
	private Bitmap bitmap = null;
	private Bitmap tempBitmap = null;
	private MyBitMap mybitmap=null;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);				
		// 全屏显示
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.image_edit);
		
		TextView TV_Title = (TextView) findViewById(R.id.TV_Title);
	    TV_Title.setText("图像编辑");
		mGetImage = (ImageView) findViewById(R.id.choose_image);
	
		
		
		Intent intent = getIntent();
		String path = intent.getStringExtra("path");
		Log.i("info", "HandleImageActivity-->path:" + path);
		
		mFile = new File(path);
		if(path == null){
			Toast.makeText(this, R.string.load_failure, Toast.LENGTH_LONG).show();
			finish();
		}
		
		Display currentDisplay=getWindowManager().getDefaultDisplay();
		int dw=currentDisplay.getWidth();
		int dh=currentDisplay.getHeight();
		mBitmap=DecodeBitmap.decodeSampledBitmapFromFile(path, dw, dh);
		mGetImage.setImageBitmap(mBitmap);
		tempBitmap=mBitmap;//临时
	}
	
	
	
public void onClick(View view) throws IOException
{
		
		int viewId = view.getId();
		Log.i("info", "onCLick-->viewId:" + viewId);
		
		switch(viewId)
		{
		    case R.id.crop_button:
		    	File f = new File(getExternalCacheDir(),"c"+mFile.getName());
		    	CropImageUri(Uri.fromFile(mFile),Uri.fromFile(f),CROP_BIG_PICTURE);
		    	break;
			
			case R.id.btn_rotate_leftrotate_c:
				//rotateLeft();
				mybitmap=new MyBitMap(mBitmap);
				bitmap=mybitmap.rotateLeft();
				mGetImage.setImageBitmap(bitmap);
				mBitmap=bitmap;
				break;
			case R.id.btn_rotate_rightrotate_c:
				//rotateRight();
				mybitmap=new MyBitMap(mBitmap);
				bitmap=mybitmap.rotateRight();
				mGetImage.setImageBitmap(bitmap);
				mBitmap=bitmap;
				break;
			case R.id.btn_rotate_horizontalrotate_c:
				mybitmap=new MyBitMap(mBitmap);
				bitmap=mybitmap.ReverseHorizontal();
				mGetImage.setImageBitmap(bitmap);
				mBitmap=bitmap;
				break;
			case R.id.btn_rotate_verticalrotate_c:
				mybitmap=new MyBitMap(mBitmap);
				bitmap=mybitmap.ReverseVertical();
				mGetImage.setImageBitmap(bitmap);
				mBitmap=bitmap;
				break;
			
			case R.id.zoom_in:
				zoom_in();
				break;
			case R.id.zoom_out:
				zoom_out();
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
	mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
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




private void CropImageUri(Uri uri,Uri outuri, int requestCode)
{
	 Intent intent = new Intent("com.android.camera.action.CROP");
	 intent.setDataAndType(uri, "image/*");
	 intent.putExtra("crop", "true");
	 intent.putExtra("scale", true);
	 intent.putExtra(MediaStore.EXTRA_OUTPUT, outuri);
	 intent.putExtra("return-data", false);
	 intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
	 intent.putExtra("noFaceDetection", true); // no face detection
	 startActivityForResult(intent, requestCode);
}



protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	super.onActivityResult(requestCode, resultCode, data);
	
	switch (requestCode) {
       case CROP_BIG_PICTURE://from crop_big_picture

	File f = new File(getExternalCacheDir(),"c"+mFile.getName());
	if(f.exists())
	{
		Display currentDisplay=getWindowManager().getDefaultDisplay();
		int dw=currentDisplay.getWidth();
		int dh=currentDisplay.getHeight();
		mBitmap=DecodeBitmap.decodeSampledBitmapFromFile(getExternalCacheDir()+"/"+"c"+mFile.getName(), dw, dh);
		mGetImage.setImageBitmap(mBitmap);
		f.delete();
		Log.i("info11111111111111111111", "onCLick-->viewId:111111111111");
		
	}
	
		 break;
	}

}






private void back()//返回上层
{
	finish();
}



Bitmap resizedBitmap=null;
int flag=0;
public Bitmap zoom(Bitmap bm, float scale){
	Log.i("info", "zoom-->bm.getWidth():" + bm.getWidth());


	scaleWidth=(float) (scaleWidth*scale);
	scaleHeight=(float) (scaleHeight*scale);
	Log.i("scaleWidth and scaleHeight are: ",scaleWidth+ " "+scaleHeight);
	
	if(scaleWidth<0.2|scaleWidth>1.2)
	{
		if(flag==0)
		{
		new AlertDialog.Builder(EditImageActivity.this)
		.setTitle("无法继续缩放！")
		.setIcon(android.R.drawable.ic_dialog_info)
		//.setMessage()
		.setPositiveButton("确定", null).show();
		}
		flag=1;
		return resizedBitmap;
	}
	    flag=0;
		
	Matrix matrix = new Matrix();
	matrix.postScale(scaleWidth, scaleHeight);
	resizedBitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
	 
	return resizedBitmap;
	
}


private void zoom_in()
{
	Bitmap bitMap = zoom(tempBitmap, (float) 1.1);
	mGetImage.setImageBitmap(bitMap);		
	mBitmap=bitMap;
}


private  void zoom_out()
{
	Bitmap bitMap = zoom(tempBitmap, (float) 0.9);
	mGetImage.setImageBitmap(bitMap);	
	mBitmap=bitMap;
}











		
		




		
	
	
}