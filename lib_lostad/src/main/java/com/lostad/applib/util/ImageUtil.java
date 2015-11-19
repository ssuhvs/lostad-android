package com.lostad.applib.util;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageUtil {
	public static int computeSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		int initialSize = computeInitialSampleSize(options, minSideLength,
				maxNumOfPixels);
		int roundedSize;
		if (initialSize <= 8) {
			roundedSize = 1;
			while (roundedSize < initialSize) {
				roundedSize <<= 1;
			}
		} else {
			roundedSize = (initialSize + 7) / 8 * 8;
		}
		return roundedSize;
	}

	private static int computeInitialSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		double w = options.outWidth;
		double h = options.outHeight;
		int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
				.sqrt(w * h / maxNumOfPixels));
		int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
				Math.floor(w / minSideLength), Math.floor(h / minSideLength));
		if (upperBound < lowerBound) {
			return lowerBound;
		}
		if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
			return 1;
		} else if (minSideLength == -1) {
			return lowerBound;
		} else {
			return upperBound;
		}
	}

//	public static Bitmap createQRCode(String str, int widthAndHeight)
//			throws WriterException {
//		Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
//		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
//		BitMatrix matrix = new MultiFormatWriter().encode(str,
//				BarcodeFormat.QR_CODE, widthAndHeight, widthAndHeight);
//		int width = matrix.getWidth();
//		int height = matrix.getHeight();
//		int[] pixels = new int[width * height];
//		int BLACK = 0xff000000;
//		for (int y = 0; y < height; y++) {
//			for (int x = 0; x < width; x++) {
//				if (matrix.get(x, y)) {
//					pixels[y * width + x] = BLACK;
//				}
//			}
//		}
//		Bitmap bitmap = Bitmap.createBitmap(width, height,
//				Bitmap.Config.ARGB_8888);
//		bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
//		return bitmap;
//	}
	
	/**
	 * Transfer drawable to bitmap
	 * 转让可拉的位图
	 * @param drawable
	 * @return
	 */
	public static Bitmap drawableToBitmap(Drawable drawable) {
		int w = drawable.getIntrinsicWidth();
		int h = drawable.getIntrinsicHeight();

		Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Config.ARGB_8888
				: Config.RGB_565;
		Bitmap bitmap = Bitmap.createBitmap(w, h, config);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, w, h);
		drawable.draw(canvas);
		return bitmap;
	}

	/**
	 * Bitmap to drawable
	 * 位图,可拉的
	 * @param bitmap
	 * @return
	 */
	public static Drawable bitmapToDrawable(Bitmap bitmap) {
		return new BitmapDrawable(bitmap);
	}

	/**
	 * Input stream to bitmap
	 * 输入流,位图
	 * @param inputStream
	 * @return
	 * @throws Exception
	 */
	public static Bitmap inputStreamToBitmap(InputStream inputStream)
			throws Exception {
		return BitmapFactory.decodeStream(inputStream);
	}

	/**
	 * Byte transfer to bitmap
	 * 字节传输位图
	 * @param byteArray
	 * @return
	 */
	public static Bitmap byteToBitmap(byte[] byteArray) {
		if (byteArray.length != 0) {
			return BitmapFactory
					.decodeByteArray(byteArray, 0, byteArray.length);
		} else {
			return null;
		}
	}

	/**
	 * Byte transfer to drawable
	 * 字节传输可拉的
	 * @param byteArray
	 * @return
	 */
	public static Drawable byteToDrawable(byte[] byteArray) {
		ByteArrayInputStream ins = null;
		if (byteArray != null) {
			ins = new ByteArrayInputStream(byteArray);
		}
		return Drawable.createFromStream(ins, null);
	}

	/**
	 * Bitmap transfer to bytes
	 * 位图转移到字节
	 * @param
	 * @return
	 */
	public static byte[] bitmapToBytes(Bitmap bm) {
		byte[] bytes = null;
		if (bm != null) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
			bytes = baos.toByteArray();
		}
		return bytes;
	}

	/**
	 * Drawable transfer to bytes
	 * 可拉的转移到字节
	 * @param drawable
	 * @return
	 */
	public static byte[] drawableToBytes(Drawable drawable) {
		BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
		Bitmap bitmap = bitmapDrawable.getBitmap();
		byte[] bytes = bitmapToBytes(bitmap);
		return bytes;
	}

	/**
	 * Base64 to byte[]
//	 */
//	public static byte[] base64ToBytes(String base64) throws IOException {
//		byte[] bytes = Base64.decode(base64);
//		return bytes;
//	}
//
//	/**
//	 * Byte[] to base64
//	 */
//	public static String bytesTobase64(byte[] bytes) {
//		String base64 = Base64.encode(bytes);
//		return base64;
//	}

	/**
	 * Create reflection images
	 * 创建反射映像
	 * @param bitmap
	 * @return
	 */
	public static Bitmap createReflectionImageWithOrigin(Bitmap bitmap) {
		final int reflectionGap = 4;
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();

		Matrix matrix = new Matrix();
		matrix.preScale(1, -1);

		Bitmap reflectionImage = Bitmap.createBitmap(bitmap, 0, h / 2, w,
				h / 2, matrix, false);

		Bitmap bitmapWithReflection = Bitmap.createBitmap(w, (h + h / 2),
				Config.ARGB_8888);

		Canvas canvas = new Canvas(bitmapWithReflection);
		canvas.drawBitmap(bitmap, 0, 0, null);
		Paint deafalutPaint = new Paint();
		canvas.drawRect(0, h, w, h + reflectionGap, deafalutPaint);

		canvas.drawBitmap(reflectionImage, 0, h + reflectionGap, null);

		Paint paint = new Paint();
		LinearGradient shader = new LinearGradient(0, bitmap.getHeight(), 0,
				bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff,
				0x00ffffff, TileMode.CLAMP);
		paint.setShader(shader);
		// Set the Transfer mode to be porter duff and destination in
		paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
		// Draw a rectangle using the paint with our linear gradient
		canvas.drawRect(0, h, w, bitmapWithReflection.getHeight()
				+ reflectionGap, paint);

		return bitmapWithReflection;
	}

	/**
	 * Get rounded corner images
	 * 得到圆角图片
	 * @param bitmap
	 * @param roundPx
	 *            5 10
	 * @return
	 */
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		Bitmap output = Bitmap.createBitmap(w, h, Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, w, h);
		final RectF rectF = new RectF(rect);
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}

	/**
	 * Resize the bitmap
	 * 调整位图
	 * @param bitmap
	 * @param width
	 * @param height
	 * @return
	 */
	public static Bitmap zoomBitmap(Bitmap bitmap, int width, int height) {
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		Matrix matrix = new Matrix();
		float scaleWidth = ((float) width / w);
		float scaleHeight = ((float) height / h);
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
		return newbmp;
	}

	/**调整的
	 * Resize the drawable
	 * @param drawable
	 * @param w
	 * @param h
	 * @return
	 */
	public static Drawable zoomDrawable(Drawable drawable, int w, int h) {
		int width = drawable.getIntrinsicWidth();
		int height = drawable.getIntrinsicHeight();
		Bitmap oldbmp = drawableToBitmap(drawable);
		Matrix matrix = new Matrix();
		float sx = ((float) w / width);
		float sy = ((float) h / height);
		matrix.postScale(sx, sy);
		Bitmap newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height,
				matrix, true);
		return new BitmapDrawable(newbmp);
	}
	
	/**
	 * Get images from SD card by path and the name of image
	 * @param //photoName从SD卡获得图像的路径和名称的形象
	 * @return
	 */
	public static Bitmap getPhotoFromSDCard(String path,String photoName){
		Bitmap photoBitmap = BitmapFactory.decodeFile(path + "/" +photoName +".png");
		if (photoBitmap == null) {
			return null;
		}else {
			return photoBitmap;
		}
	}
	
	/**
	 * Check the SD card 
	 * @return
	 * 检查SD卡
	 */
	public static boolean checkSDCardAvailable(){
		return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
	}
	
	/**从SD卡获得图像的路径和名称的形象
	 * Get image from SD card by path and the name of image
	 * @param
	 * @return
	 */
	public static boolean findPhotoFromSDCard(String path,String photoName){
		boolean flag = false;
		
		if (checkSDCardAvailable()) {
			File dir = new File(path);
			if (dir.exists()) {
				File folders = new File(path);
				File photoFile[] = folders.listFiles();
				for (int i = 0; i < photoFile.length; i++) {
					String fileName = photoFile[i].getName().split("\\.")[0];
					if (fileName.equals(photoName)) {
						flag = true;
					}
				}
			}else {
				flag = false;
			}
//			File file = new File(path + "/" + photoName  + ".jpg" );
//			if (file.exists()) {
//				flag = true;
//			}else {
//				flag = false;
//			}
			
		}else {
			flag = false;
		}
		return flag;
	}
	
	/**
	 * Save image to the SD card 将图像保存到SD卡
	 * @param photoBitmap
	 * @param photoName
	 * @param path
	 */
	public static File saveToSDCard(Bitmap photoBitmap,String path,String photoName){
		File photoFile = null ;
		if (checkSDCardAvailable()) {
			File dir = new File(path);
			if (!dir.exists()){
				dir.mkdirs();
			}
			
			photoFile = new File(path , photoName);
			FileOutputStream fileOutputStream = null;
			try {
				fileOutputStream = new FileOutputStream(photoFile);
				if (photoBitmap != null) {
					if (photoBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)) {
						fileOutputStream.flush();
//						fileOutputStream.close();
					}
				}
			} catch (FileNotFoundException e) {
				photoFile.delete();
				photoFile = null;
				e.printStackTrace();
			} catch (IOException e) {
				photoFile.delete();
				photoFile = null;
				e.printStackTrace();
			} finally{
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} 
		
		return photoFile;
	}
	
	/**
	 * Delete the image from SD   card从SD卡删除的图像
	 * @param
	 * @param path
	 * file:///sdcard/temp.jpg
	 */
	public static void deleteAllPhoto(String path){
		if (checkSDCardAvailable()) {
			File folder = new File(path);
			File[] files = folder.listFiles();
			for (int i = 0; i < files.length; i++) {
				files[i].delete();
			}
		}
	}
	
	public static void deleteFile(String path,String file){
		if (checkSDCardAvailable()) {
			try{
				File f = new File(file,path);
				if(f.exists()){
					f.delete();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public static Bitmap getSmallBitmap(String filePath,String newPath,int width,int height) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);
		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options,width,height);
		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		
		Bitmap bm = BitmapFactory.decodeFile(filePath, options);
		try {
			bm=saveImageTo100K(newPath,bm);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return null;
		}
		return bm ;

	}
	
	public static Bitmap getSmallBitmap(String filePath) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);
		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options,240,320);
		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		
		Bitmap bm = BitmapFactory.decodeFile(filePath, options);
		if(bm == null){
			return  null;
		}
//		int degree = readPictureDegree(filePath);
//		bm = rotateBitmap(bm,degree) ;
		ByteArrayOutputStream baos = null ;
		try{
			baos = new ByteArrayOutputStream();
			bm.compress(Bitmap.CompressFormat.JPEG, 30, baos);
		}finally{
			try {
				if(baos != null)
					baos.close() ;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return bm ;

	}
	private static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			// Calculate ratios of height and width to requested height and
			// width
			final int heightRatio = Math.round((float) height
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);

			// Choose the smallest ratio as inSampleSize value, this will
			// guarantee
			// a final image with both dimensions larger than or equal to the
			// requested height and width.
			inSampleSize = heightRatio < widthRatio ? widthRatio : heightRatio;
		}

		return inSampleSize;
	}
	
	//图片压缩的方法，压缩图片小于100K
	public static Bitmap saveImageTo100K(String path,Bitmap image) throws Exception{
		

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
			int options = 100;
			while ( baos.toByteArray().length / 1024>100) {	//循环判断如果压缩后图片是否大于100kb,大于继续压缩		
				baos.reset();//重置baos即清空baos
				image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
				options -= 10;//每次都减少10
			}
			    FileOutputStream fos = new FileOutputStream(new File(path));  
	        fos.write(baos.toByteArray());  
	        fos.flush();  
	        fos.close();  
		    return image ;
	}
}
