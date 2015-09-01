package dangine.graphics;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.Color;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

public class DangineTexturedQuad {
    // Quad variables
    private int vaoId = 0;
    private int vboId = 0;
    private int vboiId = 0;
    private int indicesCount = 0;
    private int transformMatrixLocation = 0;
    private FloatBuffer matrix44Buffer = BufferUtils.createFloatBuffer(16);
    VertexDataForTexture[] vertices;
    ByteBuffer verticesByteBuffer;
    DangineTexture texture;
    private int wrapMode = GL14.GL_MIRRORED_REPEAT;
    private int filterMode = GL11.GL_NEAREST;

    public DangineTexturedQuad(DangineTexture texture) {
        this.texture = texture;
        initQuad();
    }

    public DangineTexturedQuad(String imageName) {
        this.texture = DangineTextures.getImageByName(imageName);
        initQuad();
    }

    public DangineTexturedQuad withWrapModeRepeat() {
        this.wrapMode = GL11.GL_REPEAT;
        return this;
    }

    public DangineTexturedQuad withFilterModeLinear() {
        this.filterMode = GL11.GL_LINEAR;
        return this;
    }

    private VertexDataForTexture[] getVertexDataForTexture() {
        // We'll define our quad using 4 vertices of the custom 'TexturedVertex'
        // class
        VertexDataForTexture v0 = new VertexDataForTexture();
        // v0.setXYZ(-0.5f, 0.5f, 0);
        v0.setXYZ(-0.5f, 0.5f, 0);
        v0.setRGB(1, 1, 1);
        v0.setST(0, 0);
        VertexDataForTexture v1 = new VertexDataForTexture();
        // v1.setXYZ(-0.5f, -0.5f, 0);
        v1.setXYZ(-0.5f, -0.5f, 0);
        v1.setRGB(1, 1, 1);
        v1.setST(0, 1);
        // v1.setST(0, 0.5f);
        VertexDataForTexture v2 = new VertexDataForTexture();
        // v2.setXYZ(0.5f, -0.5f, 0);
        v2.setXYZ(0.5f, -0.5f, 0);
        v2.setRGB(1, 1, 1);
        v2.setST(1, 1);
        // v2.setST(0.5f, 0.5f);
        VertexDataForTexture v3 = new VertexDataForTexture();
        // v3.setXYZ(0.5f, 0.5f, 0);
        v3.setXYZ(0.5f, 0.5f, 0);
        v3.setRGB(1, 1, 1);
        v3.setST(1, 0);
        // v3.setST(0.5f, 0);
        return new VertexDataForTexture[] { v0, v1, v2, v3 };
    }

    private void initQuad() {
        // Get matrices uniform locations
        transformMatrixLocation = GL20.glGetUniformLocation(DangineShaders.getTextureProgramId(), "transformMatrix");

        vertices = getVertexDataForTexture();
        // Put each 'Vertex' in one FloatBuffer
        verticesByteBuffer = BufferUtils.createByteBuffer(vertices.length * VertexDataForTexture.stride);
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

    public void updateTransformationMatrixOfShader(Matrix4 matrix) {
        // Upload matrices to the uniform variables
        GL20.glUseProgram(DangineShaders.getTextureProgramId());

        matrix44Buffer.put(matrix.val);
        matrix44Buffer.flip();
        GL20.glUniformMatrix4(transformMatrixLocation, false, matrix44Buffer);
        GL20.glUseProgram(0);
    }

    public void drawQuad() {
        // Filter code
        GL20.glUseProgram(DangineShaders.getTextureProgramId());

        // Bind the texture
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureId());

        // This is how the texture scales. Use GL11.GL_LINEAR for smooth /
        // blurry textures, GL11.GL_NEAREST for pixel-y textures
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, filterMode);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, filterMode);

        // This is how the texture wraps around. Its where it pulls the data
        // from where it reaches the edge. https://open.gl/textures

        // GL_REPEAT: The integer part of the coordinate will be ignored and a
        // repeating pattern is formed.

        // GL_MIRRORED_REPEAT: The texture will also be repeated, but it will be
        // mirrored when the integer part of the coordinate is odd.

        // GL_CLAMP_TO_EDGE: The coordinate will simply be clamped between 0 and
        // 1.

        // GL_CLAMP_TO_BORDER: The coordinates that fall outside the range will
        // be given a specified border color.

        // GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S,
        // GL13.GL_CLAMP_TO_BORDER);
        // GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T,
        // GL13.GL_CLAMP_TO_BORDER);
        //
        // GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S,
        // GL11.GL_REPEAT);
        // GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T,
        // GL11.GL_REPEAT);
        //
        // GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S,
        // GL12.GL_CLAMP_TO_EDGE);
        // GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T,
        // GL12.GL_CLAMP_TO_EDGE);
        //
        // GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S,
        // GL14.GL_MIRRORED_REPEAT);
        // GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T,
        // GL14.GL_MIRRORED_REPEAT);

        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, wrapMode);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, wrapMode);

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

    public void changeTextureCoordinates(CharacterCoordinates coordinates) {
        changeTextureCoordinates(coordinates.getTopLeft().getX(), coordinates.getTopLeft().getY(), coordinates
                .getBottomRight().getX(), coordinates.getBottomRight().getY());
    }

    public void changeTextureCoordinates(int x1, int y1, int x2, int y2) {
        List<Vector2> newTextureCoordinates = new ArrayList<Vector2>();
        newTextureCoordinates.add(new Vector2(x1, y1));
        newTextureCoordinates.add(new Vector2(x1, y2));
        newTextureCoordinates.add(new Vector2(x2, y2));
        newTextureCoordinates.add(new Vector2(x2, y1));

        // Update vertices in the VBO, first bind the VBO
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);

        // Apply and update vertex data
        for (int i = 0; i < vertices.length; i++) {
            VertexDataForTexture vertex = vertices[i];

            // // Offset the vertex position
            // float[] st = vertex.getST();
            // vertex.setST(st[0] * 2, st[1] * 2);
            Vector2 st = newTextureCoordinates.get(i);
            vertex.setST(st.x / (float) texture.getWidth(), st.y / (float) texture.getHeight());

            // Put the new data in a ByteBuffer (in the view of a FloatBuffer)
            FloatBuffer vertexFloatBuffer = verticesByteBuffer.asFloatBuffer();
            vertexFloatBuffer.rewind();
            vertexFloatBuffer.put(vertex.getElements());
            vertexFloatBuffer.flip();

            GL15.glBufferSubData(GL15.GL_ARRAY_BUFFER, i * VertexDataForTexture.stride, vertexFloatBuffer);
        }

        // And of course unbind
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    public void setTextureColor(Color color) {
        // Update vertices in the VBO, first bind the VBO
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);

        // Apply and update vertex data
        for (int i = 0; i < vertices.length; i++) {
            VertexDataForTexture vertex = vertices[i];
            vertex.setRGBA(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f,
                    color.getAlpha() / 255f);

            // Put the new data in a ByteBuffer (in the view of a FloatBuffer)
            FloatBuffer vertexFloatBuffer = verticesByteBuffer.asFloatBuffer();
            vertexFloatBuffer.rewind();
            vertexFloatBuffer.put(vertex.getElements());
            vertexFloatBuffer.flip();

            GL15.glBufferSubData(GL15.GL_ARRAY_BUFFER, i * VertexDataForTexture.stride, vertexFloatBuffer);
        }

        // And of course unbind
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    public void destroyQuad() {
        // Select the VAO
        GL30.glBindVertexArray(vaoId);

        // Disable the VBO index from the VAO attributes list
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);

        // Delete the vertex VBO
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL15.glDeleteBuffers(vboId);

        // Delete the index VBO
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL15.glDeleteBuffers(vboiId);

        // Delete the VAO
        GL30.glBindVertexArray(0);
        GL30.glDeleteVertexArrays(vaoId);
    }

    public DangineTexture getTexture() {
        return texture;
    }

    public void setTexture(DangineTexture texture) {
        this.texture = texture;
    }

    public int getFilterMode() {
        return filterMode;
    }

    public void setFilterMode(int filterMode) {
        this.filterMode = filterMode;
    }

}
