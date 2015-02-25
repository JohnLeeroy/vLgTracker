package vlg.jli.tracker.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by johnli on 12/1/14.
 */
public class FeedList {
    public List<FeedItem> data;

    public FeedList() {
        initTestServerList();
    }
    void initTestServerList()
    {
        data = new ArrayList<FeedItem>();
        data.add(new FeedItem("Rank Up", "You reached rank 52 in the vLg Competitive 7v7 128tick server. Great Work!"));
        data.add(new FeedItem("Rage quit is in the air", "Oh Gosh and J0hnny has left a m00b and Childish in the middle of a competitive match. m00b swears he will never play competitive with them again."));
        data.add(new FeedItem("Janna doesn't play nice", "Our resident LoL fanatic continues to play without inviting her mates. "));
        data.add(new FeedItem("vLg has switched to Teamspeak!", "The old ventrilo server was getting too small. Everyone is starting to enjoy the new space."));
        data.add(new FeedItem("More vLg maps coming", "Sickminds is preparing to release five new awp maps to the vLg server."));
        data.add(new FeedItem("M00b's Internet Cuts Out. Again!", "As usual, m00b is having some shotty internet issues. Stop downloading all those cat pictures."));
        data.add(new FeedItem("New Smite God", "After demonstrating god-like gaming abilities, Hi-Rez makes a God based on Red. To be released in the next upcoming patch."));
        data.add(new FeedItem("Release The Kraken", "Oh Josh is caught drinking two forties with two shorties. Scandalous?"));
        data.add(new FeedItem("Bacon is Sizzling", "Hot off the block and ready to be bacon, it's Bacon!"));
        data.add(new FeedItem("Accuracy Up", "Your accuracy with the ak47 went up from 28% to 31%.  Amazing job!"));
        data.add(new FeedItem("Alex. Where is he now?", "Last seen 11/23/2014, please contact proper authorities(Sickminds) if found. He is stoned and dangerous." ));


    }
}
