package gov.nih.nci.cananolab.security.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;

import gov.nih.nci.cananolab.security.Group;
import gov.nih.nci.cananolab.util.StringUtils;

@Component("groupDao")
public class GroupDaoImpl extends JdbcDaoSupport implements GroupDao
{
	protected Logger logger = Logger.getLogger(GroupDaoImpl.class);
	
	@Autowired
	private DataSource dataSource;
	
	private static final String FETCH_GROUP_SQL = "SELECT g.id, g.group_name, g.group_description, g.created_by FROM groups g where g.group_name = ?";
	private static final String FETCH_GROUP_BY_ID_SQL = "SELECT g.id, g.group_name, g.group_description, g.created_by FROM groups g where g.id = ?";	
	private static final String INSERT_GROUP_SQL = "INSERT INTO groups (id, group_name, group_description) VALUES (?,?)";
	private static final String INSERT_GROUP_MEMBER_SQL = "INSERT INTO group_members (group_id, username) VALUES (?,?)";
	private static final String UPDATE_GROUP_SQL = "UPDATE groups SET group_description = ? WHERE id = ?";
	private static final String FETCH_GROUP_MEMBERS_SQL = "SELECT gm.username FROM group_members gm where gm.id = ?";
	private static final String DEL_GROUP_MEMBERS_SQL = "DELETE FROM group_members gm where gm.id = ?";
	private static final String DEL_GROUP_SQL = "DELETE FROM groups g where g.id = ?";
	private static final String FETCH_GROUPS_FOR_USER_SQL = "SELECT g.id, g.group_name, g.group_description, g.created_by FROM groups g, group_members gm " +
															"where g.id = gm.group_id and gm.username = ?";
	
	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
	}

	@Override
	public Group getGroupByName(String groupName)
	{
		logger.debug("Fetching group by name: " + groupName);
		
		Group grp = null;
		
		if (!StringUtils.isEmpty(groupName))
		{
			Object[] params = new Object[] {groupName};
			grp = (Group) getJdbcTemplate().queryForObject(FETCH_GROUP_SQL, params, new GroupMapper());
		}
		return grp;
	}

	@Override
	public Group getGroupById(Long groupid)
	{
		logger.debug("Fetching group : " + groupid);
		
		Group grp = null;
		
		if (groupid != null && groupid > 0)
		{
			Object[] params = new Object[] {groupid};
			grp = (Group) getJdbcTemplate().queryForObject(FETCH_GROUP_BY_ID_SQL, params, new GroupMapper());
		}
		return grp;
	}

	@Override
	public int insertGroup(Group newGroup)
	{
		Object[] args = {newGroup.getGroupName(), newGroup.getGroupDesc()};
		int status = this.getJdbcTemplate().update(INSERT_GROUP_SQL, args);
		return status;
	}

	@Override
	public int inserGroupMember(Long groupId, String userName)
	{
		Object[] args = {groupId, userName};
		int status = this.getJdbcTemplate().update(INSERT_GROUP_MEMBER_SQL, args);
		return status;
	}

	@Override
	public int updateGroup(Long groupId, String groupDesc)
	{
		Object[] args = {groupId, groupDesc};
		int status = this.getJdbcTemplate().update(UPDATE_GROUP_SQL, args);
		return status;
		
	}

	@Override
	public List<String> getGroupMembers(Long groupId)
	{
		logger.debug("Fetching members of group ID: " + groupId);
		
		List<String> memberList = new ArrayList<String>();
		
		if (groupId != null && groupId > 0)
		{
			Object[] params = new Object[] {groupId};
			memberList = getJdbcTemplate().queryForList(FETCH_GROUP_MEMBERS_SQL, String.class, params);
		}
		return memberList;
	}

	@Override
	public int removeGroupMembers(Long groupId)
	{
		Object[] args = {groupId};
		int status = this.getJdbcTemplate().update(DEL_GROUP_MEMBERS_SQL, args);
		return status;
	}

	@Override
	public int deleteGroup(Long groupId) {
		Object[] args = {groupId};
		int status = this.getJdbcTemplate().update(DEL_GROUP_SQL, args);
		return status;
	}

	@Override
	public List<Group> getGroupsWithMember(String username)
	{
		logger.debug("Fetching groups with user: " + username + " as member.");
		
		List<Group> groups = new ArrayList<Group>();
		
		if (!StringUtils.isEmpty(username))
		{
			Object[] params = new Object[] {username};
			groups = getJdbcTemplate().query(FETCH_GROUPS_FOR_USER_SQL, params, new GroupMapper());
		}
		return groups;
	}
	
	private static final class GroupMapper implements RowMapper
	{
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			Group group = new Group();
			group.setId(rs.getLong("ID"));
			group.setGroupName(rs.getString("GROUP_NAME"));
			group.setGroupDesc(rs.getString("GROUP_DESCRIPTION"));
			group.setCreatedBy(rs.getString("CREATED_BY"));
			
			return group;
		}
	}

}
