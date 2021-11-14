package com.hf.pojo;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @ClassName: UserVO
 * @author: xiehongfei
 * @description:
 **/
@Data
@ToString
public class UserVO implements Serializable {

    /**
    * @Author xiehongfei
    * @Description:
    * @Param id
    * @Return
    **/
    private Long id;

    /**
    * @Author xiehongfei
    * @Description: 名称
    * @Param
    * @Return
    **/
    private String name;

    /**
    * @Author xiehongfei
    * @Description:
    * @Param 年龄
    * @Return
    **/
    private Integer age;
}
