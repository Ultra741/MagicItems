package me.ultradev.magicitems.items.annotations;

import me.ultradev.magicitems.items.ItemManager;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Manager {
    Class<? extends ItemManager> manager();
}
