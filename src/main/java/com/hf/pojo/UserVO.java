package com.hf.pojo;

import com.hf.base.ErrorCode;
import com.hf.controller.ConfigController;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
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
    @NotNull(message = "id不可为空")
    private Long id;

    /**
    * @Author xiehongfei
    * @Description: 名称
    * @Param
    * @Return
    **/
    @NotNull(message = "名称不可为空", groups = {ErrorCode.class})
    private String name;

    /**
    * @Author xiehongfei
    * @Description:
    * @Param 年龄
    * @Return
    **/
    private Integer age;
}
