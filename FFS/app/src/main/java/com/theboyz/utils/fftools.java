package com.theboyz.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.IntColumn;
import tech.tablesaw.api.LongColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.api.NumericColumn;

/**
 *FFTools processes performs statistical analysis based on the user's settings
 * @author chaseuphaus
 */
public class fftools 
{
    private final String[] scoredStats;
    private final double[] statWeights;
    private final String[] index = {"playerID", "name", "Team", "game.id", "date", "Season"};
    
    /**
     * Used to initialize the statInterface with the default settings
     */
    public fftools()
    {
        //Example set of scored items
        String[] items = {"pass.comp", "passyds", "pass.tds", "pass.ints", "rush.att",
                          "rushyds", "rushtds", "recept", "recyds", "rec.tds", "st_ret_yds", 
                          "kickret.tds", "puntret.tds", "fumbslost", "two_pts", "xpa", "fgs"};
        
        //Example set of item weights
        double[] weights = {(double) .1, (double) 1/25, (double) 6, (double) -2, (double) .1,
                            (double) 1/10, (double) 6, (double) .4, (double) 1/10, (double) 6, 
                            (double) 1/20, (double) 6, (double) 6, (double) -2, (double) 2,
                            (double) 1, (double) 1};
        
        this.scoredStats = items;
        this.statWeights = weights;
    }
    
    /**
     * Used to initialize the statInterface with the settings unique to a given user
     * @param acc The userAccount for which the statistic settings are to be adjusted to
     */
    public fftools(userAccount acc)
    {
        this.scoredStats = acc.getStats();
        this.statWeights = acc.getWeights();
    }
    
    /**
     * Generates a table with two columns Score/Touches
     * @param df The player_stats table from any year
     * @return Table index by player/game/team/season/playerID with two columns -- Score, Touches
     * @throws Exception Throws an exception when the user has league settings that aren't configured properly (e.g. scoredStats contains an invalid field, scoredStats differs in length from statWeights)
     */
    public Table applyScoring(Table df) throws Exception
    {
        df = this.deriveStats(df);
        //Declare dict with weights for scored stats in league
        Table scored = Table.create();
        Table touches = Table.create();
        
        HashMap<String, Double> scoring = this.scoredDict();
        String current;
        for (int i = 0; i < scoring.keySet().size(); i++)
        {
            current = scoring.keySet().toArray()[i].toString();
            if (current.equals("two_pts") || current.equals("st_ret_yds"))
                scored.addColumns(df.doubleColumn(current).multiply(scoring.get(current)).setName(current));
            else
                scored.addColumns(df.intColumn(current).multiply(scoring.get(current)).setName(current));
        }    
    
        
        touches.addColumns(df.intColumn("rush.att")).addColumns(df.intColumn("recept")).addColumns(df.intColumn("xpa")).addColumns(df.intColumn("fga"));
        
        DoubleColumn lp = DoubleColumn.create("Score", this.summarizeByRow(scored));
        DoubleColumn tc = DoubleColumn.create("Touches", this.summarizeByRow(touches));
        scored = Table.create("Player, Points, and Touches");   
        scored.addColumns(df.stringColumn("playerID")).addColumns(df.stringColumn("name")).addColumns(df.intColumn("season")).addColumns(df.intColumn("game.id")).addColumns(df.dateColumn("date")).addColumns(df.stringColumn("Team")).addColumns(lp).addColumns(tc);
        return scored.sortAscendingOn("playerID", "name", "season", "game.id", "date", "Team");
    }

    /**
     * Generate a dictionary based on the users league settings. Key is the statistic, value is the weight of the statistic
     * @return HashMap for the user's league settings
     * @throws Exception Exception Throws an exception when the user has league settings that aren't configured properly (e.g. scoredStats contains an invalid field, scoredStats differs in length from statWeights)
     */
    private HashMap<String, Double> scoredDict() throws Exception
    {
        HashMap <String, Double> scoring = new HashMap();
        
        //Make sure we have valid data to score on
        if (this.scoredStats.length != this.statWeights.length)
            throw new Exception("Mismatch in size between scored categories and scored weights");
        
        else
        {
            //If data is valid, generate dictionary based on scored stats
            for (int i = 0; i < this.scoredStats.length; i++)
            {
                scoring.put(this.scoredStats[i], this.statWeights[i]);
            }
        }
        return scoring;
    }
    
    /**
     * Used to convert an array of Long (object type) to the long (primitive type)
     * @param objects Array of object type longs
     * @return Array of primitive type longs
     */
    private static int [] toPrimitives(Integer[] objects) 
    {
        int[] primitives = new int[objects.length];
        for (int i = 0; i < objects.length; i++)
             primitives[i] = objects[i];

        return primitives;
    }
    
    /**
     * Used to sum an entire table by row as opposed to summing a column and grouping on a row.
     * @param df Table containing only the value the user wishes to sum
     * @return Table An array containing the values for the summarize
     */
    private double[] summarizeByRow(Table df)
    {
//        df.
        List<NumericColumn<?>> cols = df.numericColumns();
        
        int x = cols.size();
        int y = cols.get(0).size();
        
        Object[][] data = new Object[x][y];
        
        ///Read table into 2D object array
        for (int i = 0; i < x; i++)
        {
            for (int j = 0; j < y; j++)
            {
                data[i][j] = cols.get(i).get(j);
            }
        }
        
        //Sum across x-axis
        try
        {
            double [] summ = new double [y];
            for (int j = 0; j < y; j++)
            {
                for (int i = 0; i < x; i++)
                {
                    summ[j] += (double)data[i][j];
                }
            }
            return summ;
        } catch (Exception e)
        //If the exception is caught, it is likely the data being passed is long as opposed to double, cast data to long to solve issue
        {
            double [] summ = new double [y];
            for (int j = 0; j < y; j++)
            {
                for (int i = 0; i < x; i++)
                {
                    summ[j] += (int)data[i][j];
                }
            }
            return summ;
        }
    }//end summarizeByRow
    
    /**
     * This function creates some statistics that are not explicitly available on NFL.com
     * @param df player_stats table from any year
     * @return Table containing the table passed in plus the derived statistics
     */
    private Table deriveStats(Table df)
    {
        DoubleColumn st_ret_yds = df.intColumn("kickret.avg").multiply(df.intColumn("kick.rets")).add(
                    df.intColumn("puntret.avg").multiply(df.intColumn("punt.rets"))).setName("st_ret_yds");
        DoubleColumn two_pts = df.intColumn("pass.twoptm").add(df.intColumn("pass.twoptm")).add(df.intColumn("rec.twoptm")).setName("two_pts");
        DoubleColumn avg = df.intColumn("fgyds").divide(df.intColumn("fgm"));
        ArrayList<Integer> fg_s = new ArrayList();
        IntColumn fga = df.intColumn("fga");
        for (int i = 0; i < avg.size(); i++)
        {
            if (avg.get(i) < 40)
                fg_s.add((fga.get(i) * 3));
            else if (avg.get(i) < 50)
                fg_s.add((fga.get(i) * 4));
            else
                fg_s.add((fga.get(i) * 5));
        }
        IntColumn fgs = IntColumn.create("fgs", toPrimitives(fg_s.toArray(new Integer[fg_s.size()])));
        df = df.insertColumn(4, fgs).insertColumn(4, st_ret_yds).insertColumn(4, two_pts);
        return df;
    }
}//end class
