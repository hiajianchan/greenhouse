package edu.haut.greenhouse.bean.user;
/**
 * @Description 用户详细信息
 * @author chen haijian
 * @date 2018年4月25日
 * @version 1.0
 */

import edu.haut.greenhouse.pojo.user.User;
import edu.haut.greenhouse.pojo.user.UserInfo;

public class UserAndInfo {

	private User user;
	
	private UserInfo info;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public UserInfo getInfo() {
		return info;
	}

	public void setInfo(UserInfo info) {
		this.info = info;
	}

	@Override
	public String toString() {
		return "UserAndInfo [user=" + user + ", info=" + info + "]";
	}
	
	
}
