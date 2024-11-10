package com.ckg.books.management.service.sevice.menu;

import com.ckg.books.management.api.menu.req.CreateMenuReq;
import com.ckg.books.management.api.menu.req.UpdateMenuReq;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

/**
 * 菜单新增、变更操作 Service 接口
 *
 * @author chenkaigui
 * @date 2024/11/8
 */
@Validated
public interface MenuOperateService {

    /**
     * 创建菜单
     *
     * @param createReq 创建请求信息
     */
    void create(@Valid CreateMenuReq createReq);

    /**
     * 修改菜单
     *
     * @param id        菜单ID
     * @param updateReq 修改请求信息
     */
    void update(@NotNull Long id, @Valid UpdateMenuReq updateReq);

    /**
     * 删除菜单
     *
     * @param id 菜单ID
     */
    void delete(@NotNull Long id);

}
