package com.theboyz.utils;

import com.theboyz.ffs.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Helpers
{
    public static <T> List<String> getListFromIterator(Iterator<T> iterator)
    {
        List<String> rVal = new ArrayList<>();
        while (iterator.hasNext())
            rVal.add((String) iterator.next());
        return rVal;
    }//End getListFromIterator

    public static int getImageId(String team)
    {
        int rVal = R.drawable.logo;

        switch (team)
        {
            case "ARI":
                rVal = R.drawable.ari;
                break;

            case "ATL":
                rVal = R.drawable.atl;
                break;

            case "BAL":
                rVal = R.drawable.bal;
                break;

            case "BUF":
                rVal = R.drawable.buf;
                break;

            case "CAR":
                rVal = R.drawable.car;
                break;

            case "CHI":
                rVal = R.drawable.chi;
                break;

            case "CIN":
                rVal = R.drawable.cin;
                break;

            case "CLE":
                rVal = R.drawable.cle;
                break;

            case "DAL":
                rVal = R.drawable.dal;
                break;

            case "DEN":
                rVal = R.drawable.den;
                break;

            case "DET":
                rVal = R.drawable.det;
                break;

            case "GB":
                rVal = R.drawable.gb;
                break;

            case "HOU":
                rVal = R.drawable.hou;
                break;

            case "IND":
                rVal = R.drawable.ind;
                break;

            case "JAX":
                rVal = R.drawable.jax;
                break;

            case "KC":
                rVal = R.drawable.kc;
                break;

            case "LA":
                rVal = R.drawable.la;
                break;

            case "LAC":
                rVal = R.drawable.lac;
                break;

            case "MIA":
                rVal = R.drawable.mia;
                break;

            case "MIN":
                rVal = R.drawable.min;
                break;

            case "NE":
                rVal = R.drawable.ne;
                break;

            case "NO":
                rVal = R.drawable.no;
                break;

            case "NYG":
                rVal = R.drawable.nyg;
                break;

            case "NYJ":
                rVal = R.drawable.nyj;
                break;

            case "OAK":
                rVal = R.drawable.oak;
                break;

            case "PHI":
                rVal = R.drawable.phi;
                break;

            case "PIT":
                rVal = R.drawable.pit;
                break;

            case "SEA":
                rVal = R.drawable.sea;
                break;

            case "SF":
                rVal = R.drawable.sf;
                break;

            case "TB":
                rVal = R.drawable.tb;
                break;

            case "TEN":
                rVal = R.drawable.ten;
                break;

            case "WAS":
                rVal = R.drawable.was;
                break;
        }//End Switch

        return rVal;
    }

    public static JSONObject getDefaultScoring() throws Exception
    {
        JSONObject scoring = new JSONObject();
        scoring.put("playerIDS", "['00-0033077', '00-0029263', '00-0033921', '00-0033906', '00-0032211', '00-0034348', '00-0033303', '00-0030061', '00-0032626', '00-0033556', '00-0034378', '00-0033930']");
        scoring.put("scoredStats", "['pass.comp', 'passyds', 'pass.tds', 'pass.ints', 'rush.att', 'rushyds', 'rushtds', 'recept', 'recyds', 'rec.tds', 'st_ret_yds', 'kickret.tds', 'puntret.tds', 'fumbslost', 'two_pts', 'xpa', 'fgs']");
        scoring.put("statWeights", "[0.1, 0.04, 6, -2, 0.1, 0.1, 6, 0.4, 0.1, 6, 0.05, 6, 6, -2, 2, 1, 1]");
        return scoring;
    }

    public static String[] convertScoringList(ArrayList<String> in)
    {
        HashMap<String, String> key = new HashMap<>();
        key.put("Pass Completions", "pass.comp");
        key.put("Pass Yards", "passyds");
        key.put("Pass Touchdowns", "pass.tds");
        key.put("Interceptions Thrown", "pass.ints");
        key.put("Rush Attempts", "rush.att");
        key.put("Rush Yards", "rushyds");
        key.put("Rush Touchdowns", "rushtds");
        key.put("Receptions", "recept");
        key.put("Receiving Yards", "recyds");
        key.put("Receiving Touchdowns", "rec.tds");
        key.put("Kick/Punt Return Yards", "st_ret_yds");
        key.put("Kick Return Touchdowns", "kickret.tds");
        key.put("Punt Return Touchdowns", "puntret.tds");
        key.put("Fumbles Lost", "fumbslost");
        key.put("Two Point Conversions", "two_pts");
        key.put("Extra Points", "xpa");
        key.put("Field Goals", "fgs");

        String[] out = new String[in.size()];

        for (int i = 0; i < in.size(); i++)
            out[i] = key.get(in.get(i));

        return out;
    }//End convertScoringList

    public static double[] DoubleToPrimitive(Double[] in)
    {
        double[] out = new double[in.length];
        for (int i = 0; i < in.length; i++)
            out[i] = in[i];

        return out;
    }

}
