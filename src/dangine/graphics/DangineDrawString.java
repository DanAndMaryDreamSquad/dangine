package dangine.graphics;

import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.math.Matrix4;

import dangine.scenegraph.SceneGraphNode;

public class DangineDrawString {
    
    class CharacterMatrix {
        final Matrix4 transformation;
        final char character;
        final CharacterCoordinates coordinates;
        
        public CharacterMatrix(char character, Matrix4 parentMatrix, int index) {
            this.character = character;
            this.coordinates = DangineFont.getCoordinatesOfCharacter(character);
            Matrix4 copy = new Matrix4(parentMatrix.getValues());
            transformation = copy.mul(new Matrix4().setTranslation(1 * index, 0, 0));
        }

        public Matrix4 getTransformation() {
            return transformation;
        }

        public char getCharacter() {
            return character;
        }

        public CharacterCoordinates getCoordinates() {
            return coordinates;
        }
    }
    
    DangineTexturedQuad quad;
    SceneGraphNode node = new SceneGraphNode();
    List<CharacterMatrix> characers = new LinkedList<CharacterMatrix>();
    final String text;

    public DangineDrawString(String text) {
        quad = new DangineTexturedQuad(DangineFont.FONT_NAME);
        node.setScale(10, -10);
        node.setPosition(5, 5);
        this.text = text;
    }

    private void precomputeString(String text) {
        char [] charArray = text.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            characers.add(new CharacterMatrix(charArray[i], node.getMatrix(), i));
        }
    }
    
    public void update() {

    }

    public void draw() {
        precomputeString(text);
        for (CharacterMatrix cm : characers) {
            quad.updateTransformationMatrixOfShader(cm.getTransformation());
            quad.changeTextureCoordinates(cm.getCoordinates());
            quad.drawQuad();            
        }
//        quad.updateTransformationMatrixOfShader(node.getMatrix());
//        quad.drawQuad();            
    }

    public SceneGraphNode getNode() {
        return node;
    }

}
