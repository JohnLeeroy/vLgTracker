package vlg.jli.tracker.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by johnli on 12/1/14.
 */
public class ActivityList {
    public List<Activity> data;

    public ActivityList() {
        initTestServerList();
    }
    void initTestServerList()
    {
        data = new ArrayList<Activity>();
        data.add(new Activity("Rank Up", "You reached rank 52 in the vLg Competitive 7v7 128tick server. Great Work!"));
        data.add(new Activity("V0nk Gets Sky High", "Rumors are spreading that v0nk is baked beyond his mind today. Completely incoherent, he wrecks the minions of hell in Diablo 3."));
        data.add(new Activity("vLg Enters LoL Semi-finals", "Thanks to the leadership of [who?], the team has made some amazing plays that advanced them to the next round."));
        data.add(new Activity("A Wild Janna Appears", "The rare specimen, Janna, has appeared in vent."));
        data.add(new Activity("More vLg maps coming", "Sickminds is preparing to release five new awp maps to the vLg server."));
        data.add(new Activity("M00b's Internet Cuts Out. Again!", "As usual, m00b is having some shotty internet issues. Stop downloading all those cat pictures."));
        data.add(new Activity("New Smite God", "After demonstrating god-like gaming abilities, Hi-Rez makes a God based on Red. To be released in the next upcoming patch."));
        data.add(new Activity("Release The Kraken", "Oh Josh is caught drinking two forties with two shorties. Scandalous?"));
        data.add(new Activity("Bacon is Sizzling", "Hot off the block and ready to be bacon, it's Bacon!"));
        data.add(new Activity("Accuracy Up", "Your accuracy with the ak47 went up from 28% to 31%.  Amazing job!"));
        data.add(new Activity("Alex. Where is he now?", "Last seen 11/23/2014, please contact proper authorities(Sickminds) if found. He is stoned and dangerous." ));


    }
}
