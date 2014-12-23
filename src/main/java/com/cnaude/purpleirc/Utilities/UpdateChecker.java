/*
 * Copyright (C) 2014 cnaude
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.cnaude.purpleirc.Utilities;

import com.cnaude.purpleirc.PurpleIRC;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import org.bukkit.scheduler.BukkitTask;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 *
 * @author cnaude
 */
public class UpdateChecker {

    PurpleIRC plugin;

    private BukkitTask bt;
    private int newBuild = 0;
    private int currentBuild = 0;
    private String currentVersion = "";
    private String newVersion = "";

    /**
     *
     * @param plugin
     */
    public UpdateChecker(PurpleIRC plugin) {
        this.plugin = plugin;
        currentVersion = plugin.getDescription().getVersion();
        try {
            currentBuild = Integer.valueOf(currentVersion.split("-")[1]);
        } catch (NumberFormatException e) {
            currentBuild = 0;
        }
        startUpdateChecker();
    }

    private void startUpdateChecker() {
        bt = this.plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, new Runnable() {
            @Override
            public void run() {
                if (plugin.isUpdateCheckerEnabled()) {
                    plugin.logInfo("Checking for " + plugin.updateCheckerMode() + " updates ... ");
                    updateCheck();
                }
            }
        }, 0, 432000);
    }

    public void updateCheck() {
        if (plugin.updateCheckerMode().equalsIgnoreCase("stable")) {
            try {
                URL url = new URL("https://api.curseforge.com/servermods/files?projectids=56773");
                URLConnection conn = url.openConnection();
                conn.setReadTimeout(5000);
                conn.addRequestProperty("User-Agent", "PurpleIRC Update Checker");
                conn.setDoOutput(true);
                final BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                final String response = reader.readLine();
                final JSONArray array = (JSONArray) JSONValue.parse(response);
                if (array.size() == 0) {
                    plugin.logInfo("No files found, or Feed URL is bad.");
                    return;
                }
                newVersion = ((String) ((JSONObject) array.get(array.size() - 1)).get("name")).trim();
                plugin.logDebug("newVersionTitle: " + newVersion);
                newBuild = Integer.valueOf(newVersion.split("-")[1]);
                if (newBuild > currentBuild) {
                    plugin.logInfo("Stable version: " + newVersion + " is out!" + " You are still running version: " + currentVersion);
                    plugin.logInfo("Update at: http://dev.bukkit.org/server-mods/purpleirc");
                } else if (currentBuild > newBuild) {
                    plugin.logInfo("Stable version: " + newVersion + " | Current Version: " + currentVersion);
                } else {
                    plugin.logInfo("No new version available");
                }
            } catch (IOException | NumberFormatException e) {
                plugin.logInfo("Error checking for latest version: " + e.getMessage());
            }
        } else {
            try {
                URL url = new URL("http://h.cnaude.org:8081/job/PurpleIRC/lastStableBuild/api/json");
                URLConnection conn = url.openConnection();
                conn.setReadTimeout(5000);
                conn.addRequestProperty("User-Agent", "PurpleIRC Update Checker");
                conn.setDoOutput(true);
                final BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                final String response = reader.readLine();
                final JSONObject obj = (JSONObject) JSONValue.parse(response);
                if (obj.isEmpty()) {
                    plugin.logInfo("No files found, or Feed URL is bad.");
                    return;
                }

                newVersion = obj.get("number").toString();
                String downloadUrl = obj.get("url").toString();
                plugin.logDebug("newVersionTitle: " + newVersion);
                newBuild = Integer.valueOf(newVersion);
                if (newBuild > currentBuild) {
                    plugin.logInfo("Latest dev build: " + newVersion + " is out!" + " You are still running build: " + currentVersion);
                    plugin.logInfo("Update at: " + downloadUrl);
                } else if (currentBuild > newBuild) {
                    plugin.logInfo("Dev build: " + newVersion + " | Current build: " + currentVersion);
                } else {
                    plugin.logInfo("No new version available");
                }
            } catch (IOException | NumberFormatException e) {
                plugin.logInfo("Error checking for latest dev build: " + e.getMessage());
            }
        }
    }

    public void cancel() {
        bt.cancel();
    }
}
