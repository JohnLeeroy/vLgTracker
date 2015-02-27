package vlg.jli.tracker.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by johnli on 12/1/14.
 */
public class User implements IDefaultData {
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

    public int killstreak = -1;
    public int deathstreak = -1;
    public int wins = -1;
    public int losses = -1;
    public int roundCount = -1;
    public int roundSurvivedCount = -1;
    public int suicideCount = -1;
    public int teamKills = -1;
    public Weapon favoriteWeapon;

    public User(String Name, int Rank, int Kills, int Deaths) {
        name = Name;
        rank = Rank;
        kills = Kills;
        deaths = Deaths;
    }

    public User() {}

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

        data.add(new String[]{"Kill Streak", String.valueOf(killstreak)});
        data.add(new String[]{"Death Streak",String.valueOf(deathstreak)});
        data.add(new String[]{"Wins",String.valueOf(wins)});
        data.add(new String[]{"Losses",String.valueOf(losses)});
        data.add(new String[]{"Rounds",String.valueOf(roundCount)});
        data.add(new String[]{"Rounds Survived",String.valueOf(roundSurvivedCount)});
        data.add(new String[]{"Suicides",String.valueOf(suicideCount)});

        if(favoriteWeapon != null) {
            data.add(new String[]{"Favorite Weapon", favoriteWeapon.name});
            data.add(new String[]{"Weapon Kills", String.valueOf(favoriteWeapon.killCount)});
        }
        //Removes all non-filled in fields
        /*for (Iterator<String[]> iter = data.listIterator(); iter.hasNext(); ) {
            String[] info = iter.next();
            if (info[1].equals("-1")) {
                iter.remove();
            }
        }*/

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

    public void initWithDefaultData()
    {
        this.name = "vLg | Red";
        this.id = "1";
        this.kills = 1337;
        this.deaths = 213;
        this.assists = 832;
        this.headshots = 612;
        this.hits = 32185;
        this.shots = 124123;
        this.time = 60*60&412;
    }

}
