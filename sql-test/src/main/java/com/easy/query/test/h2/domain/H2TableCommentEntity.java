package com.easy.query.test.h2.domain;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.h2.domain.proxy.H2TableCommentEntityProxy;
import lombok.Data;

/**
 * create time 2026/6/14
 * H2 表注释回归测试实体，用于验证表级注释生成为独立的 COMMENT ON TABLE 语句
 */
@Data
@Table(value = "t_h2_table_comment", comment = "H2表注释测试")
@EntityProxy
public class H2TableCommentEntity implements ProxyEntityAvailable<H2TableCommentEntity, H2TableCommentEntityProxy> {

    @Column(primaryKey = true, comment = "主键ID")
    private String id;

    @Column(comment = "名称")
    private String name;
}
