package dangine.graphics;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import dangine.debugger.Debugger;
import dangine.image.ResourceManifest;
import dangine.image.Resources;

public class DangineShaders {

    static DangineShader testShader;
    static DangineShader colorShader;
    static DangineShader textureShader;
    static DangineShader sampleShader;
    static DangineShader glowShader;

    public static void setupShaders() {
        testShader = new DangineShader("src/dangine/graphics/vertex.glsl", "src/dangine/graphics/fragment.glsl", false);
        colorShader = new DangineShader("src/dangine/graphics/color_vertex.glsl",
                "src/dangine/graphics/color_fragment.glsl", false);
        textureShader = new DangineShader("src/dangine/graphics/texture_vertex.glsl",
                "src/dangine/graphics/texture_fragment.glsl", true);
        sampleShader = new DangineShader("src/dangine/graphics/sample_vertex.glsl",
                "src/dangine/graphics/sample_fragment.glsl", true);
        glowShader = new DangineShader("src/dangine/graphics/glow_vertex.glsl",
                "src/dangine/graphics/glow_fragment.glsl", true);
    }

    @SuppressWarnings("deprecation")
    public static int loadShader(String filename, int type) {
        StringBuilder shaderSource = new StringBuilder();
        int shaderID = 0;

        try {
            if (Resources.shouldUseManifest()) {
                Debugger.info("manifesting via " + filename);
                filename = filename.replace('\\', '/');
                filename = filename.replace("src/", "");
                Debugger.info("now via " + filename);
                InputStream in = ResourceManifest.class.getClassLoader().getResourceAsStream(filename);

                Debugger.info("Input Stream? " + in);
                Debugger.info("bytes in " + filename + " " + in.available() + " ");

                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = reader.readLine()) != null) {
                    shaderSource.append(line).append("\n");
                }
                reader.close();
            } else {
                BufferedReader reader = new BufferedReader(new FileReader(filename));
                String line;
                while ((line = reader.readLine()) != null) {
                    shaderSource.append(line).append("\n");
                }
                reader.close();
            }
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
        testShader.destroy();
        colorShader.destroy();
        textureShader.destroy();
        sampleShader.destroy();
        glowShader.destroy();
    }

    public static int getTestProgramId() {
        return testShader.getProgramId();
    }

    public static int getColorProgramId() {
        return colorShader.getProgramId();
    }

    public static int getTextureProgramId() {
        return textureShader.getProgramId();
    }

    public static int getSampleProgramId() {
        return sampleShader.getProgramId();
    }

    public static int getGlowProgramId() {
        return glowShader.getProgramId();
    }

}
