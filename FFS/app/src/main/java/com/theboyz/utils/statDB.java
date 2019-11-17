package com.theboyz.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import tech.tablesaw.api.Table;
import tech.tablesaw.columns.Column;

/**
 * statDB communicates with the database through a REST API layer. This allows
 * the mobile device to perform operations on the database
 * @author chaseuphaus
 */
public class statDB 
{
    private final String baseURL;
    private final String tableName;
    
    public statDB()
    {
        this.baseURL = "http://99.179.141.41:25565/";
        this.tableName = "player_stats_2019";
    }
    
    public statDB(String tableIn)
    {
        this.baseURL = "http://99.179.141.41:25565/";
        this.tableName = tableIn;
    }
    
    public Table queryTable()
    {
        Table tableOut = null;
        try
        {
            //Parse out brackets and quotes from web API
            Column[] columns = this.exec_query(this.baseURL + this.tableName).columnArray();
            for (int i = 0; i < columns.length; i++)
            {
                columns[i].setName(columns[i].name().replace("[", "").replace("]", "").replace("\"", ""));
            }
            
            //Write columns to table
            tableOut = Table.create();
            tableOut.addColumns(columns);
        } catch(Exception e)
        {
            System.out.println(e.getMessage());
        }//End catch
        
        return tableOut;
    }
    
    private Table exec_query(String query) throws Exception
    {
        URL conStr = new URL(query);
        
        //Open URL and read data into StringBuffer            
        HttpURLConnection con = (HttpURLConnection) conStr.openConnection();  
        con.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null)
            content.append(inputLine + "\n");
        in.close();
        con.disconnect();

        //Return table form of json
        return Table.read().string(content.toString(), "json");
    }
}
