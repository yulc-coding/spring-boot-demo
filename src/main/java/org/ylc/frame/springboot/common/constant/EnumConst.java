package org.ylc.frame.springboot.common.constant;

/**
 * 枚举常量
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2019/7/13 20:12
 */
public class EnumConst {

    /**
     * 性别
     */
    public enum UserGenderEnum {

        MEN("1", "男"),
        WOMEN("2", "女"),
        OTHER("0", "男");

        private String code;

        private String value;

        public static String getValueByCode(String code) {
            for (UserGenderEnum userGenderEnum : values()) {
                if (userGenderEnum.code.equals(code)) {
                    return userGenderEnum.value;
                }
            }
            return null;
        }

        UserGenderEnum(String code, String value) {
            this.code = code;
            this.value = value;
        }

        public String getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 员工状态
     */
    public enum UserStateEnum {

        DISABLED(0, "未启用"),
        ENABLED(1, "启用"),
        FROZEN(99, "冻结");

        private Integer code;

        private String value;

        public static String getValueByCode(Integer code) {
            for (UserStateEnum userStateEnum : values()) {
                if (userStateEnum.code.equals(code)) {
                    return userStateEnum.value;
                }
            }
            return null;
        }

        UserStateEnum(Integer code, String value) {
            this.code = code;
            this.value = value;
        }

        public Integer getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }
    }


}
