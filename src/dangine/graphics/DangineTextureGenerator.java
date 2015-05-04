package dangine.graphics;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL32;

import com.badlogic.gdx.Gdx;

import dangine.debugger.Debugger;

public class DangineTextureGenerator {
    
    public static int frameBuffer = 0;
    public static int createdTexture = 0;

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
        buf = ByteBuffer.allocateDirect(4 * 256 * 256);
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB, 256, 256, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buf);
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

        // clear our framebuffer
        // Setup an different XNA like background color
        GL11.glClearColor(0.9f, 0.6f, 0.4f, 0f);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        
        DangineBox box = new DangineBox();
        box.draw();
//        DangineQuad quad1 = new DangineQuad();
//        quad1.drawQuad();
//        DangineTexturedQuadSample quad2 = new DangineTexturedQuadSample();
//        quad2.drawQuad();
        
        // Setup an XNA like background color
        GL11.glClearColor(0.4f, 0.6f, 0.9f, 0f);


        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
    }
}
