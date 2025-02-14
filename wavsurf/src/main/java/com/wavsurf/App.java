package com.wavsurf;

import java.io.*;
import javax.sound.sampled.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java -jar wavsurf.jar <input.wav> <output.json>");
            System.exit(1);
        }

        String inputFile = args[0];
        String outputFile = args[1];

        try {
            List<Float> peaks = generatePeaks(inputFile);
            exportToJson(peaks, outputFile);
            System.out.println("Successfully generated peaks and saved to " + outputFile);
        } catch (Exception e) {
            System.err.println("Error processing file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static List<Float> generatePeaks(String wavFile) throws UnsupportedAudioFileException, IOException {
        File file = new File(wavFile);
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
        AudioFormat format = audioInputStream.getFormat();
        
        // Get audio format details
        int frameSize = format.getFrameSize();
        float sampleRate = format.getSampleRate();
        int channels = format.getChannels();
        
        // Read the entire audio file
        byte[] audioData = audioInputStream.readAllBytes();
        audioInputStream.close();
        
        // Convert bytes to float samples
        List<Float> samples = new ArrayList<>();
        int samplesCount = audioData.length / (frameSize / channels);
        
        for (int i = 0; i < audioData.length; i += frameSize) {
            float sample = 0;
            // Convert bytes to float sample based on frame size
            if (frameSize == 2) { // 16-bit audio
                sample = (float) ((audioData[i + 1] << 8) | (audioData[i] & 0xFF)) / 32768.0f;
            } else if (frameSize == 1) { // 8-bit audio
                sample = (float) (audioData[i] & 0xFF) / 128.0f - 1.0f;
            }
            samples.add(Math.abs(sample)); // Store absolute value for peak detection
        }
        
        // Generate peaks by averaging samples in windows
        int windowSize = (int) (sampleRate / 100); // 10ms window
        List<Float> peaks = new ArrayList<>();
        
        for (int i = 0; i < samples.size(); i += windowSize) {
            float maxPeak = 0;
            int end = Math.min(i + windowSize, samples.size());
            
            for (int j = i; j < end; j++) {
                maxPeak = Math.max(maxPeak, samples.get(j));
            }
            
            peaks.add(maxPeak);
        }
        
        return peaks;
    }

    private static void exportToJson(List<Float> peaks, String outputFile) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File(outputFile), peaks);
    }
}
