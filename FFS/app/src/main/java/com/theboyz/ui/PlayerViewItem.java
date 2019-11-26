package com.theboyz.ui;

public class PlayerViewItem
{
    private String name;
    private String score;
    private int rscID;

    public PlayerViewItem(int id, String name, String score)
    {
        this.name = name;
        this.rscID = id;
        this.score = score;
    }//End constructor

    public int getImageResource() { return this.rscID; }
    public String getPlayerName() { return this.name; }
    public String getScore() { return this.score; }
}
