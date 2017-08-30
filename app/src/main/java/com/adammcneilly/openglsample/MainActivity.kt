package com.adammcneilly.openglsample

import android.content.Context
import android.opengl.GLSurfaceView
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    private val glView: GLSurfaceView by lazy { MyGLSurfaceView(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(glView)
    }

    class MyGLSurfaceView(context: Context): GLSurfaceView(context) {
        private val renderer = MyGLRenderer()

        init {
            // Create an OpenGL ES 2.0 context
            setEGLContextClientVersion(2)

            // Set renderer for drawing
            setRenderer(renderer)

            // Render the view only when there is a change in the drawing data
            renderMode = GLSurfaceView.RENDERMODE_WHEN_DIRTY
        }
    }
}
