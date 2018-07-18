package net.inconnection.charge.admin.account.common.annotation;

import java.lang.annotation.*;

/**
 * 清除权限管控
 *
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ClearAuth {

}
