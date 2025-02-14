# WAV Peak Generator

A Java utility that generates peak data from WAV audio files and provides a web-based waveform visualization tool.

## Features

- WAV file processing (supports both 8-bit and 16-bit audio)
- Peak detection with configurable window size (default: 10ms)
- JSON output for easy integration with UI applications
- Web-based waveform viewer
- Support for mono and stereo audio files

## Project Structure

```
java-wavsurf/
├── wavsurf/            # Java application
│   ├── src/           # Source files
│   ├── build.gradle   # Gradle build configuration
│   └── gradle/        # Gradle wrapper
├── viewer/            # Web viewer
│   └── index.html     # Waveform visualization interface
├── .gitignore        # Git ignore rules
└── README.md         # This file
```

## Prerequisites

- Java 11 or higher
- Modern web browser for viewing waveforms
- No additional dependencies required (Gradle wrapper is included)

## Building the Project

1. Clone the repository
2. Navigate to the project directory:
```bash
cd wavsurf
```
3. Build using Gradle wrapper:
```bash
# On Windows
gradlew.bat build

# On Linux/macOS
./gradlew build
```

The built JAR file will be available at `wavsurf/build/libs/wavsurf-1.0-SNAPSHOT.jar`

## Usage

### 1. Generating Peaks

Use the following command to process a WAV file and generate peak data:

```bash
java -jar wavsurf/build/libs/wavsurf-1.0-SNAPSHOT.jar <input.wav> <output.json>
```

Example:
```bash
java -jar wavsurf/build/libs/wavsurf-1.0-SNAPSHOT.jar audio.wav waveform.json
```

This will create a JSON file containing an array of peak values between 0 and 1.

### 2. Viewing the Waveform

1. Open `viewer/index.html` in a web browser
2. Click the "Choose File" button
3. Select the generated JSON file
4. The waveform will be displayed automatically

## Technical Details

### Peak Detection Algorithm

1. Reads WAV file in chunks
2. Converts audio samples to normalized float values (-1 to 1)
3. Takes absolute values for peak detection
4. Uses a 10ms window to find maximum peaks
5. Outputs normalized peak values (0 to 1)

### Supported WAV Formats

- Sample rates: Any
- Bit depth: 8-bit and 16-bit
- Channels: Mono and Stereo

### Output Format

The generated JSON file contains a simple array of floating-point values between 0 and 1, representing the audio peaks. Example:

```json
[0.1, 0.15, 0.8, 0.75, 0.3, ...]
```

## Troubleshooting

Common issues and solutions:

1. **"No main manifest attribute" error**
   - Make sure you're using the correct path to the JAR file
   - Rebuild the project using Gradle

2. **WAV file not recognized**
   - Ensure the file is a valid WAV format
   - Check file permissions

3. **Waveform not displaying**
   - Verify the JSON file is valid
   - Check browser console for errors
   - Try refreshing the page

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.
