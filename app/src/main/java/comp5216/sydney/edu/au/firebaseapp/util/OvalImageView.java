package comp5216.sydney.edu.au.firebaseapp.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatImageView;

/**
 * Constructor for the TaskListAdapter
 * The code for this section comes from the open source community
 */
public class OvalImageView extends AppCompatImageView {
    private Paint mPaintBitmap = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Bitmap mRawBitmap;
    private BitmapShader mShader;
    private Matrix mMatrix = new Matrix();
    private int strokeColor = 0xFFFFFFFF;// white
    private float strokeWidth = 0;//The unit is the border width of the pixel

    public OvalImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // Get a image and convert it to a Bitmap
        Bitmap rawBitmap = getBitmap(getDrawable());
        if (rawBitmap != null) {
            // Take the shorter one as the radius of the circle
            // and make sure the whole picture fills the circle
            int viewWidth = getWidth();
            int viewHeight = getHeight();
            int viewMinSize = Math.min(viewWidth, viewHeight);
            float dstWidth = viewMinSize;
            float dstHeight = viewMinSize;
            if (mShader == null || !rawBitmap.equals(mRawBitmap)) {
                mRawBitmap = rawBitmap;
                mShader = new BitmapShader(mRawBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            }
            if (mShader != null) {
                mMatrix.setScale(dstWidth / rawBitmap.getWidth()
                        , dstHeight / rawBitmap.getHeight());
                mShader.setLocalMatrix(mMatrix);
            }
            mPaintBitmap.setShader(mShader);
            float radius = viewMinSize / 2.0f;

            // If the border width is not 0, draw the border
            if(strokeWidth != 0){
                Paint whitePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
                whitePaint.setColor(strokeColor);
                // drawing a circle filled with the color of the border and the size set by the control
                canvas.drawCircle(radius, radius, radius, whitePaint);
                canvas.drawCircle(radius, radius, radius - strokeWidth, mPaintBitmap);
            }else {
                canvas.drawCircle(radius, radius, radius, mPaintBitmap);
            }
        } else {
            super.onDraw(canvas);
        }
    }

    private Bitmap getBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else if (drawable instanceof ColorDrawable) {
            Rect rect = drawable.getBounds();
            int width = rect.right - rect.left;
            int height = rect.bottom - rect.top;
            int color = ((ColorDrawable) drawable).getColor();
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            canvas.drawARGB(Color.alpha(color), Color.red(color), Color.green(color), Color.blue(color));
            return bitmap;
        } else {
            return null;
        }
    }

    /**
     * @param strokeWidth Border width, in px
     */
    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    /**
     * @param strokeColor The border color to set must be hexadecimal with transparency
     *                    for example: 0xFF0000FF
     */
    public void setStrokeColot(int strokeColor) {
        this.strokeColor = strokeColor;
    }
}
