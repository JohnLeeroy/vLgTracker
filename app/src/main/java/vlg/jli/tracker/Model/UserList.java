package vlg.jli.tracker.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by johnli on 12/1/14.
 */
public class UserList implements  IDefaultData{
    public List<User> users;

    public UserList() {
        initTestUserList();
    }
    void initTestUserList(){
        users = new ArrayList<User>();
        users.add(new User("vLg | Red", 1, 1337, 213));
        users.add(new User("vLg | Sickminds", 2, 748, 813));
        users.add(new User("vLg | m00b", 3, 802, 1027));
        users.add(new User("vLg | oh gosh", 4, 702,638));
        users.add(new User("vLg | v0nk", 5, 632, 512));
        users.add(new User("vLg | Janna", 6, 555, 598));
    }

    public void initWithDefaultData()
    {
        initTestUserList();
    }


}
