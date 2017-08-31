package com.adammcneilly.openglsample.shapes

import android.opengl.GLES20
import com.adammcneilly.openglsample.MyGLRenderer
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

/**
 * Represents a shape. Use byte buffer of coordinates for max efficiency
 *
 * By default, OpenGL ES assumes a coordinate system where [0,0,0] (X,Y,Z) specifies the center of
 * the GLSurfaceView frame, [1,1,0] is the top right corner of the frame and [-1,-1,0] is bottom
 * left corner of the frame.
 * For an illustration of this coordinate system, see the OpenGL ES developer guide.
 */
class Triangle {
    private val vertexBuffer: FloatBuffer
    private val program: Int
    private var positionHandle: Int = 0
    private var colorHandle: Int = 0
    private var mvpMatrixHandle: Int = 0

    init {
        // Initialize vertex byte buffer for shape coordinates
        // (number of coordinate values * 4 bytes per float)
        val byteBuffer = ByteBuffer.allocateDirect(TRIANGLE_COORDS.size * 4)
        // Use the device hardware's native byte order
        byteBuffer.order(ByteOrder.nativeOrder())

        // Create a floating point buffer from the ByteBuffer
        vertexBuffer = byteBuffer.asFloatBuffer()

        // Add the coordinates
        vertexBuffer.put(TRIANGLE_COORDS)

        // Set the buffer to read the first coordinate
        vertexBuffer.position(0)

        val vertexShader = MyGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode)
        val fragmentShader = MyGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode)

        // Create empty OpenGL ES Program
        program = GLES20.glCreateProgram()

        // Add the shaders
        GLES20.glAttachShader(program, vertexShader)
        GLES20.glAttachShader(program, fragmentShader)

        // Creates OpenGL ES program executables
        GLES20.glLinkProgram(program)
    }

    fun draw(mvpMatrix: FloatArray) {
        // Add program to OpenGL ES Environment
        GLES20.glUseProgram(program)

        // Get handle to vertex shader's vPosition member
        positionHandle = GLES20.glGetAttribLocation(program, "vPosition")

        // Enable a handle to the triangle vertices
        GLES20.glEnableVertexAttribArray(positionHandle)

        // Prepare the triangle coordinate data
        GLES20.glVertexAttribPointer(positionHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, VERTEX_STRIDE, vertexBuffer)

        // Get handle to fragment shader's vColor member
        colorHandle = GLES20.glGetUniformLocation(program, "vColor")

        // Set color for drawing the triangle
        GLES20.glUniform4fv(colorHandle, 1, COLOR, 0)

        // Draw triangle
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, VERTEX_COUNT)

        // Disable vertex array
        GLES20.glDisableVertexAttribArray(positionHandle)

        // get handle to shape's transformation matrix
        mvpMatrixHandle = GLES20.glGetUniformLocation(program, "uMVPMatrix")

        // Pass the projection and view transformation to the shader
        GLES20.glUniformMatrix4fv(mvpMatrixHandle, 1, false, mvpMatrix, 0)

        // Draw the triangle
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, VERTEX_COUNT)

        // Disable vertex array
        GLES20.glDisableVertexAttribArray(positionHandle)
    }

    companion object {
        // Number of coordinates per vertex
        val COORDS_PER_VERTEX = 3
        val TRIANGLE_COORDS: FloatArray = floatArrayOf(// In counter-clockwise order:
                0.0f, 0.622008459f, 0.0f, // top
                -0.5f, -0.311004243f, 0.0f, // bottom left
                0.5f, -0.311004243f, 0.0f  // bottom right
        )

        // Set color with red, green, blue, and alpha (opacity) values
        val COLOR = floatArrayOf(0.63671875f, 0.76953125f, 0.22265625f, 1.0f)

        private val VERTEX_COUNT = TRIANGLE_COORDS.size / COORDS_PER_VERTEX
        private val VERTEX_STRIDE = COORDS_PER_VERTEX * 4 // 4 bytes per vertex

        // Vertex shader is for drawing coordinates
        private val vertexShaderCode =
                // This matrix member variable provides a hook to manipulate
                // the coordinates of the objects that use this vertex shader
                "uniform mat4 uMVPMatrix;" +
                        "attribute vec4 vPosition;" +
                        "void main() {" +
                        // the matrix must be included as a modifier of gl_Position
                        // Note that the uMVPMatrix factor *must be first* in order
                        // for the matrix multiplication product to be correct.
                        "  gl_Position = uMVPMatrix * vPosition;" +
                        "}"

        // Fragment shader is for colors and textures
        private val fragmentShaderCode =
                "precision mediump float;" +
                        "uniform vec4 vColor;" +
                        "void main() {" +
                        "  gl_FragColor = vColor;" +
                        "}"
    }
}