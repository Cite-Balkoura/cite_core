package fr.milekat.cite_core.core.obj;

import java.util.ArrayList;
import java.util.HashMap;

public class Team {
    private final int id;
    private final String name;
    private final String tag;
    private int money;
    private ArrayList<Profil> members;
    private HashMap<Integer, Integer> tradesuses;
    private boolean trading;

    public Team(int id, String name, String tag, int money, ArrayList<Profil> members, HashMap<Integer, Integer> tradesuses) {
        this.id = id;
        this.name = name;
        this.tag = tag;
        this.money = money;
        this.members = members;
        this.tradesuses = tradesuses;
        this.trading = false;
    }

    public String getName() {
        return name;
    }

    public String getTag() {
        return tag;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getId() {
        return id;
    }

    public int getSize() {
        return this.members.size();
    }

    public ArrayList<Profil> getMembers() {
        return members;
    }

    public void addMembers(Profil member) {
        this.members.add(member);
    }

    public void removeMembers(Profil member) {
        this.members.remove(member);
    }

    public void setMembers(ArrayList<Profil> members) {
        this.members = members;
    }

    public boolean isTrading() {
        return trading;
    }

    public void setTrading(boolean trading) {
        this.trading = trading;
    }

    public HashMap<Integer, Integer> getTradesuses() {
        return tradesuses;
    }

    public void setTradesuses(HashMap<Integer, Integer> tradesuses) {
        this.tradesuses = tradesuses;
    }
}
