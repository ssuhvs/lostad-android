package com.lostad.applib.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
/**
 * 窗口工具类,提供可重用的窗口
 * @author sszvip
 *
 */
public class ImageChooser {
	
    private static int mSelectedIndex = -1;
	public static final int REQUEST_CODE_CROP    = -10001;//裁剪
    public static final String FILE_NAME_PIC_TEMP    = "temp_pic";
    private static final int SCALE = 5;//照片缩小比例
    private static int width =320,height=320;
    
    public static void setWidth(int w){
    	width = w;
    }
    public static void setHeight(int h){
    	height = h;
    }
    
	public static void showImagePicker(final Activity context,final int requestCode){
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("图片来源");
		builder.setNegativeButton("取消", null);
		builder.setItems(new String[]{"拍照","相册"}, new DialogInterface.OnClickListener() {
			//类型码
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case 0:
					mSelectedIndex = 0;
					Uri imageUri = null;
					Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					ImageUtil.deleteFile(Environment.getExternalStorageDirectory().getAbsolutePath(),FILE_NAME_PIC_TEMP);
					imageUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory().getAbsolutePath(),FILE_NAME_PIC_TEMP));
					//指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
					openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
					context.startActivityForResult(openCameraIntent, requestCode);
					break;
				case 1:
					mSelectedIndex = 1;
					Intent intent = new Intent();  
			        intent.setType("image/*");  
			        intent.setAction(Intent.ACTION_PICK);  
			        context.startActivityForResult(intent, requestCode);
					break;

				default:
					break;
				}
			}
		});
		builder.create().show();
	}
	
	
	public static void onActivityResult(Activity ctx,boolean isCrop,int requestCode, int resultCode, Intent data,ImageCallback callback) {
		if (resultCode == Activity.RESULT_OK) {
			switch (mSelectedIndex) {//上次选择的索引
			case 0://相机
				if(isCrop){//裁剪
					Uri uri = null;
					if (data != null) {
						uri = data.getData();
					}else {
						uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),FILE_NAME_PIC_TEMP));
					}
					cropImage(ctx,uri,width, height, REQUEST_CODE_CROP);//再次跳转
				}else{
					//将保存在本地的图片取出并缩小后显示在界面上
					Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()+"/"+FILE_NAME_PIC_TEMP);
					Bitmap newBitmap = ImageUtil.zoomBitmap(bitmap, bitmap.getWidth() / SCALE, bitmap.getHeight() / SCALE);
					//由于Bitmap内存占用较大，这里需要回收内存，否则会报out of memory异常
					bitmap.recycle();
					callback.onPicSelected(newBitmap);
				}
				
				break;

			case 1: //选择的照片的数据
				
				if(isCrop){//裁剪

					Uri uri = null;
					if (data != null) {
						uri = data.getData();
						System.out.println("Data");
					}else {
						System.out.println("File");
						uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),FILE_NAME_PIC_TEMP));
					}
					cropImage(ctx,uri,width, height, REQUEST_CODE_CROP);
					break;
					
				}else{
					
					ContentResolver resolver = ctx.getContentResolver();
					//照片的原始资源地址
					Uri originalUri = data.getData(); 
		            try {
		            	//使用ContentProvider通过URI获取原始图片
						Bitmap photo = MediaStore.Images.Media.getBitmap(resolver, originalUri);
						if (photo != null) {
							//为防止原始图片过大导致内存溢出，这里先缩小原图显示，然后释放原始Bitmap占用的内存
							Bitmap smallBitmap = ImageUtil.zoomBitmap(photo, photo.getWidth() / SCALE, photo.getHeight() / SCALE);
							//释放原始图片占用的内存，防止out of memory异常发生
							photo.recycle();
							callback.onPicSelected(smallBitmap);
						}
					} catch (FileNotFoundException e) {
					    e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
		            
				}
				break;
				
			case REQUEST_CODE_CROP://裁剪结果
				Bitmap photo = null;
				Uri photoUri = data.getData();
				if (photoUri != null) {
					photo = BitmapFactory.decodeFile(photoUri.getPath());
				}
				if (photo == null) {
					Bundle extra = data.getExtras();
					if (extra != null) {
		                photo = (Bitmap)extra.get("data");  
		            }  
				}
				callback.onPicSelected(photo);
				
				break;
			}
		}
	}
	
	
	
	//截取图片
		public static void cropImage(Activity ctx,Uri uri, int outputX, int outputY, int requestCode){
//			Intent intent = new Intent("com.android.camera.action.CROP");  
//	        intent.setDataAndType(uri, "image/*");  
//	        intent.putExtra("crop", "true");  
//	        intent.putExtra("aspectX", 1);  
//	        intent.putExtra("aspectY", 1);  
//	        intent.putExtra("outputX", outputX);   
//	        intent.putExtra("outputY", outputY); 
//	        intent.putExtra("outputFormat", "png");
//	        intent.putExtra("noFaceDetection", true);
//	        intent.putExtra("return-data", true);  
//	        ctx.startActivityForResult(intent, requestCode);
			
			Intent intent = new Intent("com.android.camera.action.CROP");
			intent.setDataAndType(uri, "image/*");
			// 设置裁剪
			intent.putExtra("crop", "true");
			// aspectX aspectY 是宽高的比例
			intent.putExtra("aspectX", 1);
			intent.putExtra("aspectY", 1);
			// outputX outputY 是裁剪图片宽高
			intent.putExtra("outputX", outputX);
			intent.putExtra("outputY", outputY);
			intent.putExtra("return-data", true);
			ctx.startActivityForResult(intent, requestCode);
		}
		
		
		public interface ImageCallback{
			void onPicSelected(Bitmap bitmap);
		}
}
