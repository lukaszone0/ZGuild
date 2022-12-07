package com.gmail.lukaszone0.zguild.managers;

import com.gmail.lukaszone0.zguild.ZGuild;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class ConfigManager extends YamlConfiguration {
    private File file;
    public ConfigManager(Plugin plugin, String name) throws IOException, InvalidConfigurationException {
        this.file = new File(plugin.getDataFolder(), name);
        if (!this.file.exists())
            if (plugin.getResource(name) != null) {
                plugin.saveResource(name, false);
            } else {
                this.file.mkdirs();
                this.file.createNewFile();
            }
        load(this.file);
    }

    public void save() throws IOException {
        save(this.file);
    }

    public boolean trySave() {
        try {
            save();
            return true;
        } catch (IOException ex) {
            ZGuild.getInstance().getLogger().log(Level.SEVERE, "Couldn't save " + this.file.getName() + " to disk", ex);
            return false;
        }
    }
}
