package com.ytoxl.module.uhome.uhomebase.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ytoxl.module.core.common.utils.StringUtils;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.dataobject.MyUrole;
import com.ytoxl.module.uhome.uhomebase.dataobject.MyUser;
import com.ytoxl.module.uhome.uhomebase.mapper.MyUresourceMapper;
import com.ytoxl.module.uhome.uhomebase.mapper.MyUroleMapper;
import com.ytoxl.module.uhome.uhomebase.mapper.MyUserMapper;
import com.ytoxl.module.uhome.uhomebase.service.AuthorityService;
import com.ytoxl.module.user.common.exception.YtoxlUserException;
import com.ytoxl.module.user.dataobject.Uresource;
import com.ytoxl.module.user.dataobject.User;
import com.ytoxl.module.user.security.CustomInvocationSecurityMetadataSourceService;
import com.ytoxl.module.user.service.UserService;


@Service
public class AuthorityServiceImpl implements AuthorityService {

	private static Logger logger = LoggerFactory.getLogger(AuthorityServiceImpl.class);
	@Autowired
	private MyUserMapper<MyUser> myUserMapper;
	@Autowired
	private MyUroleMapper<MyUrole> myUroleMapper;
	@Autowired
	private MyUresourceMapper<Uresource> myUresourceMapper;
	@Autowired
	private UserService userService;
	@Autowired
	private CustomInvocationSecurityMetadataSourceService customSecurityMetadataSource ;
	
	public static final String A_S_ERR_0 = "参数错误";
	public static final String A_S_ERR_1 = "角色名已存在";
	public static final String A_S_ERR_2 = "存在子账户";
	
	@Override
	public List<MyUrole> searchUroleByCreateUserId(Integer userId) throws UhomeStoreException{
		if(userId==null){
			logger.error("===searchUroleByCreateUserId()===:{}",A_S_ERR_0);
			throw new UhomeStoreException(A_S_ERR_0);
		}
		List<MyUrole> myUroleList = myUroleMapper.searchUroleByCreateUserId(userId);
		for(MyUrole urole:myUroleList){
			urole.setUresourceList(myUresourceMapper.getUresourcesByUroleId(urole.getUroleId()));
			//根据角色id查询所分配用户id
			List<Integer> userIds = myUroleMapper.listUserIdsByUroleId(urole.getUroleId());
			if(userIds!=null&&userIds.size()>0){
				StringBuffer names = new StringBuffer();
				for(Integer id:userIds){
					names.append(myUserMapper.get(id).getUsername()+",");
				}
				if(names.length()>0){
					urole.setNames(names.substring(0,names.length()-1));
				}
			}
		}
		return myUroleList;
	}

	@Override
	public List<Uresource> getUresourcesByUroleId(Integer uroleId)
			throws UhomeStoreException {
		if(uroleId==null){
			logger.error("===getUresourcesByUroleId()===:{}",A_S_ERR_0);
			throw new UhomeStoreException(A_S_ERR_0);
		}
		return myUresourceMapper.getUresourcesByUroleId(uroleId);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void saveOrUpdateUroleAndUresource(Integer uroleId,
			String uroleName, Integer createUserId, List<Integer> uresourceIds)
			throws UhomeStoreException {
		if(createUserId==null || StringUtils.isBlank(uroleName)){
			logger.error("===saveOrUpdateUroleAndUresource()===:{}",A_S_ERR_0);
			throw new UhomeStoreException(A_S_ERR_0);
		}
		
		MyUrole urole = new MyUrole();
		urole.setUroleId(uroleId);
		urole.setUroleName(uroleName);
		urole.setStatus(MyUrole.STATUS_ABLE);
		urole.setCreateByUserId(createUserId);
		//检查角色名是否存在
		MyUrole myUrole = myUroleMapper.checkUroleName(urole);
		if(myUrole!=null){
			logger.error("===saveOrUpdateUroleAndUresource()===:{}",A_S_ERR_1);
			throw new UhomeStoreException(A_S_ERR_1);
		}
		if(uroleId==null){//新增角色
			myUroleMapper.add(urole);
		}else{//修改角色
			myUroleMapper.update(urole);
			//删除之前的资源对应关系
			myUresourceMapper.deleteUroleUresourceByUroleId(uroleId);
		}
		if(uresourceIds!=null&&uresourceIds.size()>0)
			//保存对应资源
			myUresourceMapper.addUroleUresources(urole.getUroleId(), uresourceIds);
		//刷新资源
		customSecurityMetadataSource.init();
	}
	@Override
	public List<MyUser> listUserBycreateUserId(Integer userId) throws UhomeStoreException{
		if(userId==null){
			logger.error("===listUserBycreateUserId()===:{}",A_S_ERR_0);
			throw new UhomeStoreException(A_S_ERR_0);
		}
		List<MyUser> userList = myUserMapper.listUserBycreateUserId(userId);
		if(userList!=null&&userList.size()>0){
			for(MyUser user:userList){
				user.setUrole(myUroleMapper.getUroleByuserId(user.getUserId()));
			}
		}
		return userList;
	}

	@Override
	public MyUrole getUroleByUroleId(Integer uroleId)
			throws UhomeStoreException {
		if(uroleId==null){
			logger.error("===getUroleByUroleId()===:{}",A_S_ERR_0);
			throw new UhomeStoreException(A_S_ERR_0);
		}
		return myUroleMapper.get(uroleId);
	}
	@Override
	public MyUser getMyUserByUserId(Integer userId)throws UhomeStoreException {
		if(userId==null){
			logger.error("===getMyUserByUserId()===:{}",A_S_ERR_0);
			throw new UhomeStoreException(A_S_ERR_0);
		}
		MyUser user = myUserMapper.get(userId);
		user.setUrole(myUroleMapper.getUroleByuserId(userId));
		return user;
	}
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void addUserUroles(MyUser myUser) throws UhomeStoreException{
		if(myUser==null){
			logger.error("===addUserUroles()===:{}",A_S_ERR_0);
			throw new UhomeStoreException(A_S_ERR_0);
		}
		Integer userId = myUser.getUserId();
		if(myUser.getUserId()==null){//添加新用户
			if(userService.getByName(myUser.getUsername())!=null){
				logger.error("===addUserUroles()===:登录名与子账号或者商家帐号冲突");
				throw new UhomeStoreException("登录名与子账号或者商家帐号冲突");
			}
			User user = new User();
			user.setUsername(myUser.getUsername());
			user.setPassword(myUser.getPassword());
			user.setCreateByUserId(myUser.getCreateByUserId());
			try {
				userService.saveUser(user);
				userId = user.getUserId();
			} catch (YtoxlUserException e) {
				logger.error("===addUserUroles()===获取当前用户信息异常！");
				throw new UhomeStoreException(e.getMessage());
			}
		}
		//更新用户角色
		if(myUser.getUserId()!=null)
			myUserMapper.delUserUroles(myUser.getUserId());
		myUserMapper.addUserUroles(myUser.getUrole().getUroleId(),userId);
		//刷新资源
		customSecurityMetadataSource.init();
	}
	@Override
	public void deleteUrole(Integer uroleId)throws UhomeStoreException{
		if(uroleId==null){
			logger.error("===deleteUrole()===:{}",A_S_ERR_0);
			throw new UhomeStoreException(A_S_ERR_0);
		}
		List<Integer> userIds = myUroleMapper.listUserIdsByUroleId(uroleId);
		if(userIds!=null&&userIds.size()>0){
			logger.error("===deleteUrole()===:{}",A_S_ERR_2);
			throw new UhomeStoreException(A_S_ERR_2);
		}
		myUroleMapper.del(uroleId);
		//刷新资源
		customSecurityMetadataSource.init();
	}
	@Override
	public void deleteUser(Integer userId)throws UhomeStoreException{
		if(userId==null){
			logger.error("===deleteUrole()===:{}",A_S_ERR_0);
			throw new UhomeStoreException(A_S_ERR_0);
		}
		myUserMapper.delUserUroles(userId);
		myUserMapper.del(userId);
		//刷新资源
		customSecurityMetadataSource.init();
	}
	@Override
	public void updateStatusByUserId(MyUser myUser)throws UhomeStoreException{
		if(myUser==null){
			logger.error("===updateStatusByUserId()===:{}",A_S_ERR_0);
			throw new UhomeStoreException(A_S_ERR_0);
		}
		myUserMapper.updateStatusByUserId(myUser);
		//刷新资源
		customSecurityMetadataSource.init();
	}
}
