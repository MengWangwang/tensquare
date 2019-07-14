package com.tensquare.friend.service;

import com.tensquare.friend.dao.FriendDao;
import com.tensquare.friend.dao.NoFriendDao;
import com.tensquare.friend.pojo.Friend;
import com.tensquare.friend.pojo.NoFriend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FriendService {

    @Autowired
    private FriendDao friendDao;

    @Autowired
    private NoFriendDao noFriendDao;

    public int addFriend(String userid, String friendid) {
        //先判断userid到friend是否有数据，有就添加没有就返回零
        Friend friend= friendDao.findByUseridAndAndFriendid(userid,friendid);
        if(friend!=null){
            return 0;
        }

        //直接添加好友，让好友列表中userid到friendId方向type为0
        friend=new Friend();
        friend.setUserid(userid);
        friend.setFriendid(friendid);
        friend.setIslike("0");
        friendDao.save(friend);
        //直接添加好友，让好友列表中friendId到userid方向type为1
        if(friendDao.findByUseridAndAndFriendid(friendid,userid)!=null){
            //把双方的islike都改成1
            friendDao.updateIslike("1",userid,friendid);
            friendDao.updateIslike("1",friendid,userid);
        }
        return 1;
    }

    public int addNoFriend(String userid, String friendid) {
        //先判断是否是非好友
        NoFriend noFriend=noFriendDao.findByUseridAndAndFriendid(userid,friendid);

        if(noFriend!=null){
            return 0;
        }
        noFriend=new NoFriend();
        noFriend.setUserid(userid);
        noFriend.setFriendid(friendid);
        noFriendDao.save(noFriend);

        return 1;
    }

    public void deleteFriend(String userid, String friendid) {
        //删除列表中userid到friendID的这条数据
        friendDao.deleteFriend(userid,friendid);
        //更新friendID到userid islike为0
        friendDao.updateIslike("0",friendid,userid);
        //非好友表中添加数据
        NoFriend noFriend =new NoFriend();
        noFriend.setUserid(userid);
        noFriend.setFriendid(friendid);
        noFriendDao.save(noFriend);
    }
}
