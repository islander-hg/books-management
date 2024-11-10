package com.ckg.books.management.service.dao.utils;

import cn.hutool.core.collection.CollUtil;
import com.ckg.books.management.common.utils.security.SecurityUtils;
import com.ckg.books.management.service.dao.entity.BaseEntity;
import java.util.Date;
import java.util.List;

/**
 * Entity 工具类
 *
 * @author chenkaigui
 * @date 2024/5/24
 */
public class EntityHelper {

    /**
     * 填充基础字段值
     *
     * @param entity       entity
     * @param fromCreateOp 是否来着创建操作
     * @return entity
     */
    public static <T extends BaseEntity> T fillBaseFieldValue(T entity, boolean fromCreateOp) {
        if (null == entity) {
            return entity;
        }

        String userId = String.valueOf(SecurityUtils.getLoginUserId());
        Date curDate = new Date();
        doFillBaseFieldValue(entity, userId, curDate, fromCreateOp);
        return entity;
    }

    /**
     * 填充基础字段值
     *
     * @param entities     entity 列表
     * @param fromCreateOp 是否来着创建操作
     * @return entities
     */
    public static List<?> fillBaseFieldValue(
            List<? extends BaseEntity> entities, boolean fromCreateOp) {
        if (CollUtil.isEmpty(entities)) {
            return entities;
        }
        String userId = String.valueOf(SecurityUtils.getLoginUserId());
        Date curDate = new Date();

        for (BaseEntity entity : entities) {
            doFillBaseFieldValue(entity, userId, curDate, fromCreateOp);
        }
        return entities;
    }

    private static void doFillBaseFieldValue(
            BaseEntity entity, String userId, Date curDate, boolean fromCreateOp) {
        entity.setUpdater(userId).setUpdateTime(curDate);
        if (fromCreateOp) {
            entity.setCreator(userId).setCreateTime(curDate);
        }
    }
}
