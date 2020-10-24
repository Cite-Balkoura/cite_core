package fr.milekat.cite_core.core.obj;

import java.util.HashMap;
import java.util.UUID;

public class Profil {
    private final UUID uuid;
    private String name;
    private int team;
    private int chat_mode;
    private String muted;
    private String banned;
    private String reason;
    private boolean mods_chat;
    private boolean mods_build;
    private boolean maintenance;
    private final long discordid;
    private HashMap<Integer, Integer> crates;

    public Profil(UUID uuid, String name, int team, int chat_mode, String muted, String banned, String reason, boolean modson, boolean mods_build, boolean maintenance, long discordid, HashMap<Integer, Integer> crates) {
        this.uuid = uuid;
        this.name = name;
        this.team = team;
        this.chat_mode = chat_mode;
        this.muted = muted;
        this.banned = banned;
        this.reason = reason;
        this.mods_chat = modson;
        this.mods_build = mods_build;
        this.maintenance = maintenance;
        this.discordid = discordid;
        this.crates = crates;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTeam() {
        return team;
    }

    public void setTeam(int team) {
        this.team = team;
    }

    public int getChat_mode() {
        return chat_mode;
    }

    public void setChat_mode(int chat_mode) {
        this.chat_mode = chat_mode;
    }

    public String getMuted() {
        return muted;
    }

    public void setMuted(String muted) {
        this.muted = muted;
    }

    public String getBanned() {
        return banned;
    }

    public void setBanned(String banned) {
        this.banned = banned;
    }

    public boolean isMods_chat() {
        return mods_chat;
    }

    public void setMods_chat(boolean mods_chat) {
        this.mods_chat = mods_chat;
    }

    public boolean isMaintenance() {
        return maintenance;
    }

    public void setMaintenance(boolean maintenance) {
        this.maintenance = maintenance;
    }

    public boolean isMute(){
        return !this.muted.equals("pas mute");
    }

    public boolean isBan(){
        return !this.banned.equals("pas ban");
    }

    public long getDiscordid() {
        return discordid;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public boolean isMods_build() {
        return mods_build;
    }

    public void setMods_build(boolean mods_build) {
        this.mods_build = mods_build;
    }

    public HashMap<Integer, Integer> getCrates() {
        return crates;
    }

    public void setCrates(HashMap<Integer, Integer> crates) {
        this.crates = crates;
    }
}
