package seleniumgridmanager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

public class SeleniumGridManager {

    private Process gridProcess;

    public void startGrid() throws IOException {

        String projectRoot = System.getProperty("user.dir");

        File batFile = Paths.get(projectRoot, "SeleniumGRID", "start-grid.bat").toFile();

        if (!batFile.exists()) {
            throw new RuntimeException("start-grid.bat not found at: " + batFile.getAbsolutePath());
        }

        ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", batFile.getAbsolutePath());
        builder.directory(batFile.getParentFile());
        builder.redirectErrorStream(true);

        gridProcess = builder.start();

        System.out.println("Grid triggered from: " + batFile.getAbsolutePath());

        try {
            Thread.sleep(8000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void stopGrid() {
        System.out.println("[SeleniumGridManager] Attempting to stop Selenium Grid...");

        // First, try to destroy the parent process
        if (gridProcess != null && gridProcess.isAlive()) {
            gridProcess.destroy();
            try {
                if (!gridProcess.waitFor(5, TimeUnit.SECONDS)) {
                    gridProcess.destroyForcibly();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("[SeleniumGridManager] Parent process destroyed.");
        }

        // Try to use stop-grid.bat script if it exists
        String projectRoot = System.getProperty("user.dir");
        File stopBatFile = Paths.get(projectRoot, "SeleniumGRID", "stop-grid.bat").toFile();

        if (stopBatFile.exists()) {
            try {
                System.out.println("[SeleniumGridManager] Using stop-grid.bat to stop Grid...");
                ProcessBuilder stopBuilder = new ProcessBuilder("cmd.exe", "/c", stopBatFile.getAbsolutePath());
                stopBuilder.directory(stopBatFile.getParentFile());
                stopBuilder.redirectErrorStream(true);
                Process stopProcess = stopBuilder.start();
                stopProcess.waitFor(5, TimeUnit.SECONDS);
                System.out.println("[SeleniumGridManager] stop-grid.bat executed successfully.");
                return;
            } catch (IOException | InterruptedException e) {
                System.err.println("[SeleniumGridManager] Error executing stop-grid.bat: " + e.getMessage());
            }
        }

        // Fallback: Kill all remaining Java processes running Selenium Grid
        // This ensures Hub and Node processes started by start-grid.bat are killed
        try {
            System.out.println("[SeleniumGridManager] Killing remaining Java processes running Selenium Grid...");

            // Kill any java process (taskkill will stop all java.exe instances)
            // Be careful: this will kill ALL java processes on the system
            ProcessBuilder killBuilder = new ProcessBuilder(
                "taskkill", "/F", "/IM", "java.exe", "/T"
            );
            Process killProcess = killBuilder.start();
            killProcess.waitFor(5, TimeUnit.SECONDS);

            System.out.println("[SeleniumGridManager] taskkill completed - Java processes terminated.");
        } catch (IOException | InterruptedException e) {
            System.err.println("[SeleniumGridManager] Error killing Java processes: " + e.getMessage());
        }

        // Give a moment for processes to fully terminate
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("[SeleniumGridManager] Selenium Grid shutdown sequence completed.");
    }
}

