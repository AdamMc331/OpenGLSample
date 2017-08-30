package com.adammcneilly.openglsample.shapes

import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import java.nio.ShortBuffer

/**
 * Defines a square, which is essentially two triangles together.
 */
class Square {
    private val vertexBuffer: FloatBuffer
    private val drawListBuffer: ShortBuffer

    init {
        // Initialize byte buffer for coordinates
        // 4 bytes per long
        val byteBuffer = ByteBuffer.allocateDirect(SQUARE_COORDS.size * 4)
        byteBuffer.order(ByteOrder.nativeOrder())

        vertexBuffer = byteBuffer.asFloatBuffer()
        vertexBuffer.put(SQUARE_COORDS)
        vertexBuffer.position(0)

        // Initialize byte buffer for the draw list
        // 2 bytes per short
        val dlByteBuffer = ByteBuffer.allocateDirect(DRAW_ORDER.size * 2)
        dlByteBuffer.order(ByteOrder.nativeOrder())

        drawListBuffer = dlByteBuffer.asShortBuffer()
        drawListBuffer.put(DRAW_ORDER)
        drawListBuffer.position(0)
    }

    companion object {
        // Number of coordinates
        val COORDS_PER_VERTEX = 3
        val SQUARE_COORDS = floatArrayOf(
                -0.5f, 0.5f, 0.0f, // Top Left
                -0.5f, -0.5f, 0.0f, // Bottom Left
                0.5f, -0.5f, 0.0f, // Bottom Right
                0.5f, 0.5f, 0.0f // Top Right
        )

        // Order to draw vertices
        private val DRAW_ORDER = shortArrayOf(0, 1, 2, 0, 2, 3)
    }
}