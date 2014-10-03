package com.cnaude.purpleirc.Utilities;

import com.cnaude.purpleirc.PurpleBot;
import com.cnaude.purpleirc.PurpleIRC;
import com.dthielke.herochat.ChannelManager;
import com.gmail.nossr50.util.player.UserManager;
import com.nyancraft.reportrts.data.Ticket;
import com.palmergames.bukkit.TownyChat.channels.Channel;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.pircbotx.User;

/**
 * Main class containing all message template token expanding methods
 *
 * @author cnaude
 */
public class ChatTokenizer {

    PurpleIRC plugin;

    /**
     * Class initializer
     *
     * @param plugin
     */
    public ChatTokenizer(PurpleIRC plugin) {
        this.plugin = plugin;
    }

    /**
     * IRC to game chat tokenizer without a message
     *
     * @param ircBot
     * @param user
     * @param channel
     * @param template
     * @return
     */
    public String chatIRCTokenizer(PurpleBot ircBot, User user, org.pircbotx.Channel channel, String template) {
        return plugin.colorConverter.ircColorsToGame(template
                .replace("%NAME%", user.getNick())
                .replace("%HOST%", user.getHostmask())
                .replace("%SERVER%", user.getServer())
                .replace("%AWAY%", user.getAwayMessage())
                .replace("%NICKPREFIX%", ircBot.getNickPrefix(user, channel))
                .replace("%CHANNEL%", channel.getName()));
    }

    /**
     * IRC to Hero chat tokenizer without a message
     *
     * @param ircBot
     * @param user
     * @param channel
     * @param template
     * @param channelManager
     * @param hChannel
     * @return
     */
    public String ircChatToHeroChatTokenizer(PurpleBot ircBot, User user, org.pircbotx.Channel channel, String template, ChannelManager channelManager, String hChannel) {
        String ircNick = user.getNick();
        String tmpl;
        Player player = this.getPlayer(ircNick);
        if (player != null) {
            plugin.logDebug("ircChatToHeroChatTokenizer: player not null ");
            tmpl = playerTokenizer(player, template);
        } else {
            tmpl = playerTokenizer(ircNick, template);
        }
        return plugin.colorConverter.ircColorsToGame(tmpl
                .replace("%HEROCHANNEL%", hChannel)
                .replace("%HERONICK%", channelManager.getChannel(hChannel).getNick())
                .replace("%HEROCOLOR%", channelManager.getChannel(hChannel).getColor().toString())
                .replace("%NAME%", ircNick)
                .replace("%HOST%", user.getHostmask())
                .replace("%SERVER%", user.getServer())
                .replace("%AWAY%", user.getAwayMessage())
                .replace("%NICKPREFIX%", ircBot.getNickPrefix(user, channel))
                .replace("%CHANNEL%", channel.getName()));
    }

    /**
     * Normal IRC to game chat tokenizer
     *
     * @param ircBot
     * @param user
     * @param channel
     * @param template
     * @param message
     * @return
     */
    public String ircChatToGameTokenizer(PurpleBot ircBot, User user, org.pircbotx.Channel channel, String template, String message) {
        String ircNick = user.getNick();
        String tmpl;
        Player player = this.getPlayer(ircNick);
        if (player != null) {
            tmpl = playerTokenizer(player, template);
        } else {
            plugin.logDebug("ircChatToGameTokenizer: null player: " + ircNick);
            tmpl = playerTokenizer(ircNick, template);
        }
        return plugin.colorConverter.ircColorsToGame(tmpl
                .replace("%NAME%", ircNick)
                .replace("%HOST%", user.getHostmask())
                .replace("%SERVER%", user.getServer())
                .replace("%AWAY%", user.getAwayMessage())
                .replace("%NICKPREFIX%", ircBot.getNickPrefix(user, channel))
                .replace("%MESSAGE%", message)
                .replace("%CHANNEL%", channel.getName()));
    }

    /**
     * IRC to Hero chat channel tokenizer
     *
     * @param ircBot
     * @param user
     * @param channel
     * @param template
     * @param message
     * @param channelManager
     * @param hChannel
     * @return
     */
    public String ircChatToHeroChatTokenizer(PurpleBot ircBot, User user, org.pircbotx.Channel channel, String template, String message, ChannelManager channelManager, String hChannel) {
        String ircNick = user.getNick();
        String tmpl;
        Player player = this.getPlayer(ircNick);
        if (player != null) {
            tmpl = playerTokenizer(player, template);
        } else {
            tmpl = playerTokenizer(ircNick, template);
        }
        return plugin.colorConverter.ircColorsToGame(tmpl
                .replace("%HEROCHANNEL%", hChannel)
                .replace("%HERONICK%", channelManager.getChannel(hChannel).getNick())
                .replace("%HEROCOLOR%", channelManager.getChannel(hChannel).getColor().toString())
                .replace("%NAME%", ircNick)
                .replace("%HOST%", user.getHostmask())
                .replace("%SERVER%", user.getServer())
                .replace("%AWAY%", user.getAwayMessage())
                .replace("%NICKPREFIX%", ircBot.getNickPrefix(user, channel))
                .replace("%MESSAGE%", message)
                .replace("%CHANNEL%", channel.getName()));
    }

    /**
     * IRC to Hero chat channel tokenizer
     *
     * @param ircBot
     * @param user
     * @param channel
     * @param template
     * @param message
     * @param tChannel
     * @return
     */
    public String ircChatToTownyChatTokenizer(PurpleBot ircBot, User user, org.pircbotx.Channel channel, String template, String message, String tChannel) {
        String ircNick = user.getNick();
        String tmpl;
        Player player = this.getPlayer(ircNick);
        if (player != null) {
            tmpl = playerTokenizer(player, template);
        } else {
            tmpl = playerTokenizer(ircNick, template);
        }
        return plugin.colorConverter.ircColorsToGame(tmpl
                .replace("%TOWNYCHANNEL%", tChannel)
                .replace("%NAME%", ircNick)
                .replace("%HOST%", user.getHostmask())
                .replace("%SERVER%", user.getServer())
                .replace("%AWAY%", user.getAwayMessage())
                .replace("%NICKPREFIX%", ircBot.getNickPrefix(user, channel))
                .replace("%MESSAGE%", message)
                .replace("%CHANNEL%", channel.getName()));
    }

    /**
     * IRC kick message to game
     *
     * @param ircBot
     * @param recipient
     * @param kicker
     * @param reason
     * @param channel
     * @param template
     * @return
     */
    public String ircKickTokenizer(PurpleBot ircBot, User recipient, User kicker, String reason, org.pircbotx.Channel channel, String template) {
        return plugin.colorConverter.ircColorsToGame(template
                .replace("%NAME%", recipient.getNick())
                .replace("%HOST%", recipient.getHostmask())
                .replace("%SERVER%", recipient.getServer())
                .replace("%AWAY%", recipient.getAwayMessage())
                .replace("%NICKPREFIX%", ircBot.getNickPrefix(kicker, channel))
                .replace("%REASON%", reason)
                .replace("%KICKER%", kicker.getNick())
                .replace("%KICKERHOST%", kicker.getHostmask())
                .replace("%KICKERSERVER%", kicker.getServer())
                .replace("%KICKERAWAY%", kicker.getAwayMessage())
                .replace("%CHANNEL%", channel.getName()));
    }

    /**
     * IRC to hero kick message
     *
     * @param ircBot
     * @param recipient
     * @param kicker
     * @param reason
     * @param channel
     * @param template
     * @param channelManager
     * @param hChannel
     * @return
     */
    public String ircKickToHeroChatTokenizer(PurpleBot ircBot, User recipient, User kicker, String reason, org.pircbotx.Channel channel, String template, ChannelManager channelManager, String hChannel) {
        return plugin.colorConverter.ircColorsToGame(template
                .replace("%HEROCHANNEL%", hChannel)
                .replace("%HERONICK%", channelManager.getChannel(hChannel).getNick())
                .replace("%HEROCOLOR%", channelManager.getChannel(hChannel).getColor().toString())
                .replace("%NAME%", recipient.getNick())
                .replace("%HOST%", recipient.getHostmask())
                .replace("%SERVER%", recipient.getServer())
                .replace("%AWAY%", recipient.getAwayMessage())
                .replace("%NICKPREFIX%", ircBot.getNickPrefix(kicker, channel))
                .replace("%REASON%", reason)
                .replace("%KICKER%", kicker.getNick())
                .replace("%KICKERHOST%", kicker.getHostmask())
                .replace("%KICKERSERVER%", kicker.getServer())
                .replace("%KICKERAWAY%", kicker.getAwayMessage())
                .replace("%CHANNEL%", channel.getName()));
    }

    /**
     * IRC mode change messages
     *
     * @param ircBot
     * @param user
     * @param mode
     * @param channel
     * @param template
     * @return
     */
    public String ircModeTokenizer(PurpleBot ircBot, User user, String mode, org.pircbotx.Channel channel, String template) {
        return plugin.colorConverter.ircColorsToGame(template
                .replace("%NAME%", user.getNick())
                .replace("%HOST%", user.getHostmask())
                .replace("%SERVER%", user.getServer())
                .replace("%AWAY%", user.getAwayMessage())
                .replace("%MODE%", mode)
                .replace("%NICKPREFIX%", ircBot.getNickPrefix(user, channel))
                .replace("%CHANNEL%", channel.getName()));
    }

    /**
     * IRC notice change messages
     *
     * @param ircBot
     * @param user
     * @param message
     * @param notice
     * @param channel
     * @param template
     * @return
     */
    public String ircNoticeTokenizer(PurpleBot ircBot, User user, String message, String notice, org.pircbotx.Channel channel, String template) {
        return plugin.colorConverter.ircColorsToGame(template
                .replace("%NAME%", user.getNick())
                .replace("%HOST%", user.getHostmask())
                .replace("%SERVER%", user.getServer())
                .replace("%AWAY%", user.getAwayMessage())
                .replace("%NICKPREFIX%", ircBot.getNickPrefix(user, channel))
                .replace("%MESSAGE%", message)
                .replace("%NOTICE%", notice)
                .replace("%CHANNEL%", channel.getName()));
    }

    /**
     * Game chat to IRC
     *
     * @param pName
     * @param template
     *
     * @param message
     * @return
     */
    public String gameChatToIRCTokenizer(String pName, String template, String message) {
        return plugin.colorConverter.gameColorsToIrc(template
                .replace("%NAME%", pName)
                .replace("%MESSAGE%", plugin.colorConverter.gameColorsToIrc(message)));
    }

    /**
     * Game chat to IRC
     *
     * @param player
     * @param template
     *
     * @param message
     * @return
     */
    public String gameChatToIRCTokenizer(Player player, String template, String message) {
        if (message == null) {
            message = "";
        }
        return plugin.colorConverter.gameColorsToIrc(playerTokenizer(player, template).replace("%MESSAGE%", message));
    }

    /**
     * Game chat to IRC
     *
     * @param source
     * @param name
     * @param template
     * @param message
     * @return
     */
    public String dynmapWebChatToIRCTokenizer(String source, String name,
            String template, String message) {
        if (message == null) {
            message = "";
        }

        return plugin.colorConverter.gameColorsToIrc(
                playerTokenizer(name, template)
                .replace("%SOURCE%", source)
                .replace("%MESSAGE%", message));
    }

    /**
     * Game player AFK to IRC
     *
     * @param player
     * @param template
     *
     * @return
     */
    public String gamePlayerAFKTokenizer(Player player, String template) {
        return plugin.colorConverter.gameColorsToIrc(playerTokenizer(player, template));
    }

    /**
     * mcMMO chat to IRC
     *
     * @param player
     * @param template
     *
     * @param message
     * @param partyName
     * @return
     */
    public String mcMMOPartyChatToIRCTokenizer(Player player, String template, String message, String partyName) {
        return mcMMOChatToIRCTokenizer(player, template, message)
                .replace("%PARTY%", partyName);
    }

    /**
     * mcMMO chat to IRC
     *
     * @param player
     * @param template
     * @param message
     * @return
     */
    public String mcMMOChatToIRCTokenizer(Player player, String template, String message) {
        int powerLevel = UserManager.getPlayer(player).getPowerLevel();
        return gameChatToIRCTokenizer(player, template, message)
                .replace("%POWERLEVEL%", Integer.toString(powerLevel));
    }

    /**
     * FactionChat to IRC
     *
     * @param player
     * @param botNick
     * @param message
     * @param chatTag
     * @param chatMode
     * @return
     */
    public String chatFactionTokenizer(Player player, String botNick, String message, String chatTag, String chatMode) {
        String template;
        switch (chatMode) {
            case "public":
                template = plugin.getMsgTemplate(botNick, "faction-public-chat");
                break;
            case "ally":
                template = plugin.getMsgTemplate(botNick, "faction-ally-chat");
                break;
            case "enemy":
                template = plugin.getMsgTemplate(botNick, "faction-enemy-chat");
                break;
            default:
                return "";
        }
        return plugin.colorConverter.gameColorsToIrc(
                gameChatToIRCTokenizer(player, template, message)
                .replace("%FACTIONTAG%", chatTag)
                .replace("%FACTIONMODE%", chatMode));
    }

    /**
     * Herochat to IRC
     *
     * @param player
     * @param message
     * @param hColor
     * @param hChannel
     * @param hNick
     * @param template
     * @return
     */
    public String chatHeroTokenizer(Player player, String message, String hColor, String hChannel, String hNick, String template) {
        return gameChatToIRCTokenizer(player, template, message)
                .replace("%HEROCHANNEL%", hChannel)
                .replace("%HERONICK%", hNick)
                .replace("%HEROCOLOR%", plugin.colorConverter.gameColorsToIrc(hColor))
                .replace("%CHANNEL%", hChannel);
    }

    public String chatTownyChannelTokenizer(Player player, Channel townyChannel, String message, String template) {

        return gameChatToIRCTokenizer(player, template, message)
                .replace("%TOWNYCHANNEL%", ChatColor.translateAlternateColorCodes('&', townyChannel.getName()))
                .replace("%TOWNYCHANNELTAG%", ChatColor.translateAlternateColorCodes('&', townyChannel.getChannelTag()))
                .replace("%TOWNYMSGCOLOR%", ChatColor.translateAlternateColorCodes('&', townyChannel.getMessageColour()));
    }

    /**
     * TitanChat to IRC
     *
     * @param player
     * @param tChannel
     * @param tColor
     * @param message
     * @param template
     * @return
     */
    public String titanChatTokenizer(Player player, String tChannel, String tColor, String message, String template) {
        return gameChatToIRCTokenizer(player, template, message)
                .replace("%TITANCHANNEL%", tChannel)
                .replace("%TITANCOLOR%", plugin.colorConverter.gameColorsToIrc(tColor))
                .replace("%CHANNEL%", tChannel);
    }

    /**
     * Game chat to IRC
     *
     * @param template
     * @param message
     * @return
     */
    public String gameChatToIRCTokenizer(String template, String message) {
        return plugin.colorConverter.gameColorsToIrc(template
                .replace("%MESSAGE%", message));
    }

    /**
     * Game kick message to IRC
     *
     * @param player
     * @param template
     * @param reason
     * @param message
     * @return
     */
    public String gameKickTokenizer(Player player, String template, String message, String reason) {
        return plugin.colorConverter.gameColorsToIrc(
                gameChatToIRCTokenizer(player, template, message)
                .replace("%MESSAGE%", message)
                .replace("%REASON%", reason));
    }

    /**
     * ReportRTS notifications to IRC
     *
     * @param pName
     * @param template
     * @param ticket
     * @return
     */
    public String reportRTSTokenizer(String pName, String template, Ticket ticket) {
        String message = ticket.getMessage();
        String modName = ticket.getModName();
        String displayModName = "";
        String name = ticket.getName();
        String world = ticket.getWorld();
        String modComment = ticket.getModComment();
        int id = ticket.getId();
        if (message == null) {
            message = "";
        }
        if (modName == null) {
            modName = "";
        } else {
            Player player = this.getPlayer(modName);
            if (player != null) {
                displayModName = player.getDisplayName();
            } else {
                displayModName = modName;
            }
        }
        if (name == null) {
            name = "";
        }
        if (world == null) {
            world = "";
        }
        if (modComment == null) {
            modComment = "";
        }
        return plugin.colorConverter.gameColorsToIrc(playerTokenizer(pName, template)
                .replace("%MESSAGE%", message)
                .replace("%MODNAME%", modName)
                .replace("%DISPLAYMODNAME%", displayModName)
                .replace("%MODCOMMENT%", modComment)
                .replace("%TICKETNUMBER%", String.valueOf(id))
                .replace("%RTSNAME%", name)
                .replace("%RTSWORLD%", world));
    }

    public String reportRTSTokenizer(CommandSender sender, String message, String template) {
        return gameChatToIRCTokenizer(sender.getName(), template, message);
    }

    public String playerTokenizer(Player player, String message) {
        String pName = player.getName();
        plugin.logDebug("Tokenizing " + pName + "(O: " + player.isOnline() + ")");
        String pSuffix = plugin.getPlayerSuffix(player);
        String pPrefix = plugin.getPlayerPrefix(player);
        String gPrefix = plugin.getGroupPrefix(player);
        String gSuffix = plugin.getGroupSuffix(player);
        String group = plugin.getPlayerGroup(player);
        String displayName = player.getDisplayName();
        String playerIP = player.getAddress().getAddress().getHostAddress();
        String host = plugin.getPlayerHost(player);
        String worldName = "";
        String worldAlias = "";
        String worldColor = "";
        String jobShort = "";
        String job = "";
        if (pSuffix == null) {
            pSuffix = "";
        }
        if (pPrefix == null) {
            pPrefix = "";
        }
        if (gSuffix == null) {
            gSuffix = "";
        }
        if (gPrefix == null) {
            gPrefix = "";
        }
        if (group == null) {
            group = "";
        }
        if (playerIP == null) {
            playerIP = "";
        }
        if (displayName == null) {
            displayName = "";
        }
        if (player.getWorld() != null) {
            worldName = player.getWorld().getName();
            worldAlias = plugin.getWorldAlias(worldName);
            worldColor = plugin.getWorldColor(worldName);
        }
        if (plugin.jobsHook != null) {
            job = plugin.jobsHook.getPlayerJob(player, false);
            jobShort = plugin.jobsHook.getPlayerJob(player, true);
        }
        plugin.logDebug("[P]Raw message: " + message);
        return message.replace("%DISPLAYNAME%", displayName)
                .replace("%JOBS%", job)
                .replace("%JOBSSHORT%", jobShort)
                .replace("%NAME%", pName)
                .replace("%PLAYERIP%", playerIP)
                .replace("%HOST%", host)
                .replace("%GROUP%", group)
                .replace("%PLAYERPREFIX%", pPrefix)
                .replace("%PLAYERSUFFIX%", pSuffix)
                .replace("%GROUPPREFIX%", gPrefix)
                .replace("%GROUPSUFFIX%", gSuffix)
                .replace("%WORLDALIAS%", worldAlias)
                .replace("%WORLDCOLOR%", worldColor)
                .replace("%WORLD%", worldName);
    }

    private String playerTokenizer(String player, String message) {
        plugin.logDebug("Tokenizing " + player);
        String worldName = plugin.defaultPlayerWorld;
        String pSuffix = plugin.getPlayerSuffix(worldName, player);
        String pPrefix = plugin.getPlayerPrefix(worldName, player);
        String gPrefix = plugin.getGroupPrefix(worldName, player);
        String gSuffix = plugin.getGroupSuffix(worldName, player);
        String group = plugin.getPlayerGroup(worldName, player);
        String displayName = plugin.getDisplayName(player);
        String worldAlias = "";
        String worldColor = "";
        String jobShort = "";
        String job = "";
        if (!worldName.isEmpty()) {
            worldAlias = plugin.getWorldAlias(worldName);
            worldColor = plugin.getWorldColor(worldName);
        }
        if (pSuffix == null) {
            pSuffix = plugin.defaultPlayerSuffix;
        }
        if (pPrefix == null) {
            pPrefix = plugin.defaultPlayerPrefix;
        }
        if (gSuffix == null) {
            gSuffix = plugin.defaultGroupSuffix;
        }
        if (gPrefix == null) {
            gPrefix = plugin.defaultGroupPrefix;
        }
        if (group == null) {
            group = plugin.defaultPlayerGroup;
        }
        if (plugin.jobsHook != null) {
            job = plugin.jobsHook.getPlayerJob(player, false);
            jobShort = plugin.jobsHook.getPlayerJob(player, true);
        }
        plugin.logDebug("[S]Raw message: " + message);
        return message.replace("%DISPLAYNAME%", displayName)
                .replace("%JOBS%", job)
                .replace("%JOBSSHORT%", jobShort)
                .replace("%NAME%", player)
                .replace("%GROUP%", group)
                .replace("%PLAYERPREFIX%", pPrefix)
                .replace("%PLAYERSUFFIX%", pSuffix)
                .replace("%GROUPSUFFIX%", gSuffix)
                .replace("%GROUPPREFIX%", gPrefix)
                .replace("%WORLDALIAS%", worldAlias)
                .replace("%WORLDCOLOR%", worldColor)
                .replace("%WORLD%", worldName);
    }

    private Player getPlayer(String name) {
        Player player;
        if (plugin.exactNickMatch) {
            plugin.logDebug("Checking for exact player matching " + name);
            player = plugin.getServer().getPlayerExact(name);
        } else {
            plugin.logDebug("Checking for player matching " + name);
            player = plugin.getServer().getPlayer(name);
        }
        return player;
    }

    /**
     *
     * @param player
     * @param template
     * @param cmd
     * @param params
     * @return
     */
    public String gameCommandToIRCTokenizer(Player player, String template, String cmd, String params) {
        return plugin.colorConverter.gameColorsToIrc(playerTokenizer(player, template)
                .replace("%COMMAND%", cmd)
                .replace("%PARAMS%", params));
    }

    public String targetChatResponseTokenizer(String target, String message, String template) {
        return plugin.colorConverter.gameColorsToIrc(template
                .replace("%TARGET%", target)
                .replace("%MESSAGE%", message)
        );
    }

    public String msgChatResponseTokenizer(String target, String message, String template) {
        return plugin.colorConverter.ircColorsToGame(template
                .replace("%TARGET%", target)
                .replace("%MESSAGE%", message)
        );
    }
}
