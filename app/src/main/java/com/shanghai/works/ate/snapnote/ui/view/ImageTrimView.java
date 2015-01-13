package com.shanghai.works.ate.snapnote.ui.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.shanghai.works.ate.snapnote.R;

import java.io.ByteArrayOutputStream;

/**
 * Created by lijunxiao on 1/12/15.
 */
public class ImageTrimView extends ImageView implements View.OnClickListener{
    private Paint paint = new Paint();

    private static final int BORDER_DETECT_BUFFER = 30;
    private static final int BORDER_WIDTH = 6;

    private static final int DRAG   = 0;
    private static final int LEFT   = 1;
    private static final int TOP    = 2;
    private static final int RIGHT  = 3;
    private static final int BOTTOM = 4;
    private static final int LEFT_TOP = 5;
    private static final int RIGHT_TOP = 6;
    private static final int LEFT_BOTTOM = 7;
    private static final int RIGHT_BOTTOM = 8;

    private boolean isInitialized = false;
    private Point leftTop;
    private Point rightBottom;
    private Point oldPoint;
    private Point center;
    private Bitmap bottomStretchIcon;
    private Bitmap leftStretchIcon;
    private Bitmap topStretchIcon;
    private Bitmap rightStretchIcon;
    private Rect bottomStretchRect;
    private Rect leftStretchRect;
    private Rect topStretchRect;
    private Rect rightStretchRect;

    public ImageTrimView(Context context) {
        super(context);
        init();
    }

    public ImageTrimView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(!isInitialized) {
            initPos();
            isInitialized = true;
        }

        // Draw The Center Rectangle
        paint.reset();
        paint.setColor(Color.CYAN);
        paint.setShader(new Shader());
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(BORDER_WIDTH);
        canvas.drawRect(leftTop.x, leftTop.y, rightBottom.x, rightBottom.y, paint);

        // Draw Border Masks
        paint.reset();
        paint.setColor(Color.argb(100, 0, 0, 0));
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(0, 0, getWidth(), leftTop.y, paint);
        canvas.drawRect(0, leftTop.y, leftTop.x, rightBottom.y, paint);
        canvas.drawRect(0, rightBottom.y, getWidth(), getHeight(), paint);
        canvas.drawRect(rightBottom.x, leftTop.y, getWidth(), rightBottom.y, paint);

        // Draw Stretch Icons
        paint.reset();
        paint.setColorFilter(new LightingColorFilter(Color.RED, 0));
        paint.setAlpha(180);
        int offsetX = (rightBottom.x - leftTop.x - bottomStretchIcon.getWidth()) / 2;
        int offsetY = (rightBottom.y - leftTop.y - leftStretchIcon.getHeight()) / 2;

        bottomStretchRect.left = leftTop.x + offsetX;
        bottomStretchRect.top = rightBottom.y;
        bottomStretchRect.right = bottomStretchRect.left + bottomStretchIcon.getWidth();
        bottomStretchRect.bottom = bottomStretchRect.top + bottomStretchIcon.getHeight();

        leftStretchRect.left = leftTop.x - leftStretchIcon.getWidth();
        leftStretchRect.top = leftTop.y + offsetY;
        leftStretchRect.right = leftStretchRect.left + leftStretchIcon.getWidth();
        leftStretchRect.bottom = leftStretchRect.top + leftStretchIcon.getHeight();

        topStretchRect.left = bottomStretchRect.left;
        topStretchRect.top = leftTop.y - topStretchIcon.getHeight();
        topStretchRect.right = topStretchRect.left + topStretchIcon.getWidth();
        topStretchRect.bottom = topStretchRect.top + topStretchIcon.getHeight();

        rightStretchRect.left = rightBottom.x;
        rightStretchRect.top = leftStretchRect.top;
        rightStretchRect.right = rightStretchRect.left + rightStretchIcon.getWidth();
        rightStretchRect.bottom = rightStretchRect.top + rightStretchIcon.getHeight();

        canvas.drawBitmap(bottomStretchIcon, bottomStretchRect.left, bottomStretchRect.top, paint);
        canvas.drawBitmap(leftStretchIcon, leftStretchRect.left, leftStretchRect.top, paint);
        canvas.drawBitmap(topStretchIcon, topStretchRect.left, topStretchRect.top, paint);
        canvas.drawBitmap(rightStretchIcon, rightStretchRect.left , rightStretchRect.top, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                Log.d("ImageTrimView", "TouchEvent: ACTION_DOWN.");
                oldPoint.set((int)event.getX(), (int)event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                if (isInRectangle(event.getX(), event.getY())) {
                    adjustRectangle((int)event.getX(), (int)event.getY());
                    invalidate();
                    oldPoint.set((int) event.getX(), (int) event.getY());
                }
                break;
            case MotionEvent.ACTION_UP:
                Log.d("ImageTrimView", "TouchEvent: ACTION_UP. " + getStretchAction((int)event.getX(), (int)event.getY()));

                this.stretchTo(getStretchAction((int)event.getX(), (int)event.getY()));
                invalidate();
                oldPoint = new Point();
                break;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        Log.d("ImageTrimView", "Clicked.");
    }

    public void stretchTo(Direction direction) {
        switch (direction) {
            case LEFT:
                leftTop.x = 0;
                break;
            case TOP:
                leftTop.y = 0;
                break;
            case RIGHT:
                rightBottom.x = getWidth();
                break;
            case BOTTOM:
                rightBottom.y = getHeight();
                break;
            default:
                break;
        }
    }

    public Bitmap getTrimmedBitmap() {
        BitmapDrawable drawable = (BitmapDrawable)getDrawable();
        float x = leftTop.x - center.x + (drawable.getBitmap().getWidth() / 2);
        float y = leftTop.y - center.y + (drawable.getBitmap().getHeight() / 2);
        Bitmap trimmed = Bitmap.createBitmap(drawable.getBitmap(),
                (int)x, (int)y, rightBottom.x - leftTop.x, rightBottom.y - leftTop.y);
        return trimmed;
    }

    public byte[] getTrimmedImage() {
        BitmapDrawable drawable = (BitmapDrawable)getDrawable();
        Log.d("BitMap Width", " " + drawable.getBitmap().getWidth());
        Log.d("BitMap Height", " " + drawable.getBitmap().getHeight());
        Log.d("ImageView Width", " " + getWidth());
        Log.d("ImageView Height", " " + getHeight());
        Log.d("Drawable Width", " " + drawable.getIntrinsicWidth());
        Log.d("Drawable Height", " " + drawable.getIntrinsicHeight());
        float x = leftTop.x; //- center.x + (drawable.getBitmap().getWidth() / 2);
        float y = leftTop.y; // - center.y + (drawable.getBitmap().getHeight() / 2);
        Bitmap trimmed = Bitmap.createBitmap(drawable.getBitmap(),
                (int)x, (int)y, rightBottom.x - leftTop.x, rightBottom.y - leftTop.y);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        trimmed.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    private void init() {
        leftTop = new Point();
        rightBottom = new Point();
        oldPoint = new Point();
        center = new Point();

        Resources res = getResources();
        bottomStretchIcon = ((BitmapDrawable)res.getDrawable(R.drawable.ic_action_expand)).getBitmap();

        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        leftStretchIcon = Bitmap.createBitmap(bottomStretchIcon, 0, 0, bottomStretchIcon.getWidth(), bottomStretchIcon.getHeight(), matrix, true);
        topStretchIcon = Bitmap.createBitmap(leftStretchIcon, 0, 0, leftStretchIcon.getWidth(), leftStretchIcon.getHeight(), matrix, true);
        rightStretchIcon = Bitmap.createBitmap(topStretchIcon, 0, 0, topStretchIcon.getWidth(), topStretchIcon.getHeight(), matrix, true);

        bottomStretchRect = new Rect();
        leftStretchRect = new Rect();
        topStretchRect = new Rect();
        rightStretchRect = new Rect();
    }

    private int getResizeAndMoveAction(float x, float y) {
        int buffer = BORDER_DETECT_BUFFER;

        if (x >= (leftTop.x - buffer) && x <= (leftTop.x + buffer))
            return LEFT;
        else if (y >= (leftTop.y - buffer) && y<= (leftTop.y + buffer))
            return TOP;
        else if (x >= (rightBottom.x - buffer) && x <= (rightBottom.x + buffer))
            return RIGHT;
        else if (y >= (rightBottom.y - buffer) && y <= (rightBottom.y + buffer))
            return BOTTOM;
        else
            return DRAG;
    }

    private Direction getStretchAction(int x, int y) {
        if (bottomStretchRect.contains(x, y)) return Direction.BOTTOM;
        else if (leftStretchRect.contains(x, y)) return Direction.LEFT;
        else if (topStretchRect.contains(x, y)) return Direction.TOP;
        else if (rightStretchRect.contains(x, y)) return Direction.RIGHT;
        return Direction.NONE;
    }

    private void adjustRectangle(int x, int y) {
        int movement;
        switch (getResizeAndMoveAction(x, y)) {
            case LEFT:
                movement = x - leftTop.x;
                if (leftTop.x + movement < rightBottom.x)
                    leftTop.set(leftTop.x + movement, leftTop.y);
                break;
            case TOP:
                movement = y - leftTop.y;
                if (leftTop.y + movement < rightBottom.y)
                    leftTop.set(leftTop.x, leftTop.y + movement);
                break;
            case RIGHT:
                movement = x - rightBottom.x;
                if (leftTop.x < rightBottom.x + movement)
                    rightBottom.set(rightBottom.x + movement, rightBottom.y);
                break;
            case BOTTOM:
                movement = y - rightBottom.y;
                if (leftTop.y < rightBottom.y + movement);
                    rightBottom.set(rightBottom.x, rightBottom.y + movement);
                break;
            case DRAG:
                movement = x - oldPoint.x;
                int movementY = y - oldPoint.y;
                leftTop.set(leftTop.x + movement,leftTop.y + movementY);
                rightBottom.set(rightBottom.x + movement, rightBottom.y + movementY);

                break;
        }
    }


    private boolean isInRectangle(float x, float y) {
        int buffer = BORDER_DETECT_BUFFER;
        return (x >= (leftTop.x - buffer) &&
                x <= (rightBottom.x + buffer) &&
                y >= (leftTop.y - buffer) &&
                y<=(rightBottom.y+buffer)) ? true : false;
    }

    private void initPos() {
        int initialWidth = getWidth() / 2,
            initialHeight = getHeight() / 2;
        center.set(getWidth() / 2, getHeight() / 2);
        leftTop.set((getWidth() - initialWidth) / 2,(getHeight() - initialHeight) / 2);
        rightBottom.set(leftTop.x + initialWidth, leftTop.y + initialHeight);
    }



    public enum Direction {
        LEFT, TOP, RIGHT, BOTTOM, NONE
    }

    private enum ToolType {
        RESIZE_RECT, RESIZE_OVAL
    }
}
