package com.figo.autodyne;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.figo.autodyne.ui.FaceView;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Face;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.Size;
import android.util.Log;
import android.view.Menu;
import android.view.OrientationEventListener;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

public class MainActivity extends Activity {

	private static final String TAG = MainActivity.class.getSimpleName();

	private Camera mCameraDevice;
	private CameraPreview mPreview;

	// multiple cameras support
	private static int mNumberOfCameras;
	private static int mCameraId;

	FaceView mFaceView;

	// The display rotation in degrees. This is only valid when mCameraState is
	// not PREVIEW_STOPPED.
	private int mDisplayRotation;
	// The value for android.hardware.Camera.setDisplayOrientation.
	private int mDisplayOrientation;
	
	private MyOrientationEventListener mOrientationListener;
    // The degrees of the device rotated clockwise from its natural orientation.
    private int mOrientation = OrientationEventListener.ORIENTATION_UNKNOWN;
 // The orientation compensation for icons and thumbnails. Ex: if the value
    // is 90, the UI components should be rotated 90 degrees counter-clockwise.
    private int mOrientationCompensation = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Hide the window title.
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_main);

		if (!checkCameraHardware(this)) {
			Toast.makeText(getApplicationContext(), "No camera on this device",
					Toast.LENGTH_SHORT).show();
			finish();
		}

		// Create an instance of Camera
		mCameraDevice = getCameraInstance();

		mCameraDevice.setFaceDetectionListener(new MyFaceDetectionListener());

		// Create our Preview view and set it as the content of our activity.
		mPreview = new CameraPreview(this, mCameraDevice);
		FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
		preview.addView(mPreview);

		// Add a listener to the Capture button
		Button captureButton = (Button) findViewById(R.id.button_capture);
		captureButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// get an image from the camera
				mCameraDevice.takePicture(null, null, mPicture);
			}
		});

		mFaceView = ((FaceView) (MainActivity.this.findViewById(R.id.face_view)));

		// Create orientation listenter. This should be done first because it
        // takes some time to get first orientation.
        mOrientationListener = new MyOrientationEventListener(this);
        mOrientationListener.enable();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/** Check if this device has a camera */
	private boolean checkCameraHardware(Context context) {
		if (context.getPackageManager().hasSystemFeature(
				PackageManager.FEATURE_CAMERA)) {
			// this device has a camera
			return true;
		} else {
			// no camera on this device
			return false;
		}
	}

	/** A safe way to get an instance of the Camera object. */
	public static Camera getCameraInstance() {

		// Find the total number of cameras available
		mNumberOfCameras = Camera.getNumberOfCameras();

		// Find the ID of the default camera
		CameraInfo cameraInfo = new CameraInfo();
		for (int i = 0; i < mNumberOfCameras; i++) {
			Camera.getCameraInfo(i, cameraInfo);
			if (cameraInfo.facing == CameraInfo.CAMERA_FACING_FRONT) {
				mCameraId = i;
				break;
			}
		}

		Camera c = null;
		try {
			c = Camera.open(mCameraId); // attempt to get a Camera instance
		} catch (Exception e) {
			// Camera is not available (in use or does not exist)
		}
		return c; // returns null if camera is unavailable
	}

	/** A basic Camera preview class */
	public class CameraPreview extends ViewGroup implements
			SurfaceHolder.Callback {

		SurfaceView mSurfaceView;
		private SurfaceHolder mHolder;
		private Camera mCamera;

		Size mPreviewSize;
		List<Size> mSupportedPreviewSizes;

		@SuppressWarnings("deprecation")
		public CameraPreview(Context context, Camera camera) {
			super(context);

			mSurfaceView = new SurfaceView(context);
			addView(mSurfaceView);

			mCamera = camera;

			// Install a SurfaceHolder.Callback so we get notified when the
			// underlying surface is created and destroyed.
			mHolder = mSurfaceView.getHolder();
			mHolder.addCallback(this);
			// deprecated setting, but required on Android versions prior to 3.0
			mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

			mSupportedPreviewSizes = mCamera.getParameters()
					.getSupportedPreviewSizes();
			requestLayout();

			setDisplayOrientation();
		}

		public void surfaceCreated(SurfaceHolder holder) {
			// The Surface has been created, now tell the camera where to draw
			// the preview.
			try {
				mCamera.setPreviewDisplay(holder);
				mCamera.startPreview();

				startFaceDetection(); // start face detection feature

			} catch (IOException e) {
				Log.d(TAG, "Error setting camera preview: " + e.getMessage());
			}
		}

		public void surfaceDestroyed(SurfaceHolder holder) {
			// empty. Take care of releasing the Camera preview in your
			// activity.
			// Surface will be destroyed when we return, so stop the preview.
		}

		public void surfaceChanged(SurfaceHolder holder, int format, int w,
				int h) {
			// If your preview can change or rotate, take care of those events
			// here.
			// Make sure to stop the preview before resizing or reformatting it.

			if (mHolder.getSurface() == null) {
				// preview surface does not exist
				return;
			}

			// stop preview before making changes
			try {
				mCamera.stopPreview();
			} catch (Exception e) {
				// ignore: tried to stop a non-existent preview
			}

			// set preview size and make any resize, rotate or
			// reformatting changes here

			// start preview with new settings
			try {

				Camera.Parameters parameters = mCamera.getParameters();
				parameters.setPreviewSize(mPreviewSize.width,
						mPreviewSize.height);
				requestLayout();

				mCamera.setParameters(parameters);

				mCamera.setPreviewDisplay(mHolder);
				mCamera.startPreview();
				stopFaceDetection();
				startFaceDetection(); // re-start face detection feature

			} catch (Exception e) {
				Log.d(TAG, "Error starting camera preview: " + e.getMessage());
			}
		}

		private Size getOptimalPreviewSize(List<Size> sizes, int w, int h) {
			final double ASPECT_TOLERANCE = 0.1;
			double targetRatio = (double) w / h;
			if (sizes == null)
				return null;

			Size optimalSize = null;
			double minDiff = Double.MAX_VALUE;

			int targetHeight = h;

			// Try to find an size match aspect ratio and size
			for (Size size : sizes) {
				double ratio = (double) size.width / size.height;
				if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE)
					continue;
				if (Math.abs(size.height - targetHeight) < minDiff) {
					optimalSize = size;
					minDiff = Math.abs(size.height - targetHeight);
				}
			}

			// Cannot find the one match the aspect ratio, ignore the
			// requirement
			if (optimalSize == null) {
				minDiff = Double.MAX_VALUE;
				for (Size size : sizes) {
					if (Math.abs(size.height - targetHeight) < minDiff) {
						optimalSize = size;
						minDiff = Math.abs(size.height - targetHeight);
					}
				}
			}
			return optimalSize;
		}

		@Override
		protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
			// We purposely disregard child measurements because act as a
			// wrapper to a SurfaceView that centers the camera preview instead
			// of stretching it.
			final int width = resolveSize(getSuggestedMinimumWidth(),
					widthMeasureSpec);
			final int height = resolveSize(getSuggestedMinimumHeight(),
					heightMeasureSpec);
			setMeasuredDimension(width, height);

			if (mSupportedPreviewSizes != null) {
				mPreviewSize = getOptimalPreviewSize(mSupportedPreviewSizes,
						width, height);
			}
		}

		@Override
		protected void onLayout(boolean changed, int l, int t, int r, int b) {
			if (changed && getChildCount() > 0) {
				final View child = getChildAt(0);

				final int width = r - l;
				final int height = b - t;

				int previewWidth = width;
				int previewHeight = height;
				if (mPreviewSize != null) {
					previewWidth = mPreviewSize.width;
					previewHeight = mPreviewSize.height;
				}

				// Center the child SurfaceView within the parent.
				if (width * previewHeight > height * previewWidth) {
					final int scaledChildWidth = previewWidth * height
							/ previewHeight;
					child.layout((width - scaledChildWidth) / 2, 0,
							(width + scaledChildWidth) / 2, height);
				} else {
					final int scaledChildHeight = previewHeight * width
							/ previewWidth;
					child.layout(0, (height - scaledChildHeight) / 2, width,
							(height + scaledChildHeight) / 2);
				}
			}
		}
	}

	private PictureCallback mPicture = new PictureCallback() {

		@Override
		public void onPictureTaken(byte[] data, Camera camera) {

			File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);
			if (pictureFile == null) {
				Log.d(TAG,
						"Error creating media file, check storage permissions: ");
				return;
			}

			try {
				FileOutputStream fos = new FileOutputStream(pictureFile);
				fos.write(data);
				fos.close();
			} catch (FileNotFoundException e) {
				Log.d(TAG, "File not found: " + e.getMessage());
			} catch (IOException e) {
				Log.d(TAG, "Error accessing file: " + e.getMessage());
			}
		}
	};

	@Override
	protected void onPause() {
		super.onPause();
		releaseCamera(); // release the camera immediately on pause event
	}

	private void releaseCamera() {
		if (mCameraDevice != null) {
			mCameraDevice.release(); // release the camera for other
										// applications
			mCameraDevice = null;
		}
	}

	public static final int MEDIA_TYPE_IMAGE = 1;
	public static final int MEDIA_TYPE_VIDEO = 2;

	/** Create a file Uri for saving an image or video */
	@SuppressWarnings("unused")
	private static Uri getOutputMediaFileUri(int type) {
		return Uri.fromFile(getOutputMediaFile(type));
	}

	/** Create a File for saving an image or video */
	private static File getOutputMediaFile(int type) {
		// To be safe, you should check that the SDCard is mounted
		// using Environment.getExternalStorageState() before doing this.

		File mediaStorageDir = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				"MyCameraApp");
		// This location works best if you want the created images to be shared
		// between applications and persist after your app has been uninstalled.

		// Create the storage directory if it does not exist
		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				Log.d("MyCameraApp", "failed to create directory");
				return null;
			}
		}

		// Create a media file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US)
				.format(new Date());
		File mediaFile;
		if (type == MEDIA_TYPE_IMAGE) {
			mediaFile = new File(mediaStorageDir.getPath() + File.separator
					+ "IMG_" + timeStamp + ".jpg");
		} else if (type == MEDIA_TYPE_VIDEO) {
			mediaFile = new File(mediaStorageDir.getPath() + File.separator
					+ "VID_" + timeStamp + ".mp4");
		} else {
			return null;
		}

		return mediaFile;
	}

	class MyFaceDetectionListener implements Camera.FaceDetectionListener {

		@Override
		public void onFaceDetection(Face[] faces, Camera camera) {
			if (faces.length > 0) {
				mFaceView.setFaces(faces);
			}
		}
	}

	public void startFaceDetection() {
		// Try starting Face Detection
		Camera.Parameters params = mCameraDevice.getParameters();

		// start face detection only *after* preview has started
		if (params.getMaxNumDetectedFaces() > 0) {
			// camera supports face detection, so can start it:

			mFaceView.clear();
			mFaceView.setVisibility(View.VISIBLE);
			mFaceView.setDisplayOrientation(mDisplayOrientation);
			CameraInfo info = CameraHolder.instance().getCameraInfo()[mCameraId];
			mFaceView.setMirror(info.facing == CameraInfo.CAMERA_FACING_FRONT);
			mFaceView.resume();

			mCameraDevice.startFaceDetection();
		}
	}

	public void stopFaceDetection() {
		// Try stopping Face Detection
		Camera.Parameters params = mCameraDevice.getParameters();

		// stop face detection only *after* preview has started
		if (params.getMaxNumDetectedFaces() > 0) {
			// camera supports face detection, so can start it:
			mCameraDevice.stopFaceDetection();
		}
	}

	private void setDisplayOrientation() {
		mDisplayRotation = Util.getDisplayRotation(this);
		mDisplayOrientation = Util.getDisplayOrientation(mDisplayRotation,
				mCameraId);
		mCameraDevice.setDisplayOrientation(mDisplayOrientation);
		if (mFaceView != null) {
			mFaceView.setDisplayOrientation(mDisplayOrientation);
		}
	}

	private class MyOrientationEventListener extends OrientationEventListener {
		public MyOrientationEventListener(Context context) {
			super(context);
		}

		@Override
		public void onOrientationChanged(int orientation) {
			// We keep the last known orientation. So if the user first orient
			// the camera then point the camera to floor or sky, we still have
			// the correct orientation.
			if (orientation == ORIENTATION_UNKNOWN)
				return;
			mOrientation = Util.roundOrientation(orientation, mOrientation);
			// When the screen is unlocked, display rotation may change. Always
			// calculate the up-to-date orientationCompensation.
			int orientationCompensation = mOrientation
					+ Util.getDisplayRotation(MainActivity.this);
			if (mOrientationCompensation != orientationCompensation) {
				mOrientationCompensation = orientationCompensation;
				setOrientationIndicator(mOrientationCompensation);
			}
		}
	}

	private void setOrientationIndicator(int degree) {
		if (mFaceView != null)
			mFaceView.setOrientation(degree);
	}
}
