import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Scanner;


public final class Main {

    /** Version of Updater */
    final static String Version = "0.1.0";

    /** Result if URLreader failed. */
    final static String fail = "URLreader failed";

    /** Operating system. */
    static String os;



    // ---------------

    /** What is run. */
    public static void main (String[] args) {

        System.out.println("[NecroUpdater] Necro Mods folder updater");

        version(); // Check the Updater's version.

        manageOS(); // Check the System's OS.

        decide(); // Run the proper updater based on the OS.



    }

    // ---------------

    /** MacOS file system updater. */
    static void MacOS () {

        // New command line process
        Process process;

        // -------------------------------

        // Mods directory path
        File modsDir = new File(System.getenv("HOME") + "/Library/Application Support/minecraft/mods");

        // Config directory path
        File configDir = new File(System.getenv("HOME") + "/Library/Application Support/minecraft/config");

        // -------------------------------


        System.out.println("[NecroUpdater] Pulling the mods repository...");
        try {

            process = Runtime.getRuntime().exec("git pull", null, modsDir);

            printResults(process);

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        System.out.println("[NecroUpdater] Pulled the mods repository.");



        System.out.println("[NecroUpdater] Pulling the config repository...");
        try {

            process = Runtime.getRuntime().exec("git pull", null, configDir);

            printResults(process);

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        System.out.println("[NecroUpdater] Pulled the config repository.");


        System.out.println("[NecroUpdater] Done!");


    }

    /** Windows file system updater. */
    static void Windows () {

        // New command line process
        Process process;

        // -------------------------------

        // Mods directory path
        File modsDir = new File(System.getenv("APPDATA") + "\\.minecraft\\mods");

        // Config directory path
        File configDir = new File(System.getenv("APPDATA") + "\\.minecraft\\config");


        // -------------------------------



        System.out.println("[NecroUpdater] Pulling the mods repository...");
        try {

            process = Runtime.getRuntime().exec("git pull", null, modsDir);

            printResults(process);

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        System.out.println("[NecroUpdater] Pulled the mods repository.");



        System.out.println("[NecroUpdater] Pulling the config repository...");
        try {

            process = Runtime.getRuntime().exec("git pull", null, configDir);

            printResults(process);

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        System.out.println("[NecroUpdater] Pulled the config repository.");


        System.out.println("[NecroUpdater] Done!");


    }

    /** Linux file system updater. */
    static void Linux () {

        // New command line process
        Process process;

        // -------------------------------

        // Mods directory path
        File modsDir = new File(System.getenv("HOME") + "/.minecraft/mods");

        // Config directory
        File configDir = new File(System.getenv("HOME") + "/.minecraft/config");

        // -------------------------------



        System.out.println("[NecroUpdater] Pulling the mods repository...");
        try {

            process = Runtime.getRuntime().exec("git pull", null, modsDir);

            printResults(process);

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        System.out.println("[NecroUpdater] Pulled the mods repository.");



        System.out.println("[NecroUpdater] Pulling the config repository...");
        try {

            process = Runtime.getRuntime().exec("git pull", null, configDir);

            printResults(process);

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        System.out.println("[NecroUpdater] Pulled the config repository.");


        System.out.println("[NecroUpdater] Done!");


    }

    // ---------------

    /** Read a URL. */
    static String check (String url) {
        String r;
        try {
            URL oracle = new URL(url);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(oracle.openStream()));

            StringBuilder e = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null)
                e.append(inputLine).append("\n");

            in.close();
            r = e.toString();
        } catch (Exception ignored) {
            r = fail;
        }
        return r;
    }

    /** Enforce the version of the updater. */
    static void version () {

        Scanner wait = new Scanner(System.in);

        String latestVersion = check("https://raw.githubusercontent.com/NecroClient/api/main/Updater.yml").replace("\n", "");

        System.out.println("[NecroUpdater] Current version: " + Version);
        System.out.println("[NecroUpdater] Latest version: " + latestVersion);

        if (latestVersion.equals(fail)) {
            System.out.println("[NecroUpdater] Cannot connect to the Necro Client API.\nMake sure you have actual internet.\nOr, you might just have an old version. Download a new version here: https://github.com/NecroClient/Updater/releases");
            wait.nextLine();
            System.exit(0);
        }
        if (!latestVersion.equals(Version)) {
            System.out.println("[NecroUpdater] You're on an old version of Necro Client Updater.\nDownload a new version here: https://github.com/NecroClient/Updater/releases");
            wait.nextLine();
            System.exit(0);
        } else {
            System.out.println("[NecroUpdater] Version check: Latest!");
        }

    }

    /** Check the OS. */
    static void manageOS () {
        os = System.getProperty("os.name");
        System.out.println("[NecroUpdater] OS: " + os);
    }

    /** Run the proper updater based on the operating system. */
    static void decide () {

        if (os.contains("Mac OS") || os.contains("MacOS")) {
            MacOS();
        } else if (os.contains("Windows")) {
            Windows();
        } else if (os.contains("Linux") || os.contains("Ubuntu")) {
            Linux();
        } else {
            System.out.println("\n[NecroUpdater] Your operating System, [" + os + "], is not supported.");
            System.out.print("[NecroUpdater] This might be caused by an old version of this updater. \n[NecroUpdater] You can check for a newer version here: ");
            System.out.println("https://github.com/NecroClient/Updater/releases/latest");
            System.out.print("[NecroUpdater] If you're already on the latest version, please file a bug report here: ");
            System.out.println("https://github.com/NecroClient/Updater/issues\n");
            Scanner wait = new Scanner(System.in);
            wait.nextLine();
            System.exit(0);
        }

    }

    /** Print the results of the command line process. */
    static void printResults (Process process) {

        try {

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("[NecroUpdater] Process Results: " + line);
            }

        } catch (Exception ignored) {}
    }

    /** Utility class <b>Main</b> cannot be initialized. */
    private Main () {

    }

}
