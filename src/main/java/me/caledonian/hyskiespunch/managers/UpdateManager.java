package me.caledonian.hyskiespunch.managers;

import me.caledonian.hyskiespunch.HyskiesPunch;
import me.caledonian.hyskiespunch.utils.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import java.util.function.Consumer;

public class UpdateManager {
    private HyskiesPunch plugin;
    public UpdateManager(HyskiesPunch plugin){this.plugin = plugin;}

    private int resourceId;

    public UpdateManager(HyskiesPunch plugin, int resourceId){
        this.plugin = plugin;
        this.resourceId = resourceId;
    }

    public void getLatestVersion(Consumer<String> consumer){
        try(InputStream inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + this.resourceId).openStream(); Scanner scanner = new Scanner(inputStream)) {
            if (scanner.hasNext()) {
                consumer.accept(scanner.next());
            }
        } catch (IOException exception) {
            Logger.log(Logger.LogLevel.ERROR, "HyskiesPunch could not get access updates. Reason: " + exception.getMessage());
        }
    }
}
