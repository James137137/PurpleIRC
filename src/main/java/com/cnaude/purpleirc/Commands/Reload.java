/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cnaude.purpleirc.Commands;

import com.cnaude.purpleirc.PurpleBot;
import com.cnaude.purpleirc.PurpleIRC;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 *
 * @author cnaude
 */
public class Reload {

    private final PurpleIRC plugin;

    public Reload(PurpleIRC plugin) {
        this.plugin = plugin;
    }

    public void dispatch(CommandSender sender) {
        sender.sendMessage("Disabling PurpleIRC...");
        plugin.getServer().getPluginManager().disablePlugin(plugin);
        sender.sendMessage("Enabling PurpleIRC...");
        plugin.getServer().getPluginManager().enablePlugin(plugin);
    }
}