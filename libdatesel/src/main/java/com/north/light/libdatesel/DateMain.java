package com.north.light.libdatesel;

import com.north.light.libdatesel.api.DateMainApi;
import com.north.light.libdatesel.v1.DateMainV1;
import com.north.light.libdatesel.v2.DateMainV2;

import java.io.Serializable;

/**
 * @Author: lzt
 * @Date: 2022/1/7 8:02
 * @Description:
 */
public class DateMain implements Serializable {

    //当前使用类型:1--v1版本 2--v2版本
    private static int current = 1;

    /**
     * 获取v1版本obj
     */
    public static DateMainApi getDateInstance() {
        if (current == 1) {
            return DateMainV1.getInstance();
        } else {
            return DateMainV2.getInstance();
        }
    }

}
