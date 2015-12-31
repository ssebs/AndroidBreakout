package io.github.ssebs.weargame;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

public class MyGLSurfaceView extends GLSurfaceView {

    private final MyGLRenderer mRenderer;
    private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
    private float mPreviousX;
    private float mPreviousY;

    public MyGLSurfaceView(Context context) {
        super(context);

        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2);

        super.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        mRenderer = new MyGLRenderer();

        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(mRenderer);

        // Render the view only when there is a change in the drawing data
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, you are only
        // interested in events where the touch position changed.

        float x = e.getX();
        float y = e.getY();

        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE: {
                float dx = x - mPreviousX;
                float dy = y - mPreviousY;


                // reverse direction of rotation above the mid-line
                if (y > getHeight() / 2) {
                    dx = -dx * -1;
                } else {
                    dx = dx * -1;
                }

                // reverse direction of rotation to left of the mid-line
                if (x < getWidth() / 2) {
                    dy = -dy * -1;
                } else {
                    dy = dy * -1;
                }

                // mRenderer.setAngle(
                //  mRenderer.getAngle() +
                //  ((dx + dy) * TOUCH_SCALE_FACTOR));
                requestRender();
            }

            case MotionEvent.ACTION_DOWN: {
                if (x < getWidth() / 2 && mRenderer.getmTransX() < 0.8) {
                    mRenderer.setmTransX(mRenderer.getmTransX() + 0.15f);
                } else if (x >= getWidth() / 2 && mRenderer.getmTransX() > -0.8) {
                    mRenderer.setmTransX(mRenderer.getmTransX() - 0.15f); // nice right
                }
                mPreviousX = x;
                mPreviousY = y;
                return true;
            }
        }

        mPreviousX = x;
        mPreviousY = y;
        return true;
    }

}
