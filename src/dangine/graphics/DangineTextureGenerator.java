package dangine.graphics;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL32;

import com.badlogic.gdx.math.Matrix4;

import dangine.debugger.Debugger;

public class DangineTextureGenerator {

    public static int frameBuffer = 0;
    public static int createdTexture = 0;
    public static DangineTexture createdDangineTexture;

    public static void generateTexture() {
        // The framebuffer, which regroups 0, 1, or more textures, and 0 or 1
        // depth buffer.
        ByteBuffer buf = null;
        frameBuffer = GL30.glGenFramebuffers();
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, frameBuffer);

        // The texture we're going to render to
        createdTexture = GL11.glGenTextures();

        // "Bind" the newly created texture : all future texture functions will
        // modify this texture
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, createdTexture);

        // Give an empty image to OpenGL ( the last "0" )
        // GL11.glTexImage2D(GL_TEXTURE_2D, 0,GL_RGB, 1024, 768, 0,GL_RGB,
        // GL_UNSIGNED_BYTE, 0);
        buf = ByteBuffer.allocateDirect(4 * 128 * 32);
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB, 128, 32, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buf);
        // Poor filtering. Needed !
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);

        // Set "renderedTexture" as our colour attachement #0
        GL32.glFramebufferTexture(GL30.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0, createdTexture, 0);

        // Set the list of draw buffers.
        // GLenum DrawBuffers[1] = {GL_COLOR_ATTACHMENT0};
        IntBuffer buffers = BufferUtils.createIntBuffer(1);
        buffers.put(0, GL30.GL_COLOR_ATTACHMENT0);
        GL20.glDrawBuffers(buffers);

        // Always check that our framebuffer is ok
        if (GL30.glCheckFramebufferStatus(GL30.GL_FRAMEBUFFER) != GL30.GL_FRAMEBUFFER_COMPLETE) {
            Debugger.warn("bad frame buffer");
            System.exit(-1);
        }

        GL11.glViewport(0, 0, 128, 32); // Render on the whole framebuffer,
                                        // complete from the lower left corner
                                        // to the upper right

        // clear our framebuffer
        // Setup an different XNA like background color

        GL11.glClearColor(0.9f, 0.6f, 0.4f, 0f);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

        Matrix4 ortho = new Matrix4().setToOrtho(0, 128, 32, 0, -100, 100);
        Matrix4 transformation = new Matrix4();
        // transformation.translate(-5.9f, 5.9f, 0);
        transformation.translate(0.5f, -0.5f, 0);
        Matrix4 scale = new Matrix4();
        // scale.scl((1f/6.4f), (1f/6.4f), 1);
        scale.scl(10f, -10f, 1);
        Matrix4 result = new Matrix4();
        DangineBox box = new DangineBox();
        box.draw();
        DangineStringDrawer drawString = new DangineStringDrawer("i love mary!");
        // drawString.getNode().getMatrix().scl(10f / 128f, 10f / 128f, 1);
        drawString.getNode().setMatrix(result.mul(ortho).mul(scale).mul(transformation));
        drawString.draw();

        createdDangineTexture = new DangineTexture(createdTexture, "s_", 128, 32);
        // DangineQuad quad1 = new DangineQuad();
        // quad1.drawQuad();
        // DangineTexturedQuadSample quad2 = new DangineTexturedQuadSample();
        // quad2.drawQuad();

        // Setup an XNA like background color

        GL11.glClearColor(0.4f, 0.6f, 0.9f, 0f);

        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);

        GL11.glViewport(0, 0, 300, 200); // return to normal viewport
    }

    public static DangineTexture generateStringTexture(String message) {
        message = message.toLowerCase();
        int textureHeight = calculateTextureHeight(message);
        int textureWidth = calculateTextureWidth(message);

        // The framebuffer, which regroups 0, 1, or more textures, and 0 or 1
        // depth buffer.
        ByteBuffer buf = null;
        frameBuffer = GL30.glGenFramebuffers();
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, frameBuffer);

        // The texture we're going to render to
        createdTexture = GL11.glGenTextures();

        // "Bind" the newly created texture : all future texture functions will
        // modify this texture
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, createdTexture);

        // Give an empty image to OpenGL ( the last "0" )
        // GL11.glTexImage2D(GL_TEXTURE_2D, 0,GL_RGB, 1024, 768, 0,GL_RGB,
        // GL_UNSIGNED_BYTE, 0);
        buf = ByteBuffer.allocateDirect(4 * textureWidth * textureHeight);
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, textureWidth, textureHeight, 0, GL11.GL_RGBA,
                GL11.GL_UNSIGNED_BYTE, buf);

        // This filtering is what the texture looks like when scaled.
        // "GL_NEAREST" is "Nearest Neighbor", which looks pixely
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);

        // Set "renderedTexture" as our colour attachement #0
        GL32.glFramebufferTexture(GL30.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0, createdTexture, 0);

        // Always check that our framebuffer is ok
        if (GL30.glCheckFramebufferStatus(GL30.GL_FRAMEBUFFER) != GL30.GL_FRAMEBUFFER_COMPLETE) {
            Debugger.warn("bad frame buffer");
            System.exit(-1);
        }

        // Render on the whole framebuffer,
        // complete from the lower left corner
        // to the upper right
        GL11.glViewport(0, 0, textureWidth, textureHeight);

        // clear our framebuffer
        // Setup an different background color
        GL11.glClearColor(0.9f, 0.6f, 0.4f, 0.0f);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

        Matrix4 ortho = new Matrix4().setToOrtho(0, textureWidth, textureHeight, 0, -100, 100);
        Matrix4 transformation = new Matrix4();
        transformation.translate(0.5f, -0.5f, 0);
        Matrix4 scale = new Matrix4();
        scale.scl(10f, -10f, 1);
        String[] lines = message.split("\n");
        for (int i = 0; i < lines.length; i++) {
            Matrix4 result = new Matrix4();
            String line = lines[i];
            DangineStringDrawer drawString = new DangineStringDrawer(line);
            transformation = new Matrix4();
            transformation.translate(0.5f, -(0.5f + i), 0);
            drawString.getNode().setMatrix(result.mul(ortho).mul(scale).mul(transformation));
            drawString.draw();
        }

        createdDangineTexture = new DangineTexture(createdTexture, "s_", textureWidth, textureHeight);

        // Setup an XNA like background color
        GL11.glClearColor(0.4f, 0.6f, 0.9f, 0f);

        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);

        DangineOpenGL.viewportPortOpenGL();
        // GL11.glViewport(0, 0, DangineOpenGL., 200); // return to normal
        // viewport
        return createdDangineTexture;
    }

    public static int calculateTextureHeight(String message) {
        // Texture sides must be a power of 2 for length (i.e. 32 x 128)
        String[] lines = message.split("\n");
        int powerOfTwo = 2;
        while (powerOfTwo < lines.length * DangineFont.CHARACTER_HEIGHT_IN_PIXELS) {
            powerOfTwo = powerOfTwo * 2;
        }
        return powerOfTwo;
    }

    public static int calculateTextureWidth(String message) {
        // Texture sides must be a power of 2 for length (i.e. 32 x 128)
        String[] lines = message.split("\n");
        String longestLine = lines[0];
        for (String line : lines) {
            if (line.length() > longestLine.length()) {
                longestLine = line;
            }
        }
        int powerOfTwo = 2;
        while (powerOfTwo < longestLine.length() * DangineFont.CHARACTER_WIDTH_IN_PIXELS) {
            powerOfTwo = powerOfTwo * 2;
        }
        return powerOfTwo;
    }
}
