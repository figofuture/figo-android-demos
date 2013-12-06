/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.figo.autodyne.ui;

import com.figo.autodyne.R;
import com.figo.autodyne.Util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.hardware.Camera.Face;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

public class FaceView extends View implements FocusIndicator {
	private final String TAG = "FaceView";
	private final boolean LOGV = false;
	// The value for android.hardware.Camera.setDisplayOrientation.
	private int mDisplayOrientation;
	// The orientation compensation for the face indicator to make it look
	// correctly in all device orientations. Ex: if the value is 90, the
	// indicator should be rotated 90 degrees counter-clockwise.
	private int mOrientation;
	private boolean mMirror;
	private boolean mPause;
	private Matrix mMatrix = new Matrix();
	private RectF mRect = new RectF();
	private Face[] mFaces;
	private Drawable mFaceIndicator;
	private final Drawable mDrawableFocusing;
	private final Drawable mDrawableFocused;
	private final Drawable mDrawableFocusFailed;
	
	private Rect mCenterRect;

	Paint paint = new Paint();

	public FaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mDrawableFocusing = getResources().getDrawable(
				R.drawable.ic_focus_focusing);
		mDrawableFocused = getResources().getDrawable(
				R.drawable.ic_focus_face_focused);
		mDrawableFocusFailed = getResources().getDrawable(
				R.drawable.ic_focus_failed);
		mFaceIndicator = mDrawableFocusing;

		paint.setColor(Color.WHITE);
		paint.setStrokeWidth(2f);
		paint.setStyle(Paint.Style.STROKE);
		paint.setAntiAlias(true);
		
		DisplayMetrics metrics = new DisplayMetrics();
		WindowManager wm = (WindowManager) getContext().getSystemService(
				Context.WINDOW_SERVICE);
		wm.getDefaultDisplay().getMetrics(metrics);
		int width = metrics.widthPixels;
		int height = metrics.heightPixels;
//		if (mOrientation == 90 || mOrientation == 270) {
//			width = metrics.heightPixels;
//			height = metrics.widthPixels;
//		}
		mCenterRect = new Rect((width - 400) / 2, (height - 400) / 2,
				(width + 400) / 2, (height + 400) / 2);
	}

	public void setFaces(Face[] faces) {
		if (LOGV)
			Log.v(TAG, "Num of faces=" + faces.length);
		if (mPause)
			return;
		if (null != mFaces && faces[0].rect.contains(mFaces[0].rect)) {
			if (LOGV)
				Log.v(TAG, "Duplicated data, return!");
			return;
		}
		mFaces = faces;
		invalidate();

		// TODO
		if (mFaces != null && mFaces.length > 0) {
			// Prepare the matrix.
			Util.prepareMatrix(mMatrix, mMirror, mDisplayOrientation,
					getWidth(), getHeight());

			// Focus indicator is directional. Rotate the matrix and the canvas
			// so it looks correctly in all orientations.
			mMatrix.postRotate(mOrientation); // postRotate is clockwise
			for (int i = 0; i < mFaces.length; i++) {
				// Transform the coordinates.
				mRect.set(mFaces[i].rect);
				if (LOGV)
					Util.dumpRect(mRect, "Original rect");
				mMatrix.mapRect(mRect);
				if (LOGV)
					Util.dumpRect(mRect, "Transformed rect");

				Rect rect = new Rect();
				Util.rectFToRect(mRect, rect);

				DisplayMetrics metrics = new DisplayMetrics();
				WindowManager wm = (WindowManager) getContext()
						.getSystemService(Context.WINDOW_SERVICE);
				wm.getDefaultDisplay().getMetrics(metrics);
				int width = metrics.widthPixels;
				int height = metrics.heightPixels;
//				if (mOrientation == 90 || mOrientation == 270) {
//					width = metrics.heightPixels;
//					height = metrics.widthPixels;
//				}

				mCenterRect = new Rect((width - 400) / 2,
						(height - 400) / 2, (width + 400) / 2,
						(height + 400) / 2);
				Util.dumpRect(mCenterRect,"center rect");
				if (mCenterRect.contains(rect)) {
					Log.e(TAG, "Good!~~~~~~~~~~~~~~~~~~~~~~~");
				} else if (rect.left < mCenterRect.left) {
					Log.e(TAG, "Please turn Right!~~~~~~~~~~~~~~~~~~~~~~~"
							+ rect.left + " : " + mCenterRect.left);
				} else if (rect.right > mCenterRect.right) {
					Log.e(TAG, "Please turn Left!~~~~~~~~~~~~~~~~~~~~~~~"
							+ rect.right + " : " + mCenterRect.right);
				} else if (rect.top < mCenterRect.top) {
					Log.e(TAG, "Please turn Down!~~~~~~~~~~~~~~~~~~~~~~~"
							+ rect.top + " : " + mCenterRect.top);
				} else if (rect.bottom > mCenterRect.bottom) {
					Log.e(TAG, "Please turn Up!~~~~~~~~~~~~~~~~~~~~~~~"
							+ rect.bottom + " : " + mCenterRect.bottom);
				}
			}
		}
	}

	public void setDisplayOrientation(int orientation) {
		mDisplayOrientation = orientation;
		if (LOGV)
			Log.v(TAG, "mDisplayOrientation=" + orientation);
	}

	public void setOrientation(int orientation) {
		mOrientation = orientation;
		invalidate();
	}

	public void setMirror(boolean mirror) {
		mMirror = mirror;
		if (LOGV)
			Log.v(TAG, "mMirror=" + mirror);
	}

	public boolean faceExists() {
		return (mFaces != null && mFaces.length > 0);
	}

	@Override
	public void showStart() {
		mFaceIndicator = mDrawableFocusing;
		invalidate();
	}

	@Override
	public void showSuccess() {
		mFaceIndicator = mDrawableFocused;
		invalidate();
	}

	@Override
	public void showFail() {
		mFaceIndicator = mDrawableFocusFailed;
		invalidate();
	}

	@Override
	public void clear() {
		// Face indicator is displayed during preview. Do not clear the
		// drawable.
		mFaceIndicator = mDrawableFocusing;
		mFaces = null;
		invalidate();
	}

	public void pause() {
		mPause = true;
	}

	public void resume() {
		mPause = false;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		
		canvas.drawRect(mCenterRect, paint);
		
		if (mFaces != null && mFaces.length > 0) {
			// Prepare the matrix.
			Util.prepareMatrix(mMatrix, mMirror, mDisplayOrientation,
					getWidth(), getHeight());

			// Focus indicator is directional. Rotate the matrix and the canvas
			// so it looks correctly in all orientations.
			canvas.save();
			mMatrix.postRotate(mOrientation); // postRotate is clockwise
			canvas.rotate(-mOrientation); // rotate is counter-clockwise (for
											// canvas)
			for (int i = 0; i < mFaces.length; i++) {
				// Transform the coordinates.
				mRect.set(mFaces[i].rect);
				if (LOGV)
					Util.dumpRect(mRect, "OnDraw: Original rect");
				mMatrix.mapRect(mRect);
				if (true)
					Util.dumpRect(mRect, "OnDraw: Transformed rect");

				mFaceIndicator.setBounds(Math.round(mRect.left),
						Math.round(mRect.top), Math.round(mRect.right),
						Math.round(mRect.bottom));
				mFaceIndicator.draw(canvas);
			}
			canvas.restore();
		}
		super.onDraw(canvas);
	}
}
