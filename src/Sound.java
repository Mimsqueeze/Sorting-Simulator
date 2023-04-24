import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;

public class Sound {
    public static void makeSound(int pointer, int size) {
        try {
            byte[] buf = new byte[2];
            int frequency = 4000;
            AudioFormat af = new AudioFormat((float) frequency, 16, 1, true, false);
        
            SourceDataLine sdl = AudioSystem.getSourceDataLine(af);
            sdl.open();
            sdl.start();
    
            double level = ((double) pointer)/size;
            int offset = (int)(392*level);
            int pitch = 392 + offset;
    
            for (int j = 0; j < 25 * (float) frequency / 1000; j++) {
                float numberOfSamplesToRepresentFullSin= (float) frequency / pitch;
                double angle = j / (numberOfSamplesToRepresentFullSin/ 2.0) * Math.PI;
                short a = (short) (Math.sin(angle) * 32767);
                buf[0] = (byte) (a & 0xFF);
                buf[1] = (byte) (a >> 8);
                sdl.write(buf, 0, 2);
            }
            sdl.drain();
            sdl.stop();
        } catch (Exception e) {
            System.exit(69);
        }

    }
}
