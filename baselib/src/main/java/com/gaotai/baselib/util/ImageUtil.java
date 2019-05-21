package com.gaotai.baselib.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * 图片压缩工具类
 *
 * @author MengLiang
 */
public class ImageUtil {

    public static final String CONTENT = "content";
    public static final String FILE = "file";


    /**
     * Uri 转成   文件物理路径
     *
     * @param context
     * @param uri
     * @return
     */
    public static String getFilePath(Context context, Uri uri) {
        String filePath = null;
        if (CONTENT.equalsIgnoreCase(uri.getScheme())) {
            Cursor cursor = context.getContentResolver().query(uri,
                    new String[]{Images.Media.DATA}, null, null, null);
            if (null == cursor) {
                return null;
            }
            try {
                if (cursor.moveToNext()) {
                    filePath = cursor.getString(cursor
                            .getColumnIndex(Images.Media.DATA));
                }
            } finally {
                cursor.close();
            }
        }
        // 从文件中选择
        if (FILE.equalsIgnoreCase(uri.getScheme())) {
            filePath = uri.getPath();
        }
        return filePath;
    }

    /**
     * Bitmap 转换成 Drawable
     *
     * @param bitmap
     * @return
     */
    public static Drawable bitmapToDrawable(Resources res, Bitmap bitmap) {
        // 将字符串转换成Bitmap类型
        Drawable drawable = new BitmapDrawable(res, bitmap);
        return drawable;
    }

    /**
     * byte[] 转换成 Bitmap
     *
     * @param data
     * @return
     */
    public static Bitmap stringtoBitmap(byte[] data) {
        // 将字符串转换成Bitmap类型
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * base64  转换成Bitmap 并压缩成取小图片
     *
     * @param string
     * @return
     */
    public static Bitmap stringtoSmailBitmap1(String string) {
        // 将字符串转换成Bitmap类型
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(string, Base64.DEFAULT);

            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inJustDecodeBounds = true;
            //bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,bitmapArray.length,opts);
            BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length, opts);

            opts.inSampleSize = computeSampleSize(opts, -1, 320 * 320);
            opts.inJustDecodeBounds = false;
            try {
                bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length, opts);
            } catch (OutOfMemoryError err) {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /***
     * 获取小图片 默认 320
     *
     * @param bitmapArray
     * @return
     */
    public static Bitmap stringtoSmailBitmap(byte[] bitmapArray) {
        // 将字符串转换成Bitmap类型
        Bitmap bitmap = null;
        try {
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inJustDecodeBounds = true;
            //bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,bitmapArray.length,opts);
            BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length, opts);

            opts.inSampleSize = computeSampleSize(opts, -1, 120 * 320);
            opts.inJustDecodeBounds = false;
            try {
                bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length, opts);
            } catch (OutOfMemoryError err) {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 按 尺寸获取小图片
     *
     * @param bitmapArray
     * @return
     */
    public static Bitmap stringtoSmailBitmap80(byte[] bitmapArray) {
        // 将字符串转换成Bitmap类型
        Bitmap bitmap = null;
        try {
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inJustDecodeBounds = true;
            //bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,bitmapArray.length,opts);
            BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length, opts);

            opts.inSampleSize = computeSampleSize(opts, -1, 80 * 80);
            opts.inJustDecodeBounds = false;
            try {
                bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length, opts);
            } catch (OutOfMemoryError err) {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static int computeSampleSize(BitmapFactory.Options options,
                                        int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);

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

    private static int computeInitialSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;

        int lowerBound = (maxNumOfPixels == -1) ? 1 :
                (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 :
                (int) Math.min(Math.floor(w / minSideLength),
                        Math.floor(h / minSideLength));

        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
            return lowerBound;
        }

        if ((maxNumOfPixels == -1) &&
                (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }

    /**
     * Bitmap 转成  Base64 字符串
     *
     * @param bitmap
     * @return
     */
    public static String bitmapToBase64(Bitmap bitmap) {
        String string = null;
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.PNG, 100, bStream);
        byte[] bytes = bStream.toByteArray();
        string = Base64.encodeToString(bytes, Base64.DEFAULT);
        return string;
    }

    /***
     * 压缩图片
     *
     * @param image
     * @return
     */
    public static Bitmap compressImage1(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 50, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 40) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();// 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;// 每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片


        return bitmap;
    }


    /***
     * 压缩图片 获取压缩后的图片byte 字符
     *
     * @param image
     * @return
     */
    public static byte[] getStrByCompressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 50) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();// 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;// 每次都减少10
        }
        byte[] bytes = null;

        try {
            bytes = baos.toByteArray();
            baos.close();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return bytes;
    }

    /**
     * Bitmap 转换成 byte[]
     *
     * @param bm
     * @return
     */
    public static byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    /**
     * 压缩图片尺寸大小
     *
     * @param srcPath 图片路径
     * @return
     */
    public static Bitmap compressImageFromFile(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;// 只读边,不读内容
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        float hh = 800f;//
        float ww = 480f;//
        int be = 1;
        if (w > h && w > ww) {
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;// 设置采样率

        //newOpts.inPreferredConfig = Config.ARGB_8888;// 该模式是默认的,可不设
        newOpts.inPreferredConfig = Config.RGB_565;
        newOpts.inPurgeable = true;// 同时设置才会有效
        newOpts.inInputShareable = true;// 。当系统内存不够时候图片自动被回收

        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        // return compressBmpFromBmp(bitmap);//原来的方法调用了这个方法企图进行二次压缩
        // 其实是无效的,大家尽管尝试
        return bitmap;
    }

    /**
     * 根据宽度从本地图片路径获取该图片的缩略图
     *
     * @param temBitmap    图片
     * @param width        缩略图的宽
     * @param addedScaling 额外可以加的缩放比例
     * @return bitmap 指定宽高的缩略图
     */
    public static Bitmap getBitmapByWidth(Bitmap temBitmap, int width,
                                          int addedScaling) {
        Bitmap newBmp = null;
        if (temBitmap == null) {
            return null;
        }
        try {
            int oldwidth = temBitmap.getWidth();
            int oldheight = temBitmap.getHeight();
            int height = 0;
            float inSampleSize = 0;
            if (oldwidth > width) {
                // 根据宽设置缩放比例
                inSampleSize = oldwidth / width + 1 + addedScaling;
                // 计算缩放后的高度
                height = (int) (oldheight / inSampleSize);
            }
            // // 重新设置该属性为false，加载图片返回
            // outOptions.inJustDecodeBounds = false;
            // outOptions.inPurgeable = true;
            // outOptions.inInputShareable = true;
            Matrix matrix = new Matrix();
            matrix.postScale(1 / inSampleSize, 1 / inSampleSize); // 长和宽放大缩小的比例
            newBmp = Bitmap.createBitmap(temBitmap, 0, 0, width, height,
                    matrix, true);
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return newBmp;
    }

    /**
     * 按指定比例压缩
     *
     * @param srcBitmap
     * @param percent
     * @return
     */
    public static Bitmap bitmapZoomByPercent(Bitmap srcBitmap, double percent) {
        int srcWidth = srcBitmap.getWidth();
        int srcHeight = srcBitmap.getHeight();

        float scaleWidth = (float) percent;
        float scaleHeight = (float) percent;

        return bitmapZoomByScale(srcBitmap, scaleWidth, scaleHeight);
    }

    /**
     * 使用长宽缩放比缩放
     *
     * @param srcBitmap
     * @param scaleWidth
     * @param scaleHeight
     * @return
     */
    public static Bitmap bitmapZoomByScale(Bitmap srcBitmap, float scaleWidth,
                                           float scaleHeight) {
        int srcWidth = srcBitmap.getWidth();
        int srcHeight = srcBitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap resizedBitmap = Bitmap.createBitmap(srcBitmap, 0, 0, srcWidth,
                srcHeight, matrix, true);
        if (resizedBitmap != null) {
            return resizedBitmap;
        } else {

            return srcBitmap;
        }
    }

    /**
     * 获取文件的 base64 编码
     *
     * @param filepath 文件路径
     * @return
     */
    public static byte[] GetDataByFile(String filepath) {
        InputStream in = null;
        byte[] data = null;
        // 读取图片字节数组
        try {
            in = new FileInputStream(filepath);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * 根据指定的图像路径和大小来获取缩略图
     * 此方法有两点好处：
     * 1. 使用较小的内存空间，第一次获取的bitmap实际上为null，只是为了读取宽度和高度，
     * 第二次读取的bitmap是根据比例压缩过的图像，第三次读取的bitmap是所要的缩略图。
     * 2. 缩略图对于原图像来讲没有拉伸，这里使用了2.2版本的新工具ThumbnailUtils，使
     * 用这个工具生成的图像不会被拉伸。
     *
     * @param imagePath 图像的路径
     * @param width     指定输出图像的宽度
     * @param height    指定输出图像的高度
     * @return 生成的缩略图
     */
    public static Bitmap getImageThumbnail(String imagePath, int width, int height) {
        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        // 获取这个图片的宽和高，注意此处的bitmap为null  
        bitmap = BitmapFactory.decodeFile(imagePath, options);
        options.inJustDecodeBounds = false; // 设为 false  
        // 计算缩放比  
        int h = options.outHeight;
        int w = options.outWidth;
        int beWidth = w / width;
        int beHeight = h / height;
        int be = 1;
        if (beWidth < beHeight) {
            be = beWidth;
        } else {
            be = beHeight;
        }
        if (be <= 0) {
            be = 1;
        }
        options.inSampleSize = be;
        // 重新读入图片，读取缩放后的bitmap，注意这次要把options.inJustDecodeBounds 设为 false  
        bitmap = BitmapFactory.decodeFile(imagePath, options);
        // 利用ThumbnailUtils来创建缩略图，这里要指定要缩放哪个Bitmap对象  
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
                ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        return bitmap;
    }

    /**
     * 获取视频的缩略图
     * 先通过ThumbnailUtils来创建一个视频的缩略图，然后再利用ThumbnailUtils来生成指定大小的缩略图。
     * 如果想要的缩略图的宽和高都小于MICRO_KIND，则类型要使用MICRO_KIND作为kind的值，这样会节省内存。
     *
     * @param videoPath 视频的路径
     * @param width     指定输出视频缩略图的宽度
     * @param height    指定输出视频缩略图的高度度
     * @param kind      参照MediaStore.Images.Thumbnails类中的常量MINI_KIND和MICRO_KIND。
     *                  其中，MINI_KIND: 512 x 384，MICRO_KIND: 96 x 96
     * @return 指定大小的视频缩略图
     */
    public static Bitmap getVideoThumbnail(String videoPath, int width, int height,
                                           int kind) {
        Bitmap bitmap = null;
        // 获取视频的缩略图  
        bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, kind);
        System.out.println("w" + bitmap.getWidth());
        System.out.println("h" + bitmap.getHeight());
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
                ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        return bitmap;
    }


    /**
     * 图片旋转
     *
     * @param bm
     * @param orientationDegree 旋转角度
     * @return
     */
    public static Bitmap adjustPhotoRotation(Bitmap bm,
                                             final int orientationDegree) {
        Matrix m = new Matrix();
        m.postRotate(orientationDegree);
        Bitmap bm1 = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
                bm.getHeight(), m, true);
        return bm1;
    }


    /**
     * 以90度旋转
     *
     * @param bm
     * @return
     */
    public static Bitmap adjustPhotoRotation90(Bitmap bm) {

        Matrix m = new Matrix();
        m.setRotate(90, (float) bm.getWidth() / 2, (float) bm.getHeight() / 2);
        float targetX, targetY;

        targetX = bm.getHeight();
        targetY = 0;

        final float[] values = new float[9];
        m.getValues(values);
        float x1 = values[Matrix.MTRANS_X];
        float y1 = values[Matrix.MTRANS_Y];
        m.postTranslate(targetX - x1, targetY - y1);
        Bitmap bm1 = Bitmap.createBitmap(bm.getHeight(), bm.getWidth(),
                Bitmap.Config.ARGB_8888);
        Paint paint = new Paint();
        Canvas canvas = new Canvas(bm1);
        canvas.drawBitmap(bm, m, paint);
        return bm1;
    }

    /**
     * 获取图片的旋转角度
     *
     * @param filepath
     * @return
     */
    public static int getExifOrientation(String filepath) {
        int degree = 0;
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(filepath);
        } catch (IOException ex) {
            // MmsLog.e(ISMS_TAG, "getExifOrientation():", ex);
        }

        if (exif != null) {
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, -1);
            if (orientation != -1) {
                // We only recognize a subset of orientation tag values.
                switch (orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        degree = 90;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        degree = 180;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        degree = 270;
                        break;
                    default:
                        break;
                }
            }
        }
        return degree;
    }

    /**
     * 旋转图片，使图片保持正确的方向。
     *
     * @param bitmap  原始图片
     * @param degrees 原始图片的角度
     * @return Bitmap 旋转后的图片
     */
    public static Bitmap rotateBitmap(Bitmap bitmap, int degrees) {
        if (degrees == 0 || null == bitmap) {
            return bitmap;
        }
        Matrix matrix = new Matrix();
        matrix.setRotate(degrees, bitmap.getWidth() / 2, bitmap.getHeight() / 2);
        Bitmap bmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        if (null != bitmap) {
            bitmap.recycle();
        }
        return bmp;
    }

    /**
     * 保存图片文件
     *
     * @param data
     * @param path 保存路径地址
     */
    public static boolean saveImageFile(byte[] data, String path) {
        if (data == null || TextUtils.isEmpty(path))
            return false;
        if (data.length < 3)
            return false;//判断输入的byte是否为空
        try {
            //创建文件
            File filesDir = new File(path.substring(0, path.lastIndexOf("/")));
            if (!filesDir.exists()) {
                filesDir.mkdirs();
            }

            FileOutputStream imageOutput = new FileOutputStream(new File(path));//打开输入流
            imageOutput.write(data, 0, data.length);//将byte写入硬盘
            imageOutput.close();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /***
     * 压缩图片 获取压缩后的图片byte 字符
     *
     * @param image
     * @return
     */
    public static byte[] getStrByCompressImage100(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();// 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;// 每次都减少10
            if (options < 0) {
                break;
            }
        }
        byte[] bytes = null;

        try {
            bytes = baos.toByteArray();
            baos.close();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return bytes;
    }


    /**
     * 保存图片到相册
     *
     * @param context
     * @param imagePath 图片地址
     * @param imagename 图片名称
     * @throws IOException
     */
    public static boolean saveBitmap(Context context, String imagePath, String imagename) throws IOException {
        try {
            ContentResolver cr = context.getContentResolver();
            //String url = MediaStore.Images.Media.insertImage(cr, bitmap,imagename, "");
            String url = MediaStore.Images.Media.insertImage(cr, imagePath, imagename, "");
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.fromFile(new File(url));
            intent.setData(uri);
            context.sendBroadcast(intent);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * bitmap 保存成 图片文件
     *
     * @param filepath  图片文件夹
     * @param imagename 图片名称
     * @param bitmap
     * @throws IOException
     */
    public static void saveBitmap(String filepath, String imagename, Bitmap bitmap) {
        try {
            File file = new File(filepath);
            if (!file.exists())
                file.mkdirs();
            File f = new File(filepath + imagename);
            if (!f.exists()) {
                f.createNewFile();
            }
            FileOutputStream fOut = null;
            try {
                fOut = new FileOutputStream(f);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
            bitmap.compress(CompressFormat.PNG, 100, fOut);
            try {
                fOut.flush();
                fOut.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存bitmap到sd卡下
     * <p>The saveBitmap</p>
     *
     * @param bitmap
     * @param filePath
     */
    public static void saveBitmap(Bitmap bitmap, String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
        FileOutputStream out;
        try {
            //创建文件
            File filesDir = new File(filePath.substring(0, filePath.lastIndexOf("/")));
            if (!filesDir.exists()) {
                filesDir.mkdirs();
            }
            file.createNewFile();
            out = new FileOutputStream(file);
            if (bitmap.compress(Bitmap.CompressFormat.PNG, 90, out)) {
                out.flush();
                out.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 图片文件转成 Bitmap
     *
     * @param srcPath 文件地址
     * @return
     */
    public static Bitmap compressImageFromFile1280(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;// 只读边,不读内容
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        float hh = 1280f;//
        float ww = 720f;//
        int be = 1;
        if (w > h && w > ww) {
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;// 设置采样率

        // newOpts.inPreferredConfig = Config.ARGB_8888;// 该模式是默认的,可不设
        newOpts.inPreferredConfig = Config.RGB_565;
        newOpts.inPurgeable = true;// 同时设置才会有效
        newOpts.inInputShareable = true;// 。当系统内存不够时候图片自动被回收

        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        // return compressBmpFromBmp(bitmap);//原来的方法调用了这个方法企图进行二次压缩
        // 其实是无效的,大家尽管尝试
        return bitmap;
    }


    /***
     * 压缩图片 获取压缩后的图片byte 字符
     *
     * @param image
     * @param maxSize 最大KB
     * @return
     */
    public static byte[] getStrByCompressImage(Bitmap image, int maxSize) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > maxSize) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();// 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;// 每次都减少10
            if (options < 0) {
                break;
            }
        }
        byte[] bytes = null;

        try {
            bytes = baos.toByteArray();
            baos.close();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return bytes;
    }

    /**
     * 保存并压缩图片
     *
     * @param imagefile    图片的路径
     * @param imageNewFile 图片的新的存储路径
     * @param maxSize      文件压缩最大KB
     * @return
     */
    public static void saveCompressImage(String imagefile, String imageNewFile, int maxSize) {
        Bitmap bitmap = ImageUtil.compressImageFromFile(imagefile);
        int jiaodu = ImageUtil.getExifOrientation(imagefile);
        if (jiaodu != 0) {
            bitmap = ImageUtil.rotateBitmap(bitmap, jiaodu);
        }

        if (bitmap != null) {
            byte[] buffer = ImageUtil.getStrByCompressImage(bitmap);
            ImageUtil.saveImageFile(buffer, imageNewFile);
            bitmap.recycle();
        }
    }

    /**
     * 图片加载设置
     *
     * @param resid 自定义默认图片
     */
    public static DisplayImageOptions getImageOptions(final int resid) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(resid)// 设置图片下载期间显示的图片
                .showImageForEmptyUri(resid)// 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(resid)// 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true)// 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true)// 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(0))// 设置成圆角图片
                .build();// 创建DisplayImageOptions对象
        return options;
    }

    /**
     * 加载图片
     *
     * @param imigUrl 图片地址
     * @param imgv    图片显示空间
     * @param resid   默认显示图片
     */
    public static void loadImg(String imigUrl, final ImageView imgv, final int resid) {
        DisplayImageOptions options = getImageOptions(resid);

        ImageLoader.getInstance().displayImage(imigUrl, imgv, options, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {
                imgv.setImageResource(resid);
            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {
                imgv.setImageResource(resid);
            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                imgv.setImageBitmap(bitmap);
            }

            @Override
            public void onLoadingCancelled(String s, View view) {
            }
        });
    }
}
