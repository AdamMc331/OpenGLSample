package com.adammcneilly.openglsample

import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import com.adammcneilly.openglsample.shapes.Square
import com.adammcneilly.openglsample.shapes.Triangle
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10


/**
 * Basic GLSurfaceView.Renderer that renders just a black screen.
 */
class MyGLRenderer : GLSurfaceView.Renderer {
    private lateinit var triangle: Triangle
    private lateinit var square: Square
    // mvpMatrix is an abbreviation for "Model View Projection Matrix"
    private val mvpMatrix = FloatArray(16)
    private val projectionMatrix = FloatArray(16)
    private val viewMatrix = FloatArray(16)

    override fun onDrawFrame(p0: GL10?) {
        // Redraw color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)

        // Set the camera position (View matrix)
        Matrix.setLookAtM(viewMatrix, 0, 0F, 0F, -3F, 0f, 0f, 0f, 0f, 1.0f, 0.0f)

        // Calculate the projection and view transformation
        Matrix.multiplyMM(mvpMatrix, 0, projectionMatrix, 0, viewMatrix, 0)

        // Draw shape
        triangle.draw(mvpMatrix)
    }

    override fun onSurfaceChanged(p0: GL10?, width: Int, height: Int) {
        // Reset viewport size
        GLES20.glViewport(0, 0, width, height)

        val ratio = width.toFloat() / height

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1F, 1F, 3F, 7F)
    }

    override fun onSurfaceCreated(p0: GL10?, p1: EGLConfig?) {
        // Set the background frame color
        GLES20.glClearColor(0.0F, 0.0F, 0.0F, 1.0F)

        // Initialize shapes here for memory efficiency if they'll never change
        triangle = Triangle()
        square = Square()
    }

    companion object {
        // Shaders contain OpenGL Shading Language (GLSL) code that must be compiled prior to
        // using it in the OpenGL ES environment.
        // To compile this code, create a utility method in your renderer class:
        fun loadShader(type: Int, shaderCode: String): Int {
            // Create a vertex shader type or a fragment shader type
            val shader = GLES20.glCreateShader(type)

            // Add the source code to the shader and compile it
            GLES20.glShaderSource(shader, shaderCode)
            GLES20.glCompileShader(shader)

            return shader
        }
    }
}