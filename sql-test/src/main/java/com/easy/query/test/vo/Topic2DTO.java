package com.easy.query.test.vo;


import lombok.Data;
import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.Navigate;

import java.time.LocalDateTime;

import com.easy.query.core.annotation.InsertIgnore;
import com.easy.query.core.enums.RelationTypeEnum;

import java.util.List;

import lombok.Data;
import com.easy.query.core.annotation.UpdateIgnore;
import com.easy.query.core.annotation.EasyAssertMessage;

/**
 * this file automatically generated by easy-query struct dto mapping
 * 当前文件是easy-query自动生成的 结构化dto 映射
 * {@link com.easy.query.test.entity.Topic2 }
 *
 * @author xuejiaming
 * @easy-query-dto schema: normal
 */
@Data
@SuppressWarnings("EasyQueryFieldMissMatch")
public class Topic2DTO {


    private String id;
    private String title;
    @Column(defaultUse = false)
    private String title1;


}
