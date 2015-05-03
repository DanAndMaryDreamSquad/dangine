package dangine.graphics;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.glu.GLU;

public class DangineShader {

    final int vertexShaderId;
    final int fragmentShaderId;
    final int programId;

    public DangineShader(String vertexPath, String fragmentPath, boolean withTexture) {
        int errorCheckValue = GL11.glGetError();
        // Load the vertex shader
        vertexShaderId = DangineShaders.loadShader(vertexPath, GL20.GL_VERTEX_SHADER);
        // Load the fragment shader
        fragmentShaderId = DangineShaders.loadShader(fragmentPath, GL20.GL_FRAGMENT_SHADER);

        // Create a new shader program that links both shaders
        programId = GL20.glCreateProgram();
        GL20.glAttachShader(programId, vertexShaderId);
        GL20.glAttachShader(programId, fragmentShaderId);

        // Position information will be attribute 0
        GL20.glBindAttribLocation(programId, 0, "in_Position");
        // Color information will be attribute 1
        GL20.glBindAttribLocation(programId, 1, "in_Color");
        if (withTexture) {
            // Textute information will be attribute 2
            GL20.glBindAttribLocation(programId, 2, "in_TextureCoord");
        }

        GL20.glLinkProgram(programId);
        GL20.glValidateProgram(programId);

        errorCheckValue = GL11.glGetError();
        if (errorCheckValue != GL11.GL_NO_ERROR) {
            System.out.println("ERROR - Could not create the shaders:" + GLU.gluErrorString(errorCheckValue));
            System.exit(-1);
        }
    }

    public void destroy() {
        GL20.glUseProgram(0);
        GL20.glDetachShader(programId, vertexShaderId);
        GL20.glDetachShader(programId, fragmentShaderId);

        GL20.glDeleteShader(vertexShaderId);
        GL20.glDeleteShader(fragmentShaderId);
        GL20.glDeleteProgram(programId);
    }

    public int getVertexShaderId() {
        return vertexShaderId;
    }

    public int getFragmentShaderId() {
        return fragmentShaderId;
    }

    public int getProgramId() {
        return programId;
    }

}
