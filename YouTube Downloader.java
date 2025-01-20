import javax.swing.JOptionPane;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class YouTubeDownloader {
    public static void main(String[] args) {
        // Prompt the user to enter a YouTube URL
        String videoUrl = JOptionPane.showInputDialog(null, "Enter the YouTube video URL:", "YouTube Downloader", JOptionPane.QUESTION_MESSAGE);

        if (videoUrl == null || videoUrl.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No URL provided. Exiting.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Ask where to save the video
        String savePath = JOptionPane.showInputDialog(null, "Enter the directory where you want to save the video (e.g., C:\\Downloads):", "Save Path", JOptionPane.QUESTION_MESSAGE);

        if (savePath == null || savePath.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No save path provided. Exiting.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Command to download the YouTube video using yt-dlp
        String command = "yt-dlp -o \"" + savePath + "/%(title)s.%(ext)s\" " + videoUrl;

        try {
            // Execute the command
            ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", command);
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            // Read the output of the command
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            StringBuilder output = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            process.waitFor();
            reader.close();

            // Show a success message
            JOptionPane.showMessageDialog(null, "Download completed successfully!\n" + output, "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            // Show an error message if something goes wrong
            JOptionPane.showMessageDialog(null, "An error occurred while downloading the video:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
