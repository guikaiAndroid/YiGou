package com.guikai.latte.icon;

import com.joanzapata.iconify.Icon;

public enum EcIcons implements Icon {

    icon_home('\ue6b8'),
    icon_home_fill('\ue6bb'),
    icon_sort('\ue6fe'),
    icon_sort_fill('\ue682'),
    icon_discover('\ue67e'),
    icon_discover_fill('\ue6ba'),
    icon_cart('\ue6af'),
    icon_cart_fill('\ue6b9'),
    icon_my('\ue78b'),
    icon_my_fill('\ue78c'),

    icon_scan('\ue749'),
    icon_ali_pay('\ue600');

    private char character;

    EcIcons(char character) {
        this.character = character;
    }

    @Override
    public String key() {
        return name().replace('_', '-');
    }

    @Override
    public char character() {
        return character;
    }
}
