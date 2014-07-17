package com.imagehandle;

import java.io.File;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * 
 * @author mrpod2g
 * 
 * 
 */

public class ChooseImageActivity extends Activity{
	
    //ImageView imv;
	private static final int FLAG_CHOOSE = 1;
	private static final int FLAG_CHOOSE2 = 2;
	static String imageFilePath;   
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		 
	        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
	                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.main);
	}
	
	

	public void onClick(View view){
		
		switch(view.getId()){
		case R.id.choose_img:
			Intent intent = new Intent();
			intent.setAction(Intent.ACTION_GET_CONTENT);
			intent.setType("image/*");
			startActivityForResult(intent, FLAG_CHOOSE);
			break;
		case R.id.choose_cam:
			imageFilePath=Environment.getExternalStorageDirectory().getAbsolutePath()+"/mypicture.jpg";
			File imageFile=new File(imageFilePath);
			Uri imageFileUri=Uri.fromFile(imageFile);
			intent=new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,imageFileUri);
			startActivityForResult(intent, FLAG_CHOOSE2);
			break;
			
		case R.id.about_me:
			pintu();
			break;
			
			
			
			default:
				break;
		}
		
	}
	
	
	
	private void pintu()
	{
		Intent intent = new Intent(this,AboutMeActivity.class);
		startActivity(intent);
		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		Log.i("info", "onActivityResult-->requestCode:" + requestCode);
		if (resultCode == RESULT_OK){
			switch(requestCode)
			{
			case 1:
				Uri uri = data.getData();
				Log.i("info", "onActivityResult-->uri:" + uri + "-->uri.getAuthority()" + uri.getAuthority());
				if (!TextUtils.isEmpty(uri.getAuthority()))
				{

					Cursor cursor = getContentResolver().query(uri, new String[]{ MediaStore.Images.Media.DATA }, null, null, null);
					if (null == cursor)
					{
						Toast.makeText(this, R.string.no_found, Toast.LENGTH_SHORT).show();
						return;
					}
					cursor.moveToFirst();
					String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
					Log.d("may", "path="+path);
					Intent intent = new Intent(this, HandleImageActivity.class);
					intent.putExtra("path", path);
					startActivity(intent);				
				}
				else
				{
					Intent intent = new Intent(this, HandleImageActivity.class);
					intent.putExtra("path", uri.getPath());
					Log.d("may222222222222222222", "path="+uri.getPath());
					startActivity(intent);	
				}				
				break;
				
			case 2:
				Log.i("infoooooooooo",imageFilePath);
				Intent intent = new Intent(this, HandleImageActivity.class);
				String path=imageFilePath;
				intent.putExtra("path",path);
				Log.i("infoooooooooo",path);
				//System.out.println(path);
				startActivity(intent);
				break;
				
				default:
					break;
			}
		}
	}
	
}
