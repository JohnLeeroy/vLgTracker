package vlg.jli.tracker.Model;

import java.util.ArrayList;
import java.util.List;

import vlg.jli.tracker.Model.Server;

/**
 * Created by johnli on 12/1/14.
 */
public class ServerList implements IDefaultData{
    public List<Server> servers;

    public ServerList() {
        initTestServerList();
    }

    public void initWithDefaultData()
    {
        initTestServerList();
    }

    void initTestServerList()
    {
        servers = new ArrayList<Server>();
        Server mainComp = new Server("0", "192.12.31.11", "27015", "vLg Competitve 7v7 128tick", "de_dust2", 12, 14);
        Server scoutsKnives = new Server("1", "192.12.31.11", "27015", "vLg ScoutsKnives 128tick knife mod", "scoutsknives", 12, 12);
        Server propHunt = new Server("2", "192.12.31.11", "27015", "vLg PropHunt ", "cs_assault", 3, 14);
        Server deagle = new Server("3", "192.12.31.11", "27015", "vLg Deagles Only 7v7 128tick ", "dm_ohgosh_v2", 3, 14);
        servers.add(mainComp);
        servers.add(scoutsKnives);
        servers.add(propHunt);
        servers.add(deagle);

    }

}
