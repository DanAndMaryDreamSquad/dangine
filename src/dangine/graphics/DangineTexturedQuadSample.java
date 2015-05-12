package dangine.graphics;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import dangine.image.TextureLoader;

public class DangineTexturedQuadSample {

    // Quad variables
    private int vaoId = 0;
    private int vboId = 0;
    private int vboiId = 0;
    private int indicesCount = 0;

    // private int texId =
    // DangineTextures.loadPNGTexture("src/assets/images/bgs/snowsky1.png",
    // GL13.GL_TEXTURE0);
    private int texId = TextureLoader.loadPNGTexture("src/assets/images/weapons/greatsword.png", GL13.GL_TEXTURE0);

    public DangineTexturedQuadSample() {
        setupQuad();
    }

    private void setupQuad() {
        // We'll define our quad using 4 vertices of the custom 'TexturedVertex'
        // class
        VertexDataForTexture v0 = new VertexDataForTexture();
        v0.setXYZ(-0.5f, 0.5f, 0);
        v0.setRGB(1, 0, 0);
        v0.setST(0, 0);
        VertexDataForTexture v1 = new VertexDataForTexture();
        v1.setXYZ(-0.5f, -0.5f, 0);
        v1.setRGB(0, 1, 0);
        v1.setST(0, 1);
        VertexDataForTexture v2 = new VertexDataForTexture();
        v2.setXYZ(0.5f, -0.5f, 0);
        v2.setRGB(0, 0, 1);
        v2.setST(1, 1);
        VertexDataForTexture v3 = new VertexDataForTexture();
        v3.setXYZ(0.5f, 0.5f, 0);
        v3.setRGB(1, 1, 1);
        v3.setST(1, 0);

        VertexDataForTexture[] vertices = new VertexDataForTexture[] { v0, v1, v2, v3 };

        // Put each 'Vertex' in one FloatBuffer
        ByteBuffer verticesByteBuffer = BufferUtils.createByteBuffer(vertices.length * VertexDataForTexture.stride);
        FloatBuffer verticesFloatBuffer = verticesByteBuffer.asFloatBuffer();
        for (int i = 0; i < vertices.length; i++) {
            // Add position, color and texture floats to the buffer
            verticesFloatBuffer.put(vertices[i].getElements());
        }
        verticesFloatBuffer.flip();

        // OpenGL expects to draw vertices in counter clockwise order by default
        byte[] indices = { 0, 1, 2, 2, 3, 0 };
        indicesCount = indices.length;
        ByteBuffer indicesBuffer = BufferUtils.createByteBuffer(indicesCount);
        indicesBuffer.put(indices);
        indicesBuffer.flip();

        // Create a new Vertex Array Object in memory and select it (bind)
        vaoId = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vaoId);

        // Create a new Vertex Buffer Object in memory and select it (bind)
        vboId = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verticesFloatBuffer, GL15.GL_STREAM_DRAW);

        // Put the position coordinates in attribute list 0
        GL20.glVertexAttribPointer(0, VertexDataForTexture.positionElementCount, GL11.GL_FLOAT, false,
                VertexDataForTexture.stride, VertexDataForTexture.positionByteOffset);
        // Put the color components in attribute list 1
        GL20.glVertexAttribPointer(1, VertexDataForTexture.colorElementCount, GL11.GL_FLOAT, false,
                VertexDataForTexture.stride, VertexDataForTexture.colorByteOffset);
        // Put the texture coordinates in attribute list 2
        GL20.glVertexAttribPointer(2, VertexDataForTexture.textureElementCount, GL11.GL_FLOAT, false,
                VertexDataForTexture.stride, VertexDataForTexture.textureByteOffset);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

        // Deselect (bind to 0) the VAO
        GL30.glBindVertexArray(0);

        // Create a new VBO for the indices and select it (bind) - INDICES
        vboiId = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboiId);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    public void drawQuad() {
        // GL20.glUseProgram(DangineShaders.getColorProgramId());
        GL20.glUseProgram(DangineShaders.getTextureProgramId());
        // GL20.glUseProgram(DangineShaders.getTransformProgramId());

        // Bind the texture
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        // GL11.glBindTexture(GL11.GL_TEXTURE_2D, texId);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, DangineTextureGenerator.createdTexture);

        // Bind to the VAO that has all the information about the vertices
        GL30.glBindVertexArray(vaoId);
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);

        // Bind to the index VBO that has all the information about the order of
        // the vertices
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboiId);

        // Draw the vertices
        GL11.glDrawElements(GL11.GL_TRIANGLES, indicesCount, GL11.GL_UNSIGNED_BYTE, 0);

        // Put everything back to default (deselect)
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
        GL20.glUseProgram(0);
    }

}
