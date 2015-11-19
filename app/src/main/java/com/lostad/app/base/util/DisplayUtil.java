package com.lostad.app.base.util;

import java.text.DecimalFormat;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 转换工具类
 * @author xssong
 *
 */
public final class DisplayUtil {
    public static final float SCALE = 0.5f;
    private static final int STEP = 3;
    private DisplayUtil(){
        
    }
     /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     * @param context
     * @param pxValue
     * @return
     */
    public static int pxToDip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (pxValue / scale + SCALE);
    }
    
     /**
      * 将dip或dp值转换为px值，保证尺寸大小不变
      * @param dipValue
      * @param scale
      * @return
      */
    public static int dipToPx(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (dipValue * scale + SCALE);
    }
    
     /**
      * 将px值转换为sp值，保证文字大小不变
      * @param pxValue
      * @param fontScale
      * @return
      */
    public static int pxToSp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;  
        return (int) (pxValue / fontScale + SCALE);
    }
    
     /**
      * 将px值转换为sp值，保证文字大小不变
      * @param spValue
      * @param fontScale
      * @return
      */
    public static int spToPx(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;  
        return (int) (spValue * fontScale + SCALE);
    }
    /**
     * 生成圆型头像
     * @param bitmap 位图
     * @return 位图
     */
    public static Bitmap toRoundCorner(Bitmap bitmap) { 
        return toRoundCorner(bitmap, 0);
    }
    /**
     * 生成圆角头像
     * @param bitmap 位图
     * @param pixels 像素
     * @return 位图
     */
    public static Bitmap toRoundCorner(Bitmap bitmap, float pixels) { 
        if (pixels == 0){
            return toRoundBitmap(bitmap);
        }
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);  
        Canvas canvas = new Canvas(output);  
  
        final int color = 0xff424242;  
        final Paint paint = new Paint();  
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());  
        final RectF rectF = new RectF(rect);  
        final float roundPx = pixels;  
  
        paint.setAntiAlias(true);  
        canvas.drawARGB(0, 0, 0, 0);  
        
        paint.setColor(color);  
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);  
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));  
        canvas.drawBitmap(bitmap, rect, rect, paint);  
        return output;  
    }
    private static Bitmap toRoundBitmap(Bitmap bitmap) {  
        if (bitmap == null){
            return null;
        }
        int width = bitmap.getWidth();  
        int height = bitmap.getHeight();  
        //正方形的边长  
        int r = 0;  
        if(width > height) {  
            r = height;  
        } else {  
            r = width;  
        }  
        //构建一个bitmap  
        Bitmap backgroundBmp = Bitmap.createBitmap(width, height, Config.ARGB_8888);  
        Canvas canvas = new Canvas(backgroundBmp);  //new一个Canvas，在backgroundBmp上画图  
        Paint paint = new Paint();  
        //设置边缘光滑，去掉锯齿  
        paint.setAntiAlias(true);  
        //宽高相等，即正方形  
        RectF rect = new RectF(0, 0, r, r);  
        //通过制定的rect画一个圆角矩形，当圆角X轴方向的半径等于Y轴方向的半径时，  
        //且都等于r/2时，画出来的圆角矩形就是圆形  
        canvas.drawRoundRect(rect, r/2, r/2, paint);  
        //设置当两个图形相交时的模式，SRC_IN为取SRC图形相交的部分，多余的将被去掉  
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));  
        //canvas将bitmap画在backgroundBmp上  
        canvas.drawBitmap(bitmap, null, rect, paint);  
        //返回已经绘画好的backgroundBmp  
        return backgroundBmp;  
    }  
    /**
     * 保留小数点位数
     */
    public static String floatToFixed(Float value){
        if (value == 0){
            return "0.00";
        }
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(value);
    }
    public static String formatProfit(String value){
        Float profit = Float.parseFloat(value);
        if (profit > 0){
            return "+" + value;
        }
        return value;
    }
    public static String formatProfit(Float value){
        return formatProfit(floatToFixed(value));
    }
   
    /**
     * 设置默认图片
     * @param context
     * @param header
     * @param pixels
     * @param resourceId
     */
    public static void setDefaultImage(final Context context, final ImageView header, float pixels, int resourceId){
        Bitmap defaultBitMap = BitmapFactory.decodeResource(context.getResources(), resourceId);
        Bitmap heardIcon = DisplayUtil.toRoundCorner(defaultBitMap, pixels);
        header.setImageBitmap(heardIcon);
    }
}
