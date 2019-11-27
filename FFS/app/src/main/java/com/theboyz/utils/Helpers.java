package com.theboyz.utils;

import com.theboyz.ffs.R;

import org.json.JSONObject;

import java.util.ArrayList;
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

        switch(team)
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
        return new JSONObject("{\"pass.comp\":0.1,\"passyds\":0.04,\"pass.tds\":6,\"pass.ints\":-2,\"rush.att\":0.1,\"rushyds\":0.1,\"rushtds\":6,\"recept\":0.4,\"recyds\":0.1,\"rec.tds\":6,\"st_ret_yds\":0.05,\"kickret.tds\":6,\"puntret.tds\":6,\"fumbslost\":-2,\"two_pts\":2,\"xpa\":1,\"fgs\":1}");
    }
}
