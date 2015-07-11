package dangine.graphics;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.Color;

import com.badlogic.gdx.math.Matrix4;

import dangine.harness.VertexDataForColor;

public class DangineColoredQuad {

    // Quad variables
    private int vaoId = 0;
    private int vboId = 0;
    private int vboiId = 0;
    private int indicesCount = 0;
    private int transformMatrixLocation = 0;
    private FloatBuffer matrix44Buffer = BufferUtils.createFloatBuffer(16);
    VertexDataForColor[] vertices;
    FloatBuffer verticesBuffer;

    /**
     * Stores the color and vertex information for a quad. The Quad itself needs
     * to be transformed by a matrix before drawn
     */
    public DangineColoredQuad(Color color) {
        // Get matrices uniform locations
        transformMatrixLocation = GL20.glGetUniformLocation(DangineShaders.getColorProgramId(), "transformMatrix");

        // We'll define our quad using 4 vertices of the custom 'Vertex' class
        VertexDataForColor v0 = new VertexDataForColor();
        v0.setXYZ(-0.5f, 0.5f, 0f);
        v0.setRGBA(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f);
        VertexDataForColor v1 = new VertexDataForColor();
        v1.setXYZ(-0.5f, -0.5f, 0f);
        v1.setRGBA(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f);
        VertexDataForColor v2 = new VertexDataForColor();
        v2.setXYZ(0.5f, -0.5f, 0f);
        v2.setRGBA(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f);
        VertexDataForColor v3 = new VertexDataForColor();
        v3.setXYZ(0.5f, 0.5f, 0f);
        v3.setRGBA(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f);

        vertices = new VertexDataForColor[] { v0, v1, v2, v3 };
        // Put each 'Vertex' in one FloatBuffer
        verticesBuffer = BufferUtils.createFloatBuffer(vertices.length * VertexDataForColor.elementCount);
        for (int i = 0; i < vertices.length; i++) {
            verticesBuffer.put(vertices[i].getXYZW());
            verticesBuffer.put(vertices[i].getRGBA());
        }
        verticesBuffer.flip();

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
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verticesBuffer, GL15.GL_STATIC_DRAW);
        // Put the positions in attribute list 0
        GL20.glVertexAttribPointer(0, 4, GL11.GL_FLOAT, false, VertexDataForColor.sizeInBytes, 0);
        // Put the colors in attribute list 1
        GL20.glVertexAttribPointer(1, 4, GL11.GL_FLOAT, false, VertexDataForColor.sizeInBytes,
                VertexDataForColor.elementBytes * 4);
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
        // Use the shader that draws colors with a transformation
        GL20.glUseProgram(DangineShaders.getColorProgramId());

        // Bind to the VAO that has all the information about the vertices
        GL30.glBindVertexArray(vaoId);
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);

        // Bind to the index VBO that has all the information about the order of
        // the vertices
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboiId);

        // Draw the vertices
        GL11.glDrawElements(GL11.GL_TRIANGLES, indicesCount, GL11.GL_UNSIGNED_BYTE, 0);

        // Put everything back to default (deselect)
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL30.glBindVertexArray(0);
        GL20.glUseProgram(0);
    }

    public void setQuadColor(Color color) {
        // Update vertices in the VBO, first bind the VBO
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);

        // Apply and update vertex data
        for (int i = 0; i < vertices.length; i++) {
            VertexDataForColor vertex = vertices[i];
            vertex.setRGBA(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f,
                    color.getAlpha() / 255f);
        }

        FloatBuffer vertexFloatBuffer = verticesBuffer;
        vertexFloatBuffer.rewind();
        for (int j = 0; j < vertices.length; j++) {
            verticesBuffer.put(vertices[j].getXYZW());
            verticesBuffer.put(vertices[j].getRGBA());
        }
        vertexFloatBuffer.flip();

        GL15.glBufferSubData(GL15.GL_ARRAY_BUFFER, 0, vertexFloatBuffer);

        // And of course unbind
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    public void setQuadColor(Color topLeft, Color topRight, Color bottomLeft, Color bottomRight) {
        // Update vertices in the VBO, first bind the VBO
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);

        // Apply and update vertex data
        vertices[0].setRGBA(topLeft.getRed() / 255f, topLeft.getGreen() / 255f, topLeft.getBlue() / 255f,
                topLeft.getAlpha() / 255f);
        vertices[1].setRGBA(topRight.getRed() / 255f, topRight.getGreen() / 255f, topRight.getBlue() / 255f,
                topRight.getAlpha() / 255f);
        vertices[2].setRGBA(bottomLeft.getRed() / 255f, bottomLeft.getGreen() / 255f, bottomLeft.getBlue() / 255f,
                bottomLeft.getAlpha() / 255f);
        vertices[3].setRGBA(bottomRight.getRed() / 255f, bottomRight.getGreen() / 255f, bottomRight.getBlue() / 255f,
                bottomRight.getAlpha() / 255f);

        FloatBuffer vertexFloatBuffer = verticesBuffer;
        vertexFloatBuffer.rewind();
        for (int j = 0; j < vertices.length; j++) {
            verticesBuffer.put(vertices[j].getXYZW());
            verticesBuffer.put(vertices[j].getRGBA());
        }
        vertexFloatBuffer.flip();

        GL15.glBufferSubData(GL15.GL_ARRAY_BUFFER, 0, vertexFloatBuffer);

        // And of course unbind
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    public void updateTransformationMatrixOfShader(Matrix4 matrix) {

        // badlogic result
        // Debugger.info("colorquad result:\n" + matrix.toString());

        // Upload matrices to the uniform variables
        GL20.glUseProgram(DangineShaders.getColorProgramId());

        matrix44Buffer.put(matrix.val);
        matrix44Buffer.flip();
        GL20.glUniformMatrix4(transformMatrixLocation, false, matrix44Buffer);
        GL20.glUseProgram(0);
    }

    public void destroyQuad() {
        // Select the VAO
        GL30.glBindVertexArray(vaoId);

        // Disable the VBO index from the VAO attributes list
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);

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

}
