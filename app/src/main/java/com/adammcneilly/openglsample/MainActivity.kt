package com.adammcneilly.openglsample

import android.content.Context
import android.opengl.GLSurfaceView
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent

class MainActivity : AppCompatActivity() {

    private val glView: GLSurfaceView by lazy { MyGLSurfaceView(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(glView)
    }

    class MyGLSurfaceView(context: Context): GLSurfaceView(context) {
        private val renderer = MyGLRenderer()
        private var previousX = 0F
        private var previousY = 0F

        init {
            // Create an OpenGL ES 2.0 context
            setEGLContextClientVersion(2)

            // Set renderer for drawing
            setRenderer(renderer)

            // Render the view only when there is a change in the drawing data
            renderMode = GLSurfaceView.RENDERMODE_WHEN_DIRTY
        }

        companion object {
            private val TOUCH_SCALE_FACTOR = 180F / 320
        }

        override fun onTouchEvent(event: MotionEvent?): Boolean {
            // MotionEvent reports input details from the touch screen
            // and other input controls. In this case, you are only
            // interested in events where the touch position changed.

            val x = event?.x ?: 0F
            val y = event?.y ?: 0F

            when (event?.action) {
                MotionEvent.ACTION_MOVE -> {
                    var dx = x - previousX
                    var dy = y - previousY

                    // Reverse direction of rotation above the mid-line
                    if (y > (height / 2)) {
                        dx *= -1
                    }

                    // Reverse direction of rotation to the left of the mid-line
                    if (x < (width / 2)) {
                        dy *= -1
                    }

                    renderer.angle = renderer.angle + ((dx + dy) * TOUCH_SCALE_FACTOR)
                    requestRender()
                }
            }

            previousX = x
            previousY = y
            return true
        }
    }
}
