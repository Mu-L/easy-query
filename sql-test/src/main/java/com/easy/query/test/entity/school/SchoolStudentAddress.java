package com.easy.query.test.entity.school;

import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.enums.RelationTypeEnum;
import lombok.Data;

/**
 * create time 2023/7/16 11:28
 * 文件说明
 *
 * @author xuejiaming
 */
@Table("school_student_address")
@Data
public class SchoolStudentAddress {
    private String id;
    private String studentId;
    private String address;
    @Navigate(value = RelationTypeEnum.ManyToOne,selfProperty = "studentId",targetProperty = "id")
    private SchoolStudent schoolStudent;
}