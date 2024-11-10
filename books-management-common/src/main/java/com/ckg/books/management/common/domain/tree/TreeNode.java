package com.ckg.books.management.common.domain.tree;

import java.util.List;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 树节点信息
 *
 * @author chenkaigui
 * @date 2024/11/9
 */
@Data
@Accessors(chain = true)
public class TreeNode {

    /**
     * 节点ID
     */
    private Long id;

    /**
     * 节点名称
     */
    private String name;

    /**
     * 子节点
     */
    private List<TreeNode> children;
}
