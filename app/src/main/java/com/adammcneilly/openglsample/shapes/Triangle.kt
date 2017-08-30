package com.adammcneilly.openglsample.shapes

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
    }
}