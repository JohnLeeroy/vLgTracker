package vlg.jli.tracker.Model;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by johnli on 12/1/14.
 */
public class User {
    public String id;
    public String name;
    public String avatar;
    public String steamId;

    public String imageUrl;
    public String activity;
    public String clanName;
    public int rank;
    public int skill;
    public int kills;
    public int deaths;
    public int headshots;
    public int time;
    public int assists;
    public int hits;
    public int shots;

    public String country;






    int killstreak;
    int deathstreak;
    int wins;
    int losses;
    int roundCount;
    int roundSurvivedCount;


    public User(String Name, int Rank, int Kills, int Deaths) {
        name = Name;
        rank = Rank;
        kills = Kills;
        deaths = Deaths;

    }

    public User() { }

    public List<String[]> convertToList(){
        List<String[]> data = new ArrayList<String[]>();
        //if(shots == 0)
        float accuracy = hits/((float)shots);

        data.add(new String[]{"Name",name});
        data.add(new String[]{"Rank",String.valueOf(rank)});
        data.add(new String[]{"Kills",String.valueOf(kills)});
        data.add(new String[]{"Deaths",String.valueOf(deaths)});
        data.add(new String[]{"Assists",String.valueOf(assists)});
        data.add(new String[]{"Headshots",String.valueOf(headshots)});
        data.add(new String[]{"Accuracy",String.valueOf(accuracy)});
        data.add(new String[]{"Time Played",String.valueOf(time)});
        return data;
    }

    public static User getRed()
    {
        User me = new User();//("vLg | Red", 1, 1337, 213);
        me.name = "vLg | Red";
        me.id = "1";
        me.kills = 1337;
        me.deaths = 213;
        me.assists = 832;
        me.headshots = 612;
        me.hits = 32185;
        me.shots = 124123;
        me.time = 60*60&412;
        return me;
    }
}
