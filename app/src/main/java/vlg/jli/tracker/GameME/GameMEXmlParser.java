package vlg.jli.tracker.GameME;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import vlg.jli.tracker.Model.Server;
import vlg.jli.tracker.Model.ServerPlayer;
import vlg.jli.tracker.Model.User;

/**
 * Created by johnli on 12/2/14.
 */
public class GameMEXmlParser {
    private static final String ns = null;

// PARSE
    public List<Server> parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return parseForServer(parser);
        } finally {
            in.close();
        }
    }

    public List<ServerPlayer> parseForPlayerList(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return parseForPlayerList(parser);
        } finally {
            in.close();
        }
    }

    public List<User> parseForUserList(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return parseForUserList(parser);
        } finally {
            in.close();
        }
    }
    //InputStream in
    List<ServerPlayer> parseForPlayerList(XmlPullParser parser) throws XmlPullParserException, IOException {
        List<ServerPlayer> entries = new ArrayList<ServerPlayer>();

        parser.require(XmlPullParser.START_TAG, ns, "gameME");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals("serverinfo")) {
                parser.require(XmlPullParser.START_TAG, ns, "serverinfo");
                while (parser.next() != XmlPullParser.END_TAG) {
                    if (parser.getEventType() != XmlPullParser.START_TAG)
                        continue;

                    name = parser.getName();
                    if (name.equals("server")) {
                        parser.require(XmlPullParser.START_TAG, ns, "server");
                        while (parser.next() != XmlPullParser.END_TAG) {
                            //if (parser.getEventType() != XmlPullParser.START_TAG)
                             //   continue;

                            name = parser.getName();
                            if (name.equals("players")) {
                                entries = readServerPlayerList(parser);
                            }
                            else
                                skip(parser);
                        }
                    }
                    else
                        skip(parser);
                }
            }
            else {
                skip(parser);
            }
        }
        return entries;
    }

    //InputStream in
    List<User> parseForUserList(XmlPullParser parser) throws XmlPullParserException, IOException {
        List<User> entries = new ArrayList<User>();

        parser.require(XmlPullParser.START_TAG, ns, "gameME");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals("playerlist")) {
                entries = readUsers(parser);
                /*parser.require(XmlPullParser.START_TAG, ns, "playerlist");
                while (parser.next() != XmlPullParser.END_TAG) {
                    if (parser.getEventType() != XmlPullParser.START_TAG)
                        continue;

                    name = parser.getName();
                    if (name.equals("player")) {
                        entries = readUsers(parser);
                    }
                    else
                        skip(parser);
                }
                */
            }
            else {
                skip(parser);
            }
        }
        return entries;
    }


    //InputStream in
    List<Server> parseForServer(XmlPullParser parser) throws XmlPullParserException, IOException {
        List<Server> entries = new ArrayList<Server>();

        parser.require(XmlPullParser.START_TAG, ns, "gameME");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals("serverlist")) {
                entries = readServers(parser);
            }
            else {
                skip(parser);
            }
        }
        return entries;
    }

    List<User> parseUser(XmlPullParser parser) throws XmlPullParserException, IOException {
        List<User> user = new ArrayList<User>();

        parser.require(XmlPullParser.START_TAG, ns, "gameME");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals("serverlist")) {
                user = readUsers(parser);
            }
            else {
                skip(parser);
            }
        }
        return user;
    }


//////READ
    List<ServerPlayer> readServerPlayerList(XmlPullParser parser) throws XmlPullParserException, IOException {
        List<ServerPlayer> users = new ArrayList<ServerPlayer>();
        parser.require(XmlPullParser.START_TAG, ns, "players");
        String title = null;
        String summary = null;
        String link = null;
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if(name.equals("player"))
            {
                ServerPlayer player = getServerPlayer(parser);
                users.add(player);
            }
            else
                skip(parser);
        }

        return users;
    }

    List<User> readPlayerList(XmlPullParser parser) throws XmlPullParserException, IOException {
        List<User> users = new ArrayList<User>();
        parser.require(XmlPullParser.START_TAG, ns, "players");
        String title = null;
        String summary = null;
        String link = null;
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if(name.equals("player"))
            {
                User user = getUser(parser);
                users.add(user);
            }
            else
                skip(parser);
        }

        return users;
    }

    List<Server> readServers(XmlPullParser parser) throws XmlPullParserException, IOException {
        List<Server> servers = new ArrayList<Server>();
        parser.require(XmlPullParser.START_TAG, ns, "serverlist");
        String title = null;
        String summary = null;
        String link = null;
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if(name.equals("server"))
            {
                Server server = getServer(parser);
                servers.add(server);
            }
            else
                skip(parser);
        }

        return servers;
    }


    List<User> readUsers(XmlPullParser parser) throws XmlPullParserException, IOException {
        List<User> users = new ArrayList<User>();
        parser.require(XmlPullParser.START_TAG, ns, "playerlist");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if(name.equals("player"))
            {
                User user = getUser(parser);
                users.add(user);
            }
            else
                skip(parser);
        }

        return users;
    }
    /*

    public String name;
    public String steamdId;
    public String team;
    public int kills;
    public int deaths;
    public int skill;

     */
//// GET
    ServerPlayer getServerPlayer(XmlPullParser parser) throws XmlPullParserException, IOException {
        ServerPlayer user = new ServerPlayer();

        parser.require(XmlPullParser.START_TAG, ns, "player");
        while(parser.next() != XmlPullParser.END_TAG)
        {
            String key = parser.getName();
            parser.require(XmlPullParser.START_TAG, ns, key);

            if (key.equals("name")) {
                user.name = readText(parser);
            }  else if (key.equals("uniqueid")) {
                user.steamdId = readText(parser);
            //} else if (key.equals("avatar")) {
                //user.imageUrl = readText(parser);
            } else if (key.equals("skill")) {
                user.skill = Integer.parseInt(readText(parser));
            } else if (key.equals("kills")) {
                user.kills = Integer.parseInt(readText(parser));
            } else if (key.equals("deaths")) {
                user.deaths = Integer.parseInt(readText(parser));
            } else if (key.equals("team")) {
                user.team = readText(parser);
            }else
                skip(parser);
            parser.require(XmlPullParser.END_TAG, ns, key);
        }
        return user;
    }

    User getUser(XmlPullParser parser) throws XmlPullParserException, IOException {
        User user = new User();

        parser.require(XmlPullParser.START_TAG, ns, "player");
        while(parser.next() != XmlPullParser.END_TAG)
        {
            String key = parser.getName();
            parser.require(XmlPullParser.START_TAG, ns, key);
            if (key.equals("id")) {
                user.id = readText(parser);
            } else if (key.equals("name")) {
                user.name = readText(parser);
            }  else if (key.equals("uniqueid")) {
                user.steamId = readText(parser);
            } else if (key.equals("avatar")) {
                skip(parser);
                //user.imageUrl = readText(parser);
            } else if (key.equals("activity")) {
                user.activity = readText(parser);
            } else if (key.equals("clanname")) {
                user.clanName = readText(parser);
            } else if (key.equals("rank")) {
                user.rank = Integer.parseInt(readText(parser));
            } else if (key.equals("skill")) {
                user.skill = Integer.parseInt(readText(parser));
            } else if (key.equals("kills")) {
                user.kills = Integer.parseInt(readText(parser));
            } else if (key.equals("deaths")) {
                user.deaths = Integer.parseInt(readText(parser));
            } else if (key.equals("hs")) {
                user.headshots = Integer.parseInt(readText(parser));
            } else if (key.equals("time")) {
                user.time = Integer.parseInt(readText(parser));
            } else if (key.equals("assists")) {
                user.assists = Integer.parseInt(readText(parser));
            } else if (key.equals("cn")) {
                user.country = readText(parser);
            }
            else
                skip(parser);
            parser.require(XmlPullParser.END_TAG, ns, key);
        }
        return user;
    }

    Server getServer(XmlPullParser parser) throws XmlPullParserException, IOException {
        Server server = new Server();

        parser.require(XmlPullParser.START_TAG, ns, "server");
        while(parser.next() != XmlPullParser.END_TAG)
        {
            String key = parser.getName();
            parser.require(XmlPullParser.START_TAG, ns, key);
            if (key.equals("id")) {
                server.id = readText(parser);
            } else if (key.equals("addr")) {
                server.addr = readText(parser);
            } else if (key.equals("port")) {
                server.port = readText(parser);
            } else if (key.equals("name")) {
                server.name = readText(parser);
            } else if (key.equals("map")) {
                server.map = readText(parser);
            } else if (key.equals("act")) {
                server.playerCount = Integer.parseInt(readText(parser));
            } else if (key.equals("max")) {
                server.maxPlayers = Integer.parseInt(readText(parser));
            }
            else
                skip(parser);
            parser.require(XmlPullParser.END_TAG, ns, key);
        }
        return server;
    }

    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    // Skips tags the parser isn't interested in. Uses depth to handle nested tags. i.e.,
    // if the next tag after a START_TAG isn't a matching END_TAG, it keeps going until it
    // finds the matching END_TAG (as indicated by the value of "depth" being 0).
    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }
}