package cz.educanet;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL33;
import org.lwjgl.system.MemoryUtil;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Block {

    private int squareVaoId;
    private int squareVboId;
    private int squareEboId;
    private int squareColorId;

    private final int[] indices = {
            0, 1, 3,
            1, 2, 3
    };

    public Block(float x, float y, float area) {
        float longest = (float)Math.sqrt(2);
        float c1 = (float)Math.sqrt(((x + area) * (x + area)) + (y * y));
        float c2 = (float)Math.sqrt(((x + area) * (x + area)) + ((y - area) * (y - area)));
        float c3 = (float)Math.sqrt((x * x) + ((y - area) * (y - area)));
        float c4 = (float)Math.sqrt((x * x) + (y * y));
        float p1 = (c1 / longest);
        float p2 = (c2 / longest);
        float p3 = (c3 / longest);
        float p4 = (c4 / longest);

        float[] vertices = {
                x + area, y, 0.0f,
                x + area, y - area, 0.0f,
                x, y - area, 0.0f,
                x, y, 0.0f,
        };

        float[] colors = {
                p1, p1, p1, 1f,
                p2, p2, p2, 1f,
                p3, p3, p3, 1f,
                p4, p4, p4, 1f,
        };

        squareVaoId = GL33.glGenVertexArrays();
        squareEboId = GL33.glGenBuffers();
        squareVboId = GL33.glGenBuffers();
        squareColorId = GL33.glGenBuffers();

        GL33.glBindVertexArray(squareVaoId);

        GL33.glBindBuffer(GL33.GL_ELEMENT_ARRAY_BUFFER, squareEboId);
        IntBuffer ib = BufferUtils.createIntBuffer(indices.length)
                .put(indices)
                .flip();
        GL33.glBufferData(GL33.GL_ELEMENT_ARRAY_BUFFER, ib, GL33.GL_STATIC_DRAW);

        GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, squareColorId);
        FloatBuffer cfb = BufferUtils.createFloatBuffer(colors.length).put(colors).flip();

        GL33.glBufferData(GL33.GL_ARRAY_BUFFER, cfb, GL33.GL_STATIC_DRAW);
        GL33.glVertexAttribPointer(1, 4, GL33.GL_FLOAT, false, 0, 0);
        GL33.glEnableVertexAttribArray(1);

        MemoryUtil.memFree(cfb);

        GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, squareVboId);

        FloatBuffer fb = BufferUtils.createFloatBuffer(vertices.length)
                .put(vertices)
                .flip();

        GL33.glBufferData(GL33.GL_ARRAY_BUFFER, fb, GL33.GL_STATIC_DRAW);
        GL33.glVertexAttribPointer(0, 3, GL33.GL_FLOAT, false, 0, 0);
        GL33.glEnableVertexAttribArray(0);

        MemoryUtil.memFree(ib);
        MemoryUtil.memFree(fb);
    }

    public void render() {
        GL33.glUseProgram(Shaders.shaderProgramId);
        GL33.glBindVertexArray(squareVaoId);
        GL33.glDrawElements(GL33.GL_TRIANGLES, indices.length, GL33.GL_UNSIGNED_INT, 0);
    }
}
