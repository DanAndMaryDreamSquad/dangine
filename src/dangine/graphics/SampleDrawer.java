package dangine.graphics;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.GdxNativesLoader;

import dangine.debugger.Debugger;
import dangine.scenegraph.SceneGraphNode;

public class SampleDrawer {
    public static final int WIDTH = 300;
    public static final int HEIGHT = 200;
    private final double PI = 3.14159265358979323846;

    private int transformMatrixLocation = 0;
    private int textureTransformMatrixLocation = 0;
    private Matrix4f projectionMatrix = null;
    private Matrix4f viewMatrix = null;
    private Matrix4f modelMatrix = null;
    private Vector3f modelPos = null;
    private Vector3f modelAngle = null;
    private Vector3f modelScale = null;
    private Vector3f cameraPos = null;
    private FloatBuffer matrix44Buffer = null;

    private Matrix4 projectMatrix2 = new Matrix4().setToOrtho(0, WIDTH, HEIGHT, 0, -100, 100);
    private Matrix4 viewMatrix2 = new Matrix4().idt();
    private Matrix4 modelMatrix2 = new Matrix4().idt();

    public static final SceneGraphNode PARENT_ORTHO_NODE = new SceneGraphNode();

    private DangineBox box;
    private DanginePicture picture;
    private DanginePicture pictureCreated;
    private DangineDrawString drawString;

    // Entry point for the application
    public static void main(String[] args) {
        new SampleDrawer();
    }

    DangineQuad dangineQuad;
    DangineTexturedQuadSample dangineTexturedQuad;

    public SampleDrawer() {
        PARENT_ORTHO_NODE.setMatrix(new Matrix4().setToOrtho(0, WIDTH, HEIGHT, 0, -100, 100));
        GdxNativesLoader.load();

        // Set the default quad rotation, scale and position values
        modelPos = new Vector3f(1, 1, 0);
        modelAngle = new Vector3f(0, 0, 0);
        modelScale = new Vector3f(100, 100, 1);
        cameraPos = new Vector3f(0, 0, -1);

        modelMatrix2.scale(100, 100, 1);

        // Initialize OpenGL (Display)
        DangineOpenGL.setupOpenGL();
        DangineTextures.initialize();
        dangineQuad = new DangineQuad();
        dangineTexturedQuad = new DangineTexturedQuadSample();
        DangineShaders.setupShaders();
        this.setupMatrices();
        box = new DangineBox();
        picture = new DanginePicture("starfont");
        drawString = new DangineDrawString("i love mary!");
        DangineTextureGenerator.generateTexture();
        // pictureCreated = new DanginePicture(new
        // DangineTexture(DangineTextureGenerator.createdTexture, "created", 0,
        // 0));
        pictureCreated = new DanginePicture(DangineTextureGenerator.generateStringTexture("i love\nmary!"));
        PARENT_ORTHO_NODE.addChild(box.getNode());
        PARENT_ORTHO_NODE.addChild(picture.getNode());
        PARENT_ORTHO_NODE.addChild(pictureCreated.getNode());
        PARENT_ORTHO_NODE.addChild(drawString.getNode());

        PARENT_ORTHO_NODE.propagate();

        // Get matrices uniform locations
        transformMatrixLocation = GL20.glGetUniformLocation(DangineShaders.getColorProgramId(), "transformMatrix");

        // Get matrices uniform locations
        textureTransformMatrixLocation = GL20.glGetUniformLocation(DangineShaders.getTextureProgramId(),
                "transformMatrix");

        boolean madeTexture = false;

        while (!Display.isCloseRequested()) {
            // Do a single loop (logic/render)
            this.update();
            this.renderCycle();

            // Force a maximum FPS of about 60
            Display.sync(60);
            // Let the CPU synchronize with the GPU if GPU is tagging behind
            Display.update();
        }

        // Destroy OpenGL (Display)
        this.destroy();
    }

    private void setupMatrices() {
        // Setup projection matrix
        projectionMatrix = new Matrix4f();
        float fieldOfView = 60f;
        float aspectRatio = (float) WIDTH / (float) HEIGHT;
        float near_plane = 0.1f;
        float far_plane = 100f;

        float y_scale = this.coTangent(this.degreesToRadians(fieldOfView / 2f));
        float x_scale = y_scale / aspectRatio;
        float frustum_length = far_plane - near_plane;

        // projectionMatrix.m00 = x_scale;
        // projectionMatrix.m11 = y_scale;
        // projectionMatrix.m22 = -((far_plane + near_plane) / frustum_length);
        // projectionMatrix.m23 = -1;
        // projectionMatrix.m32 = -((2 * near_plane * far_plane) /
        // frustum_length);
        // projectionMatrix.m33 = 0;

        projectionMatrix = createOrthoProjectionMatrix(0, WIDTH, 0, HEIGHT, -100, 100);

        // Matrix4 ortho = new Matrix4().setToOrtho2D(0, 0, WIDTH, HEIGHT, -1,
        // 1);
        // FloatBuffer buf = BufferUtils.createFloatBuffer(16);
        // buf.get(ortho.getValues());
        // buf.flip();
        // projectionMatrix.load(buf);

        // Setup view matrix
        viewMatrix = new Matrix4f();

        // Setup model matrix
        modelMatrix = new Matrix4f();

        // Create a FloatBuffer with the proper size to store our matrices later
        matrix44Buffer = BufferUtils.createFloatBuffer(16);
    }

    public void update() {
        Matrix4 result2 = new Matrix4().idt();
        modelMatrix2.idt();

        // -- Update matrices
        // Reset view and model matrices
        viewMatrix = new Matrix4f();
        modelMatrix = new Matrix4f();

        // Translate camera
        // Matrix4f.translate(cameraPos, viewMatrix, viewMatrix);

        // Scale, translate and rotate model
        Matrix4f.scale(modelScale, modelMatrix, modelMatrix);
        Matrix4f.translate(modelPos, modelMatrix, modelMatrix);
        Matrix4f.rotate(this.degreesToRadians(modelAngle.z), new Vector3f(0, 0, 1), modelMatrix, modelMatrix);
        Matrix4f.rotate(this.degreesToRadians(modelAngle.y), new Vector3f(0, 1, 0), modelMatrix, modelMatrix);
        Matrix4f.rotate(this.degreesToRadians(modelAngle.x), new Vector3f(1, 0, 0), modelMatrix, modelMatrix);

        // lwjgl result
        Matrix4f result = new Matrix4f();
        // Debugger.info("lwjgl proj:\n" + projectionMatrix.toString());
        Matrix4f.mul(projectionMatrix, viewMatrix, result);
        Matrix4f.mul(result, modelMatrix, result);

        // badlogic transforms
        modelMatrix2.scale(modelScale.x, modelScale.y, modelScale.z);
        modelMatrix2.translate(modelPos.x, modelPos.y, modelPos.z);
        modelMatrix2.rotate(1, 0, 0, modelAngle.x);
        modelMatrix2.rotate(0, 1, 0, modelAngle.y);
        modelMatrix2.rotate(0, 0, 1, modelAngle.z);

        result2 = projectMatrix2.cpy();
        result2 = result2.mul(viewMatrix2).mul(modelMatrix2);

        // Upload matrices to the uniform variables
        GL20.glUseProgram(DangineShaders.getTextureProgramId());

        // result.store(matrix44Buffer);
        matrix44Buffer.put(result2.val);
        matrix44Buffer.flip();
        GL20.glUniformMatrix4(textureTransformMatrixLocation, false, matrix44Buffer);

        // Upload matrices to the uniform variables
        GL20.glUseProgram(DangineShaders.getColorProgramId());

        // result.store(matrix44Buffer);
        matrix44Buffer.put(result2.val);
        matrix44Buffer.flip();
        GL20.glUniformMatrix4(transformMatrixLocation, false, matrix44Buffer);
        GL20.glUseProgram(0);

        // badlogic result
        Debugger.info("baglogic result:\n" + result2.toString());
    }

    public void renderCycle() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

        // dangineQuad.drawQuad();
        box.draw();
        picture.draw();
        drawString.draw();
        pictureCreated.draw();
        // dangineTexturedQuad.drawQuad();
    }

    public void destroy() {
        // Delete the shaders
        DangineShaders.destroyShaders();
        dangineQuad.destroyQuad();
        DangineOpenGL.destroyOpenGL();
    }

    private float coTangent(float angle) {
        return (float) (1f / Math.tan(angle));
    }

    private float degreesToRadians(float degrees) {
        return degrees * (float) (PI / 180d);
    }

    // Method to create and return a 2D orthographic projection matrix
    public static Matrix4f createOrthoProjectionMatrix(float left, float right, float top, float bottom, float near,
            float far) {
        Matrix4f m = new Matrix4f();

        m.m00 = 2.0f / (right - left);
        m.m01 = 0.0f;
        m.m02 = 0.0f;
        m.m03 = 0.0f;

        m.m10 = 0.0f;
        m.m11 = 2.0f / (top - bottom);
        m.m12 = 0.0f;
        m.m13 = 0.0f;

        m.m20 = 0.0f;
        m.m21 = 0.0f;
        m.m22 = -2.0f / (far - near);
        m.m23 = 0.0f;

        m.m30 = -(right + left) / (right - left);
        m.m31 = -(top + bottom) / (top - bottom);
        m.m32 = -(far + near) / (far - near);
        m.m33 = 1.0f;

        return m;
    }

}