package com.xulai.woolboots;

import net.neoforged.neoforge.common.ModConfigSpec;

public final class WoolBootsConfig {

    public static final ModConfigSpec SPEC;

    public static final ModConfigSpec.BooleanValue SPRINT_AUDIBLE;

    public static final ModConfigSpec.BooleanValue FALL_AUDIBLE;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        SPRINT_AUDIBLE = builder
                .comment("Whether sprinting can be heard. When false, sprinting steps are silenced as well.")
                .define("sprint_audible", true);
        FALL_AUDIBLE = builder
                .comment("Whether falling can be heard. When false, landing vibrations are silenced as well.")
                .define("fall_audible", true);
        SPEC = builder.build();
    }

    private WoolBootsConfig() {
    }
}