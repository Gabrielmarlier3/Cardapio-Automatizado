import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class EnvLoader {
    private static Map<String, String> envVariables = new HashMap<>();

    static {
        try {
            // Lê o arquivo .env linha por linha
            Files.lines(Paths.get(".env")).forEach(line -> {
                String[] parts = line.split("=", 2);
                if (parts.length == 2) {
                    envVariables.put(parts[0].trim(), parts[1].trim());
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getEnv(String key) {
        return envVariables.get(key);
    }
}
