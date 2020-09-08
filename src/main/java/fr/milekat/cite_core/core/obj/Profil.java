package fr.milekat.cite_core.core.obj;

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
    private boolean scoreboard;
    private int ptsevent;
    private boolean maintenance;
    private final long discordid;

    public Profil(UUID uuid, String name, int team, int chat_mode, String muted, String banned, String reason, boolean modson, boolean mods_build, boolean scoreboard, int ptsevent, boolean maintenance, long discordid) {
        this.uuid = uuid;
        this.name = name;
        this.team = team;
        this.chat_mode = chat_mode;
        this.muted = muted;
        this.banned = banned;
        this.reason = reason;
        this.mods_chat = modson;
        this.mods_build = mods_build;
        this.scoreboard = scoreboard;
        this.ptsevent = ptsevent;
        this.maintenance = maintenance;
        this.discordid = discordid;
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

    public int getPtsevent() {
        return ptsevent;
    }

    public void setPtsevent(int ptsevent) {
        this.ptsevent = ptsevent;
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

    public boolean isScoreboard() {
        return scoreboard;
    }

    public void setScoreboard(boolean scoreboard) {
        this.scoreboard = scoreboard;
    }
}
