package dangine.graphics;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public class DangineShaders {

    static DangineShader colorShader;
    static DangineShader transformShader;   
    static DangineShader textureShader;  
    static DangineShader sampleShader;   
   
    public static void setupShaders() {
        colorShader = new DangineShader("src/dangine/graphics/vertex.glsl", "src/dangine/graphics/fragment.glsl", false);
        transformShader = new DangineShader("src/dangine/graphics/transform_vertex.glsl", "src/dangine/graphics/transform_fragment.glsl", false);
        textureShader = new DangineShader("src/dangine/graphics/texture_vertex.glsl", "src/dangine/graphics/texture_fragment.glsl", true);      
        sampleShader = new DangineShader("src/dangine/graphics/sample_vertex.glsl", "src/dangine/graphics/sample_fragment.glsl", true);        
    }

    @SuppressWarnings("deprecation")
    public static int loadShader(String filename, int type) {
        StringBuilder shaderSource = new StringBuilder();
        int shaderID = 0;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null) {
                shaderSource.append(line).append("\n");
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("Could not read file.");
            e.printStackTrace();
            System.exit(-1);
        }

        shaderID = GL20.glCreateShader(type);
        GL20.glShaderSource(shaderID, shaderSource);
        GL20.glCompileShader(shaderID);

        if (GL20.glGetShader(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            System.err.println("Could not compile shader.");
            System.exit(-1);
        }

        return shaderID;
    }

    public static void destroyShaders() {
        colorShader.destroy();
        transformShader.destroy();
        textureShader.destroy();
        sampleShader.destroy();
    }

    public static int getColorProgramId() {
        return colorShader.getProgramId();
    }
    
    public static int getTransformProgramId() {
        return transformShader.getProgramId();
    }
    
    public static int getTextureProgramId() {
        return textureShader.getProgramId();
    }
    
    public static int getSampleProgramId() {
        return sampleShader.getProgramId();
    }

}
