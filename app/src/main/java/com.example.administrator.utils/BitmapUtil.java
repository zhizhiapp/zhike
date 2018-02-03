package com.example.administrator.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.util.Base64;

import com.example.administrator.constants.MyConstants;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class BitmapUtil {
	private static URL url;
	public static Bitmap getImg(String imgUrl) { //下载中文名字的图片
		Bitmap bitmap =null;
		try {
			URL url = new URL("http://112.74.60.159/ProductPictures/"+java.net.URLEncoder.encode(imgUrl));
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();//打开一个这个图片的连接
			connection.setConnectTimeout(500000);//下载图片的最大时间,自己设置的     1000 = 1秒   要在5秒钟内把这张图片下载到、否则就是网络超时
			connection.setDoInput(true);//设置可以  上网
			connection.setUseCaches(false);//设置缓存   
			InputStream stream =  connection.getInputStream();
			bitmap = BitmapFactory.decodeStream(stream);
			stream.close();//关闭流
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;
	}
	
	public static Bitmap GetImg(String imgUrl,int id) {//下载图片
		Bitmap bitmap =null;//定义个对象     告诉程序我现在还没有 图片文件
		try {
			if (id==1) {//微信
				 url = new URL(imgUrl);
			}
			
			if (id == 2) {//手机
				//  http://www.xiongsongai.com/
				 url = new URL(MyConstants.Service_URL+imgUrl);
			}
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();//打开一个这个图片的连接
			connection.setConnectTimeout(300000);//下载图片的最大时间,自己设置的     1000 = 1秒   要在5秒钟内把这张图片下载到、否则就是网络超时
			connection.setDoInput(true);//设置可以  上网
			connection.setUseCaches(false);//设置缓存   
			InputStream stream =  connection.getInputStream();//拿到网络图片的流
			bitmap = BitmapFactory.decodeStream(stream);//把下载到的网络图片  赋值给这个 bitmap 
			stream.close();//关闭流
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;//调用方法的时候   可以拿到返回值
	}
	
	
    
	public static Drawable loadDrawable(String temp){  //base 64
      ByteArrayInputStream bais = new ByteArrayInputStream(Base64.decode(temp.getBytes(), Base64.DEFAULT));  
      return Drawable.createFromStream(bais, "");  
  }


	/**
	 *将文件压缩后覆盖源文件
	 */
	public static void compressImage(File file) {  //图片的质量压缩
		Bitmap bitmap=BitmapFactory.decodeFile(file.getAbsolutePath());
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 80;//先压缩到80%
		while (baos.toByteArray().length / 1024 > 200) { // 循环判断如果压缩后图片是否大于200kb,大于继续压缩
			if (options <= 0) {//有的图片过大，可能当options小于或者等于0时，它的大小还是大于目标大小，于是就会发生异常，异常的原因是options超过规定值。所以此处需要判断一下
				break;
			}
			baos.reset();// 重置baos即清空baos
			options -= 10;// 每次都减少10
			bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
		}
		try {
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(baos.toByteArray());
			fos.close();
			baos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	/**
	 * 获取缩略图
	 * @param imagePath:文件路径
	 * @param width:缩略图宽度
	 * @param height:缩略图高度
	 * @return
	 */
	public static Bitmap getImageThumbnail(String imagePath, int width, int height) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);
		int h = options.outHeight;//获取图片高度
		int w = options.outWidth;//获取图片宽度
		int scaleWidth = w / width; //计算宽度缩放比
		int scaleHeight = h / height; //计算高度缩放比
		int scale = 1;//初始缩放比
		if (scaleWidth < scaleHeight) {//选择合适的缩放比
			scale = scaleWidth;
		} else {
			scale = scaleHeight;
		}
		if (scale <= 0) {//判断缩放比是否符合条件
			scale = 1;
		}
		options.inSampleSize = scale;
		// 重新读入图片，读取缩放后的bitmap，注意这次要把inJustDecodeBounds 设为 false
		options.inJustDecodeBounds = false;
		bitmap = BitmapFactory.decodeFile(imagePath, options);
		// 利用ThumbnailUtils来创建缩略图，这里要指定要缩放哪个Bitmap对象
		bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
		return bitmap;
	}



















}
