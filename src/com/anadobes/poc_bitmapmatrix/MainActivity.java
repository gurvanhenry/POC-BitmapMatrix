package com.anadobes.poc_bitmapmatrix;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

    public static final String TAG = MainActivity.class.getSimpleName();

    public static Paint red = new Paint();
    static {
        red.setColor(Color.RED);
        red.setTextSize(40);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.main_layout);

        addTextView("UP SIDE DOWN BITMAP PROBLEMON SAMSUNG NOTE", mainLayout);

        // addTextView("---- bretagne_ferienhaus ---- :", mainLayout);
        addTextView("=> pb if rotation 180° and medium unzoom ", mainLayout);
        addTextView("original image :", mainLayout);
        addBitmapView(R.drawable.bretagne_ferienhaus, mainLayout, 0);
        // addBitmapView(R.drawable.bretagne_ferienhaus, mainLayout, 45);
        // addBitmapView(R.drawable.bretagne_ferienhaus, mainLayout, 90);
        // addBitmapView(R.drawable.bretagne_ferienhaus, mainLayout, 91);
        // addBitmapView(R.drawable.bretagne_ferienhaus, mainLayout, -90);
        addTextView("180° 1x : ok", mainLayout);
        addBitmapView(R.drawable.bretagne_ferienhaus, mainLayout, 180);

        /** scale **/
        addTextView("180° 0.3x : ok", mainLayout);
        addBitmapView(R.drawable.bretagne_ferienhaus, mainLayout, 180, 0.3f);
        addTextView("180° 0.35x to 0.99 : PROBLEM on Galaxy Note", mainLayout);
        addBitmapView(R.drawable.bretagne_ferienhaus, mainLayout, 180, 0.35f);
        addBitmapView(R.drawable.bretagne_ferienhaus, mainLayout, 180, 0.5f);
        addBitmapView(R.drawable.bretagne_ferienhaus, mainLayout, 180, 0.99f);
        addTextView("180° 1.5x : ok", mainLayout);
        addBitmapView(R.drawable.bretagne_ferienhaus, mainLayout, 180, 1.01f);
        addBitmapView(R.drawable.bretagne_ferienhaus, mainLayout, 180, 1.5f);

        addTextView("181° 0.5x : ok", mainLayout);
        addBitmapView(R.drawable.bretagne_ferienhaus, mainLayout, 181, 0.5f);

        // /** translation **/
        // addBitmapView(R.drawable.bretagne_ferienhaus, mainLayout, 180, 0.5f, 100);
        // addBitmapView(R.drawable.bretagne_ferienhaus, mainLayout, 180, 0.5f, -20);
        //
        // addTextView("Contrairement à Reserv l'image n'est pas conplètement retournée qd elle est proche du bord mais elle est partiellement bien chargée.",
        // mainLayout);
        //
        // addTextView("", mainLayout);
        // addTextView("---- bretagne_ferienhaus_small 180° ----", mainLayout);
        // addTextView("1x", mainLayout);
        // addBitmapView(R.drawable.bretagne_ferienhaus_small, mainLayout, 180);
        // addTextView("0.8x PROBLEM", mainLayout);
        // addBitmapView(R.drawable.bretagne_ferienhaus_small, mainLayout, 180, 0.8f);
        // addTextView("0.5x PROBLEM", mainLayout);
        // addBitmapView(R.drawable.bretagne_ferienhaus_small, mainLayout, 180, 0.5f);
    }

    private void addTextView(final String text, LinearLayout layout) {
        layout.addView(new TextView(this) {
            {
                setText(text);
            }
        });
    }

    private void addBitmapView(int resImageId, LinearLayout layout, int rotationAngle) {
        addBitmapView(resImageId, layout, rotationAngle, 1.0f);
    }

    private void addBitmapView(int resImageId, LinearLayout layout, int rotationAngle, float scale) {
        addBitmapView(resImageId, layout, rotationAngle, scale, 0);
    }

    private void addBitmapView(int resImageId, LinearLayout layout, int rotationAngle, float scale, int translate) {
        BitmapView bitmapV = new BitmapView(this, resImageId, rotationAngle, scale, translate);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 400);
        layoutParams.setMargins(10, 10, 10, 10);
        bitmapV.setLayoutParams(layoutParams);
        bitmapV.setBackgroundColor(Color.argb(20, 15, 25, 50));
        layout.addView(bitmapV);
    }

    public class BitmapView extends View {

        Bitmap mBitmap;
        private int mRotationAngle = 0;
        private float mScale = 0;
        private int mTranslate = 0;

        public BitmapView(Context context, int resImageId, int rotationAngle, float scale, int translate) {
            super(context);
            mBitmap = BitmapFactory.decodeResource(getResources(), resImageId);
            mRotationAngle = rotationAngle;
            mScale = scale;
            mTranslate = translate;
        }

        /**
         * Tranformation on canvas
         */
        // @Override
        // public void onDraw(Canvas canvas) {
        // canvas.rotate(mRotationAngle, mBitmap.getWidth() / 2, mBitmap.getHeight() / 2);
        // canvas.scale(mScale, mScale);
        // canvas.translate(mTranslate, mTranslate);
        // canvas.drawBitmap(mBitmap, 0, 0, null);
        // canvas.drawText(mRotationAngle + "° / " + mScale + "x", 100, 70, red);
        // canvas.drawText("trX,trY=" + mTranslate, 100, 120, red);
        // }

        /**
         * Tranformation by Matrix
         */
        @Override
        public void onDraw(Canvas canvas) {
            Matrix m = new Matrix();
            m.postRotate(mRotationAngle, mBitmap.getWidth() / 2, mBitmap.getHeight() / 2);
            m.postScale(mScale, mScale);
            m.postTranslate(mTranslate, mTranslate);
            canvas.concat(m);
            canvas.drawBitmap(mBitmap, 0, 0, null);
            canvas.drawText(mRotationAngle + "° / " + mScale + "x", 100, 70, red);
            canvas.drawText("trX,trY=" + mTranslate, 100, 120, red);
        }
    }
}
