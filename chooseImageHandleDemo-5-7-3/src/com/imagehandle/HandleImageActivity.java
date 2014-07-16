package com.imagehandle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.imagehandle.utils.DecodeBitmap;


public class HandleImageActivity extends Activity{

	private ImageView mGetImage = null;
	private TextView TV_Info=null;
	private File mFile;
	private String mNewPath,temppath;
	private int flag=1;
	private static final int CAMERA_WITH_DATA = 1;
	private static final int PHOTO_PICKED_WITH_DATA = 2;
	private static final int SELECT_PICTURE = 3;
	private Bitmap mBitmap = null;
    

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);				
		
		// 全屏显示
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		
		setContentView(R.layout.image_handle);
	
		
		mGetImage = (ImageView) findViewById(R.id.choose_image);
		Intent intent = getIntent();
		String path = intent.getStringExtra("path");
		temppath=path;
		Log.i("info", "HandleImageActivity-->path:" + path);
		mFile = new File(path);
		if(path == null){
			Toast.makeText(this, R.string.load_failure, Toast.LENGTH_LONG).show();
			finish();
		}
		
		
	    String name=mFile.getName();
	    TV_Info = (TextView) findViewById(R.id.TV_Info);
		TV_Info.setText(name);
		
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
			case R.id.edit:
				editHandle();
				break;
			case R.id.crop_button:
				doCropPhoto(mBitmap);
				break;
			
			case R.id.tone:
				toneHandle();
				break;
			case R.id.frame:
				addFrameHandle();
				break;
			case R.id.share:
				share();
				break;
			case R.id.effect:
				effect();
				break;
			case R.id.save_as:
				createSDCardDir();
				saveMyBitmap();
				flag=0;
				break;
			case R.id.back_home:
				cancelSave();
				break;
			default:
				break;
		}
	}
	
	
	
	
	private void share()
	{
		 Uri u=Uri.fromFile(mFile);
		 Intent intent=new Intent(Intent.ACTION_SEND);  
         intent.setType("image/*");  
         intent.putExtra(Intent.EXTRA_STREAM, u);  
         intent.putExtra(Intent.EXTRA_SUBJECT, "Share");  
         intent.putExtra(Intent.EXTRA_TEXT, "这是我用图片制作软件处理的照片，不错吧~");  
         intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
         startActivity(Intent.createChooser(intent,"一键分享至"));  
	}
	
	private void toneHandle()//调色跳转
	{
		Log.i("infoooooooooo","wqwqwwqwqwqwqw");
		Intent intent = new Intent(this, ToneImageActivity.class);
		String path=temppath;
		intent.putExtra("path",path);
		Log.i("infoooooooooo",path);
		//System.out.println(path);
		startActivity(intent);
		
		
	}
	
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		Log.i("info", "onActivityResult-->requestCode:" + requestCode);
		
		if (resultCode == RESULT_OK && null != data){
			switch (requestCode) {
				case CAMERA_WITH_DATA:
					Bitmap cropBmp = data.getParcelableExtra("data");
					if(cropBmp != null){
						doCropPhoto(cropBmp);
					}
					
					break;
				case PHOTO_PICKED_WITH_DATA:
					Bitmap bmp = data.getParcelableExtra("data");
	
					if(bmp != null){		
						mGetImage.setImageBitmap(bmp);		
					}		
					break;
				case SELECT_PICTURE:
					mGetImage.setImageDrawable(Drawable.createFromPath(mFile.getAbsolutePath()));
					break;
				default:
					break;
			}
		}
	}

	private void editHandle(){
		Log.i("infoooooooooo","wqwqwwqwqwqwqw");
		Intent intent = new Intent(this, EditImageActivity.class);
		String path=temppath;
		intent.putExtra("path",path);
		Log.i("infoooooooooo",path);
		//System.out.println(path);
		startActivity(intent);
		
	}
	
	public static Intent getCropImageIntent(Bitmap data){
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setType("image/*");
	    intent.putExtra("data", data);
	    intent.putExtra("crop", "true");
	    intent.putExtra("aspectX", 1);
	    intent.putExtra("aspectY", 1);
	    intent.putExtra("outputX", 128);
	    intent.putExtra("outputY", 128);
//	    intent.putExtra("output", data);
        intent.putExtra("outputFormat", "JPEG");//返回格式
	    intent.putExtra("return-data", true);

	    return intent;
	}  
	
	private void doCropPhoto(Bitmap data){

		Intent intent = getCropImageIntent(data);
		startActivityForResult(intent, PHOTO_PICKED_WITH_DATA);
	}
	
	
	
	
	private void addFrameHandle()//添加边框跳转
	{
		Log.i("infoooooooooo","wqwqwwqwqwqwqw");
		Intent intent = new Intent(this, AddframeImageActivity.class);
		String path=temppath;
		intent.putExtra("path",path);
		Log.i("infoooooooooo",path);
		//System.out.println(path);
		startActivity(intent);
	}
	

	
	private void effect()//特效跳转
	{
		Log.i("infoooooooooo","wqwqwwqwqwqwqw");
		Intent intent = new Intent(this, EffectImageActivity.class);
		String path=temppath;
		intent.putExtra("path",path);
		Log.i("infoooooooooo",path);
		//System.out.println(path);
		startActivity(intent);
	}	
	
	
	
	
	
	//判断SD卡是否插入-->
	public String getSDPath(){
		File SDdir = null;
	    boolean sdCardExist = Environment.getExternalStorageState()
	    		.equals(android.os.Environment.MEDIA_MOUNTED);
	    if(sdCardExist){
	    	SDdir = Environment.getExternalStorageDirectory();
	    }
	    if(SDdir != null){
	    	return SDdir.toString();
	    }
	    else{
	    	return null;
	    }
	}

	//创建文件夹
	public void createSDCardDir(){
		if(getSDPath()==null){
			Toast.makeText(this, R.string.no_sd_card, Toast.LENGTH_LONG).show();
		}else{
			if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
				// 创建一个文件夹对象，赋值为外部存储器的目录
				File sdcardDir =Environment.getExternalStorageDirectory();
				//得到一个路径，内容是sdcard的文件夹路径和名字
				mNewPath = sdcardDir.getPath()+"/tempImages/";//newPath在程序中要声明
				Log.i("info", "mNewPath:" + mNewPath);
				File path = new File(mNewPath);
				if (!path.exists()) {
					//若不存在，创建目录，可以在应用启动的时候创建
					path.mkdirs();
				}
			}
			else{
				Log.i("info", "createSDCardDir false");
			}
		}
	}
	
	//保存图片了
	@SuppressLint("SimpleDateFormat")
	public void saveMyBitmap() throws IOException {
	        
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyyMMdd_HHmmss");   
	    String date = dateformat.format(new Date());
	    String bitName = date;
	    Log.i("info", "saveMyBitmap-->bitName:" + bitName);
		File f = new File(mNewPath + bitName + ".jpg");
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
		//finish();
		new AlertDialog.Builder(HandleImageActivity.this)
		.setTitle("保存成功!")
		.setIcon(android.R.drawable.ic_dialog_info)
		.setMessage("图片保存在:"+f.getPath())
		.setPositiveButton("确定", null).show();
	}

	
	
	private void cancelSave(){
		if(flag==1)
		{
			 new AlertDialog.Builder(this)     
	         .setTitle("提示")     
	         .setMessage("你确定不保存吗？")     
	         .setNegativeButton("取消",  
	                 new DialogInterface.OnClickListener() {     
	                 public void onClick(DialogInterface dialog, int which) { 
	                             // TODO Auto-generated method stub                                 
	                     }  
	                 })  
	   
	         .setPositiveButton("确定",  
	                 new DialogInterface.OnClickListener() {     
	                     public void onClick(DialogInterface dialog, int whichButton) {     
	                    	Intent intent = new Intent(HandleImageActivity.this, ChooseImageActivity.class);
	                 		startActivity(intent);  
	                     }     
	                 }).show();    
			
		}
		
		else
		{
			Intent intent = new Intent(HandleImageActivity.this, ChooseImageActivity.class);
     		startActivity(intent); 
		}
		
	}


	
}