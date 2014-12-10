package vlg.jli.tracker.Model;

/**
 * Created by johnli on 11/28/14.
 */
public class Server {

    public String id;
    public String addr;
    public String port;
    public String game;

    public String name;
    public String map;
    public int playerCount; //act
    public int maxPlayers; //max

    //Global stats
    int killCount;
    int headshotCount;
    int suicideCount;
    int shotCount;
    int hitCount;

    public static final String WIFI = "Wi-Fi";
    public static final String ANY = "Any";
    private static final String URL = "http://stackoverflow.com/feeds/tag?tagnames=android&sort=newest";

    // Whether there is a Wi-Fi connection.
    private static boolean wifiConnected = false;
    // Whether there is a mobile connection.
    private static boolean mobileConnected = false;
    // Whether the display should be refreshed.
    public static boolean refreshDisplay = true;
    public static String sPref = null;

    public Server() { }
    public Server(String Id, String Address, String Port)
    {
        id = Id;
        addr = Address;
        port = Port;
        game = "CSGO";
    }

    public Server(String Id, String Address, String Port, String Name, String MapName, int PlayerCount, int MaxPlayers)
    {
        id = Id;
        addr = Address;
        port = Port;
        game = "CSGO";
        name = Name;
        map = MapName;
        playerCount = PlayerCount;
        maxPlayers = MaxPlayers;
    }
}