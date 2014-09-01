package game.player;

import game.dao.PlayerDao;

import common.utils.UUID;

public class PlayerService {
	
	/**
	 * 验证玩家是否登录
	 * @param playerid
	 * @param deviceid
	 * @param token
	 * @return
	 */
	public static boolean authPlayer(int playerid,String deviceid,String token){
		boolean result=false;
		Player p=PlayerCache.getPlayer(playerid);
		if(p!=null&&p.getId()==playerid&&deviceid.equals(p.getDeviceid())&&token.equals(p.getToken())){
			result=true;
		}
		return result;
	}
	
	/**
	 * 登录
	 * @param identity
	 * @param password
	 * @param deviceid
	 * @return
	 */
	public static Player login(String identity,String password,String deviceid){
		Player p=null;
		//使用email登录
		if(identity.contains("@")){
			PlayerBean bean=PlayerDao.loadByEmail(identity);
			if(bean!=null&&password.equals(bean.getPassword())){
				bean.setDeviceid(deviceid);
				bean.setToken(UUID.createUUIDString());
				p=PlayerCache.initPlayer(bean);
			}
		}else{//使用手机登录
			PlayerBean bean=PlayerDao.loadByPhone(identity);
			if(bean!=null&&password.equals(bean.getPassword())){
				bean.setDeviceid(deviceid);
				bean.setToken(UUID.createUUIDString());
				p=PlayerCache.initPlayer(bean);
			}
		}
		return p;
	}
}