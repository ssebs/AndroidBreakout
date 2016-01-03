package io.github.ssebs.weargame;


import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Rectangle {

    float rectangleCoords[] = {   // in counterclockwise order:
            -0.15f, 0.1f, 0.0f, // top right
            -0.15f, -0.1f, 0.0f, // bottom right
            0.15f, -0.1f, 0.0f,  // bottom left
            0.15f, 0.1f, 0.0f, // top left
            0.15f, -0.1f, 0.0f, // bottom  left
            -0.15f, 0.1f, 0.0f // top right
    };


    public Rectangle(float newCoords[]) {
        rectangleCoords = newCoords;

        // initialize vertex byte buffer for shape coordinates
        // (number of coordinate values * 4 bytes per float)
        ByteBuffer bb = ByteBuffer.allocateDirect(rectangleCoords.length * 4);
        /*  A Byte buffer is a streamable array of bytes (data turned into binary) */

        // use the device hardware's native byte order
        bb.order(ByteOrder.nativeOrder());
        /* needed, just copy and paste later on */


        // create a floating point buffer from the ByteBuffer
        vertexBuffer = bb.asFloatBuffer();
        /* This sets the Float buffer to the size of the bytebuffer */

        // add the coordinates to the FloatBuffer
        vertexBuffer.put(rectangleCoords);


        // set the buffer to read the first coordinate
        vertexBuffer.position(0);

        int vertexShader = MyGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        /* Returns the shader handle /\  \/ */
        int fragmentShader = MyGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

        // create empty OpenGL ES Program for the shaders
        mProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(mProgram, vertexShader);
        GLES20.glAttachShader(mProgram, fragmentShader);
        GLES20.glLinkProgram(mProgram);

    }

    /**
     * Default
     */
    public Rectangle() {
        // initialize vertex byte buffer for shape coordinates
        // (number of coordinate values * 4 bytes per float)
        ByteBuffer bb = ByteBuffer.allocateDirect(rectangleCoords.length * 4);
        /*  A Byte buffer is a streamable array of bytes (data turned into binary) */

        // use the device hardware's native byte order
        bb.order(ByteOrder.nativeOrder());
        /* needed, just copy and paste later on */


        // create a floating point buffer from the ByteBuffer
        vertexBuffer = bb.asFloatBuffer();
        /* This sets the Float buffer to the size of the bytebuffer */

        // add the coordinates to the FloatBuffer
        vertexBuffer.put(rectangleCoords);


        // set the buffer to read the first coordinate
        vertexBuffer.position(0);

        int vertexShader = MyGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        /* Returns the shader handle /\  \/ */
        int fragmentShader = MyGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

        // create empty OpenGL ES Program for the shaders
        mProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(mProgram, vertexShader);
        GLES20.glAttachShader(mProgram, fragmentShader);
        GLES20.glLinkProgram(mProgram);

    }

    public void draw(float[] mvpMatrix) {
        // Add program to OpenGL ES environment
        GLES20.glUseProgram(mProgram);

        // get handle to vertex shader's vPosition member
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");

        // get handle to shape's transformation matrix
        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");

        // Pass the projection and view transformation to the shader
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);

        // Enable a handle to the triangle vertices
        GLES20.glEnableVertexAttribArray(mPositionHandle);    // Enable Vertex array

        // Prepare the triangle coordinate data
        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride, vertexBuffer);

        // get handle to fragment shader's vColor member
        mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");

        // Set color for drawing the triangle
        GLES20.glUniform4fv(mColorHandle, 1, color, 0);

        // Draw the triangle
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);    // Actually draws

        // Disable vertex array
        GLES20.glDisableVertexAttribArray(mPositionHandle);    // Disable Vertex array
    }

    /*
    * MEMBER VARS
    */
    private final int mProgram; // Shader program handle
    private int mPositionHandle; // Positional data from shader
    private int mColorHandle;  // Color data from shader
    // Use to access and set the view transformation
    private int mMVPMatrixHandle;

    private final int vertexCount = rectangleCoords.length / COORDS_PER_VERTEX;
    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex


    private final String vertexShaderCode =
            // This matrix member variable provides a hook to manipulate
            // the coordinates of the objects that use this vertex shader
            "uniform mat4 uMVPMatrix;" +
                    "attribute vec4 vPosition;" +
                    "void main() {" +
                    // the matrix must be included as a modifier of gl_Position
                    // Note that the uMVPMatrix factor *must be first* in order
                    // for the matrix multiplication product to be correct.
                    "  gl_Position = uMVPMatrix * vPosition;" +
                    "}";

    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}";

    private FloatBuffer vertexBuffer;

    // number of coordinates per vertex in this array
    static final int COORDS_PER_VERTEX = 3;

    // Set color with red, green, blue and alpha (opacity) values
    float color[] = {0.255f, 0.686f, 1.0f, 1.0f};


}// end class