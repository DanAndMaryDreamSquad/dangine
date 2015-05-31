package dangine.audio;

import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.SourceDataLine;

import com.jcraft.jogg.Packet;
import com.jcraft.jogg.Page;
import com.jcraft.jogg.StreamState;
import com.jcraft.jogg.SyncState;
import com.jcraft.jorbis.Block;
import com.jcraft.jorbis.Comment;
import com.jcraft.jorbis.DspState;
import com.jcraft.jorbis.Info;

import dangine.debugger.Debugger;

/**
 * All the variables needed to decode and play an ogg vorbis file
 * http://www.jcraft.com/jorbis/tutorial/Tutorial.html
 */
public class OggPlayerDTO {

    private InputStream inputStream = null;

    /*
     * We need a buffer, it's size, a count to know how many bytes we have read
     * and an index to keep track of where we are. This is standard networking
     * stuff used with read().
     */
    byte[] buffer = null;
    int bufferSize = 2048;
    int count = 0;
    int index = 0;

    /*
     * JOgg and JOrbis require fields for the converted buffer. This is a buffer
     * that is modified in regards to the number of audio channels. Naturally,
     * it will also need a size.
     */
    byte[] convertedBuffer;
    int convertedBufferSize;

    // The source data line onto which data can be written.
    private SourceDataLine outputLine = null;

    // A three-dimensional an array with PCM information.
    private float[][][] pcmInfo;

    // The index for the PCM information.
    private int[] pcmIndex;

    // Here are the four required JOgg objects...
    private Packet joggPacket = new Packet();
    private Page joggPage = new Page();
    private StreamState joggStreamState = new StreamState();
    private SyncState joggSyncState = new SyncState();

    // ... followed by the four required JOrbis objects.
    private DspState jorbisDspState = new DspState();
    private Block jorbisBlock = new Block(jorbisDspState);
    private Comment jorbisComment = new Comment();
    private Info jorbisInfo = new Info();

    /**
     * Initializes JOrbis. First, we initialize the <code>SyncState</code>
     * object. After that, we prepare the <code>SyncState</code> buffer. Then we
     * "initialize" our buffer, taking the data in <code>SyncState</code>.
     */
    public void initializeJOrbis() {
        count = 0;
        index = 0;
        outputLine = null;
        joggPacket = new Packet();
        joggPage = new Page();
        joggStreamState = new StreamState();
        joggSyncState = new SyncState();
        jorbisDspState = new DspState();
        jorbisBlock = new Block(jorbisDspState);
        jorbisComment = new Comment();
        jorbisInfo = new Info();
        Debugger.info("Initializing JOrbis.");

        // Initialize SyncState
        joggSyncState.init();

        // Prepare the to SyncState internal buffer
        joggSyncState.buffer(bufferSize);

        /*
         * Fill the buffer with the data from SyncState's internal buffer. Note
         * how the size of this new buffer is different from bufferSize.
         */
        buffer = joggSyncState.data;

        Debugger.info("Done initializing JOrbis.");
    }

    public void resetTrack() {
        count = 0;
        index = 0;
        joggSyncState.reset();
        joggStreamState.reset();
        try {
            inputStream.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public byte[] getBuffer() {
        return buffer;
    }

    public int getBufferSize() {
        return bufferSize;
    }

    public int getCount() {
        return count;
    }

    public int getIndex() {
        return index;
    }

    public byte[] getConvertedBuffer() {
        return convertedBuffer;
    }

    public int getConvertedBufferSize() {
        return convertedBufferSize;
    }

    public SourceDataLine getOutputLine() {
        return outputLine;
    }

    public float[][][] getPcmInfo() {
        return pcmInfo;
    }

    public int[] getPcmIndex() {
        return pcmIndex;
    }

    public Packet getJoggPacket() {
        return joggPacket;
    }

    public Page getJoggPage() {
        return joggPage;
    }

    public StreamState getJoggStreamState() {
        return joggStreamState;
    }

    public SyncState getJoggSyncState() {
        return joggSyncState;
    }

    public DspState getJorbisDspState() {
        return jorbisDspState;
    }

    public Block getJorbisBlock() {
        return jorbisBlock;
    }

    public Comment getJorbisComment() {
        return jorbisComment;
    }

    public Info getJorbisInfo() {
        return jorbisInfo;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setConvertedBufferSize(int convertedBufferSize) {
        this.convertedBufferSize = convertedBufferSize;
    }

    public void setConvertedBuffer(byte[] convertedBuffer) {
        this.convertedBuffer = convertedBuffer;
    }

    public void setOutputLine(SourceDataLine outputLine) {
        this.outputLine = outputLine;
    }

    public void setPcmInfo(float[][][] pcmInfo) {
        this.pcmInfo = pcmInfo;
    }

    public void setPcmIndex(int[] pcmIndex) {
        this.pcmIndex = pcmIndex;
    }

    public void setBuffer(byte[] buffer) {
        this.buffer = buffer;
    }
}
