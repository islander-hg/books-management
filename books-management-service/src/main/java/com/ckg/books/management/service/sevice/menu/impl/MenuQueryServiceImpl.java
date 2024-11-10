package com.ckg.books.management.service.sevice.menu.impl;

import cn.hutool.core.collection.CollUtil;
import com.ckg.books.management.api.menu.resp.GetMenuResp;
import com.ckg.books.management.common.constants.AuthConstant;
import com.ckg.books.management.common.domain.tree.TreeNode;
import com.ckg.books.management.common.utils.spring.BeanHelper;
import com.ckg.books.management.service.dao.entity.MenuEntity;
import com.ckg.books.management.service.dao.entity.RoleMenuEntity;
import com.ckg.books.management.service.dao.entity.UserRoleEntity;
import com.ckg.books.management.service.dao.repository.MenuRespository;
import com.ckg.books.management.service.dao.repository.RoleMenuRespository;
import com.ckg.books.management.service.dao.repository.RoleRespository;
import com.ckg.books.management.service.dao.repository.UserRespository;
import com.ckg.books.management.service.dao.repository.UserRoleRespository;
import com.ckg.books.management.service.sevice.auth.UserPermissionService;
import com.ckg.books.management.service.sevice.menu.MenuQueryService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 菜单查询操作 Service 接口实现
 *
 * @author chenkaigui
 * @date 2024/11/8
 */
@Slf4j
@Service
public class MenuQueryServiceImpl implements MenuQueryService {

    @Resource
    private MenuRespository menuRespository;

    @Resource
    private UserRoleRespository userRoleRespository;

    @Resource
    private RoleMenuRespository roleMenuRespository;

    @Resource
    private UserRespository userRespository;

    @Resource
    private RoleRespository roleRespository;

    @Resource
    private UserPermissionService userPermissionService;

    @Override
    public GetMenuResp get(Long id) {
        MenuEntity roleEntity = menuRespository.getById(id, true);
        return BeanHelper.copyProperties(roleEntity, GetMenuResp.class);
    }

    @Override
    public Set<String> findPermsByUserId(Long userId) {
        if (userPermissionService.isSuperuser(userId)) {
            return Collections.singleton(AuthConstant.ALL_PERMISSION);
        }
        return menuRespository.findPermsByIdIn(findMenuIdByUserId(userId));
    }

    @Override
    public List<TreeNode> getUserMenuTree(Long userId) {
        // 超管使用所有菜单构建树
        if (userPermissionService.isSuperuser(userId)) {
            return buildMenuTree(menuRespository.list());
        }
        // 校验用户存在性
        userRespository.getById(userId, true);
        // 构建菜单树
        return buildMenuTree(menuRespository.findByIdIn(findMenuIdByUserId(userId)));
    }

    @Override
    public List<TreeNode> getRoleMenuTree(Long roleId) {
        // 校验角色存在性
        roleRespository.getById(roleId, true);
        // 构建菜单树
        return buildMenuTree(menuRespository.findByIdIn(findMenuIdByRoleId(roleId)));
    }

    /**
     * 通过用户ID 获取 菜单ID列表
     *
     * @param userId 用户ID
     * @return 菜单ID列表
     */
    private Collection<Long> findMenuIdByUserId(Long userId) {
        List<UserRoleEntity> userRoleEntities = userRoleRespository.findByUserId(userId);
        if (CollUtil.isEmpty(userRoleEntities)) {
            return Collections.emptySet();
        }

        List<RoleMenuEntity> roleMenuEntities =
                roleMenuRespository
                        .findByRoleIdIn(userRoleEntities.stream()
                                .map(UserRoleEntity::getRoleId).collect(Collectors.toList()));
        if (CollUtil.isEmpty(roleMenuEntities)) {
            return Collections.emptySet();
        }
        return roleMenuEntities.stream().map(RoleMenuEntity::getMenuId).collect(Collectors.toSet());
    }

    /**
     * 通过角色ID 获取 菜单ID列表
     *
     * @param roleId 角色ID
     * @return 菜单ID列表
     */
    private Collection<Long> findMenuIdByRoleId(Long roleId) {
        List<RoleMenuEntity> roleMenuEntities =
                roleMenuRespository.findByRoleIdIn(Collections.singleton(roleId));
        if (CollUtil.isEmpty(roleMenuEntities)) {
            return Collections.emptySet();
        }
        return roleMenuEntities.stream().map(RoleMenuEntity::getMenuId).collect(Collectors.toSet());
    }

    /**
     * 构建菜单树
     *
     * @param menuEntities 菜单信息列表
     * @return 菜单树
     */
    private List<TreeNode> buildMenuTree(List<MenuEntity> menuEntities) {
        if (CollUtil.isEmpty(menuEntities)) {
            return Collections.emptyList();
        }

        List<TreeNode> treeNodes = new ArrayList<>();
        List<Long> menuIds =
                menuEntities.stream().map(MenuEntity::getId).collect(Collectors.toList());

        for (Iterator<MenuEntity> iterator = menuEntities.iterator(); iterator.hasNext(); ) {
            MenuEntity menuEntity = iterator.next();
            // 遍历顶层父节点
            if (!menuIds.contains(menuEntity.getParentId())) {
                TreeNode treeNode = new TreeNode().setId(menuEntity.getId())
                        .setName(menuEntity.getName());
                buildMenuTree(menuEntities, treeNode);
                treeNodes.add(treeNode);
            }
        }
        return treeNodes;
    }

    /**
     * 构建菜单树
     *
     * @param menuEntities 菜单信息列表
     * @param parentNode   当前父节点信息
     */
    private void buildMenuTree(List<MenuEntity> menuEntities, TreeNode parentNode) {
        List<TreeNode> childNodeList = getChildList(menuEntities, parentNode);
        parentNode.setChildren(childNodeList);
        for (TreeNode childNode : childNodeList) {
            buildMenuTree(menuEntities, childNode);
        }
    }

    /**
     * 获取子节点信息列表
     *
     * @param menuEntities 菜单信息列表
     * @param parentNode   父节点信息
     * @return 子节点信息列表
     */
    private List<TreeNode> getChildList(
            List<MenuEntity> menuEntities, TreeNode parentNode) {
        List<TreeNode> treeNodes = new ArrayList<>();
        for (MenuEntity menuEntity : menuEntities) {
            if (parentNode.getId().equals(menuEntity.getParentId())) {
                treeNodes.add(new TreeNode()
                        .setId(menuEntity.getId()).setName(menuEntity.getName()));
            }
        }
        return treeNodes;
    }

}
