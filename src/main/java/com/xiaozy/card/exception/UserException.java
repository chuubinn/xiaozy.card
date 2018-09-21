package com.xiaozy.card.exception;

import com.xiaozy.card.enums.ResultEnum;
import lombok.Getter;

@Getter
public class UserException extends RuntimeException{

    private Integer code;

    public UserException(ResultEnum resultEnum){
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }

    public UserException(String msg,Integer code){
        super(msg);
        this.code = code;
    }
}
