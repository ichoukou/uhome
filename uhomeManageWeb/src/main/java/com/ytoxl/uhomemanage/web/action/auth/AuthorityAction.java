package com.ytoxl.uhomemanage.web.action.auth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.ytoxl.module.core.common.utils.StringUtils;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.dataobject.MyUrole;
import com.ytoxl.module.uhome.uhomebase.dataobject.MyUser;
import com.ytoxl.module.uhome.uhomebase.service.AuthorityService;
import com.ytoxl.module.user.common.exception.YtoxlUserException;
import com.ytoxl.module.user.dataobject.Uresource;
import com.ytoxl.module.user.dataobject.User;
import com.ytoxl.module.user.service.UserService;
import com.ytoxl.uhomemanage.web.action.BaseAction;

/**
 * 后台权限管理action
 * 
 * @author anqian
 * 
 */
public class AuthorityAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(AuthorityAction.class);
	@Autowired
	private UserService userService;
	@Autowired
	private AuthorityService authorityService;
	@Value("${default_password}")
	private String default_password;
	/**
	 * 角色列表
	 */
	private List<MyUrole> urolelist;
	/**
	 * 选中角色ID
	 */
	private Integer uroleId;
	/**
	 * 所有可选资源
	 */
	private List<Uresource> allUresouceList;
	/**
	 * 当前选中角色已有资源
	 */
	private List<Uresource> currentUresouceList;
	/**
	 * 重新选中的资源
	 */
	private String uresourceIds;
	/**
	 * 重新选中的资源
	 */
	private List<MyUser> userList;
	/**
	 * 当前选中角色名
	 */
	private String uroleName;
	/**
	 * 当前选中角色已分配用户名
	 */
	private String userNames;
	/**
	 * 当前选中用户
	 */
	private MyUser myUser;
	/**
	 * 当前选中用户
	 */
	private Integer myUserId;
	/**
	 * 查看角色列表
	 * @return 
	 * @throws Exception
	 */
	public String listRoles() throws Exception {
		Integer userId = userService.getCurrentUser().getUserId();
		urolelist = authorityService.searchUroleByCreateUserId(userId);
		return "listRoles";
	}
	/**
	 * 查看子帐号列表
	 * @return 
	 * @throws Exception
	 */
	public String listUsers() throws Exception {
		Integer userId = userService.getCurrentUser().getUserId();
		userList = authorityService.listUserBycreateUserId(userId);
		return "listUsers";
	}
	/**
	 * 进入新增、编辑页面
	 * @return
	 * @throws Exception
	 */
	public String toEdit() throws Exception {
		JsonConfig jsonConfig = new JsonConfig();  
		Map<String,Object> resourceMap = new HashMap<String,Object>();
		List<Uresource> allUresouceListMenu = new ArrayList<Uresource>();
		List<Uresource> allUresouceListNoMenu = new ArrayList<Uresource>();
		List<Uresource> allUresouceList = authorityService.getUresourcesByUroleId(userService.getCurrentUser().getUroles().get(0).getUroleId());
		if(allUresouceList!=null&&allUresouceList.size()>0){
			for(Uresource u:allUresouceList){
				if(u.getUrl().equals("/admin/user/user-sellerDetailSeller")||u.getUrl().equals("/admin/auth/auth-listRoles")){//如果是帐号这个资源
					if(userService.getCurrentUser().getUroles().get(0).getUroleId()==100003){//如果当前是后台卖家 则过滤掉帐号 权限这个模块
						continue;
					}
				}
				if(u.getIsMenu().equals(Uresource.ISMENU_YES)){//菜单
					allUresouceListMenu.add(u);
				}else if(u.getIsMenu().equals(Uresource.ISMENU_NO)){//非菜单
					allUresouceListNoMenu.add(u);
				}
			}
		}
		resourceMap.put("allUresouceListMenu", allUresouceListMenu);
		resourceMap.put("allUresouceListNoMenu", allUresouceListNoMenu);
		if(uroleId!=null){
			resourceMap.put("currentUresouceList", authorityService.getUresourcesByUroleId(uroleId));
			resourceMap.put("uroleName", authorityService.getUroleByUroleId(uroleId).getUroleName());
		}
		setMessage(JSONObject.fromObject(resourceMap, jsonConfig).toString());
		return JSONMSG;
	}
	/**
	 * 删除角色
	 * @return
	 * @throws Exception
	 */
	public String deleteUrole() {
		try {
			authorityService.deleteUrole(uroleId);
		} catch (UhomeStoreException e) {
			setMessage(e.getMessage());
			logger.error("===deleteUrole()===删除角色异常！角色ID：{}",uroleId);
		}
		return JSONMSG;
	}
	/**
	 * 删除子账户
	 * @return
	 * @throws Exception
	 */
	public String deleteUser() {
			try {
				authorityService.deleteUser(myUserId);
			} catch (UhomeStoreException e) {
				setMessage(e.getMessage());
				logger.error("===deleteUser()===删除子账户异常！账户ID：{}",myUserId);
			}
		return JSONMSG;
	}
	/**
	 * 禁用子帐号
	 * @return
	 * @throws Exception
	 */
	public String updateUnableUser() {
		
		try {
			myUser.setStatus(MyUser.STATUS_UNABLE);
			authorityService.updateStatusByUserId(myUser);
		} catch (UhomeStoreException e) {
			setMessage(e.getMessage());
			logger.error("===updateUnableUser()===禁用子帐号异常！账户ID：{}",myUser.getUserId());
		}
		return JSONMSG;
	}
	/**
	 * 启用子帐号
	 * @return
	 * @throws Exception
	 */
	public String updateAbleUser() {
		
		try {
			myUser.setStatus(MyUser.STATUS_ABLE);
			authorityService.updateStatusByUserId(myUser);
		} catch (UhomeStoreException e) {
			setMessage(e.getMessage());
			logger.error("===updateAbleUser()===启用子帐号异常！账户ID：{}",myUser.getUserId());
		}
		return JSONMSG;
	}
	/**
	 * 重置密码
	 * @return
	 * @throws Exception
	 */
	public String updateDefaultPassword() throws Exception{
		userService.updateDefaultPassword(myUserId);
		return JSONMSG;
	}
	/**
	 * 保存、修改角色权限
	 * @return
	 * @throws Exception
	 */
	public String save() throws Exception{
		try {
			Integer createUserId = userService.getCurrentUser().getUserId();
			List<Integer> Ids = new ArrayList<Integer>();
			if(StringUtils.isNotBlank(uresourceIds)){
				String[] sIds = uresourceIds.split(",");
				for(String sId:sIds)
					Ids.add(Integer.valueOf(sId));
			}
			authorityService.saveOrUpdateUroleAndUresource(uroleId, uroleName, createUserId, Ids);
		} catch (UhomeStoreException e) {
			logger.error("===save()===更新角色权限异常！");
			setMessage(e.getMessage());
		}catch (YtoxlUserException e) {
			logger.error("===save()===获取当前用户信息异常！");
			setMessage(e.getMessage());
		}
		return JSONMSG;
	}
	/**
	 * 进入新增、修改子帐号页面
	 * @return
	 * @throws Exception
	 */
	public String toEditUser() throws Exception{
		Integer userId = userService.getCurrentUser().getUserId();
		urolelist = authorityService.searchUroleByCreateUserId(userId);
		if(myUserId!=null)
		myUser = authorityService.getMyUserByUserId(myUserId);
		return "toEditUser";
	}
	
	/**
	 * 保存、修改子帐号
	 * @return
	 * @throws Exception 
	 */
	public String saveUser() throws Exception{
		myUser.setPassword(default_password);
		myUser.setCreateByUserId(userService.getCurrentUser().getUserId());
		myUser.setStatus(User.STATUS_ABLE);//默认设置成激活状态
		authorityService.addUserUroles(myUser);
		return listUsers();
	}
	/**
	 * 查询用户名是否存在
	 * @return
	 * @throws Exception 
	 */
	public String checkUser(){
		if(userService.getByName(ServletActionContext.getRequest().getParameter("username"))!=null){
			logger.error("===checkUser()===登录名与子账号或者商家帐号冲突！");
			setMessage("登录名与子账号或者商家帐号冲突");
		}
		return JSONMSG;
	}
	public List<MyUrole> getUrolelist() {
		return urolelist;
	}
	public void setUrolelist(List<MyUrole> urolelist) {
		this.urolelist = urolelist;
	}
	public Integer getUroleId() {
		return uroleId;
	}
	public void setUroleId(Integer uroleId) {
		this.uroleId = uroleId;
	}
	public List<Uresource> getAllUresouceList() {
		return allUresouceList;
	}
	public void setAllUresouceList(List<Uresource> allUresouceList) {
		this.allUresouceList = allUresouceList;
	}
	public List<Uresource> getCurrentUresouceList() {
		return currentUresouceList;
	}
	public void setCurrentUresouceList(List<Uresource> currentUresouceList) {
		this.currentUresouceList = currentUresouceList;
	}
	public String getUroleName() {
		return uroleName;
	}
	public void setUroleName(String uroleName) {
		this.uroleName = uroleName;
	}
	public String getUserNames() {
		return userNames;
	}
	public void setUserNames(String userNames) {
		this.userNames = userNames;
	}
	public List<MyUser> getUserList() {
		return userList;
	}
	public void setUserList(List<MyUser> userList) {
		this.userList = userList;
	}
	public String getUresourceIds() {
		return uresourceIds;
	}
	public void setUresourceIds(String uresourceIds) {
		this.uresourceIds = uresourceIds;
	}
	public MyUser getMyUser() {
		return myUser;
	}
	public void setMyUser(MyUser myUser) {
		this.myUser = myUser;
	}
	public Integer getMyUserId() {
		return myUserId;
	}
	public void setMyUserId(Integer myUserId) {
		this.myUserId = myUserId;
	}
	
}
