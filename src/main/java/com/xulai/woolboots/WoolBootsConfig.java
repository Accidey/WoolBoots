package com.xulai.woolboots;

import net.neoforged.neoforge.common.ModConfigSpec;

public final class WoolBootsConfig {

    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    static {
        SPRINT_AUDIBLE = BUILDER
                .comment(
                        "疾跑是否能被听到",
                        "Whether sprinting can be heard",
                        "开启后疾跑时仍会产生脚步振动，被监守者与幽匿感测体察觉。",
                        "关闭后疾跑脚步也会静音。",
                        "When enabled, sprinting still creates step vibrations that wardens and sculk sensors can hear.",
                        "When disabled, sprinting steps are silenced as well.",
                        "Default: true / 默认：true"
                )
                .define("sprintAudible", true);

        BUILDER.comment(" ");

        FALL_AUDIBLE = BUILDER
                .comment(
                        "摔落是否能被听到",
                        "Whether falling can be heard",
                        "开启后落地时仍会产生振动，被监守者与幽匿感测体察觉。",
                        "关闭后落地振动也会静音。",
                        "When enabled, landing still creates vibrations that wardens and sculk sensors can hear.",
                        "When disabled, landing vibrations are silenced as well.",
                        "Default: true / 默认：true"
                )
                .define("fallAudible", true);
    }

    public static final ModConfigSpec.BooleanValue SPRINT_AUDIBLE;

    public static final ModConfigSpec.BooleanValue FALL_AUDIBLE;

    public static final ModConfigSpec SPEC = BUILDER.build();

    private WoolBootsConfig() {
    }
}