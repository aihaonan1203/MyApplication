package com.gaotai.baselib.activity;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.gaotai.baselib.activity.bean.ILauncher;
import com.gaotai.baselib.log.LOG;
import com.gaotai.baselib.util.ExifHelper;
import com.gaotai.baselib.util.FileUtils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Base64;
import android.widget.Toast;



/**
 * <b>
 * 照片获取和剪辑BaseActivity 
 * </b>
 * <br/>
 * 开发者如需要实现照片的获取和剪辑，请将本类作为基类进行继承，继承后Activity即可以实现基类提供的功能：<br/>
 * 1、根据需要进行大尺寸和小尺寸照片的即时拍照剪辑；<br/>
 * 2、根据需要进行大尺寸和小尺寸照片文件从相册选取剪辑；<br/>
 * 3、可在继承子类中通过实现事件回调接口ILauncherListener返回所选照片数据可为URI或者Base64序列化字符串以作后续处理<br/>
 */
public class BasePhotoPickerActivity extends BaseActivity implements ILauncher {
	ILauncherListener launcherListener;
	private static final String TAG = "PhotoPickerActivity";
	/**
	 * 返回Base64编码字符串
	 */
	public static final int DATA_URL = 0; // Return base64 encoded string
	/**
	 * 返回文件URI
	 */
	public static final int FILE_URI = 1; // Return file uri
											// (content://media/external/images/media/2

	private static final int TAKE_BIG_PICTURE = 1;
	private static final int TAKE_SMALL_PICTURE = 2;
	private static final int CROP_BIG_PICTURE = 3;
	private static final int CROP_SMALL_PICTURE = 4;
	private static final int CHOOSE_BIG_PICTURE = 5;
	private static final int CHOOSE_SMALL_PICTURE = 6;
	private static final int CROP_PICTURE_DATA = 7;
	private static final String IMAGE_FILE_LOCATION = "file:///sdcard/temp.jpg";// temp
																				// file
	private Uri PicUri = Uri.parse(IMAGE_FILE_LOCATION);// The Uri to store the
														// big bitmap // for
														// Android)

	private int returnType = DATA_URL; // 默认返回的数据格式
	private int quality = 100;
	private int small_width = 300;
	private int small_height = 300;
	private int big_width = 500;
	private int big_height = 500;
	private String action;
	private int reqcode;

	public BasePhotoPickerActivity() {
		this.launcherListener = (ILauncherListener) this;
	}

	/**
	 * 获取返回数据类型 *
	 * 
	 * @return 0:Base64编码字符串 1:文件URI
	 */
	public int GetReturnType() {
		return this.returnType;
	}

	/**
	 * 设置返回数据类型
	 * 
	 * @param value
	 *            0:Base64编码字符串 1:文件URI
	 */
	public void SetReturnType(int value) {
		this.returnType = value;
	}

	/**
	 * 获取照片压缩质量
	 * 
	 * @return 图像质量(1~100)
	 */
	public int GetQuality() {
		return this.quality;
	}

	/**
	 * 设置照片压缩质量
	 * 
	 * @param value
	 *            图像质量(1~100)
	 */
	public void SetQuality(int value) {
		this.quality = value;
	}

	/**
	 * 拍照（需要获取大尺寸照片的应用场景中使用）
	 * 
	 * @param reqcode
	 *            自定义的请求标记识别代码
	 */
	public void TakeBigPicture(int reqcode) {
		this.action = "TakeBigPicture";
		this.reqcode = reqcode;
		if (PicUri == null)
			LOG.e(TAG, "image uri can't be null");
		// capture a big bitmap and store it in Uri
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// action is
																	// capture
		intent.putExtra(MediaStore.EXTRA_OUTPUT, PicUri);
		this.startActivityForResult(intent, TAKE_BIG_PICTURE);
	}

	/**
	 * 拍照（需要获取小尺寸照片的应用场景中使用）
	 * 
	 * @param reqcode
	 *            自定义的请求标记识别代码
	 */
	public void TakeSmallPicture(int reqcode) {
		this.action = "TakeSmallPicture";
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// action is
																	// capture
		intent.putExtra(MediaStore.EXTRA_OUTPUT, PicUri);
		this.startActivityForResult(intent, TAKE_SMALL_PICTURE);
	}

	/**
	 * 相册选择照片（需要获取大尺寸照片的应用场景中使用）
	 * 
	 * @param reqcode
	 *            自定义的请求标记识别代码
	 */
	public void ChooseBigPicture(int reqcode) {
		this.action = "ChooseBigPicture";
		this.reqcode = reqcode;
		doPickPhotoFromGallery(CHOOSE_BIG_PICTURE);
		/*Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
		intent.setType("image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", big_width);
		intent.putExtra("outputY", big_height);

		// intent.putExtra("scale", true);
		intent.putExtra("return-data", false);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, PicUri);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection", false); // no face detection
		this.startActivityForResult(intent, CHOOSE_BIG_PICTURE);*/
	}

	/**
	 * 相册选择照片（需要获取小尺寸照片的应用场景中使用）
	 * 
	 * @param reqcode
	 *            自定义的请求标记识别代码
	 */
	public void ChooseSmallPicture(int reqcode) {
		this.action = "ChooseSmallPicture";
		this.reqcode = reqcode;

		doPickPhotoFromGallery(CHOOSE_SMALL_PICTURE);
		/*
		 * Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
		 * intent.setType("image/*"); intent.putExtra("crop", "true");
		 * intent.putExtra("aspectX", 1); intent.putExtra("aspectY", 1);
		 * intent.putExtra("outputX", small_width); intent.putExtra("outputY",
		 * small_height); // intent.putExtra("scale", true);
		 * intent.putExtra("return-data", true); intent.putExtra("outputFormat",
		 * Bitmap.CompressFormat.JPEG.toString());
		 * intent.putExtra("noFaceDetection", true); // no face detection
		 * this.startActivityForResult(intent, CHOOSE_SMALL_PICTURE);
		 */
	}

	/**
	 * 从Gallery获取照片
	 * 
	 */
	protected void doPickPhotoFromGallery(int actioncode) {
		try {
			Intent intent = new Intent(Intent.ACTION_PICK, null);
			intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
					"image/*");
			startActivityForResult(intent, actioncode);

		} catch (ActivityNotFoundException e) {
			Toast.makeText(this, "没有获取到照片3", Toast.LENGTH_LONG).show();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != Activity.RESULT_OK) {// result is not correct
			LOG.e(TAG, "requestCode = " + requestCode);
			LOG.e(TAG, "resultCode = " + resultCode);
			LOG.e(TAG, "data = " + data);
			this.failPicture("Did not complete!");
			return;
		} else {
			switch (requestCode) {
			case TAKE_BIG_PICTURE:
				LOG.d(TAG, "TAKE_BIG_PICTURE: data = " + data);// it seems to be
																// null
				// TODO sent to crop
				cropImageUri(PicUri, big_width, big_height, CROP_BIG_PICTURE);
				// or decode as bitmap and display it
				// if(imageUri != null){
				// Bitmap bitmap = decodeUriAsBitmap(imageUri);
				// imageView.setImageBitmap(bitmap);
				// }
				break;
			case CROP_BIG_PICTURE:// from crop_big_picture
				LOG.d(TAG, "CROP_BIG_PICTURE: data = " + data);// it seems to be
																// null
				if (PicUri != null) {

					if (this.returnType == FILE_URI) {
						launcherListener.onSuccess(this, this.reqcode,
								PicUri.toString());
					} else if (this.returnType == DATA_URL) {
						Bitmap bitmap = decodeUriAsBitmap(PicUri);
						processPicture(bitmap, this.quality);
					}

				}
				break;
			case TAKE_SMALL_PICTURE:
				LOG.i(TAG, "TAKE_SMALL_PICTURE: data = " + data);
				// TODO sent to crop
				cropImageUri(PicUri, small_width, small_height,
						CROP_SMALL_PICTURE);
				// or decode as bitmap and display it
				// if(imageUri != null){
				// Bitmap bitmap = decodeUriAsBitmap(imageUri);
				// imageView.setImageBitmap(bitmap);
				// }
				break;
			case CROP_SMALL_PICTURE:
				if (PicUri != null) {
					if (this.returnType == FILE_URI) {
						launcherListener.onSuccess(this, this.reqcode,
								PicUri.toString());
					} else if (this.returnType == DATA_URL) {
						Bitmap bitmap = decodeUriAsBitmap(PicUri);
						processPicture(bitmap, this.quality);
					}
				}
				break;
			case CHOOSE_BIG_PICTURE:
				LOG.d(TAG, "CHOOSE_BIG_PICTURE: data = " + data);// it seems to
				/*// 如果发现类似content://com.android.providers.media.documents/document/image:33229
				// 可能为ANDROID版本比较新，或者是厂商定制和标准有出入导致，这里需要判断处理
				try {
					if (data != null) {
						Uri currUri = data.getData();
						// 如果发现类似content://com.android.providers.media.documents/document/image:33229
						// 尝试 转换成普通文件uri
						if (currUri.toString().indexOf("content://") != -1) {
							Uri contentUri = ContentUriToUri(data.getData());

							if (contentUri != null) {
								// cropImageUri(contentUri, small_width,
								// small_height, CROP_SMALL_PICTURE);
								startPhotoZoom(currUri, big_width,
										big_width, CROP_PICTURE_DATA);
							}
						} else {

							Bitmap bitmap = data.getParcelableExtra("data");
							if (this.returnType == FILE_URI) {

								launcherListener.onSuccess(this, this.reqcode,
										SaveBitmap(bitmap).toString());
							} else if (this.returnType == DATA_URL) {

								processPicture(bitmap, this.quality);
							}
						}
					}
				} catch (Exception ex) {

				}	*/											// be null
				if (PicUri != null) {
					if (this.returnType == FILE_URI) {
						launcherListener.onSuccess(this, this.reqcode,
								PicUri.toString());
					} else if (this.returnType == DATA_URL) {
						Bitmap bitmap = decodeUriAsBitmap(PicUri);
						processPicture(bitmap, this.quality);
					}
				}
				break;
		
			case CHOOSE_SMALL_PICTURE:
				LOG.d(TAG, "CHOOSE_SMALL_PICTURE: data = " + data);

				// 如果发现类似content://com.android.providers.media.documents/document/image:33229
				// 可能为ANDROID版本比较新，或者是厂商定制和标准有出入导致，这里需要判断处理
				try {
					if (data != null) {
						Uri currUri = data.getData();
						// 如果发现类似content://com.android.providers.media.documents/document/image:33229
						// 尝试 转换成普通文件uri
						if (currUri.toString().indexOf("content://") != -1) {
							Uri contentUri = ContentUriToUri(data.getData());

							if (contentUri != null) {
								// cropImageUri(contentUri, small_width,
								// small_height, CROP_SMALL_PICTURE);
								startPhotoZoom(currUri, small_width,
										small_height, CROP_PICTURE_DATA);
							}
						} else {

							Bitmap bitmap = data.getParcelableExtra("data");
							if (this.returnType == FILE_URI) {

								launcherListener.onSuccess(this, this.reqcode,
										SaveBitmap(bitmap).toString());
							} else if (this.returnType == DATA_URL) {

								processPicture(bitmap, this.quality);
							}
						}
					}
				} catch (Exception ex) {

				}

				break;
			case CROP_PICTURE_DATA:
				Bundle extras = data.getExtras();
				if (extras != null) {
					Bitmap bitmap = extras.getParcelable("data");
					if (this.returnType == FILE_URI) {

						launcherListener.onSuccess(this, this.reqcode,
								SaveBitmap(bitmap).toString());
					} else if (this.returnType == DATA_URL) {
						processPicture(bitmap, this.quality);
					}
				}
				break;
			default:
				break;
			}
		}
	}

	private Uri ContentUriToUri(Uri contentUri) {

		String[] proj = { MediaStore.Images.Media.DATA };

		Cursor actualimagecursor = managedQuery(contentUri, proj, null, null,
				null);

		int actual_image_column_index = actualimagecursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

		actualimagecursor.moveToFirst();

		String img_path = actualimagecursor
				.getString(actual_image_column_index);

		return Uri.parse("file//" + img_path);

	}

	@SuppressLint("NewApi")
	private Uri ContentUriToFileUri(Context context, Uri contentUri) {
		Uri result = null;
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
			String wholeID = DocumentsContract.getDocumentId(contentUri);
			String id = wholeID.split(":")[1];
			String[] column = { MediaStore.Images.Media.DATA };
			String sel = MediaStore.Images.Media._ID + "=?";
			Cursor cursor = context.getContentResolver().query(
					MediaStore.Images.Media.EXTERNAL_CONTENT_URI, column, sel,
					new String[] { id }, null);
			int columnIndex = cursor.getColumnIndex(column[0]);
			if (cursor.moveToFirst()) {
				result = Uri.parse("file://" + cursor.getString(columnIndex));
			}
			cursor.close();
		} else {
			String[] projection = { MediaStore.Images.Media.DATA };
			Cursor cursor = context.getContentResolver().query(contentUri,
					projection, null, null, null);
			int column_index = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			result = Uri.parse("file://" + cursor.getString(column_index));
		}
		return result;
	}

	private void failPicture(String err) {
		launcherListener.onError(this, this.reqcode, err);
	}

	private void cropImageUri(Uri uri, int outputX, int outputY, int requestCode) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		//比列
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", outputX);
		intent.putExtra("outputY", outputY);
		// intent.putExtra("scale", true);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		intent.putExtra("return-data", false);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection", true); // no face detection
		this.startActivityForResult(intent, requestCode);
	}

	/**
	 * 
	 * 调用图片剪辑程序
	 * 
	 * */
	private void startPhotoZoom(Uri uri, int outputX, int outputY,
			int requestCode) {
		try {
			// 启动gallery去剪辑这个照片
			final Intent intent = new Intent("com.android.camera.action.CROP");
			intent.setDataAndType(uri, "image/*");
			intent.putExtra("crop", "true");
			intent.putExtra("aspectX", 1);
			intent.putExtra("aspectY", 1);
			intent.putExtra("outputX", outputX);
			intent.putExtra("outputY", outputY);
			intent.putExtra("return-data", true);

			// intent.setData(Uri.fromFile(picture));

			startActivityForResult(intent, requestCode);
		} catch (Exception e) {
			Toast.makeText(this, "没有获取到照片", Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * 将文件URI转换为Bitmap
	 * 
	 * @param uri
	 *            图像文件的URI
	 * @return Bitmap
	 */
	public Bitmap decodeUriAsBitmap(Uri uri) {
		Bitmap bitmap = null;
		try {
			bitmap = BitmapFactory.decodeStream(getContentResolver()
					.openInputStream(uri));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		return bitmap;
	}

	private Uri SaveBitmap(Bitmap bitmap) {
		Uri result = null;

		File picture;
		try {
			picture = File.createTempFile("temp", ".jpg",
					Environment.getExternalStorageDirectory());

			BufferedOutputStream bos = new BufferedOutputStream(
					new FileOutputStream(picture));
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
			result = Uri.fromFile(picture);
			bos.flush();
			bos.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;

	}

	/**
	 * base64转为Bitmap
	 * 
	 * @param base64Data
	 *            编码的图像文件字符串
	 * @return Bitmap
	 */
	public Bitmap base64ToBitmap(String base64Data) {
		byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
		return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
	}

	/**
	 * JPEG图像转Base64字符串，支持压缩质量
	 * 
	 * @param bitmap
	 *            Bitmap
	 * @param mQuality
	 *            图像质量1~100
	 */
	private void processPicture(Bitmap bitmap, int mQuality) {
		ByteArrayOutputStream jpeg_data = new ByteArrayOutputStream();
		try {
			if (bitmap.compress(CompressFormat.JPEG, mQuality, jpeg_data)) {
				byte[] code = jpeg_data.toByteArray();
				byte[] output = Base64.encode(code, Base64.NO_WRAP);
				String js_out = new String(output);
				launcherListener.onSuccess(this, this.reqcode, js_out);
				js_out = null;
				output = null;
				code = null;
			}
		} catch (Exception e) {
			this.failPicture("Error compressing image.");
		}
		jpeg_data = null;
	}

	private Bitmap getScaledBitmap(String imageUrl, int targetWidth,
			int targetHeight) throws IOException {
		// If no new width or height were specified return the original bitmap
		if (targetWidth <= 0 && targetHeight <= 0) {
			return BitmapFactory.decodeStream(FileUtils
					.getInputStreamFromUriString(imageUrl, this));
		}

		// figure out the original width and height of the image
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeStream(
			FileUtils.getInputStreamFromUriString(imageUrl, this), null,
				options);

		// CB-2292: WTF? Why is the width null?
		if (options.outWidth == 0 || options.outHeight == 0) {
			return null;
		}

		// determine the correct aspect ratio
		int[] widthHeight = calculateAspectRatio(targetWidth, targetHeight,
				options.outWidth, options.outHeight);

		// Load in the smallest bitmap possible that is closest to the size we
		// want
		options.inJustDecodeBounds = false;
		options.inSampleSize = calculateSampleSize(options.outWidth,
				options.outHeight, targetWidth, targetHeight);
		Bitmap unscaledBitmap = BitmapFactory.decodeStream(
			FileUtils.getInputStreamFromUriString(imageUrl, this), null,
				options);
		if (unscaledBitmap == null) {
			return null;
		}

		return Bitmap.createScaledBitmap(unscaledBitmap, widthHeight[0],
				widthHeight[1], true);
	}

	/**
	 * Maintain the aspect ratio so the resulting image does not look smooshed
	 * 
	 * @param origWidth
	 * @param origHeight
	 * @return
	 */
	private int[] calculateAspectRatio(int targetWidth, int targetHeight,
			int origWidth, int origHeight) {
		int newWidth = targetWidth;
		int newHeight = targetHeight;

		// If no new width or height were specified return the original bitmap
		if (newWidth <= 0 && newHeight <= 0) {
			newWidth = origWidth;
			newHeight = origHeight;
		}
		// Only the width was specified
		else if (newWidth > 0 && newHeight <= 0) {
			newHeight = (newWidth * origHeight) / origWidth;
		}
		// only the height was specified
		else if (newWidth <= 0 && newHeight > 0) {
			newWidth = (newHeight * origWidth) / origHeight;
		}
		// If the user specified both a positive width and height
		// (potentially different aspect ratio) then the width or height is
		// scaled so that the image fits while maintaining aspect ratio.
		// Alternatively, the specified width and height could have been
		// kept and Bitmap.SCALE_TO_FIT specified when scaling, but this
		// would result in whitespace in the new image.
		else {
			double newRatio = newWidth / (double) newHeight;
			double origRatio = origWidth / (double) origHeight;

			if (origRatio > newRatio) {
				newHeight = (newWidth * origHeight) / origWidth;
			} else if (origRatio < newRatio) {
				newWidth = (newHeight * origWidth) / origHeight;
			}
		}

		int[] retval = new int[2];
		retval[0] = newWidth;
		retval[1] = newHeight;
		return retval;
	}

	/**
	 * Figure out if the bitmap should be rotated. For instance if the picture
	 * was taken in portrait mode
	 * 
	 * @param rotate
	 * @param bitmap
	 * @return rotated bitmap
	 */
	private Bitmap getRotatedBitmap(int rotate, Bitmap bitmap, ExifHelper exif) {
		Matrix matrix = new Matrix();
		if (rotate == 180) {
			matrix.setRotate(rotate);
		} else {
			matrix.setRotate(rotate, (float) bitmap.getWidth() / 2,
					(float) bitmap.getHeight() / 2);
		}
		bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
				bitmap.getHeight(), matrix, true);
		exif.resetOrientation();
		return bitmap;
	}

	/**
	 * Figure out what ratio we can load our image into memory at while still
	 * being bigger than our desired width and height
	 * 
	 * @param srcWidth
	 * @param srcHeight
	 * @param dstWidth
	 * @param dstHeight
	 * @return
	 */
	private static int calculateSampleSize(int srcWidth, int srcHeight,
			int dstWidth, int dstHeight) {
		final float srcAspect = (float) srcWidth / (float) srcHeight;
		final float dstAspect = (float) dstWidth / (float) dstHeight;

		if (srcAspect > dstAspect) {
			return srcWidth / dstWidth;
		} else {
			return srcHeight / dstHeight;
		}
	}

	public void setLauncherListener(ILauncherListener listener) {
		this.launcherListener = listener;
	}

	public ILauncherListener getLauncherListener() {
		return this.launcherListener;
	}

}

