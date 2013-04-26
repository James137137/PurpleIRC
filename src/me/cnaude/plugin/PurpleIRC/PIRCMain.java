package me.cnaude.plugin.PurpleIRC;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jibble.pircbot.Colors;

/**
 *
 * @author Chris Naudé
 */
public class PIRCMain extends JavaPlugin {
    
    public static String LOG_HEADER;
    static final Logger log = Logger.getLogger("Minecraft");
    private File pluginFolder;
    private File botsFolder;
    private File configFile; 
    public static long startTime;
    public String minecraftMsg;
    public String ircMsg;
    
    private boolean debugEnabled;
    
    public HashMap<String,PIRCBot> ircBots = new HashMap<String,PIRCBot>();

    @Override
    public void onEnable() {
        LOG_HEADER = "[" + this.getName() + "]";
        pluginFolder = getDataFolder();
        botsFolder = new File (pluginFolder + "/bots");
        configFile = new File(pluginFolder, "config.yml");
        createConfig();
        getConfig().options().copyDefaults(true);
        saveConfig();
        loadConfig();
        getServer().getPluginManager().registerEvents(new PIRCListener(this), this);
        getCommand("irc").setExecutor(new PIRCCommands(this));     
        
        loadBots();        
    }
    
    @Override
    public void onDisable() {
        if (ircBots.isEmpty()) {
            logInfo("No IRC bots to disconnect.");
        } else {
            logInfo("Disconnecting IRC bots.");
            for (PIRCBot ircBot : ircBots.values()) {
                ircBot.quit();
            }
        }
    }    
    
    private void loadConfig() {
        debugEnabled = getConfig().getBoolean("Debug");
        minecraftMsg = ChatColor.translateAlternateColorCodes('&', getConfig().getString("message-format.minecraft"));
        ircMsg = ChatColor.translateAlternateColorCodes('&', getConfig().getString("message-format.minecraft"));
        logDebug("Debug enabled");
    }
    
    private void loadBots() {
        if (botsFolder.exists()) {
            logInfo("Checking for bot files in " +  botsFolder);
            for (File file : botsFolder.listFiles()) {
                if (file.getName().endsWith("bot")) {
                    logInfo("Loading bot: " + file.getName());
                    //ircBots.put(file.getName().replace(".bot",""), new PIRCBot(file,this));
                    PIRCBot pircBot = new PIRCBot(file,this);
                }
            }
        }
    }
    
    private void createConfig() {
        if (!pluginFolder.exists()) {
            try {
                pluginFolder.mkdir();
            } catch (Exception e) {
                logError(e.getMessage());
            }
        }

        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
            } catch (Exception e) {
                logError(e.getMessage());
            }
        }
        
        if (!botsFolder.exists()) {
            try {
                botsFolder.mkdir();
            } catch (Exception e) {
                logError(e.getMessage());
            }
        }
    }
    
    public void logInfo(String _message) {
        log.log(Level.INFO, String.format("%s %s", LOG_HEADER, _message));
    }

    public void logError(String _message) {
        log.log(Level.SEVERE, String.format("%s %s", LOG_HEADER, _message));
    }

    public void logDebug(String _message) {
        if (debugEnabled) {
            log.log(Level.INFO, String.format("%s [DEBUG] %s", LOG_HEADER, _message));
        }
    }
    
    public String getMCUptime() {
        long jvmUptime = ManagementFactory.getRuntimeMXBean().getUptime();
        String msg = "Server uptime: " + (int)(jvmUptime / 86400000L) + " days" 
                + " " + (int)(jvmUptime / 3600000L % 24L) + " hours" 
                + " " + (int)(jvmUptime / 60000L % 60L) + " minutes" 
                + " " + (int)(jvmUptime / 1000L % 60L) + " seconds.";
        return msg;
    }
    
    public String getMCPlayers() {
        String msg = "Players currently online("
                + getServer().getOnlinePlayers().length
                + "/" + getServer().getMaxPlayers() + "): ";
        for (Player p : getServer().getOnlinePlayers()) {
            msg = msg + p.getName() + ", ";
        }
        msg = msg.substring(0, msg.length() - 1);
        return msg;
    }
    
    public String getIRCColor(ChatColor color) {
        if (color.equals(ChatColor.AQUA)) {
            return Colors.CYAN;
        }
        if (color.equals(ChatColor.BLACK)) {
            return Colors.BLACK;
        }
        if (color.equals(ChatColor.BLUE)) {
            return Colors.BLUE;
        }
        if (color.equals(ChatColor.BOLD)) {
            return Colors.BOLD;
        }
        if (color.equals(ChatColor.DARK_AQUA)) {
            return Colors.TEAL;
        }
        if (color.equals(ChatColor.DARK_BLUE)) {
            return Colors.DARK_BLUE;
        }
        if (color.equals(ChatColor.DARK_GRAY)) {
            return Colors.DARK_GRAY;
        }
        if (color.equals(ChatColor.DARK_GREEN)) {
            return Colors.DARK_GREEN;
        }
        if (color.equals(ChatColor.DARK_PURPLE)) {
            return Colors.PURPLE;
        }
        if (color.equals(ChatColor.DARK_RED)) {
            return Colors.RED;
        }
        if (color.equals(ChatColor.GOLD)) {
            return Colors.YELLOW;
        }
        if (color.equals(ChatColor.GRAY)) {
            return Colors.DARK_GRAY;
        }
        if (color.equals(ChatColor.GREEN)) {
            return Colors.GREEN;
        }        
        if (color.equals(ChatColor.LIGHT_PURPLE)) {
            return Colors.MAGENTA;
        }
        if (color.equals(ChatColor.RED)) {
            return Colors.RED;
        }
        if (color.equals(ChatColor.YELLOW)) {
            return Colors.YELLOW;
        }
        if (color.equals(ChatColor.WHITE)) {
            return Colors.WHITE;
        }
        return Colors.NORMAL;
    }
}
