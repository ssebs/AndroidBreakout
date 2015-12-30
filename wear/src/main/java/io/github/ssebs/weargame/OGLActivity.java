package io.github.ssebs.weargame;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

public class OGLActivity extends Activity {

    private GLSurfaceView mGLView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create a GLSurfaceView instance and set it
        // as the ContentView for this Activity.
        mGLView = new MyGLSurfaceView(this);
        setContentView(mGLView);

    }
}
