package dangine.audio;

import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
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
 * All the ogg vorbis sound logic I don't really understand
 * http://www.jcraft.com/jorbis/tutorial/Tutorial.html
 */
public class OggPlayerCodec {

    /**
     * This method reads the header of the stream, which consists of three
     * packets.
     * 
     * @return true if the header was successfully read, false otherwise
     */
    public static boolean readHeader(OggPlayerDTO oggPlayerDTO) {
        InputStream inputStream = oggPlayerDTO.getInputStream();
        SyncState joggSyncState = oggPlayerDTO.getJoggSyncState();
        Page joggPage = oggPlayerDTO.getJoggPage();
        StreamState joggStreamState = oggPlayerDTO.getJoggStreamState();
        Info jorbisInfo = oggPlayerDTO.getJorbisInfo();
        Comment jorbisComment = oggPlayerDTO.getJorbisComment();
        Packet joggPacket = oggPlayerDTO.getJoggPacket();
        byte[] buffer = oggPlayerDTO.getBuffer();
        Debugger.info("Starting to read the header.");

        /*
         * Variable used in loops below. While we need more data, we will
         * continue to read from the InputStream.
         */
        boolean needMoreData = true;

        /*
         * We will read the first three packets of the header. We start off by
         * defining packet = 1 and increment that value whenever we have
         * successfully read another packet.
         */
        int packet = 1;

        /*
         * While we need more data (which we do until we have read the three
         * header packets), this loop reads from the stream and has a big
         * <code>switch</code> statement which does what it's supposed to do in
         * regards to the current packet.
         */
        while (needMoreData) {
            // Read from the InputStream.
            try {
                Debugger.info("input stream? " + inputStream.toString());
                Debugger.info("buffer? " + buffer.toString());
                int count = inputStream.read(buffer, oggPlayerDTO.getIndex(), oggPlayerDTO.getBufferSize());
                oggPlayerDTO.setCount(count);
            } catch (IOException exception) {
                System.err.println("Could not read from the input stream.");
                System.err.println(exception);
            }

            // We let SyncState know how many bytes we read.
            joggSyncState.wrote(oggPlayerDTO.getCount());

            /*
             * We want to read the first three packets. For the first packet, we
             * need to initialize the StreamState object and a couple of other
             * things. For packet two and three, the procedure is the same: we
             * take out a page, and then we take out the packet.
             */
            switch (packet) {
            // The first packet.
            case 1: {
                // We take out a page.
                switch (joggSyncState.pageout(joggPage)) {
                // If there is a hole in the data, we must exit.
                case -1: {
                    System.err.println("There is a hole in the first " + "packet data.");
                    return false;
                }

                // If we need more data, we break to get it.
                case 0: {
                    break;
                }

                /*
                 * We got where we wanted. We have successfully read the first
                 * packet, and we will now initialize and reset StreamState, and
                 * initialize the Info and Comment objects. Afterwards we will
                 * check that the page doesn't contain any errors, that the
                 * packet doesn't contain any errors and that it's Vorbis data.
                 */
                case 1: {
                    // Initializes and resets StreamState.
                    joggStreamState.init(joggPage.serialno());
                    joggStreamState.reset();

                    // Initializes the Info and Comment objects.
                    jorbisInfo.init();
                    jorbisComment.init();

                    // Check the page (serial number and stuff).
                    if (joggStreamState.pagein(joggPage) == -1) {
                        System.err.println("We got an error while " + "reading the first header page.");
                        return false;
                    }

                    /*
                     * Try to extract a packet. All other return values than "1"
                     * indicates there's something wrong.
                     */
                    if (joggStreamState.packetout(joggPacket) != 1) {
                        System.err.println("We got an error while " + "reading the first header packet.");
                        return false;
                    }

                    /*
                     * We give the packet to the Info object, so that it can
                     * extract the Comment-related information, among other
                     * things. If this fails, it's not Vorbis data.
                     */
                    if (jorbisInfo.synthesis_headerin(jorbisComment, joggPacket) < 0) {
                        System.err.println("We got an error while " + "interpreting the first packet. "
                                + "Apparantly, it's not Vorbis data.");
                        return false;
                    }

                    // We're done here, let's increment "packet".
                    packet++;
                    break;
                }
                }

                /*
                 * Note how we are NOT breaking here if we have proceeded to the
                 * second packet. We don't want to read from the input stream
                 * again if it's not necessary.
                 */
                if (packet == 1)
                    break;
            }

            // The code for the second and third packets follow.
            case 2:
            case 3: {
                // Try to get a new page again.
                switch (joggSyncState.pageout(joggPage)) {
                // If there is a hole in the data, we must exit.
                case -1: {
                    System.err.println("There is a hole in the second " + "or third packet data.");
                    return false;
                }

                // If we need more data, we break to get it.
                case 0: {
                    break;
                }

                /*
                 * Here is where we take the page, extract a packet and and (if
                 * everything goes well) give the information to the Info and
                 * Comment objects like we did above.
                 */
                case 1: {
                    // Share the page with the StreamState object.
                    joggStreamState.pagein(joggPage);

                    /*
                     * Just like the switch(...packetout...) lines above.
                     */
                    switch (joggStreamState.packetout(joggPacket)) {
                    // If there is a hole in the data, we must exit.
                    case -1: {
                        System.err.println("There is a hole in the first" + "packet data.");
                        return false;
                    }

                    // If we need more data, we break to get it.
                    case 0: {
                        break;
                    }

                    // We got a packet, let's process it.
                    case 1: {
                        /*
                         * Like above, we give the packet to the Info and
                         * Comment objects.
                         */
                        jorbisInfo.synthesis_headerin(jorbisComment, joggPacket);

                        // Increment packet.
                        packet++;

                        if (packet == 4) {
                            /*
                             * There is no fourth packet, so we will just end
                             * the loop here.
                             */
                            needMoreData = false;
                        }

                        break;
                    }
                    }

                    break;
                }
                }

                break;
            }
            }

            // We get the new index and an updated buffer.
            oggPlayerDTO.setIndex(joggSyncState.buffer(oggPlayerDTO.getBufferSize()));
            buffer = joggSyncState.data;

            /*
             * If we need more data but can't get it, the stream doesn't contain
             * enough information.
             */
            if (oggPlayerDTO.getCount() == 0 && needMoreData) {
                System.err.println("Not enough header data was supplied.");
                return false;
            }
        }

        Debugger.info("Finished reading the header.");

        return true;
    }

    /**
     * This method starts the sound system. It starts with initializing the
     * <code>DspState</code> object, after which it sets up the
     * <code>Block</code> object. Last but not least, it opens a line to the
     * source data line.
     * 
     * @return true if the sound system was successfully started, false
     *         otherwise
     */
    public static boolean initializeSound(OggPlayerDTO oggPlayerDTO) {
        Info jorbisInfo = oggPlayerDTO.getJorbisInfo();
        DspState jorbisDspState = oggPlayerDTO.getJorbisDspState();
        Block jorbisBlock = oggPlayerDTO.getJorbisBlock();
        Debugger.info("Starting to read the header.");
        Debugger.info("Initializing the sound system.");

        // This buffer is used by the decoding method.
        oggPlayerDTO.setConvertedBufferSize(oggPlayerDTO.getBufferSize() * 2);
        oggPlayerDTO.setConvertedBuffer(new byte[oggPlayerDTO.getConvertedBufferSize()]);

        // Initializes the DSP synthesis.
        jorbisDspState.synthesis_init(jorbisInfo);

        // Make the Block object aware of the DSP.
        jorbisBlock.init(jorbisDspState);

        // Wee need to know the channels and rate.
        int channels = jorbisInfo.channels;
        int rate = jorbisInfo.rate;

        // Creates an AudioFormat object and a DataLine.Info object.
        AudioFormat audioFormat = new AudioFormat((float) rate, 16, channels, true, false);
        DataLine.Info datalineInfo = new DataLine.Info(SourceDataLine.class, audioFormat, AudioSystem.NOT_SPECIFIED);

        // Check if the line is supported.
        if (!AudioSystem.isLineSupported(datalineInfo)) {
            System.err.println("Audio output line is not supported.");
            return false;
        }

        /*
         * Everything seems to be alright. Let's try to open a line with the
         * specified format and start the source data line.
         */
        try {
            oggPlayerDTO.setOutputLine((SourceDataLine) AudioSystem.getLine(datalineInfo));
            oggPlayerDTO.getOutputLine().open(audioFormat);
        } catch (LineUnavailableException exception) {
            System.out.println("The audio output line could not be opened due " + "to resource restrictions.");
            System.err.println(exception);
            return false;
        } catch (IllegalStateException exception) {
            System.out.println("The audio output line is already open.");
            System.err.println(exception);
            return false;
        } catch (SecurityException exception) {
            System.out.println("The audio output line could not be opened due " + "to security restrictions.");
            System.err.println(exception);
            return false;
        }

        // Start it.
        oggPlayerDTO.getOutputLine().start();

        /*
         * We create the PCM variables. The index is an array with the same
         * length as the number of audio channels.
         */
        oggPlayerDTO.setPcmInfo(new float[1][][]);
        oggPlayerDTO.setPcmIndex(new int[jorbisInfo.channels]);

        Debugger.info("Done initializing the sound system.");

        return true;
    }

    /**
     * This method reads a part of the stream body. Whenever it extracts a
     * packet, it will decode it by calling <code>decodeCurrentPacket()</code>.
     */
    public static void readBody(OggPlayerDTO oggPlayerDTO) {
        InputStream inputStream = oggPlayerDTO.getInputStream();
        SyncState joggSyncState = oggPlayerDTO.getJoggSyncState();
        Page joggPage = oggPlayerDTO.getJoggPage();
        StreamState joggStreamState = oggPlayerDTO.getJoggStreamState();
        Packet joggPacket = oggPlayerDTO.getJoggPacket();
        // Debugger.info("Reading the body.");

        /*
         * Variable used in loops below, like in readHeader(). While we need
         * more data, we will continue to read from the InputStream.
         */
        boolean needMoreData = true;

        while (needMoreData) {
            switch (joggSyncState.pageout(joggPage)) {
            // If there is a hole in the data, we just proceed.
            case -1: {
                Debugger.info("There is a hole in the data. We proceed.");
            }

            // If we need more data, we break to get it.
            case 0: {
                break;
            }

            // If we have successfully checked out a page, we continue.
            case 1: {
                // Give the page to the StreamState object.
                joggStreamState.pagein(joggPage);

                // If granulepos() returns "0", we don't need more data.
                if (joggPage.granulepos() == 0) {
                    needMoreData = false;
                    break;
                }

                // Here is where we process the packets.
                processPackets: while (true) {
                    switch (joggStreamState.packetout(joggPacket)) {
                    // Is it a hole in the data?
                    case -1: {
                        Debugger.info("There is a hole in the data, we " + "continue though.");
                    }

                    // If we need more data, we break to get it.
                    case 0: {
                        break processPackets;
                    }

                    /*
                     * If we have the data we need, we decode the packet.
                     */
                    case 1: {
                        OggPlayerCodec.decodeCurrentPacket(oggPlayerDTO);
                    }
                    }
                }

                /*
                 * If the page is the end-of-stream, we don't need more data.
                 */
                if (joggPage.eos() != 0) {
                    needMoreData = false;
                }
            }
            }

            // If we need more data...
            if (needMoreData) {
                // We get the new index and an updated buffer.
                oggPlayerDTO.setIndex(joggSyncState.buffer(oggPlayerDTO.getBufferSize()));
                oggPlayerDTO.setBuffer(joggSyncState.data);

                // Read from the InputStream.
                try {
                    oggPlayerDTO.setCount(inputStream.read(oggPlayerDTO.getBuffer(), oggPlayerDTO.getIndex(),
                            oggPlayerDTO.getBufferSize()));
                } catch (Exception e) {
                    System.err.println(e);
                    return;
                }

                // We let SyncState know how many bytes we read.
                joggSyncState.wrote(oggPlayerDTO.getCount());

                // There's no more data in the stream.
                if (oggPlayerDTO.getCount() == 0) {
                    needMoreData = false;
                }

                return;
            }
        }
        Debugger.info("Done reading the body.");
    }

    /**
     * Decodes the current packet and sends it to the audio output line.
     */
    private static void decodeCurrentPacket(OggPlayerDTO oggPlayerDTO) {
        Info jorbisInfo = oggPlayerDTO.getJorbisInfo();
        Packet joggPacket = oggPlayerDTO.getJoggPacket();
        DspState jorbisDspState = oggPlayerDTO.getJorbisDspState();
        Block jorbisBlock = oggPlayerDTO.getJorbisBlock();
        int convertedBufferSize = oggPlayerDTO.getConvertedBufferSize();
        float[][][] pcmInfo = oggPlayerDTO.getPcmInfo();
        int[] pcmIndex = oggPlayerDTO.getPcmIndex();
        byte[] convertedBuffer = oggPlayerDTO.getConvertedBuffer();
        SourceDataLine outputLine = oggPlayerDTO.getOutputLine();

        int samples;

        // Check that the packet is a audio data packet etc.
        if (jorbisBlock.synthesis(joggPacket) == 0) {
            // Give the block to the DspState object.
            jorbisDspState.synthesis_blockin(jorbisBlock);
        }

        // We need to know how many samples to process.
        int range;

        /*
         * Get the PCM information and count the samples. And while these
         * samples are more than zero...
         */
        while ((samples = jorbisDspState.synthesis_pcmout(pcmInfo, pcmIndex)) > 0) {
            // We need to know for how many samples we are going to process.
            if (samples < convertedBufferSize) {
                range = samples;
            } else {
                range = convertedBufferSize;
            }

            // For each channel...
            for (int i = 0; i < jorbisInfo.channels; i++) {
                int sampleIndex = i * 2;

                // For every sample in our range...
                for (int j = 0; j < range; j++) {
                    /*
                     * Get the PCM value for the channel at the correct
                     * position.
                     */
                    int value = (int) (pcmInfo[0][i][pcmIndex[i] + j] * 32767);

                    /*
                     * We make sure our value doesn't exceed or falls below
                     * +-32767.
                     */
                    if (value > 32767) {
                        value = 32767;
                    }
                    if (value < -32768) {
                        value = -32768;
                    }

                    /*
                     * It the value is less than zero, we bitwise-or it with
                     * 32768 (which is 1000000000000000 = 10^15).
                     */
                    if (value < 0)
                        value = value | 32768;

                    /*
                     * Take our value and split it into two, one with the last
                     * byte and one with the first byte.
                     */
                    convertedBuffer[sampleIndex] = (byte) (value);
                    convertedBuffer[sampleIndex + 1] = (byte) (value >>> 8);

                    /*
                     * Move the sample index forward by two (since that's how
                     * many values we get at once) times the number of channels.
                     */
                    sampleIndex += 2 * (jorbisInfo.channels);
                }
            }

            // Write the buffer to the audio output line.
            outputLine.write(convertedBuffer, 0, 2 * jorbisInfo.channels * range);

            // Update the DspState object.
            jorbisDspState.synthesis_read(range);
        }
    }

    /**
     * A clean-up method, called when everything is finished. Clears the
     * JOgg/JOrbis objects and closes the <code>InputStream</code>.
     */
    public static void cleanUp(OggPlayerDTO oggPlayerDTO) {
        InputStream inputStream = oggPlayerDTO.getInputStream();
        SyncState joggSyncState = oggPlayerDTO.getJoggSyncState();
        StreamState joggStreamState = oggPlayerDTO.getJoggStreamState();
        Info jorbisInfo = oggPlayerDTO.getJorbisInfo();
        DspState jorbisDspState = oggPlayerDTO.getJorbisDspState();
        Block jorbisBlock = oggPlayerDTO.getJorbisBlock();
        Debugger.info("Cleaning up JOrbis.");

        // Clear the necessary JOgg/JOrbis objects.
        joggStreamState.clear();
        jorbisBlock.clear();
        jorbisDspState.clear();
        jorbisInfo.clear();
        joggSyncState.clear();

        // Closes the stream.
        try {
            if (inputStream != null)
                inputStream.close();
        } catch (Exception e) {
        }

        Debugger.info("Done cleaning up JOrbis.");
    }
}